package boardgame;

public abstract class Piece { //Deve ser abstrata por conta do m�todo "possibleMoves (abstrato)"

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
	} // N�o tem o setBoard, por que n�o pode permitir que o tabuleiro seja alterado.

	public abstract boolean [][] possibleMoves(); // possibleMoves � Abstract por uma pe�a ser um tipo gen�rico.
	// Os movimentos "especiais" das pe�as s�o uma matriz de booleanos de poss�veis movimentos das pe�as. (Ex.: Movimentos laterais da torre)
	// A matriz ter� valor verdadeiro nas posi��es poss�veis de movimenta��o e valor falso em todas as posi��es restantes. 
		
	
	public boolean possibleMove (Position position) {// possibleMove � para ver se a pe�a pode mover em determinada posi��o.
		return possibleMoves()[position.getRow()][position.getColumn()]; //Retorna V ou F se a pe�a pode ir para determinada posi��o
		// M�todo concreto "possibleMove" que usa o m�todo abstrato "possibleMoves" (Hook Methods - faz um gancho com a suclasse abstrata.
		//CHAMA UMA POSS�VEL IMPLEMENTA��O DA SUBCLASSE CONCRETA DA CLASSE PIECE.
		// existe um padr�o de projeto com esse nome ("Template Method"). Consegue fornecer uma implementa��o padr�o de um m�todo que depende de um m�todo abstrato.
	}
	
	public boolean IsThereAnyPossibleMove() { // IsThereAnyPossibleMove � para verificar se a pe�a n�o est� presa. Posi��o Verdadeira. Se todas as posi��es forem Falsas, a pe�a est� presa.
		boolean [][] mat = possibleMoves(); // Chama o m�todo abstrato booleano
		for (int i=0; i<mat.length; i++) { // Percorre as linhas  para ver se alguma posi��o � verdadeira.
			for (int j=0; j<mat.length; j++) {  // Percorre as colunas  para ver se alguma posi��o � verdadeira.
				if (mat[i][j]) { // Se a varredura retornar true 
					return true; // Diz que existe um movimento poss�vel
				}
			}
		}
		return false; //Caso n�o haja nenhum true na varredura, retorna false.
	}
}
