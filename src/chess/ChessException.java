package chess;

public class ChessException extends RuntimeException { //Antes era uma RuntimeException, mas ao criar a validateSourcePosition, trocou por BoardException. Pois uma ChessException é uma BoardException
	private static final long serialVersionUID = 1L;

	public ChessException (String msg) {
		super(msg);
	}
	}
