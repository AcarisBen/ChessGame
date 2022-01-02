package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //Matriz de peças
	
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
	
	public Piece piece (Position position) { // Sobrecarga do método acima
		return pieces [position.getRow()][position.getColumn()]; // Retorna a peça pela posição que ela ocupa.
	}

	public void placePiece(Piece piece, Position position) { // Responsável por colocar a peça na devida posição dela no tabuleiro
		pieces[position.getRow()][position.getColumn()] = piece; //vai na matriz "pieces", na posição dada "position.get" e atribui à peça "piece"
		piece.position = position; //Inicialmente, a peça era nula (classe Board), mas agora a posição da peça será "position",
		//que é acessada livremente, mesmo sendo protected (classe Piece), por ser do mesmo pacote "boardgame."
	}
	}
