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
	
	private boolean canMove(Position position)	{ // O m�todo auxilia o cavalo para se mover em determinada posi��o "position". Feito para ver se tem alguma pe�a onde o cavalo ir� se mexer.
		ChessPiece p = (ChessPiece)getBoard().piece(position); // Saber se o cavalo pode mover para a posi��o. Pega a pe�a "p" na "position"
		return p == null || p.getColor() != getColor(); // Verifica se n�o tem pe�a "p" na posi��o (== null) ou se tem uma pe�a advers�ria.
	}
	
	@Override //Implementa os m�todos da classe abstrata "possibleMoves"
	public boolean[][] possibleMoves() {
		 boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // Matriz de booleanos com a mesma dimens�o do tabuleiro, retornando valor falso. Deixa a pe�a como se tivesse presa
		
		 Position p = new Position(0,0); // Cria uma posi��o "p" auxiliar para testar toadas as 8 dire��es poss�veis do cavalo.
		 
		 //mover pra cima e esquerda
		 //(-1,-2)
		 p.setValues(position.getRow() - 1, position.getColumn() - 2); // Posi��o acima e a esquerda da posi��o de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //(-2,-1)
		 p.setValues(position.getRow() - 2, position.getColumn() - 1); // Posi��o acima e a esquerda da posi��o de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover pra cima e para direita
		 //(-2,+1)
		 p.setValues(position.getRow() - 2, position.getColumn() + 1); // Posi��o para cima e para direita da posi��o de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //(-1,+2)
		 p.setValues(position.getRow() - 1, position.getColumn() + 2); // Posi��o para cima e para direita da posi��o de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover pra baixo e pra direita
		 //(+1,+2)
		 p.setValues(position.getRow() + 1, position.getColumn() + 2); // Posi��o a noroeste da posi��o de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //(+2,+1)
		 p.setValues(position.getRow() + 2, position.getColumn() + 1); // Posi��o para baixo e para direita da posi��o de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover pra baixo e pra esquerda
		 //(+2,-1)
		 p.setValues(position.getRow() + 2, position.getColumn() - 1); // Posi��o para baixo e para esquerda da posi��o de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //(+1,-2)
		 p.setValues(position.getRow() + 1, position.getColumn() - 2); // Posi��o para baixo e para esquerda da posi��o de origem do cavalo
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 return mat;
	}
}
