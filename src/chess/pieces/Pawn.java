package chess.pieces;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece { //Classe pe�o

	private ChessMatch chessMatch; // A classe pe�o tamb�m precisa receber autoriza��o para mexer na partida, para poder fazer o movimento en Passant.
	
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
				 
				// #SpecialMove enPassant - M�todo para testar se o pe�o branco pode fazer o movimento
				 if (position.getRow() == 3) { // Se a posi��o do pe�o branco est� na linha 5 (que seria a linha 3 da matriz)
					 Position left = new Position(position.getRow(), position.getColumn() - 1); // Vari�vel "left" que seria uma casa � esquerda do pe�o branco
					if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					// Testa se a posi��o "left" existe, se tem um opponente nesta posi��o e se a pe�a que est� l� est� vulner�vel para o en Passant
						mat [left.getRow() - 1 ] [left.getColumn()] = true;// o pe�o pode capturar a pe�a na posi��o "left", se movendo tamb�m para uma posi��o acima
					}
					
					 Position right = new Position(position.getRow(), position.getColumn() + 1); // Vari�vel "right" que seria uma casa � direita do pe�o branco
						if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
						// Testa se a posi��o "right" existe, se tem um opponente nesta posi��o e se a pe�a que est� l� est� vulner�vel para o en Passant
							mat [right.getRow() - 1 ] [right.getColumn()] = true;// o pe�o pode capturar a pe�a na posi��o "right", se movendo tamb�m para uma posi��o acima
						}	
				 }
				 
			 } else { // // Mexendo com o pe�o preto
				 p.setValues(position.getRow() + 1, position.getColumn()); // andar uma posi��o pra baixo
				 if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // se a posi��o existe, e n�o tem nenhuma pe�a,
					 mat [p.getRow()][p.getColumn()]= true; // o pe�o pode andar para l�
				 }
				 
				 p.setValues(position.getRow() + 2, position.getColumn()); // andar duas posi��es pra baixo
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
				 
				// #SpecialMove enPassant - M�todo para testar se o pe�o preto pode fazer o movimento
				 if (position.getRow() == 4) { // Se a posi��o do pe�o preto est� na linha 4 (que seria a linha 4 da matriz)
					 Position left = new Position(position.getRow(), position.getColumn() - 1); // Vari�vel "left" que seria uma casa � esquerda do pe�o preto
					if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					// Testa se a posi��o "left" existe, se tem um opponente nesta posi��o e se a pe�a que est� l� est� vulner�vel para o en Passant
						mat [left.getRow() + 1 ] [left.getColumn()] = true;// o pe�o pode capturar a pe�a na posi��o "left", se movendo tamb�m para uma posi��o abaixo
					}
					
					 Position right = new Position(position.getRow(), position.getColumn() + 1); // Vari�vel "right" que seria uma casa � direita do pe�o branco
						if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
						// Testa se a posi��o "right" existe, se tem um opponente nesta posi��o e se a pe�a que est� l� est� vulner�vel para o en Passant
							mat [right.getRow() + 1 ] [right.getColumn()] = true;// o pe�o pode capturar a pe�a na posi��o "right", se movendo tamb�m para uma posi��o abaixo
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
