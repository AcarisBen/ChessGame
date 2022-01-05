package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch { // Regras do jogo

	private Board board;
	
	public ChessMatch() { // Quem sabe a dimensão do tabuleiro é a class "ChessMatch"
		board = new Board(8, 8); // Inicia a partida com este tabuleiro.
		initialSetup(); // Chama a configuração inicial do tabuleiro
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
	
	public ChessPiece performChessMove (ChessPosition sourcePosition, ChessPosition targetPosition) {
	// 	Move a peça da posição de origem para a posição de destino e se for o caso, tambem a posição de captura
		Position source = sourcePosition.toPosition(); //Converte posiçoes de origem para posições da matriz		
		Position target = targetPosition.toPosition(); //Converte posiçoes de destino para posições da matriz		
		validateSourcePosition(source); // Valida se a posição de origem havia uma peça. Chama o método validateSourcePosition
		Piece capturedPiece = makeMove(source, target); //Realiza o movimento da peça, baseado na posição de origem e destino.
		return (ChessPiece) capturedPiece; // Retorna a peça capturada, com DownCasting da peça capturada do tipo "ChessPiece"
		}
	
	private Piece makeMove(Position source, Position target) { //Lógica de realizar um movimento baseado na posição de origem e de destino
		Piece p = board.removePiece(source); // Retira a peça na posição de origem
		Piece capturedPiece = board.removePiece(target); //Remove a peça que está na posição de destino (Peça capturada)
		board.placePiece(p, target); // Coloca a peça "p" na posição da peça de destino.
		return capturedPiece; //retorna a peça capturada.
	}
	private void validateSourcePosition(Position position) { // Exceção para testar se existe uma peça na posição de origem 
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position"); //thereIsAPiece lança uma BoardException e aqui é uma ChessException, que também é uma BoardEception (Mais específica).
																			  // Para facilitar para o programa, troca-se a "RuntimeException" na "ChessException" por "BoardException"
		}
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { //Operação de colocar as peças. Recebe as coordenadas do xadrez.
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); //instancia a peça na posição baseada da matriz.
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

