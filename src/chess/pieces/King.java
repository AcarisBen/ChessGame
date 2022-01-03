package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece { // Classe que extede a classe "ChessPiece", que extende a classe "Piece"


	public King(Board board, Color color) { // Constructor que pega o "color" da classe "ChessPiece" e o "board" da classe "piece" 	
		super(board, color);
	}

	@Override
	public String toString() { // Deve imprimir na tela a letra correspondente da Rei
		return "K";
	}
}
