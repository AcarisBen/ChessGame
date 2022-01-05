package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece { // Classe que extede da classe "ChessPiece", que extende a classe "Piece"

	public Rook(Board board, Color color) { // Constructor que pega o "color" da classe "ChessPiece" e o "board" da classe "piece" 
		super(board, color);
		}

	@Override
	public String toString() { // Deve imprimir na tela a letra correspondente da Torre
		return "R";
	}

	@Override //Implementa os métodos da classe abstrata "possibleMoves"
	public boolean[][] possibleMoves() { // Matriz de booleanos com a mesma dimensão do tabuleiro, retornando valor falso. Deixa a peça como se tivesse presa
		 boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		return mat;
	}
}
