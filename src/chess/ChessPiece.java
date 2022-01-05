package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece { //Ao transformar o Piece em abstrata, deve alterar tbm a "ChessPiece"
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() { // Não tem setColor por que não pode deixar alterar a cor.
		return color;
	}

	protected boolean isThereOpponentPiece(Position position) { //isThereOpponentPiece A peça deve saber se existe uma peça adversária em uma dada posição.
		//Este método será acessível por todas as peças, por isso que está nesta classe.
		ChessPiece p = (ChessPiece)getBoard().piece(position); //Verifica se tem uma peça adversária na posição. Deve fazer um Downcasting para "ChessPiece"
		return p !=null && p.getColor() != color; //Testa se tem uma peça na posição e se a cor da peça na p.position (peça sendo capturada) é diferente da que está sendo mexida
	}
}
