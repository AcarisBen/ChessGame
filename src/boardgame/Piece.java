package boardgame;

public class Piece {

	protected Position position;
	private Board board;
		
	public Piece() {
	}

	public Piece(Board board) {
		this.board = board;
		position = null; 
	} // No início, a posição no tabuleiro tem que ser nula. Por isso, que o constructor não tem a position.

	protected Board getBoard() { // Somente classes dentro do mesmo pacote e subclasses que podem acessar o tabuleiro. Não pode acessar o tabuleiro pelas outras camadas. Somente a camada "Board" deve acessá-lo.  
		return board; 
	} // Não tem o setBoard, por que ão pode permitir que o tabuleiro seja alterado.

	public void setBoard(Board board) {
		this.board = board;
	}

}
