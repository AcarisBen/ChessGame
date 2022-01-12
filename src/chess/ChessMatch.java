package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch { // Regras do jogo

	private int turn; 
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>(); //Instacia uma lista para controlar as peças restantes do tabuleiro
	private List<Piece> capturedPieces = new ArrayList<>(); // Instancia uma lista para controlar as peças capturadas.

	
	public ChessMatch() { // Quem sabe a dimensão do tabuleiro é a class "ChessMatch"
		board = new Board(8, 8); // Inicia a partida com este tabuleiro.
		turn = 1; // O jogo inicia no turno 1.
		currentPlayer = Color.WHITE; // O primeiro jogador a iniciar o jogo é o branco
		check = false; // Por padrão, um boolean sempre é falso, mas pode-se enfatizar no constructor
		initialSetup(); // Chama a configuração inicial do tabuleiro
		}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck () { // expõe a propriedade de xeque para que possa ser alcançado pelo programa principal 
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}

	public ChessPiece getEnPassantVulnerable() { // Getter do en Passant começa null por não ter nenhuma peça vulnerável para o próximo turno, então não inicia ela no constructor.
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}

	public ChessPiece[][] getPieces() { // Retorna a matriz de peças de xadrez correspondente a partida.
	
	// O "Board" tem uma matriz do tipo "Piece", mas o método retorna o "ChessPiece" (Camada de xadrez).
	// Para o programa, não deve ser liberado peças do tipo "Piece", mas do tipo "ChessPiece". Pois é um desenvolvimento em camadas.
	// O programa deve enxergar a peça de xadrez, e não a peça interna do tabuleiro. Então, deve transformar o "Piece" e ChessPiece.
	ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		
		for (int i=0; i<board.getRows(); i++) { // Percorre a matriz de peças do tabuleiro e pra cada peça, será feita um downcasting para "ChessPiece".
			for  (int j=0; j<board.getColumns(); j++) {
				mat [i][j] = (ChessPiece)board.piece(i, j);
				//Downcasting para ChessPiece. O programa interpreta que a peça é de "ChessPiece" e não só "Piece"
			}
		}
		return mat; // Por fim, este código retorna a matriz de peças da partida de xadrez.
	}
	
	public boolean [][]possibleMoves(ChessPosition sourcePosition) { // Avisa ao jogador quais posições a peça selecionada poderá ir.
		Position position = sourcePosition.toPosition(); // converte a posição do xadrez para uma posição normal
		validateSourcePosition(position); // valida a posição de origem assim que o jogador informar a peça
		return board.piece(position).possibleMoves(); // Retorna os movimentos possíveis da posição
		
	}
	public ChessPiece performChessMove (ChessPosition sourcePosition, ChessPosition targetPosition) {
	// 	Move a peça da posição de origem para a posição de destino e se for o caso, tambem a posição de captura
		Position source = sourcePosition.toPosition(); //Converte posiçoes de origem para posições da matriz		
		Position target = targetPosition.toPosition(); //Converte posiçoes de destino para posições da matriz		
		validateSourcePosition(source); // Valida se a posição de origem havia uma peça. Chama o método "validateSourcePosition"
		validateTargetPosition(source, target); // Valida se a posição de destino, para saber se o programa está colocando a peça na posição final corretamente. Chama o método "validateTargetPosition"
		Piece capturedPiece = makeMove(source, target); //Realiza o movimento da peça, baseado na posição de origem e destino.
		
		
		if (testCheck(currentPlayer)) { //Se o jogador lançou um movimento e ficou em xeuqe, deve desfazer o movimento e lançar uma exceção
			undoMove(source, target, capturedPiece);// Testar se o movimento deixou o jogador atual em xeque
			throw new ChessException("You putted yourself in check!");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target); // Variável para a peça que foi para o "target" (Movimento en Passant)
		
		// #specialMove promotion - testa a lógica para fazer o peão ser promovido
		promoted = null; // faz com que toda vez seja feito um novo teste.
		if (movedPiece instanceof Pawn) { // Se a peça movida for uma instancia do pião 
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				// Testa se a peça movida (pião) é da cor branca e se ela chegou até o final (da matriz seria posíção 0) ou se é um pião preto e chegou ao final (posição 7 da  matriz)
			promoted = (ChessPiece)board.piece(target); // a variável "promoted" receberá a peça que estiver na posição de destino. precisa ter uma peça nessa posição para que haja a pormoção. Se não o valor da variável promoted será null.
			promoted = replacePromotedPiece("Q"); // a variável "promoted" receberá a letra "Q" como argumento do método  método "replacePromotedPiece"
			}
			
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false; // Testa se o opponent está em xeque.
		// se o teste de xeque do oponente for "true" então o opponente está em xeque, se não retorna "false"
		if (testCheckMate(opponent(currentPlayer))) {//testa se a jogada feita, deixa o outro jogador em xeque-mate.
			checkMate = true; //Passa a variável checkMate para true. O jogador recebeu xeque-mate
		} else {
			nextTurn(); // Chama o próximo turno.
		}
		
		// #specialMove en passant - Testa se a peça é um peão que moveu duas casas. Neste caso, esta peça está vulnerável ao en Passant.
		if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {// Teste se a peça movida (movedPiece) é um peão e a diferença de linhas foi 2 pra mais ou pra menos, significa que foi um  movimento inicial de peão de duas casas. 
		 enPassantVulnerable = movedPiece; // o peão fica vulnerável a tomar o en passant
		} else { // senão for o caso
			enPassantVulnerable = null; // nenhuma peça está vulnerável
		}
	
		return (ChessPiece) capturedPiece; // Retorna a peça capturada, com DownCasting da peça capturada do tipo "ChessPiece"
		}
	
	public ChessPiece replacePromotedPiece(String type) { // Método para trocar o pião por uma outra peça no movimento promoção
		if (promoted == null) { // Quando a variável "promoted" for nula, não poderá trocar a peça promovida. 
			 throw new IllegalStateException("There is no piece to be promoted"); // Esse método é chamado qdo o usuário fizer a escolha da peça. Então tem que ter peça indicando que tem peça a ser promovida.
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) { //As letras "B, N, R, e Q" são as únicas possíveis. A operação equals serve para comparar se um String é igual a outro. Já que o String é um tipo classe e não o tipo primitivo
			// Antes estava assim, mas não foi tratada esta exceção: throw new InvalidParameterException("Invalid type for promotion");
			return promoted; //Retorna o valor da variável "promoted" que, por padrão, é a rainha.
		}
		
		Position pos = promoted.getChessPosition().toPosition(); // Chama a posição da peça promovida
		Piece p = board.removePiece(pos); // A variável "p" guarda a peça que foi removida na posição "pos"
		piecesOnTheBoard.remove(p); // Remove a peça "p" da lista de peças do tabuleiro
		ChessPiece newPiece = newPiece(type, promoted.getColor()); // Instancia uma nova peça comforme o "type"
		board.placePiece(newPiece, pos); // coloca uma nova peça no lugar da peça removida.
		piecesOnTheBoard.add(newPiece); // adiciona a nova peça para a lista de peças do tabuleiro
		
		return newPiece; // retorna a nova peça instanciada
	}
	
	private ChessPiece newPiece(String type, Color color) { // Método para criar uma nova peça baseada no String solicitado e na cor do jogador que fizer a promoção
		if (type.equals("B")) return new Bishop(board, color); // Se colocar "B" será um novo bispo
		if (type.equals("N")) return new Knight(board, color); // Se colocar "N" será um novo cavalo
		if (type.equals("Q")) return new Queen(board, color); //Se colocar "Q" será um novo rainha
		return new Rook(board, color); // Se não colocar nenhuma das outras letras, será colocado uma nova torre
		}
	
	private Piece makeMove(Position source, Position target) { //Lógica de realizar um movimento baseado na posição de origem e de destino
		ChessPiece p = (ChessPiece)board.removePiece(source);//Faz um DownCasting para "ChessPiece" para poder acrescentar a contagem dos movimentos. Era assim: Piece p = board.removePiece(source); // Retira a peça na posição de origem
		p.increaseMoveCount();//incrementa a contagem dos movimentos das peças
		Piece capturedPiece = board.removePiece(target); //Remove a peça que está na posição de destino (Peça capturada)
		board.placePiece(p, target); // Coloca a peça "p" na posição da peça de destino.
		
		if (capturedPiece != null) { // se capturou uma peça
			piecesOnTheBoard.remove(capturedPiece); // remove a peça capturada da lista de peças no tabuleiro.
			capturedPieces.add(capturedPiece); // adiciona a peça capturada na lista de peças capturadas
		}
		// #specialmove castling kingside rook - tratar o movimento do roque pequeno mexendo a torre manualmente
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { //Se a peça "p" for uma instancia de rei e a posição da peça no destino for = a duas casas depois da posição de origem à direira.
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // Posição de origem da torre antes do Roque
			Position targetT = new Position(source.getRow(), source.getColumn() + 1); // Posição de destino da torre após o Roque
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT); // Remove a torre da posição dela de origem
			board.placePiece(rook, targetT); // coloca a torre na posição de destino dela 
			rook.increaseMoveCount(); // conta um movimento a mais para a torre
		}
			
		// #specialmove castling queenside rook - tratar o movimento do roque grande mexendo a torre manualmente
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) { //Se a peça "p" for uma instancia de rei e a posição da peça no destino for = a duas casas depois da posição de origem à esquerda.
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // Posição de origem da torre antes do Roque
			Position targetT = new Position(source.getRow(), source.getColumn() - 1); // Posição de destino da torre após o Roque
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT); // Remove a torre da posição dela de origem
			board.placePiece(rook, targetT); // coloca a torre na posição de destino dela 
			rook.increaseMoveCount(); // conta um movimento a mais para a torre
		}
		
		// specialMove en passant - Como este movimento é um movimento não natural, deve ser feito manualmente. 
		if (p instanceof Pawn) // testa se a peça "p" é uma instancia de peão
			if (source.getColumn() != target.getColumn() && capturedPiece == null) { // se o movimento na coluna de origem da peça é diferente do movimento na coluna de destino da peça (peão andou em diagonal) e não capturou nenhuma peça neste movimento
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {// se a cor da peça que moveu é a cor branca
					pawnPosition = new Position(target.getRow() + 1, target.getColumn()); // posição "pawnPosition" será a posição abaixo da peça branca
				} else { // se a cor da peça que moveu é a cor preta
					pawnPosition = new Position(target.getRow() - 1, target.getColumn()); // posição "pawnPosition" será a posição acima da peça preta
				}
				capturedPiece = board.removePiece(pawnPosition);// chama a "capturedPiece" de "pawnPosition" e tira ela do jogo
				capturedPieces.add(capturedPiece); // adiciona a "pawnPosition" que agora é "capturedPiece" na lista de "capturedPieces"
				piecesOnTheBoard.remove(capturedPiece); // remove a "pawnPosition" que é "capturedPiece" da lista de "piecesOnTheBoard"
				
			}

			return capturedPiece; //retorna a peça capturada.
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) { //Método para desfazer o movimento makeMove, para não por o rei em xeque.
		ChessPiece p = (ChessPiece)board.removePiece(target);// Faz um "DownCasting" para "ChessPiece" para poder diminuir a contagem dos movimentos. Era assim: Piece p = board.removePiece(target); // Cria uma peça para remove-la da posição de destino
		p.decraseMoveCount(); // diminui a contagem dos movimentos das peças
		board.placePiece(p, source); // coloca a peça para a posição de destino
		
		if (capturedPiece != null) { // uma peça tinha sido capturada,
			board.placePiece(capturedPiece, target); // Volta a peça pro tabuleiro na posição de destino
			capturedPieces.remove(capturedPiece); // Tira a peça da lista de pecças capturadas.
			piecesOnTheBoard.add(capturedPiece); // Coloca a peça na lista de peças no tabuleiro.
		}
		
		// #specialmove castling kingside rook - desfaz o movimento do roque pequeno 
				if (p instanceof King && target.getColumn() == source.getColumn() + 2) { //Se a peça "p" for uma instancia de rei e a posição da peça no destino for = a duas casas depois da posição de origem à direira.
					Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // Posição de origem da torre antes do Roque
					Position targetT = new Position(source.getRow(), source.getColumn() + 1); // Posição de destino da torre após o Roque
					ChessPiece rook = (ChessPiece)board.removePiece(targetT); // Remove a torre da posição dela de destino
					board.placePiece(rook, sourceT); // coloca a torre na posição de origem de volta dela 
					rook.decraseMoveCount(); // tira um movimento para a torre
				}
					
		// #specialmove castling queenside rook - tratar o movimento do roque grande mexendo a torre manualmente
				if (p instanceof King && target.getColumn() == source.getColumn() - 2) { //Se a peça "p" for uma instancia de rei e a posição da peça no destino for = a duas casas depois da posição de origem à esquerda.
					Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // Posição de origem da torre antes do Roque
					Position targetT = new Position(source.getRow(), source.getColumn() - 1); // Posição de destino da torre após o Roque
					ChessPiece rook = (ChessPiece)board.removePiece(targetT); // Remove a torre da posição dela de destino
					board.placePiece(rook, sourceT); // coloca a torre na posição de origem dela 
					rook.decraseMoveCount(); // tira um movimento para a torre
				}
				
		// specialMove en passant - Desfaz o movimento en Passant, se colocar a peça de destino na posição de origem, (como as outras) ela voltaria para a posição errada (Voltaria para a posição de destino da peça que a capturou)
				if (p instanceof Pawn) // testa se a peça "p" é uma instancia de peão
					if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) { // se o movimento na coluna de origem da peça é diferente do movimento na coluna de destino da peça (peão andou em diagonal) e capturou a peça vulnerável com en Passant
						ChessPiece pawn = (ChessPiece)board.removePiece(target);
						Position pawnPosition;
					if (p.getColor() == Color.WHITE) {// se a cor da peça que moveu é a cor branca
							pawnPosition = new Position(3, target.getColumn()); // posição "pawnPosition" será a posição que a peça preta estava
					} else { // se a cor da peça que moveu é a cor preta
							pawnPosition = new Position(4, target.getColumn()); // posição "pawnPosition" será a posição que a peça branca estava
					}
					board.placePiece(pawn, pawnPosition); // coloca a peca "Pawn" na posição "pawnPosition", que é a correta
					}
		}
		
	private void validateSourcePosition(Position position) { // Exceção para testar se existe uma peça na posição de origem 
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position"); //thereIsAPiece lança uma BoardException e aqui é uma ChessException, que também é uma BoardEception (Mais específica).
																			  // Para facilitar para o programa, troca-se a "RuntimeException" na "ChessException" por "BoardException"
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { // Verifica se o turno pertence a cor da peça selecionada no turno.
			throw new ChessException("Wrong color piece selected. This turn belongs to the other player"); //Pega a peça do tabuleiro na posição "position", faz um DownCasting para "ChessPiece" e testa a cor dela.
			//Se a cor for diferente da cor do jogador do turno atual, então seria uma peça do jogador adversário 
		}
		if (!board.piece(position).IsThereAnyPossibleMove()) { // Testa se existe movimentos possíveis para peça. Se não ter movimentos (a peça estiver presa) deve retornar uma exceção.
			throw new ChessException("There is no piece on source position");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) { // Valida se a posição de destino é um movimento possível em relação à peça que está na posição de origem
		if (!board.piece(source).possibleMove(target)) { // Testa se o movimento da peça na posição de destino não é um movimento possível, em relação à posição de origem.
			throw new ChessException("The chosen piece can not move to target position."); // Caso seja um movimento não possível, deve lançar a exceção.
		}
	}
	
	private void nextTurn() {
		turn ++; //Incrementa o número de turnos
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // Alterna os jogadores
		
	}
	
	private Color opponent (Color color) { // Devolve o oponente de uma cor.
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; // Se a cor que passou como argumento for branco, retorna a cor preta, se não retorna a cor branca
	}
	
	private ChessPiece king (Color color) { // Método para localizar o rei de uma determinada cor
		List < Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());//Procura na lista de peças em jogo qual são as peças dessa cor.
		for (Piece p : list) {//Pra cada peça "p" na lista "List"
			if (p instanceof King) { // se a peça "p" é uma instancia do Rei
				return (ChessPiece)p; // // retorna o Rei (DownCasting) para "ChessPiece"
			} //O compilador pede uma exceçção caso seja percorrido a lista e não encontre o rei
		} // Por isso, é lançada a exceção abaixo. porém não é para occorer este tipo de erro.
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	private boolean testCheck (Color color) { // Método pra testar se um rei de uma cor está em xeque, tem que percorrer todas as peças adversárias e ver para cada uma das peças se existe um movimento possível dela que cai na casa do rei. Se isso acontecer, o rei está em xeque.
		Position kingPosition = king(color).getChessPosition().toPosition(); //Pega a posição do rei "kingPosition" e chama o método "king". Pega a posição do rei em formato de matriz
		List <Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());// Cria uma lista com as peças no tabuleiro com as cores do oponente do rei
		for (Piece p : opponentPieces) {
			boolean [][] mat = p.possibleMoves(); // Pega a matriz de movimentos possíveis para a peça adversária "p".
			if (mat [kingPosition.getRow()] [kingPosition.getColumn()]) { // se na matriz, a posição correspondente à posição do rei for "true", o rei está em xeque.
				return true; // deve retornar "true"
			}
		}
		return false; // se esgotar todas as peças adversárias e nenhuma dessas peças tiver na matriz de movimentos possíveis, a posição do rei marcada como "true", o rei não está em xeque.
	}
	
	private boolean testCheckMate (Color color) { //Testa se o rei está em xeque-mate
		if (!testCheck(color)) { // Se o rei não estiver em xeque, ele não pode estar em xeque-mate
			return false; //retorna falso
		}
		List < Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());//Procura na lista de peças em jogo quais são as peças dessa cor.
		// se todas as peças dessa cor, não terem um movimento possível para sair do xeque, esta cor está em xeque-mate.
		for (Piece p : list) { // percorre todas as peças pertencentes a cor da lista
		// se existir alguma peça "p" na lista "list" que possua um movimento que tira do xeque, retorna false (A cor não está em xeque-mate). Se esgotar o "for" e não encontrar nenhum movimento que possa sair do xeque), então é xeque-mate.
			boolean [][] mat = p.possibleMoves(); //Matriz de booleanos recebendo os movimentos possíveis da peça "p"
			for (int i=0; i<board.getRows(); i++) { // Percorre as linhas da matriz
				for (int j=0; j<board.getColumns(); j++) { //Percorre as colunas da matriz
					if (mat[i][j]) { //Para cada elemento da matriz, testa se o mmovimento possível para tirar do xeque,
						Position source = ((ChessPiece)p).getChessPosition().toPosition();//Pega a posição da peça "p", na posição de origem("source"). Position é um atributo "protected". Então essa classe não tem permissão para acessa-la. Por isso deve ser feito um "DownCasting" para "ChessPiece", onde a partir dessa posição no formato "ChessPiece" pode chamar o "ChessPosition" 
						Position target = new Position(i, j); //Leva a peça "p" para a posição de destino ("target") da matriz [i][j], que seria um movimento possível.
						Piece capturedPiece = makeMove(source, target); // Faz o movimento da peça "p" a partir da posição "source" para "target" (de origem par o destino.
						boolean testCheck = testCheck(color);//Testa se o rei daquela cor ainda está em xeque. Cria uma variável auxiliar booleana, e faz ela receber a chamada do testCheck 
						undoMove(source, target, capturedPiece); // Desfaz o movimento para que não atrapalhe os movimentos das peças.
						if (!testCheck) { // testa se não estava em xeque. ou seja retirou o rei do xeque.
							return false; // se retira o rei do xeque, retorna falso.
						}
					}
				}
			}
		}
		return true; // retorna "true", a cor está em xeque-mate.
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { //Operação de colocar as peças. Recebe as coordenadas do xadrez.
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); //instancia a peça na posição baseada da matriz.
		piecesOnTheBoard.add(piece); // Adiciona uma peça para a lista "piecesOnTheBoard"
	}
	private void initialSetup() { //Assim que iniciará no tabuleiro
		placeNewPiece('a', 1, new Rook(board, Color.WHITE)); // Coloca a torre branca na posição "a1"
		placeNewPiece('b', 1, new Knight(board, Color.WHITE)); // Coloca o cavalo branco na posição "b1"
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE)); // Coloca o bispo branco na posição "c1"
		placeNewPiece('d', 1, new Queen(board, Color.WHITE)); // Coloca a rainha branca na posição "d1"
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));// Coloca o rei branco na posição "e1". Da um erro no rei, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE)); // Coloca a torre branca na posição "f1"
		placeNewPiece('g', 1, new Knight(board, Color.WHITE)); // Coloca o cavalo branco na posição "g1"
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));// Coloca a torre branca na posição "h1"
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));// Coloca o peão branco na posição "a2". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));// Coloca o peão branco na posição "b2". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));// Coloca o peão branco na posição "c2". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));// Coloca o peão branco na posição "d2". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));// Coloca o peão branco na posição "e2". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));// Coloca o peão branco na posição "f2". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));// Coloca o peão branco na posição "g2". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));// Coloca o peão branco na posição "h2". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));// Coloca a torre branca na posição "a8"
        placeNewPiece('b', 8, new Knight(board, Color.BLACK)); // Coloca o cavalo preto na posição "b8"
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK)); // Coloca o bispo preto na posição "c8"
		placeNewPiece('d', 8, new Queen(board, Color.BLACK)); // Coloca a rainha branca na posição "d8"
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));// Coloca o rei preto na posição "e8". Da um erro no rei, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida)
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK)); // Coloca o bispo preto na posição "f8"
        placeNewPiece('g', 8, new Knight(board, Color.BLACK)); // Coloca o cavalo preto na posição "g8"
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));// Coloca a torre branca na posição "h8"
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));// Coloca o peão branco na posição "a7". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida 
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));// Coloca o peão branco na posição "b7". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));// Coloca o peão branco na posição "c7". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));// Coloca o peão branco na posição "d7". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));// Coloca o peão branco na posição "e7". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));// Coloca o peão branco na posição "f7". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));// Coloca o peão branco na posição "g7". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));// Coloca o peão branco na posição "h7". Da um erro no peão, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela peça também acessar a partida
        }
}

