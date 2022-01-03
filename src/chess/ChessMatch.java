package chess;

import boardgame.Board;
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

