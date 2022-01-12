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
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>(); //Instacia uma lista para controlar as pe�as restantes do tabuleiro
	private List<Piece> capturedPieces = new ArrayList<>(); // Instancia uma lista para controlar as pe�as capturadas.

	
	public ChessMatch() { // Quem sabe a dimens�o do tabuleiro � a class "ChessMatch"
		board = new Board(8, 8); // Inicia a partida com este tabuleiro.
		turn = 1; // O jogo inicia no turno 1.
		currentPlayer = Color.WHITE; // O primeiro jogador a iniciar o jogo � o branco
		check = false; // Por padr�o, um boolean sempre � falso, mas pode-se enfatizar no constructor
		initialSetup(); // Chama a configura��o inicial do tabuleiro
		}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck () { // exp�e a propriedade de xeque para que possa ser alcan�ado pelo programa principal 
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}

	public ChessPiece getEnPassantVulnerable() { // Getter do en Passant come�a null por n�o ter nenhuma pe�a vulner�vel para o pr�ximo turno, ent�o n�o inicia ela no constructor.
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}

	public ChessPiece[][] getPieces() { // Retorna a matriz de pe�as de xadrez correspondente a partida.
	
	// O "Board" tem uma matriz do tipo "Piece", mas o m�todo retorna o "ChessPiece" (Camada de xadrez).
	// Para o programa, n�o deve ser liberado pe�as do tipo "Piece", mas do tipo "ChessPiece". Pois � um desenvolvimento em camadas.
	// O programa deve enxergar a pe�a de xadrez, e n�o a pe�a interna do tabuleiro. Ent�o, deve transformar o "Piece" e ChessPiece.
	ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		
		for (int i=0; i<board.getRows(); i++) { // Percorre a matriz de pe�as do tabuleiro e pra cada pe�a, ser� feita um downcasting para "ChessPiece".
			for  (int j=0; j<board.getColumns(); j++) {
				mat [i][j] = (ChessPiece)board.piece(i, j);
				//Downcasting para ChessPiece. O programa interpreta que a pe�a � de "ChessPiece" e n�o s� "Piece"
			}
		}
		return mat; // Por fim, este c�digo retorna a matriz de pe�as da partida de xadrez.
	}
	
	public boolean [][]possibleMoves(ChessPosition sourcePosition) { // Avisa ao jogador quais posi��es a pe�a selecionada poder� ir.
		Position position = sourcePosition.toPosition(); // converte a posi��o do xadrez para uma posi��o normal
		validateSourcePosition(position); // valida a posi��o de origem assim que o jogador informar a pe�a
		return board.piece(position).possibleMoves(); // Retorna os movimentos poss�veis da posi��o
		
	}
	public ChessPiece performChessMove (ChessPosition sourcePosition, ChessPosition targetPosition) {
	// 	Move a pe�a da posi��o de origem para a posi��o de destino e se for o caso, tambem a posi��o de captura
		Position source = sourcePosition.toPosition(); //Converte posi�oes de origem para posi��es da matriz		
		Position target = targetPosition.toPosition(); //Converte posi�oes de destino para posi��es da matriz		
		validateSourcePosition(source); // Valida se a posi��o de origem havia uma pe�a. Chama o m�todo "validateSourcePosition"
		validateTargetPosition(source, target); // Valida se a posi��o de destino, para saber se o programa est� colocando a pe�a na posi��o final corretamente. Chama o m�todo "validateTargetPosition"
		Piece capturedPiece = makeMove(source, target); //Realiza o movimento da pe�a, baseado na posi��o de origem e destino.
		
		
		if (testCheck(currentPlayer)) { //Se o jogador lan�ou um movimento e ficou em xeuqe, deve desfazer o movimento e lan�ar uma exce��o
			undoMove(source, target, capturedPiece);// Testar se o movimento deixou o jogador atual em xeque
			throw new ChessException("You putted yourself in check!");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target); // Vari�vel para a pe�a que foi para o "target" (Movimento en Passant)
		
		// #specialMove promotion - testa a l�gica para fazer o pe�o ser promovido
		promoted = null; // faz com que toda vez seja feito um novo teste.
		if (movedPiece instanceof Pawn) { // Se a pe�a movida for uma instancia do pi�o 
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				// Testa se a pe�a movida (pi�o) � da cor branca e se ela chegou at� o final (da matriz seria pos���o 0) ou se � um pi�o preto e chegou ao final (posi��o 7 da  matriz)
			promoted = (ChessPiece)board.piece(target); // a vari�vel "promoted" receber� a pe�a que estiver na posi��o de destino. precisa ter uma pe�a nessa posi��o para que haja a pormo��o. Se n�o o valor da vari�vel promoted ser� null.
			promoted = replacePromotedPiece("Q"); // a vari�vel "promoted" receber� a letra "Q" como argumento do m�todo  m�todo "replacePromotedPiece"
			}
			
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false; // Testa se o opponent est� em xeque.
		// se o teste de xeque do oponente for "true" ent�o o opponente est� em xeque, se n�o retorna "false"
		if (testCheckMate(opponent(currentPlayer))) {//testa se a jogada feita, deixa o outro jogador em xeque-mate.
			checkMate = true; //Passa a vari�vel checkMate para true. O jogador recebeu xeque-mate
		} else {
			nextTurn(); // Chama o pr�ximo turno.
		}
		
		// #specialMove en passant - Testa se a pe�a � um pe�o que moveu duas casas. Neste caso, esta pe�a est� vulner�vel ao en Passant.
		if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {// Teste se a pe�a movida (movedPiece) � um pe�o e a diferen�a de linhas foi 2 pra mais ou pra menos, significa que foi um  movimento inicial de pe�o de duas casas. 
		 enPassantVulnerable = movedPiece; // o pe�o fica vulner�vel a tomar o en passant
		} else { // sen�o for o caso
			enPassantVulnerable = null; // nenhuma pe�a est� vulner�vel
		}
	
		return (ChessPiece) capturedPiece; // Retorna a pe�a capturada, com DownCasting da pe�a capturada do tipo "ChessPiece"
		}
	
	public ChessPiece replacePromotedPiece(String type) { // M�todo para trocar o pi�o por uma outra pe�a no movimento promo��o
		if (promoted == null) { // Quando a vari�vel "promoted" for nula, n�o poder� trocar a pe�a promovida. 
			 throw new IllegalStateException("There is no piece to be promoted"); // Esse m�todo � chamado qdo o usu�rio fizer a escolha da pe�a. Ent�o tem que ter pe�a indicando que tem pe�a a ser promovida.
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) { //As letras "B, N, R, e Q" s�o as �nicas poss�veis. A opera��o equals serve para comparar se um String � igual a outro. J� que o String � um tipo classe e n�o o tipo primitivo
			// Antes estava assim, mas n�o foi tratada esta exce��o: throw new InvalidParameterException("Invalid type for promotion");
			return promoted; //Retorna o valor da vari�vel "promoted" que, por padr�o, � a rainha.
		}
		
		Position pos = promoted.getChessPosition().toPosition(); // Chama a posi��o da pe�a promovida
		Piece p = board.removePiece(pos); // A vari�vel "p" guarda a pe�a que foi removida na posi��o "pos"
		piecesOnTheBoard.remove(p); // Remove a pe�a "p" da lista de pe�as do tabuleiro
		ChessPiece newPiece = newPiece(type, promoted.getColor()); // Instancia uma nova pe�a comforme o "type"
		board.placePiece(newPiece, pos); // coloca uma nova pe�a no lugar da pe�a removida.
		piecesOnTheBoard.add(newPiece); // adiciona a nova pe�a para a lista de pe�as do tabuleiro
		
		return newPiece; // retorna a nova pe�a instanciada
	}
	
	private ChessPiece newPiece(String type, Color color) { // M�todo para criar uma nova pe�a baseada no String solicitado e na cor do jogador que fizer a promo��o
		if (type.equals("B")) return new Bishop(board, color); // Se colocar "B" ser� um novo bispo
		if (type.equals("N")) return new Knight(board, color); // Se colocar "N" ser� um novo cavalo
		if (type.equals("Q")) return new Queen(board, color); //Se colocar "Q" ser� um novo rainha
		return new Rook(board, color); // Se n�o colocar nenhuma das outras letras, ser� colocado uma nova torre
		}
	
	private Piece makeMove(Position source, Position target) { //L�gica de realizar um movimento baseado na posi��o de origem e de destino
		ChessPiece p = (ChessPiece)board.removePiece(source);//Faz um DownCasting para "ChessPiece" para poder acrescentar a contagem dos movimentos. Era assim: Piece p = board.removePiece(source); // Retira a pe�a na posi��o de origem
		p.increaseMoveCount();//incrementa a contagem dos movimentos das pe�as
		Piece capturedPiece = board.removePiece(target); //Remove a pe�a que est� na posi��o de destino (Pe�a capturada)
		board.placePiece(p, target); // Coloca a pe�a "p" na posi��o da pe�a de destino.
		
		if (capturedPiece != null) { // se capturou uma pe�a
			piecesOnTheBoard.remove(capturedPiece); // remove a pe�a capturada da lista de pe�as no tabuleiro.
			capturedPieces.add(capturedPiece); // adiciona a pe�a capturada na lista de pe�as capturadas
		}
		// #specialmove castling kingside rook - tratar o movimento do roque pequeno mexendo a torre manualmente
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { //Se a pe�a "p" for uma instancia de rei e a posi��o da pe�a no destino for = a duas casas depois da posi��o de origem � direira.
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // Posi��o de origem da torre antes do Roque
			Position targetT = new Position(source.getRow(), source.getColumn() + 1); // Posi��o de destino da torre ap�s o Roque
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT); // Remove a torre da posi��o dela de origem
			board.placePiece(rook, targetT); // coloca a torre na posi��o de destino dela 
			rook.increaseMoveCount(); // conta um movimento a mais para a torre
		}
			
		// #specialmove castling queenside rook - tratar o movimento do roque grande mexendo a torre manualmente
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) { //Se a pe�a "p" for uma instancia de rei e a posi��o da pe�a no destino for = a duas casas depois da posi��o de origem � esquerda.
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // Posi��o de origem da torre antes do Roque
			Position targetT = new Position(source.getRow(), source.getColumn() - 1); // Posi��o de destino da torre ap�s o Roque
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT); // Remove a torre da posi��o dela de origem
			board.placePiece(rook, targetT); // coloca a torre na posi��o de destino dela 
			rook.increaseMoveCount(); // conta um movimento a mais para a torre
		}
		
		// specialMove en passant - Como este movimento � um movimento n�o natural, deve ser feito manualmente. 
		if (p instanceof Pawn) // testa se a pe�a "p" � uma instancia de pe�o
			if (source.getColumn() != target.getColumn() && capturedPiece == null) { // se o movimento na coluna de origem da pe�a � diferente do movimento na coluna de destino da pe�a (pe�o andou em diagonal) e n�o capturou nenhuma pe�a neste movimento
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {// se a cor da pe�a que moveu � a cor branca
					pawnPosition = new Position(target.getRow() + 1, target.getColumn()); // posi��o "pawnPosition" ser� a posi��o abaixo da pe�a branca
				} else { // se a cor da pe�a que moveu � a cor preta
					pawnPosition = new Position(target.getRow() - 1, target.getColumn()); // posi��o "pawnPosition" ser� a posi��o acima da pe�a preta
				}
				capturedPiece = board.removePiece(pawnPosition);// chama a "capturedPiece" de "pawnPosition" e tira ela do jogo
				capturedPieces.add(capturedPiece); // adiciona a "pawnPosition" que agora � "capturedPiece" na lista de "capturedPieces"
				piecesOnTheBoard.remove(capturedPiece); // remove a "pawnPosition" que � "capturedPiece" da lista de "piecesOnTheBoard"
				
			}

			return capturedPiece; //retorna a pe�a capturada.
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) { //M�todo para desfazer o movimento makeMove, para n�o por o rei em xeque.
		ChessPiece p = (ChessPiece)board.removePiece(target);// Faz um "DownCasting" para "ChessPiece" para poder diminuir a contagem dos movimentos. Era assim: Piece p = board.removePiece(target); // Cria uma pe�a para remove-la da posi��o de destino
		p.decraseMoveCount(); // diminui a contagem dos movimentos das pe�as
		board.placePiece(p, source); // coloca a pe�a para a posi��o de destino
		
		if (capturedPiece != null) { // uma pe�a tinha sido capturada,
			board.placePiece(capturedPiece, target); // Volta a pe�a pro tabuleiro na posi��o de destino
			capturedPieces.remove(capturedPiece); // Tira a pe�a da lista de pec�as capturadas.
			piecesOnTheBoard.add(capturedPiece); // Coloca a pe�a na lista de pe�as no tabuleiro.
		}
		
		// #specialmove castling kingside rook - desfaz o movimento do roque pequeno 
				if (p instanceof King && target.getColumn() == source.getColumn() + 2) { //Se a pe�a "p" for uma instancia de rei e a posi��o da pe�a no destino for = a duas casas depois da posi��o de origem � direira.
					Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // Posi��o de origem da torre antes do Roque
					Position targetT = new Position(source.getRow(), source.getColumn() + 1); // Posi��o de destino da torre ap�s o Roque
					ChessPiece rook = (ChessPiece)board.removePiece(targetT); // Remove a torre da posi��o dela de destino
					board.placePiece(rook, sourceT); // coloca a torre na posi��o de origem de volta dela 
					rook.decraseMoveCount(); // tira um movimento para a torre
				}
					
		// #specialmove castling queenside rook - tratar o movimento do roque grande mexendo a torre manualmente
				if (p instanceof King && target.getColumn() == source.getColumn() - 2) { //Se a pe�a "p" for uma instancia de rei e a posi��o da pe�a no destino for = a duas casas depois da posi��o de origem � esquerda.
					Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // Posi��o de origem da torre antes do Roque
					Position targetT = new Position(source.getRow(), source.getColumn() - 1); // Posi��o de destino da torre ap�s o Roque
					ChessPiece rook = (ChessPiece)board.removePiece(targetT); // Remove a torre da posi��o dela de destino
					board.placePiece(rook, sourceT); // coloca a torre na posi��o de origem dela 
					rook.decraseMoveCount(); // tira um movimento para a torre
				}
				
		// specialMove en passant - Desfaz o movimento en Passant, se colocar a pe�a de destino na posi��o de origem, (como as outras) ela voltaria para a posi��o errada (Voltaria para a posi��o de destino da pe�a que a capturou)
				if (p instanceof Pawn) // testa se a pe�a "p" � uma instancia de pe�o
					if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) { // se o movimento na coluna de origem da pe�a � diferente do movimento na coluna de destino da pe�a (pe�o andou em diagonal) e capturou a pe�a vulner�vel com en Passant
						ChessPiece pawn = (ChessPiece)board.removePiece(target);
						Position pawnPosition;
					if (p.getColor() == Color.WHITE) {// se a cor da pe�a que moveu � a cor branca
							pawnPosition = new Position(3, target.getColumn()); // posi��o "pawnPosition" ser� a posi��o que a pe�a preta estava
					} else { // se a cor da pe�a que moveu � a cor preta
							pawnPosition = new Position(4, target.getColumn()); // posi��o "pawnPosition" ser� a posi��o que a pe�a branca estava
					}
					board.placePiece(pawn, pawnPosition); // coloca a peca "Pawn" na posi��o "pawnPosition", que � a correta
					}
		}
		
	private void validateSourcePosition(Position position) { // Exce��o para testar se existe uma pe�a na posi��o de origem 
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position"); //thereIsAPiece lan�a uma BoardException e aqui � uma ChessException, que tamb�m � uma BoardEception (Mais espec�fica).
																			  // Para facilitar para o programa, troca-se a "RuntimeException" na "ChessException" por "BoardException"
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { // Verifica se o turno pertence a cor da pe�a selecionada no turno.
			throw new ChessException("Wrong color piece selected. This turn belongs to the other player"); //Pega a pe�a do tabuleiro na posi��o "position", faz um DownCasting para "ChessPiece" e testa a cor dela.
			//Se a cor for diferente da cor do jogador do turno atual, ent�o seria uma pe�a do jogador advers�rio 
		}
		if (!board.piece(position).IsThereAnyPossibleMove()) { // Testa se existe movimentos poss�veis para pe�a. Se n�o ter movimentos (a pe�a estiver presa) deve retornar uma exce��o.
			throw new ChessException("There is no piece on source position");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) { // Valida se a posi��o de destino � um movimento poss�vel em rela��o � pe�a que est� na posi��o de origem
		if (!board.piece(source).possibleMove(target)) { // Testa se o movimento da pe�a na posi��o de destino n�o � um movimento poss�vel, em rela��o � posi��o de origem.
			throw new ChessException("The chosen piece can not move to target position."); // Caso seja um movimento n�o poss�vel, deve lan�ar a exce��o.
		}
	}
	
	private void nextTurn() {
		turn ++; //Incrementa o n�mero de turnos
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // Alterna os jogadores
		
	}
	
	private Color opponent (Color color) { // Devolve o oponente de uma cor.
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; // Se a cor que passou como argumento for branco, retorna a cor preta, se n�o retorna a cor branca
	}
	
	private ChessPiece king (Color color) { // M�todo para localizar o rei de uma determinada cor
		List < Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());//Procura na lista de pe�as em jogo qual s�o as pe�as dessa cor.
		for (Piece p : list) {//Pra cada pe�a "p" na lista "List"
			if (p instanceof King) { // se a pe�a "p" � uma instancia do Rei
				return (ChessPiece)p; // // retorna o Rei (DownCasting) para "ChessPiece"
			} //O compilador pede uma exce���o caso seja percorrido a lista e n�o encontre o rei
		} // Por isso, � lan�ada a exce��o abaixo. por�m n�o � para occorer este tipo de erro.
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	private boolean testCheck (Color color) { // M�todo pra testar se um rei de uma cor est� em xeque, tem que percorrer todas as pe�as advers�rias e ver para cada uma das pe�as se existe um movimento poss�vel dela que cai na casa do rei. Se isso acontecer, o rei est� em xeque.
		Position kingPosition = king(color).getChessPosition().toPosition(); //Pega a posi��o do rei "kingPosition" e chama o m�todo "king". Pega a posi��o do rei em formato de matriz
		List <Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());// Cria uma lista com as pe�as no tabuleiro com as cores do oponente do rei
		for (Piece p : opponentPieces) {
			boolean [][] mat = p.possibleMoves(); // Pega a matriz de movimentos poss�veis para a pe�a advers�ria "p".
			if (mat [kingPosition.getRow()] [kingPosition.getColumn()]) { // se na matriz, a posi��o correspondente � posi��o do rei for "true", o rei est� em xeque.
				return true; // deve retornar "true"
			}
		}
		return false; // se esgotar todas as pe�as advers�rias e nenhuma dessas pe�as tiver na matriz de movimentos poss�veis, a posi��o do rei marcada como "true", o rei n�o est� em xeque.
	}
	
	private boolean testCheckMate (Color color) { //Testa se o rei est� em xeque-mate
		if (!testCheck(color)) { // Se o rei n�o estiver em xeque, ele n�o pode estar em xeque-mate
			return false; //retorna falso
		}
		List < Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());//Procura na lista de pe�as em jogo quais s�o as pe�as dessa cor.
		// se todas as pe�as dessa cor, n�o terem um movimento poss�vel para sair do xeque, esta cor est� em xeque-mate.
		for (Piece p : list) { // percorre todas as pe�as pertencentes a cor da lista
		// se existir alguma pe�a "p" na lista "list" que possua um movimento que tira do xeque, retorna false (A cor n�o est� em xeque-mate). Se esgotar o "for" e n�o encontrar nenhum movimento que possa sair do xeque), ent�o � xeque-mate.
			boolean [][] mat = p.possibleMoves(); //Matriz de booleanos recebendo os movimentos poss�veis da pe�a "p"
			for (int i=0; i<board.getRows(); i++) { // Percorre as linhas da matriz
				for (int j=0; j<board.getColumns(); j++) { //Percorre as colunas da matriz
					if (mat[i][j]) { //Para cada elemento da matriz, testa se o mmovimento poss�vel para tirar do xeque,
						Position source = ((ChessPiece)p).getChessPosition().toPosition();//Pega a posi��o da pe�a "p", na posi��o de origem("source"). Position � um atributo "protected". Ent�o essa classe n�o tem permiss�o para acessa-la. Por isso deve ser feito um "DownCasting" para "ChessPiece", onde a partir dessa posi��o no formato "ChessPiece" pode chamar o "ChessPosition" 
						Position target = new Position(i, j); //Leva a pe�a "p" para a posi��o de destino ("target") da matriz [i][j], que seria um movimento poss�vel.
						Piece capturedPiece = makeMove(source, target); // Faz o movimento da pe�a "p" a partir da posi��o "source" para "target" (de origem par o destino.
						boolean testCheck = testCheck(color);//Testa se o rei daquela cor ainda est� em xeque. Cria uma vari�vel auxiliar booleana, e faz ela receber a chamada do testCheck 
						undoMove(source, target, capturedPiece); // Desfaz o movimento para que n�o atrapalhe os movimentos das pe�as.
						if (!testCheck) { // testa se n�o estava em xeque. ou seja retirou o rei do xeque.
							return false; // se retira o rei do xeque, retorna falso.
						}
					}
				}
			}
		}
		return true; // retorna "true", a cor est� em xeque-mate.
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { //Opera��o de colocar as pe�as. Recebe as coordenadas do xadrez.
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); //instancia a pe�a na posi��o baseada da matriz.
		piecesOnTheBoard.add(piece); // Adiciona uma pe�a para a lista "piecesOnTheBoard"
	}
	private void initialSetup() { //Assim que iniciar� no tabuleiro
		placeNewPiece('a', 1, new Rook(board, Color.WHITE)); // Coloca a torre branca na posi��o "a1"
		placeNewPiece('b', 1, new Knight(board, Color.WHITE)); // Coloca o cavalo branco na posi��o "b1"
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE)); // Coloca o bispo branco na posi��o "c1"
		placeNewPiece('d', 1, new Queen(board, Color.WHITE)); // Coloca a rainha branca na posi��o "d1"
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));// Coloca o rei branco na posi��o "e1". Da um erro no rei, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE)); // Coloca a torre branca na posi��o "f1"
		placeNewPiece('g', 1, new Knight(board, Color.WHITE)); // Coloca o cavalo branco na posi��o "g1"
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));// Coloca a torre branca na posi��o "h1"
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));// Coloca o pe�o branco na posi��o "a2". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));// Coloca o pe�o branco na posi��o "b2". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));// Coloca o pe�o branco na posi��o "c2". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));// Coloca o pe�o branco na posi��o "d2". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));// Coloca o pe�o branco na posi��o "e2". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));// Coloca o pe�o branco na posi��o "f2". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));// Coloca o pe�o branco na posi��o "g2". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));// Coloca o pe�o branco na posi��o "h2". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));// Coloca a torre branca na posi��o "a8"
        placeNewPiece('b', 8, new Knight(board, Color.BLACK)); // Coloca o cavalo preto na posi��o "b8"
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK)); // Coloca o bispo preto na posi��o "c8"
		placeNewPiece('d', 8, new Queen(board, Color.BLACK)); // Coloca a rainha branca na posi��o "d8"
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));// Coloca o rei preto na posi��o "e8". Da um erro no rei, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida)
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK)); // Coloca o bispo preto na posi��o "f8"
        placeNewPiece('g', 8, new Knight(board, Color.BLACK)); // Coloca o cavalo preto na posi��o "g8"
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));// Coloca a torre branca na posi��o "h8"
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));// Coloca o pe�o branco na posi��o "a7". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida 
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));// Coloca o pe�o branco na posi��o "b7". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));// Coloca o pe�o branco na posi��o "c7". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));// Coloca o pe�o branco na posi��o "d7". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));// Coloca o pe�o branco na posi��o "e7". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));// Coloca o pe�o branco na posi��o "f7". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));// Coloca o pe�o branco na posi��o "g7". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));// Coloca o pe�o branco na posi��o "h7". Da um erro no pe�o, POIS ELE PRECISA INFORMAR qual partida que seria. (This - essa partida). Pela pe�a tamb�m acessar a partida
        }
}

