package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece { //Classe peão

	public Pawn(Board board, Color color) {
		super(board, color);
		}

	@Override
	public boolean[][] possibleMoves() { // Matriz de booleanos com a mesma dimensão do tabuleiro, retornando valor falso. Deixa a peça como se tivesse presa
			 boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
			
			 Position p = new Position(0, 0); // Marcar as posições válidas para que a torre possa andar.
			
			 if (getColor() == Color.WHITE) { // Mexendo com o peão branco
				 
				 p.setValues(position.getRow() - 1, position.getColumn()); // andar uma posição pra cima
				 if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // se a posição existe, e não tem nenhuma peça,
					 mat [p.getRow()][p.getColumn()]= true; // o peão pode andar para lá
				 }
				 
				 p.setValues(position.getRow() - 2, position.getColumn()); // andar duas posições pra cima
				 Position p2 = new Position(position.getRow() - 1, position.getColumn()); // Segunda posição para avaliar se uma casa acima tem alguma peça 
				 if (getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0 && getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					 // Testa se a posição existe (uma ou duas casas acima), se não tem nenhuma peça naquela posição e se é a primeira vez que o peão está mexendo.
					 mat [p.getRow()][p.getColumn()]= true; // o peão também pode andar para lá
				 }
			 
				 p.setValues(position.getRow() - 1, position.getColumn() - 1 ); // andar uma posição pra diagonal para esquerda
				 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posição existe, e se tem alguma peça adverssária la.
					 mat [p.getRow()][p.getColumn()]= true; // o peão pode andar para lá
				 }
			 
				 p.setValues(position.getRow() - 1, position.getColumn() + 1 ); // andar uma posição pra diagonal para direita
				 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posição existe, e se tem alguma peça adverssária la.
					 mat [p.getRow()][p.getColumn()]= true; // o peão pode andar para lá
				 }
			 } else { // // Mexendo com o peão preto
				 p.setValues(position.getRow() + 1, position.getColumn()); // andar uma posição pra cima
				 if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // se a posição existe, e não tem nenhuma peça,
					 mat [p.getRow()][p.getColumn()]= true; // o peão pode andar para lá
				 }
				 
				 p.setValues(position.getRow() + 2, position.getColumn()); // andar duas posições pra cima
				 Position p2 = new Position(position.getRow() + 1, position.getColumn()); // Segunda posição para avaliar se uma casa acima tem alguma peça 
				 if (getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0 && getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					 // Testa se a posição existe (uma ou duas casas acima), se não tem nenhuma peça naquela posição e se é a primeira vez que o peão está mexendo.
					 mat [p.getRow()][p.getColumn()]= true; // o peão também pode andar para lá
				 }
			 
				 p.setValues(position.getRow() + 1, position.getColumn() - 1 ); // andar uma posição pra diagonal para esquerda
				 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posição existe, e se tem alguma peça adverssária la.
					 mat [p.getRow()][p.getColumn()]= true; // o peão pode andar para lá
				 }
			 
				 p.setValues(position.getRow() + 1, position.getColumn() + 1 ); // andar uma posição pra diagonal para direita
				 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posição existe, e se tem alguma peça adverssária la.
					 mat [p.getRow()][p.getColumn()]= true; // o peão pode andar para lá
				 }
			 }
		return mat;
	}
	@Override
	public String toString () {
		return "P";
	}
}
