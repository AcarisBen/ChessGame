package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch { // Regras do jogo

	private int turn; 
	private Color currentPlayer;
	private Board board;
	private boolean check;
	
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
		
		check = (testCheck(opponent(currentPlayer))) ? true : false; // Testa se o opponent está em xeque.
		// se o teste de xeque do oponente for "true" então o opponente está em xeque, se não retorna "false"
		nextTurn(); // Chama o próximo turno.
		return (ChessPiece) capturedPiece; // Retorna a peça capturada, com DownCasting da peça capturada do tipo "ChessPiece"
		}
	
	private Piece makeMove(Position source, Position target) { //Lógica de realizar um movimento baseado na posição de origem e de destino
		Piece p = board.removePiece(source); // Retira a peça na posição de origem
		Piece capturedPiece = board.removePiece(target); //Remove a peça que está na posição de destino (Peça capturada)
		board.placePiece(p, target); // Coloca a peça "p" na posição da peça de destino.
		
		if (capturedPiece != null) { // se capturou uma peça
			piecesOnTheBoard.remove(capturedPiece); // remove a peça capturada da lista de peças no tabuleiro.
			capturedPieces.add(capturedPiece); // adiciona a peça capturada na lista de peças capturadas
		}
		
		return capturedPiece; //retorna a peça capturada.
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) { //Método para desfazer o movimento makeMove, para não por o rei em xeque.
		Piece p = board.removePiece(target); // Cria uma peça para remove-la da posição de destino
		board.placePiece(p, source); // coloca a peça para a posição de destino
		
		if (capturedPiece != null) { // uma peça tinha sido capturada,
			board.placePiece(capturedPiece, target); // Volta a peça pro tabuleiro na posição de destino
			capturedPieces.remove(capturedPiece); // Tira a peça da lista de pecças capturadas.
			piecesOnTheBoard.add(capturedPiece); // Coloca a peça na lista de peças no tabuleiro.
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
		List < Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());//Procura na lista de peças em jogo qual é o rei dessa cor.
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
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { //Operação de colocar as peças. Recebe as coordenadas do xadrez.
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); //instancia a peça na posição baseada da matriz.
		piecesOnTheBoard.add(piece); // Adiciona uma peça para a lista "piecesOnTheBoard"
	}
	private void initialSetup() { //Assim que iniciará no tabuleiro
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
        }
}

