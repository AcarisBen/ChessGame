package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece { //Classe pe�o

	public Pawn(Board board, Color color) {
		super(board, color);
		}

	@Override
	public boolean[][] possibleMoves() { // Matriz de booleanos com a mesma dimens�o do tabuleiro, retornando valor falso. Deixa a pe�a como se tivesse presa
			 boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
			
			 Position p = new Position(0, 0); // Marcar as posi��es v�lidas para que a torre possa andar.
			
			 if (getColor() == Color.WHITE) { // Mexendo com o pe�o branco
				 
				 p.setValues(position.getRow() - 1, position.getColumn()); // andar uma posi��o pra cima
				 if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // se a posi��o existe, e n�o tem nenhuma pe�a,
					 mat [p.getRow()][p.getColumn()]= true; // o pe�o pode andar para l�
				 }
				 
				 p.setValues(position.getRow() - 2, position.getColumn()); // andar duas posi��es pra cima
				 Position p2 = new Position(position.getRow() - 1, position.getColumn()); // Segunda posi��o para avaliar se uma casa acima tem alguma pe�a 
				 if (getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0 && getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					 // Testa se a posi��o existe (uma ou duas casas acima), se n�o tem nenhuma pe�a naquela posi��o e se � a primeira vez que o pe�o est� mexendo.
					 mat [p.getRow()][p.getColumn()]= true; // o pe�o tamb�m pode andar para l�
				 }
			 
				 p.setValues(position.getRow() - 1, position.getColumn() - 1 ); // andar uma posi��o pra diagonal para esquerda
				 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posi��o existe, e se tem alguma pe�a adverss�ria la.
					 mat [p.getRow()][p.getColumn()]= true; // o pe�o pode andar para l�
				 }
			 
				 p.setValues(position.getRow() - 1, position.getColumn() + 1 ); // andar uma posi��o pra diagonal para direita
				 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posi��o existe, e se tem alguma pe�a adverss�ria la.
					 mat [p.getRow()][p.getColumn()]= true; // o pe�o pode andar para l�
				 }
			 } else { // // Mexendo com o pe�o preto
				 p.setValues(position.getRow() + 1, position.getColumn()); // andar uma posi��o pra cima
				 if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // se a posi��o existe, e n�o tem nenhuma pe�a,
					 mat [p.getRow()][p.getColumn()]= true; // o pe�o pode andar para l�
				 }
				 
				 p.setValues(position.getRow() + 2, position.getColumn()); // andar duas posi��es pra cima
				 Position p2 = new Position(position.getRow() + 1, position.getColumn()); // Segunda posi��o para avaliar se uma casa acima tem alguma pe�a 
				 if (getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0 && getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					 // Testa se a posi��o existe (uma ou duas casas acima), se n�o tem nenhuma pe�a naquela posi��o e se � a primeira vez que o pe�o est� mexendo.
					 mat [p.getRow()][p.getColumn()]= true; // o pe�o tamb�m pode andar para l�
				 }
			 
				 p.setValues(position.getRow() + 1, position.getColumn() - 1 ); // andar uma posi��o pra diagonal para esquerda
				 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posi��o existe, e se tem alguma pe�a adverss�ria la.
					 mat [p.getRow()][p.getColumn()]= true; // o pe�o pode andar para l�
				 }
			 
				 p.setValues(position.getRow() + 1, position.getColumn() + 1 ); // andar uma posi��o pra diagonal para direita
				 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posi��o existe, e se tem alguma pe�a adverss�ria la.
					 mat [p.getRow()][p.getColumn()]= true; // o pe�o pode andar para l�
				 }
			 }
		return mat;
	}
	@Override
	public String toString () {
		return "P";
	}
}
