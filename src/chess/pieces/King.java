package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece { // Classe que extede a classe "ChessPiece", que extende a classe "Piece"
	
	//O rei precisa ter acesso a classe "ChessMatch" para poder executar a jogada "Castrling" (Roque) para verificar os movimentos poss�veis.
	private ChessMatch chessMatch;
	
	public King(Board board, Color color, ChessMatch chessMatch) { // Constructor que pega o "color" da classe "ChessPiece" e o "board" da classe "piece" 	
		super(board, color);
		this.chessMatch = chessMatch; //Associa o "ChessMatch" ao "King"
	}

	@Override
	public String toString() { // Deve imprimir na tela a letra correspondente da Rei
		return "K";
	}
	
	private boolean canMove(Position position)	{ // O m�todo auxilia o rei para se mover em determinada posi��o "position". Feito para ver se tem alguma pe�a onde o rei ir� se mexer.
		ChessPiece p = (ChessPiece)getBoard().piece(position); // Saber se o rei pode mover para a posi��o. Pega a pe�a "p" na "position"
		return p == null || p.getColor() != getColor(); // Verifica se n�o tem pe�a "p" na posi��o (== null) ou se tem uma pe�a advers�ria.
	}
	
	private boolean testRookCastling (Position position) { // M�todo para auxiliar a condi��o da torre para Roque
		ChessPiece p = (ChessPiece)getBoard().piece(position); //declara a vari�vel "p" recebendo a pe�a na posi��o "position"
		return p != null && p instanceof Rook & p.getColor() == getColor() && p.getMoveCount() == 0; // testa e retorna se a pe�a � diferente de nulo, se ela � uma torre , se a pe�a � da mesma cor e que ainda n�o houve movimento com ela.
	}
	
	
	@Override //Implementa os m�todos da classe abstrata "possibleMoves"
	public boolean[][] possibleMoves() {
		 boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // Matriz de booleanos com a mesma dimens�o do tabuleiro, retornando valor falso. Deixa a pe�a como se tivesse presa
		
		 Position p = new Position(0,0); // Cria uma posi��o "p" auxiliar para testar toadas as 8 dire��es poss�veis do Rei.
		 //mover 1 casa pra cima
		 p.setValues(position.getRow() - 1, position.getColumn()); // Posi��o acima da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra baixo
		 p.setValues(position.getRow() + 1, position.getColumn()); // Posi��o abaixo da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra esquerda
		 p.setValues(position.getRow(), position.getColumn() - 1); // Posi��o a esquerda da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra direita
		 p.setValues(position.getRow(), position.getColumn() + 1); // Posi��o a direita da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra noroeste
		 p.setValues(position.getRow() - 1, position.getColumn() - 1); // Posi��o a noroeste da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra nordeste
		 p.setValues(position.getRow() - 1, position.getColumn() + 1); // Posi��o a nordeste da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra sudoeste
		 p.setValues(position.getRow() + 1, position.getColumn() - 1); // Posi��o a sudoeste da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //mover 1 casa pra sudeste
		 p.setValues(position.getRow() + 1, position.getColumn() + 1); // Posi��o a sudeste da posi��o de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posi��o "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posi��o "p"
		 }
		 
		 //#special moving castling - verifica se pode fazer o roque
		 if (getMoveCount() == 0 && !chessMatch.getCheck())  { // testa se o rei n�o se movimentou e se n�o est� em xeque 
			 // #specialmove castling kingside rook - Roque pequeno
			 Position posT1 = new Position(position.getRow(), position.getColumn() + 3); // identifica a posi��o da torre do rei � 3 casas a direita do rei
			 if (testRookCastling(posT1)) {// Testa se existe uma torre na posi��o posT1 apta para fazer o Roque
				 Position p1 = new Position(position.getRow(), position.getColumn() + 1); // Testa se a casa a direita do rei est� vazia
				 Position p2 = new Position(position.getRow(), position.getColumn() + 2); // Testa se a casa a esquerda da torre est� vazia
				 if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) { // Testa se n�o h� pe�as ocupando as casas do meio entre a torre e o rei
					mat[position.getRow()][position.getRow() +2] = true; // inclui na matriz de movimentos poss�veis o movimento do rei Roque
				 }
			 }
			 
			// #specialmove castling queenside rook - Roque grande
			 Position posT2 = new Position(position.getRow(), position.getColumn() - 4); // identifica a posi��o da torre do rei � 4 casas a esquerda do rei
			 if (testRookCastling(posT2)) {// Testa se existe uma torre na posi��o posT2 apta para fazer o Roque
				 Position p1 = new Position(position.getRow(), position.getColumn() - 1); // Testa se a casa a esquerda do rei est� vazia
				 Position p2 = new Position(position.getRow(), position.getColumn() - 2); // Testa se a casa do meio entre a torre e o rei est� vazia
				 Position p3 = new Position(position.getRow(), position.getColumn() - 3); // Testa se a casa a direita da torre est� vazia
				 if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) { // Testa se n�o h� pe�as ocupando as casas do meio entre a torre e o rei
					mat[position.getRow()][position.getRow() - 2] = true; // inclui na matriz de movimentos poss�veis o movimento do rei Roque
				 }
			 }
		 }
		 return mat;
	}
}
