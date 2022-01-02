package boardgame;

public class BoardException extends RuntimeException { // Execção opcional de ser tratada
	private static final long serialVersionUID = 1L; // Número de versão

	public BoardException(String msg) { // Cpnstrutr que recebe a mensagem 
		super(msg); // Repassa a mensagem para o construtor da superclasse "RuntimeException"
	}

}
