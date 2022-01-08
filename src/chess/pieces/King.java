package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece { // Classe que extede a classe "ChessPiece", que extende a classe "Piece"
	
	//O rei precisa ter acesso a classe "ChessMatch" para poder executar a jogada "Castrling" (Roque) para verificar os movimentos possíveis.
	private ChessMatch chessMatch;
	
	public King(Board board, Color color, ChessMatch chessMatch) { // Constructor que pega o "color" da classe "ChessPiece" e o "board" da classe "piece" 	
		super(board, color);
		this.chessMatch = chessMatch; //Associa o "ChessMatch" ao "King"
	}

	@Override
	public String toString() { // Deve imprimir na tela a letra correspondente da Rei
		return "K";
	}
	
	private boolean canMove(Position position)	{ // O método auxilia o rei para se mover em determinada posição "position". Feito para ver se tem alguma peça onde o rei irá se mexer.
		ChessPiece p = (ChessPiece)getBoard().piece(position); // Saber se o rei pode mover para a posição. Pega a peça "p" na "position"
		return p == null || p.getColor() != getColor(); // Verifica se não tem peça "p" na posição (== null) ou se tem uma peça adversária.
	}
	
	private boolean testRookCastling (Position position) { // Método para auxiliar a condição da torre para Roque
		ChessPiece p = (ChessPiece)getBoard().piece(position); //declara a variável "p" recebendo a peça na posição "position"
		return p != null && p instanceof Rook & p.getColor() == getColor() && p.getMoveCount() == 0; // testa e retorna se a peça é diferente de nulo, se ela é uma torre , se a peça é da mesma cor e que ainda não houve movimento com ela.
	}
	
	
	@Override //Implementa os métodos da classe abstrata "possibleMoves"
	public boolean[][] possibleMoves() {
		 boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // Matriz de booleanos com a mesma dimensão do tabuleiro, retornando valor falso. Deixa a peça como se tivesse presa
		
		 Position p = new Position(0,0); // Cria uma posição "p" auxiliar para testar toadas as 8 direções possíveis do Rei.
		 //mover 1 casa pra cima
		 p.setValues(position.getRow() - 1, position.getColumn()); // Posição acima da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra baixo
		 p.setValues(position.getRow() + 1, position.getColumn()); // Posição abaixo da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra esquerda
		 p.setValues(position.getRow(), position.getColumn() - 1); // Posição a esquerda da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra direita
		 p.setValues(position.getRow(), position.getColumn() + 1); // Posição a direita da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra noroeste
		 p.setValues(position.getRow() - 1, position.getColumn() - 1); // Posição a noroeste da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra nordeste
		 p.setValues(position.getRow() - 1, position.getColumn() + 1); // Posição a nordeste da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra sudoeste
		 p.setValues(position.getRow() + 1, position.getColumn() - 1); // Posição a sudoeste da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //mover 1 casa pra sudeste
		 p.setValues(position.getRow() + 1, position.getColumn() + 1); // Posição a sudeste da posição de origem do rei
		 if (getBoard().positionExists(p) && canMove(p)) { //se a posição "p" existe e pode se mover pra "p"
			 mat[p.getRow()][p.getColumn()] = true; //pode se mover para a posição "p"
		 }
		 
		 //#special moving castling - verifica se pode fazer o roque
		 if (getMoveCount() == 0 && !chessMatch.getCheck())  { // testa se o rei não se movimentou e se não está em xeque 
			 // #specialmove castling kingside rook - Roque pequeno
			 Position posT1 = new Position(position.getRow(), position.getColumn() + 3); // identifica a posição da torre do rei é 3 casas a direita do rei
			 if (testRookCastling(posT1)) {// Testa se existe uma torre na posição posT1 apta para fazer o Roque
				 Position p1 = new Position(position.getRow(), position.getColumn() + 1); // Testa se a casa a direita do rei está vazia
				 Position p2 = new Position(position.getRow(), position.getColumn() + 2); // Testa se a casa a esquerda da torre está vazia
				 if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) { // Testa se não há peças ocupando as casas do meio entre a torre e o rei
					mat[position.getRow()][position.getRow() +2] = true; // inclui na matriz de movimentos possíveis o movimento do rei Roque
				 }
			 }
			 
			// #specialmove castling queenside rook - Roque grande
			 Position posT2 = new Position(position.getRow(), position.getColumn() - 4); // identifica a posição da torre do rei é 4 casas a esquerda do rei
			 if (testRookCastling(posT2)) {// Testa se existe uma torre na posição posT2 apta para fazer o Roque
				 Position p1 = new Position(position.getRow(), position.getColumn() - 1); // Testa se a casa a esquerda do rei está vazia
				 Position p2 = new Position(position.getRow(), position.getColumn() - 2); // Testa se a casa do meio entre a torre e o rei está vazia
				 Position p3 = new Position(position.getRow(), position.getColumn() - 3); // Testa se a casa a direita da torre está vazia
				 if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) { // Testa se não há peças ocupando as casas do meio entre a torre e o rei
					mat[position.getRow()][position.getRow() - 2] = true; // inclui na matriz de movimentos possíveis o movimento do rei Roque
				 }
			 }
		 }
		 return mat;
	}
}
