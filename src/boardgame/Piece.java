package boardgame;

public class Piece {

	protected Position position;
	private Board board;
		
	public Piece() {
	}

	public Piece(Board board) {
		this.board = board;
		position = null; 
	} // No in�cio, a posi��o no tabuleiro tem que ser nula. Por isso, que o constructor n�o tem a position.

	protected Board getBoard() { // Somente classes dentro do mesmo pacote e subclasses que podem acessar o tabuleiro. N�o pode acessar o tabuleiro pelas outras camadas. Somente a camada "Board" deve acess�-lo.  
		return board; 
	} // N�o tem o setBoard, por que �o pode permitir que o tabuleiro seja alterado.

	public void setBoard(Board board) {
		this.board = board;
	}

}
