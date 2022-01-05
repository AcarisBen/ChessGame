package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece { //Ao transformar o Piece em abstrata, deve alterar tbm a "ChessPiece"
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() { // Não tem setColor por que não pode deixar alterar a cor.
		return color;
	}
}
