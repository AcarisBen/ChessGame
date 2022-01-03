package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch { // Regras do jogo

	private Board board;
	
	public ChessMatch() { // Quem sabe a dimens�o do tabuleiro � a class "ChessMatch"
		board = new Board(8, 8); // Inicia a partida com este tabuleiro.
		initialSetup(); // Chama a configura��o inicial do tabuleiro
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
	private void placeNewPiece(char column, int row, ChessPiece piece) { //Opera��o de colocar as pe�as. Recebe as coordenadas do xadrez.
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); //instancia a pe�a na posi��o baseada da matriz.
	}
	private void initialSetup() { //Assim que iniciar� no tabuleiro
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

