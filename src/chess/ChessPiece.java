package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece { 

	private Color color;
	
	public ChessPiece() {
	}

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() { // N�o tem setColor por que n�o pode deixar alterar a cor.
		return color;
	}
}
