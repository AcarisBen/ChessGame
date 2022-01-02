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

	public void placePiece(Piece piece, Position position) { // Respons�vel por colocar a pe�a na devida posi��o dela no tabuleiro
		pieces[position.getRow()][position.getColumn()] = piece; //vai na matriz "pieces", na posi��o dada "position.get" e atribui � pe�a "piece"
		piece.position = position; //Inicialmente, a pe�a era nula (classe Board), mas agora a posi��o da pe�a ser� "position",
		//que � acessada livremente, mesmo sendo protected (classe Piece), por ser do mesmo pacote "boardgame."
	}
	}
