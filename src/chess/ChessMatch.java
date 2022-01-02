package chess;

import boardgame.Board;

public class ChessMatch { // Regras do jogo

	private Board board;
	
	public ChessMatch() { // Quem sabe a dimens�o do tabuleiro � a class "ChessMatch"
		board = new Board(8, 8);
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
}

