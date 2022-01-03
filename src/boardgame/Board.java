package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //Matriz de peças
	
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
	} // Retirado os Setters setRow e setColumns pois não deve ser permitido alterar o número de linhas e colunas do tabuleiro

	public int getColumns() {
		return columns;
	}

	public Piece piece (int row, int column) {
		if (!positionExists(row, column)) { // Se não existir a posição "positionExists"
			throw new BoardException("Position not on the board");
		}
		return pieces [row][column];
	}
	
	public Piece piece (Position position) { // Sobrecarga do método acima
		if (!positionExists(position)) { // Se não existir a posição "positionExists"
			throw new BoardException("Position not on the board");
		}
			return pieces [position.getRow()][position.getColumn()]; // Retorna a peça pela posição que ela ocupa.
	}

	public void placePiece(Piece piece, Position position) { // Responsável por colocar a peça na devida posição dela no tabuleiro
		if (thereIsAPiece(position)) { // não pode por uma peça, caso já haja uma peça na mesma posição
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece; //vai na matriz "pieces", na posição dada "position.get" e atribui à peça "piece"
		piece.position = position; //Inicialmente, a peça era nula (classe Board), mas agora a posição da peça será "position",
		//que é acessada livremente, mesmo sendo protected (classe Piece), por ser do mesmo pacote "boardgame."
	}
	
	private boolean positionExists(int row, int column) { // mais fácil de testar com linha e coluna dentro da classe
		return row >= 0 && row < rows && column >= 0 && column < columns; // condição para ver se uma posição existe, dentro do tabuleiro.
	}

	public boolean positionExists (Position position) { // sobrecarga baseada na "positionExists" acima
		return positionExists(position.getRow(), position.getColumn()); // testa se a posição existe.
	}
	
	public boolean thereIsAPiece(Position position) { //texta se há uma peça na posição "Position"
		if (!positionExists(position)) { // Se não existir a posição "positionExists"
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null; // se a peça for diferente de nulo, tem uma peça nesta posição
		// piece(position) é um método da própria classe "Public Piece piece (Position position)"
	}
}