package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch { // Regras do jogo

	private int turn; 
	private Color currentPlayer;
	private Board board;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>(); //Instacia uma lista para controlar as peças restantes do tabuleiro
	private List<Piece> capturedPieces = new ArrayList<>(); // Instancia uma lista para controlar as peças capturadas.

	
	public ChessMatch() { // Quem sabe a dimensão do tabuleiro é a class "ChessMatch"
		board = new Board(8, 8); // Inicia a partida com este tabuleiro.
		turn = 1; // O jogo inicia no turno 1.
		currentPlayer = Color.WHITE; // O primeiro jogador a iniciar o jogo é o branco
		initialSetup(); // Chama a configuração inicial do tabuleiro
		}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
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

