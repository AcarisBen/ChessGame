package chess;

import boardgame.Board;
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
	
	private void initialSetup() { //Assim que iniciará no tabuleiro
		board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
		board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
		board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
	}
}

