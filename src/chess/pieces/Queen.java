package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

	//A rainha copia os movimentos da torre e do bispo
public class Queen extends ChessPiece { // Classe que extede da classe "ChessPiece", que extende a classe "Piece"

	public Queen(Board board, Color color) { // Constructor que pega o "color" da classe "ChessPiece" e o "board" da classe "piece" 
		super(board, color);
		}

	@Override
	public String toString() { // Deve imprimir na tela a letra correspondente da rainha
		return "Q";
	}

	@Override //Implementa os m�todos da classe abstrata "possibleMoves"
	public boolean[][] possibleMoves() { // Matriz de booleanos com a mesma dimens�o do tabuleiro, retornando valor falso. Deixa a pe�a como se tivesse presa
		 boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		 Position p = new Position(0, 0); // Marcar as posi��es v�lidas para que a rainha possa andar.
		 
		 // Mover a rainha para cima
		 p.setValues(position.getRow() - 1, position.getColumn()); //Pega a linha acima da pe�a, na mesma coluna
		 while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posi��o "p" estiver vaga e n�o ter pe�a l�,
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
			p.setRow(p.getRow() - 1); //Repete andar para cima na mesma coluna enquanto existir casa vazias.
		 }
		 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa pra cima vazia ou com uma pe�a advers�ria. Se sim, deve ser marcada a posi��o como verdadeiro. 
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
		 }
		 
		 // Mover a rainha para esquerda
		 p.setValues(position.getRow(), position.getColumn() - 1); //Pega a coluna anterior da pe�a, na mesma linha
		 while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posi��o "p" estiver vaga e n�o ter pe�a l�,
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
			 p.setColumn(p.getColumn() - 1); //Repete andar para esquerda na mesma linha enquanto existir casa vazias.
		 }
		 if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa pra esquerda vazia ou com uma pe�a advers�ria. Se sim, deve ser marcada a posi��o como verdadeiro. 
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
		 }
		 
		// Mover a rainha para direita
		p.setValues(position.getRow(), position.getColumn() + 1); //Pega a coluna posterior da pe�a, na mesma linha
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posi��o "p" estiver vaga e n�o ter pe�a l�,
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
			p.setColumn(p.getColumn() + 1); //Repete andar para direita na mesma linha enquanto existir casa vazias.
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa pra direita vazia ou com uma pe�a advers�ria. Se sim, deve ser marcada a posi��o como verdadeiro. 
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
		}
				 
		// Mover a rainha para baixo
		p.setValues(position.getRow() + 1, position.getColumn()); //Pega a linha abaixo da pe�a, na mesma coluna
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posi��o "p" estiver vaga e n�o ter pe�a l�,
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
			p.setRow(p.getRow() + 1); //Repete andar para baixo na mesma coluna enquanto existir casa vazias.
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa pra cima vazia ou com uma pe�a advers�ria. Se sim, deve ser marcada a posi��o como verdadeiro. 
			mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA.
			 }
		
		// Mover a rainha para noroeste
		p.setValues(position.getRow() - 1, position.getColumn() - 1 ); //Pega a linha acima da pe�a, na coluna a esquerda
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posi��o "p" estiver vaga e n�o ter pe�a l�,
		mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
		p.setValues(p.getRow() - 1, p.getColumn() - 1); //Repete andar para cima e a esquerda enquanto existir casa vazias.
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa vazia ou com uma pe�a advers�ria. Se sim, deve ser marcada a posi��o como verdadeiro. 
		mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
		}
				 
		// Mover a rainha para nordeste
		p.setValues(position.getRow() - 1, position.getColumn() + 1); //Pega a linha acima da pe�a, na coluna a direita
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posi��o "p" estiver vaga e n�o ter pe�a l�,
		mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
		p.setValues(p.getRow() - 1, p.getColumn() + 1); //Repete andar para cima e a direita enquanto existir casa vazias.
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa vazia ou com uma pe�a advers�ria. Se sim, deve ser marcada a posi��o como verdadeiro. 
		mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
		}
				 
		// Mover a rainha para sudeste
		p.setValues(position.getRow() + 1 , position.getColumn() + 1); //Pega a linha abaixo da pe�a, na coluna a direita
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posi��o "p" estiver vaga e n�o ter pe�a l�,
		mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
		p.setValues(p.getRow() + 1, p.getColumn() + 1); //Repete andar para baixo e para direita enquanto existir casa vazias.
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa vazia ou com uma pe�a advers�ria. Se sim, deve ser marcada a posi��o como verdadeiro. 
		mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
		}
				 
		// Mover a rainha para sudoeste
		p.setValues(position.getRow() + 1, position.getColumn() - 1); //Pega a linha abaixo da pe�a, na coluna da esquerda
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // Enquanto a posi��o "p" estiver vaga e n�o ter pe�a l�,
		mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA,
		p.setValues(p.getRow() +1, p.getColumn() - 1); //Repete andar para baixo e para esquerda enquanto existir casa vazias.
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {  // Testa se existe mais uma casa vazia ou com uma pe�a advers�ria. Se sim, deve ser marcada a posi��o como verdadeiro. 
		mat [p.getRow()][p.getColumn()] = true; // MARCA COMO VERDADEIRO A POSI��O DA MATRIZ, DIZENDO AO PROGRAMA QUE A PE�A PODE IR PARA LA.
		}				
				 
		return mat;
	}
}