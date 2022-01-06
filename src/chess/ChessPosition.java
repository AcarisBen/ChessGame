package chess;

import boardgame.Position;

public class ChessPosition { // Passa pro programa para ser informado uma letra e um número, convertendo para a posição na matriz

	private char column;
	private int row;
	
	
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {//Programação defensiva, caso seja informado algum valor fora do padrão do xadrez
			// lembrando que é possível realizar operações com char. 'a'=1, 'b'=2...
			throw new ChessException("Error instantiating chess position. Valid values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() { // Não tem setRow nem setColumn para não deixar alterar a esses valores.
		return column;
	}

	public int getRow() {
		return row;
	}
	protected Position toPosition() { // Dada na posição do tabuleiro, retorna uma posição da matriz.
		return new Position(8 - row, column - 'a'); //8 - linha da matriz e coluna da matriz - char 'a'
	}

	protected static ChessPosition fromPosition(Position position) {// Dada a posição da matriz deve converter para a posição do xadrez 
		return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow()); //casting pra "char"(por não ser automático) e pega a posição da matriz e converte para a posição do xadrez
	}

	@Override
	public String toString() { //imprime na tela a coluna e a linha. As "" na frente mostra pro programa que é tudo String.
		return "" + column + row;
	}
}