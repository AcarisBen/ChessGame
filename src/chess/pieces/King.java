package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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
	
	private boolean canMove(Position position)	{ // O método auxilia o rei para se mover em determinada posição "position". Feito para ver se tem alguma peça onde o rei irá se mexer.
		ChessPiece p = (ChessPiece)getBoard().piece(position); // Saber se o rei pode mover para a posição. Pega a peça "p" na "position"
		return p == null || p.getColor() != getColor(); // Verifica se não tem peça "p" na posição (== null) ou se tem uma peça adversária.
	}
	
	@Override //Implementa os métodos da classe abstrata "possibleMoves"
	public boolean[][] possibleMoves() {
		 boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // Matriz de booleanos com a mesma dimensão do tabuleiro, retornando valor falso. Deixa a peça como se tivesse presa
		
		 Position p = new Position(0,0); // Cria uma posição "p" auxiliar para testar toadas as 8 direções possíveis do Rei.
		 //mover 1 casa pra cima
		 p.setValues(position.getRow() - 1, position.getColumn()); // Posição acima da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra baixo
		 p.setValues(position.getRow() + 1, position.getColumn()); // Posição abaixo da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra esquerda
		 p.setValues(position.getRow(), position.getColumn() - 1); // Posição a esquerda da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra direita
		 p.setValues(position.getRow(), position.getColumn() + 1); // Posição a direita da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra noroeste
		 p.setValues(position.getRow() - 1, position.getColumn() - 1); // Posição a noroeste da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra nordeste
		 p.setValues(position.getRow() - 1, position.getColumn() + 1); // Posição a nordeste da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra sudoeste
		 p.setValues(position.getRow() + 1, position.getColumn() - 1); // Posição a sudoeste da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra sudeste
		 p.setValues(position.getRow() + 1, position.getColumn() + 1); // Posição a sudeste da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 return mat;
	}
}
