package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //Matriz de pe�as
	
	public Board() {
	}

	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1)
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column"); // Se for informado uma linha ou coluna menores que 1
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	} // Retirado os Setters setRow e setColumns pois n�o deve ser permitido alterar o n�mero de linhas e colunas do tabuleiro

	public int getColumns() {
		return columns;
	}

	public Piece piece (int row, int column) {
		if (!positionExists(row, column)) { // Se n�o existir a posi��o "positionExists"
			throw new BoardException("Position not on the board");
		}
		return pieces [row][column];
	}
	
	public Piece piece (Position position) { // Sobrecarga do m�todo acima
		if (!positionExists(position)) { // Se n�o existir a posi��o "positionExists"
			throw new BoardException("Position not on the board");
		}
			return pieces [position.getRow()][position.getColumn()]; // Retorna a pe�a pela posi��o que ela ocupa.
	}

	public void placePiece(Piece piece, Position position) { // Respons�vel por colocar a pe�a na devida posi��o dela no tabuleiro
		if (thereIsAPiece(position)) { // n�o pode por uma pe�a, caso j� haja uma pe�a na mesma posi��o
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece; //vai na matriz "pieces", na posi��o dada "position.get" e atribui � pe�a "piece"
		piece.position = position; //Inicialmente, a pe�a era nula (classe Board), mas agora a posi��o da pe�a ser� "position",
		//que � acessada livremente, mesmo sendo protected (classe Piece), por ser do mesmo pacote "boardgame."
	}
	
	private boolean positionExists(int row, int column) { // mais f�cil de testar com linha e coluna dentro da classe
		return row >= 0 && row < rows && column >= 0 && column < columns; // condi��o para ver se uma posi��o existe, dentro do tabuleiro.
	}

	public boolean positionExists (Position position) { // sobrecarga baseada na "positionExists" acima
		return positionExists(position.getRow(), position.getColumn()); // testa se a posi��o existe.
	}
	
	public boolean thereIsAPiece(Position position) { //texta se h� uma pe�a na posi��o "Position"
		if (!positionExists(position)) { // Se n�o existir a posi��o "positionExists"
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null; // se a pe�a for diferente de nulo, tem uma pe�a nesta posi��o
		// piece(position) � um m�todo da pr�pria classe "Public Piece piece (Position position)"
	}
}