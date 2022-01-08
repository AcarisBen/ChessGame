package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece { // Classe que extede da classe "ChessPiece", que extende a classe "Piece"

	public Bishop(Board board, Color color) { // Constructor que pega o "color" da classe "ChessPiece" e o "board" da classe "piece" 
		super(board, color);
		}

	@Override
	public String toString() { // Deve imprimir na tela a letra correspondente do bispo
		return "B";
	}

	@Override //Implementa os métodos da classe abstrata "possibleMoves"
	public boolean[][] possibleMoves() { // Matriz de booleanos com a mesma dimensão do tabuleiro, retornando valor falso. Deixa a peça como se tivesse presa
		 boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		 Position p = new Position(0, 0); // Marcar as posições válidas para que o bispo possa andar.
		 
		 // Mover o bispo para noroeste
		 p.setValues(position.getRow() - 1, position.getColumn() - 1 ); //Pega a linha acima da peça, na coluna a esquerda
		 while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posição "p" estiver vaga e não ter peça lá,
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSIÇÃO DA MATRIZ, DIZENDO AO PROGRAMA QUE A PEÇA PODE IR PARA LA,
			p.setValues(p.getRow() - 1, p.getColumn() - 1); //Repete andar para cima e a esquerda enquanto existir casa vazias.
		 }
		 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa vazia ou com uma peça adversária. Se sim, deve ser marcada a posição como verdadeiro. 
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSIÇÃO DA MATRIZ, DIZENDO AO PROGRAMA QUE A PEÇA PODE IR PARA LA,
		 }
		 
		 // Mover o bispo para nordeste
		 p.setValues(position.getRow() - 1, position.getColumn() + 1); //Pega a linha acima da peça, na coluna a direita
		 while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posição "p" estiver vaga e não ter peça lá,
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSIÇÃO DA MATRIZ, DIZENDO AO PROGRAMA QUE A PEÇA PODE IR PARA LA,
			 p.setValues(p.getRow() - 1, p.getColumn() + 1); //Repete andar para cima e a direita enquanto existir casa vazias.
		 }
		 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa vazia ou com uma peça adversária. Se sim, deve ser marcada a posição como verdadeiro. 
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSIÇÃO DA MATRIZ, DIZENDO AO PROGRAMA QUE A PEÇA PODE IR PARA LA,
		 }
		 
		// Mover o bispo para sudeste
		p.setValues(position.getRow() + 1 , position.getColumn() + 1); //Pega a linha abaixo da peça, na coluna a direita
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posição "p" estiver vaga e não ter peça lá,
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSIÇÃO DA MATRIZ, DIZENDO AO PROGRAMA QUE A PEÇA PODE IR PARA LA,
			p.setValues(p.getRow() + 1, p.getColumn() + 1); //Repete andar para baixo e para direita enquanto existir casa vazias.
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa vazia ou com uma peça adversária. Se sim, deve ser marcada a posição como verdadeiro. 
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSIÇÃO DA MATRIZ, DIZENDO AO PROGRAMA QUE A PEÇA PODE IR PARA LA,
		}
				 
		// Mover o bispo para sudoeste
		p.setValues(position.getRow() + 1, position.getColumn() - 1); //Pega a linha abaixo da peça, na coluna da esquerda
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posição "p" estiver vaga e não ter peça lá,
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSIÇÃO DA MATRIZ, DIZENDO AO PROGRAMA QUE A PEÇA PODE IR PARA LA,
			p.setValues(p.getRow() +1, p.getColumn() - 1); //Repete andar para baixo e para esquerda enquanto existir casa vazias.
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa vazia ou com uma peça adversária. Se sim, deve ser marcada a posição como verdadeiro. 
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSIÇÃO DA MATRIZ, DIZENDO AO PROGRAMA QUE A PEÇA PODE IR PARA LA.
			 }		 
				 
		return mat;
	}
}
