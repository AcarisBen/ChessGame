package boardgame;

public abstract class Piece { //Deve ser abstrata por conta do método "possibleMoves (abstrato)"

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
	} // Não tem o setBoard, por que não pode permitir que o tabuleiro seja alterado.

	public abstract boolean [][] possibleMoves(); // possibleMoves é Abstract por uma peça ser um tipo genérico.
	// Os movimentos "especiais" das peças são uma matriz de booleanos de possíveis movimentos das peças. (Ex.: Movimentos laterais da torre)
	// A matriz terá valor verdadeiro nas posições possíveis de movimentação e valor falso em todas as posições restantes. 
		
	
	public boolean possibleMove (Position position) {// possibleMove é para ver se a peça pode mover em determinada posição.
		return possibleMoves()[position.getRow()][position.getColumn()]; //Retorna V ou F se a peça pode ir para determinada posição
		// Método concreto "possibleMove" que usa o método abstrato "possibleMoves" (Hook Methods - faz um gancho com a suclasse abstrata.
		//CHAMA UMA POSSÍVEL IMPLEMENTAÇÃO DA SUBCLASSE CONCRETA DA CLASSE PIECE.
		// existe um padrão de projeto com esse nome ("Template Method"). Consegue fornecer uma implementação padrão de um método que depende de um método abstrato.
	}
	
	public boolean IsThereAnyPossibleMove() { // IsThereAnyPossibleMove é para verificar se a peça não está presa. Posição Verdadeira. Se todas as posições forem Falsas, a peça está presa.
		boolean [][] mat = possibleMoves(); // Chama o método abstrato booleano
		for (int i=0; i<mat.length; i++) { // Percorre as linhas  para ver se alguma posição é verdadeira.
			for (int j=0; j<mat.length; j++) {  // Percorre as colunas  para ver se alguma posição é verdadeira.
				if (mat[i][j]) { // Se a varredura retornar true 
					return true; // Diz que existe um movimento possível
				}
			}
		}
		return false; //Caso não haja nenhum true na varredura, retorna false.
	}
}
