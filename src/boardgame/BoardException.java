package boardgame;

public class BoardException extends RuntimeException { // Exec��o opcional de ser tratada
	private static final long serialVersionUID = 1L; // N�mero de vers�o

	public BoardException(String msg) { // Cpnstrutr que recebe a mensagem 
		super(msg); // Repassa a mensagem para o construtor da superclasse "RuntimeException"
	}

}
