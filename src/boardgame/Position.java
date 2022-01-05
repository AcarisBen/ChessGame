package boardgame;

public class Position {

	private Integer row;
	private Integer column;
	
	public Position() {
	}

	public Position(Integer row, Integer column) {
		this.row = row;
		this.column = column;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) { // Atualiza as linhas de uma posi��o
		this.row = row;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) { // Atualiza as colunas  de uma posi��o
		this.column = column;
	}

	public void setValues (Integer row, Integer column) { // Atualiza os valores de uma posi��o
	this.row = row;
	this.column = column;
	}

	@Override
	public String toString() {
		return "Position: "
				+ row
				+ ", "
				+ column;
	}
	
	
}
