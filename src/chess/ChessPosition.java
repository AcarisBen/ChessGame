package chess;

import boardgame.Position;

public class ChessPosition { // Passa pro programa para ser informado uma letra e um n�mero, convertendo para a posi��o na matriz

	private char column;
	private int row;
	
	
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {//Programa��o defensiva, caso seja informado algum valor fora do padr�o do xadrez
			// lembrando que � poss�vel realizar opera��es com char. 'a'=1, 'b'=2...
			throw new ChessException("Error instantiating chess position. Valid values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() { // N�o tem setRow nem setColumn para n�o deixar alterar a esses valores.
		return column;
	}

	public int getRow() {
		return row;
	}
	protected Position toPosition() { // Dada na posi��o do tabuleiro, retorna uma posi��o da matriz.
		return new Position(8 - row, column - 'a'); //8 - linha da matriz e coluna da matriz - char 'a'
	}

	protected static ChessPosition fromPosition(Position position) {// Dada a posi��o da matriz deve converter para a posi��o do xadrez 
		return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow()); //casting pra "char"(por n�o ser autom�tico) e pega a posi��o da matriz e converte para a posi��o do xadrez
	}

	@Override
	public String toString() { //imprime na tela a coluna e a linha. As "" na frente mostra pro programa que � tudo String.
		return "" + column + row;
	}
}