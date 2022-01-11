package chess.pieces;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece { //Classe peão

	private ChessMatch chessMatch; // A classe peão também precisa receber autorização para mexer na partida, para poder fazer o movimento en Passant.
	
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
				 
				// #SpecialMove enPassant - Método para testar se o peão branco pode fazer o movimento
				 if (position.getRow() == 3) { // Se a posição do peão branco está na linha 5 (que seria a linha 3 da matriz)
					 Position left = new Position(position.getRow(), position.getColumn() - 1); // Variável "left" que seria uma casa à esquerda do peão branco
					if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					// Testa se a posição "left" existe, se tem um opponente nesta posição e se a peça que está lá está vulnerável para o en Passant
						mat [left.getRow() - 1 ] [left.getColumn()] = true;// o peão pode capturar a peça na posição "left", se movendo também para uma posição acima
					}
					
					 Position right = new Position(position.getRow(), position.getColumn() + 1); // Variável "right" que seria uma casa à direita do peão branco
						if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
						// Testa se a posição "right" existe, se tem um opponente nesta posição e se a peça que está lá está vulnerável para o en Passant
							mat [right.getRow() - 1 ] [right.getColumn()] = true;// o peão pode capturar a peça na posição "right", se movendo também para uma posição acima
						}	
				 }
				 
			 } else { // // Mexendo com o peão preto
				 p.setValues(position.getRow() + 1, position.getColumn()); // andar uma posição pra baixo
				 if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // se a posição existe, e não tem nenhuma peça,
					 mat [p.getRow()][p.getColumn()]= true; // o peão pode andar para lá
				 }
				 
				 p.setValues(position.getRow() + 2, position.getColumn()); // andar duas posições pra baixo
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
				 
				// #SpecialMove enPassant - Método para testar se o peão preto pode fazer o movimento
				 if (position.getRow() == 4) { // Se a posição do peão preto está na linha 4 (que seria a linha 4 da matriz)
					 Position left = new Position(position.getRow(), position.getColumn() - 1); // Variável "left" que seria uma casa à esquerda do peão preto
					if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					// Testa se a posição "left" existe, se tem um opponente nesta posição e se a peça que está lá está vulnerável para o en Passant
						mat [left.getRow() + 1 ] [left.getColumn()] = true;// o peão pode capturar a peça na posição "left", se movendo também para uma posição abaixo
					}
					
					 Position right = new Position(position.getRow(), position.getColumn() + 1); // Variável "right" que seria uma casa à direita do peão branco
						if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
						// Testa se a posição "right" existe, se tem um opponente nesta posição e se a peça que está lá está vulnerável para o en Passant
							mat [right.getRow() + 1 ] [right.getColumn()] = true;// o peão pode capturar a peça na posição "right", se movendo também para uma posição abaixo
						}	
				 }
			 }
		return mat;
	}
	@Override
	public String toString () {
		return "P";
	}
}
