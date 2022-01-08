package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece { // Classe que extede a classe "ChessPiece", que extende a classe "Piece"


	public Knight(Board board, Color color) { // Constructor que pega o "color" da classe "ChessPiece" e o "board" da classe "piece" 	
		super(board, color);
	}

	@Override
	public String toString() { // Deve imprimir na tela a letra correspondente ao cavalo
		return "N";
	}
	
	private boolean canMove(Position position)	{ // O método auxilia o cavalo para se mover em determinada posição "position". Feito para ver se tem alguma peça onde o cavalo irá se mexer.
		ChessPiece p = (ChessPiece)getBoard().piece(position); // Saber se o cavalo pode mover para a posição. Pega a peça "p" na "position"
		return p == null || p.getColor() != getColor(); // Verifica se não tem peça "p" na posição (== null) ou se tem uma peça adversária.
	}
	
	@Override //Implementa os métodos da classe abstrata "possibleMoves"
	public boolean[][] possibleMoves() {
		 boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // Matriz de booleanos com a mesma dimensão do tabuleiro, retornando valor falso. Deixa a peça como se tivesse presa
		
		 Position p = new Position(0,0); // Cria uma posição "p" auxiliar para testar toadas as 8 direções possíveis do cavalo.
		 
		 //mover pra cima e esquerda
		 //(-1,-2)
		 p.setValues(position.getRow() - 1, position.getColumn() - 2); // Posição acima e a esquerda da posição de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //(-2,-1)
		 p.setValues(position.getRow() - 2, position.getColumn() - 1); // Posição acima e a esquerda da posição de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover pra cima e para direita
		 //(-2,+1)
		 p.setValues(position.getRow() - 2, position.getColumn() + 1); // Posição para cima e para direita da posição de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //(-1,+2)
		 p.setValues(position.getRow() - 1, position.getColumn() + 2); // Posição para cima e para direita da posição de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover pra baixo e pra direita
		 //(+1,+2)
		 p.setValues(position.getRow() + 1, position.getColumn() + 2); // Posição a noroeste da posição de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //(+2,+1)
		 p.setValues(position.getRow() + 2, position.getColumn() + 1); // Posição para baixo e para direita da posição de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover pra baixo e pra esquerda
		 //(+2,-1)
		 p.setValues(position.getRow() + 2, position.getColumn() - 1); // Posição para baixo e para esquerda da posição de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //(+1,-2)
		 p.setValues(position.getRow() + 1, position.getColumn() - 2); // Posição para baixo e para esquerda da posição de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 return mat;
	}
}
