package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //Matriz de pe�as
	
	public Board() {
	}

	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public Piece piece (int row, int column) {
		return pieces [row][column];		
	}
	
	public Piece piece (Position position) { // Sobrecarga do m�todo acima
		return pieces [position.getRow()][position.getColumn()]; // Retorna a pe�a pela posi��o que ela ocupa.
	}

	}
