package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece { //Ao transformar o Piece em abstrata, deve alterar tbm a "ChessPiece"
	
	private Color color;
	private int moveCount;
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() { // N?o tem setColor por que n?o pode deixar alterar a cor.
		return color;
	}
	
	public void increaseMoveCount() { //incrementa a contagem dos movimentos das pe?as
		moveCount++;
	}
	
	public void decraseMoveCount() { //diminui a contagem dos movimentos das pe?as
		moveCount--;
	}
	
	
	public int getMoveCount() {
		return moveCount;
	}

	public ChessPosition getChessPosition() { 
		return ChessPosition.fromPosition(position); // Usa o m?todo est?tico "fromPosition" que converte uma posi??o "position" herdada da classe "Piece" para "ChessPosition"
		
	}
	protected boolean isThereOpponentPiece(Position position) { //isThereOpponentPiece A pe?a deve saber se existe uma pe?a advers?ria em uma dada posi??o.
		//Este m?todo ser? acess?vel por todas as pe?as, por isso que est? nesta classe.
		ChessPiece p = (ChessPiece)getBoard().piece(position); //Verifica se tem uma pe?a advers?ria na posi??o. Deve fazer um Downcasting para "ChessPiece"
		return p !=null && p.getColor() != color; //Testa se tem uma pe?a na posi??o e se a cor da pe?a na p.position (pe?a sendo capturada) ? diferente da que est? sendo mexida
	}
	
	
}
