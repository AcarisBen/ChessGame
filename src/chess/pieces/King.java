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
	
	private boolean canMove(Position position)	{ // O m�todo auxilia o rei para se mover em determinada posi��o "position". Feito para ver se tem alguma pe�a onde o rei ir� se mexer.
		ChessPiece p = (ChessPiece)getBoard().piece(position); // Saber se o rei pode mover para a posi��o. Pega a pe�a "p" na "position"
		return p == null || p.getColor() != getColor(); // Verifica se n�o tem pe�a "p" na posi��o (== null) ou se tem uma pe�a advers�ria.
	}
	
	@Override //Implementa os m�todos da classe abstrata "possibleMoves"
	public boolean[][] possibleMoves() {
		 boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // Matriz de booleanos com a mesma dimens�o do tabuleiro, retornando valor falso. Deixa a pe�a como se tivesse presa
		
		 Position p = new Position(0,0); // Cria uma posi��o "p" auxiliar para testar toadas as 8 dire��es poss�veis do Rei.
		 //mover 1 casa pra cima
		 p.setValues(position.getRow() - 1, position.getColumn()); // Posi��o acima da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra baixo
		 p.setValues(position.getRow() + 1, position.getColumn()); // Posi��o abaixo da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra esquerda
		 p.setValues(position.getRow(), position.getColumn() - 1); // Posi��o a esquerda da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra direita
		 p.setValues(position.getRow(), position.getColumn() + 1); // Posi��o a direita da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra noroeste
		 p.setValues(position.getRow() - 1, position.getColumn() - 1); // Posi��o a noroeste da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra nordeste
		 p.setValues(position.getRow() - 1, position.getColumn() + 1); // Posi��o a nordeste da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra sudoeste
		 p.setValues(position.getRow() + 1, position.getColumn() - 1); // Posi��o a sudoeste da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra sudeste
		 p.setValues(position.getRow() + 1, position.getColumn() + 1); // Posi��o a sudeste da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 return mat;
	}
}
