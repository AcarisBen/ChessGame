package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	// C�digo copiado do Stackoverflow para altera��o das cores do texto e do fundo
	// no Git Bash
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
	System.out.print("\033[H\033[2J");
	System.out.flush();
	}
	
	public static ChessPosition readChessPosition(Scanner sc) { //m�todo que le uma posi��o do usu�rio. Recebe o Scanner do programa principal
		try{
			String s = sc.nextLine(); // O usuario escreve a posi��o 
			char column = s.charAt(0); // ler o primeiro caracter do String
			int row = Integer.parseInt(s.substring(1)); //O segundo caracter � a linha. Recorta o String escrito e converte o resultado para inteiro
			return new ChessPosition(column, row); //Retorna os valores como "ChessPosition"
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8."); // Lan�a a exce��o que foi um erro de entrada de dados.
		}
	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {//Imprime n�o s� o tabuleiro, mas imprime agora toda a partida.
		printBoard(chessMatch.getPieces()); //Imprime o tabuleiro
		System.out.println();//Pula linha
		printCapturedPieces(captured);
		System.out.println(" Turn: " + chessMatch.getTurn()); //Informa o turno que est�
		System.out.println(" Waiting player: " + chessMatch.getCurrentPlayer()); // Mensagem que est� esperando o jogador jogar.
	}
		
	public static void printBoard(ChessPiece[][] pieces) { // M�todo para imprimir as pe�as da partida.
		System.out.println("   a b c d e f g h"); //Imprime a parte de cima do tabuleiro
		System.out.println();
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(" " + (8 - i) + "  "); // imprimi primeiro os n�meros do tabuleiro
			for (int j = 0; j < pieces.length; j++) { // imprimi a pe�a.
				printPiece(pieces[i][j], false); // imprime as pe�as nas posi��es, mas sem o fundo colorido
			}
			System.out.print(" " + (8 - i));
						
			System.out.println(); // leva para a pr�xima linha.
		
		}
		System.out.println();
		System.out.println("   a b c d e f g h"); // imprimi a parte de baixo do tabuleiro
	}
	
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) { // Sobrecarga do m�toda acima, mas com a matriz booleana "possibleMoves" para mostrar as poss�veis posi��es da pe�a selecionada.
		System.out.println();
		System.out.println("   a b c d e f g h"); //Imprime a parte de cima do tabuleiro
		System.out.println();
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(" " + (8 - i) + "  "); // imprimi primeiro os n�meros do tabuleiro
			for (int j = 0; j < pieces.length; j++) { // imprimi a pe�a.
				printPiece(pieces[i][j], possibleMoves[i][j]); //imprime as pe�as nas posi��es, mas com o fundo colorido indicando as futuras posi��es poss�veis
			}
			System.out.print(" " + (8 - i));
						
			System.out.println(); // leva para a pr�xima linha.
		
		}
		System.out.println();
		System.out.println("   a b c d e f g h"); // imprimi a parte de baixo do tabuleiro
	}

	private static void printPiece(ChessPiece piece, boolean background) { // M�todo para imprimir a pe�a no tabuleiro.
		//Baseado na pe�a "Piece" da classe "ChessPiece" e no "boolean" para pintar as futuras posi��es poss�veis da pe�a selecionada
		
		if (background) { //Testa se a vari�vel booleana "background" � verdadeira
				System.out.print(ANSI_BLUE_BACKGROUND); //Pinta a cor do fundo da tela de rosa
			} 
				
    	if (piece == null) { // Se n�o ter uma pe�a no tabuleiro
            System.out.print("-" + ANSI_RESET); //Imprime  "-" e Reseta a cor original
        }
        else {
            if (piece.getColor() == Color.WHITE) { //pinta as pecas de branco
                System.out.print(ANSI_WHITE + piece + ANSI_RESET); //Reseta a cor original
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET); //Pinta as pe�as de amarelo. e depois Reseta a cor original
            }
        }
        System.out.print(" ");
	}
	
	private static void printCapturedPieces(List<ChessPiece> captured) { //m�todo para imprimir as pe�as capturadas. Recebe uma lista de pe�as capturadas
		List <ChessPiece> white = captured.stream().filter(x -> x.getColor()== Color.WHITE).collect(Collectors.toList()); // Cria uma lista de pe�as capturadas brancas, com filtro com a fun��o Lambda.
		List <ChessPiece> black = captured.stream().filter(x -> x.getColor()== Color.BLACK).collect(Collectors.toList()); // Cria uma lista de pe�as capturadas pretas, com filtro com a fun��o Lambda.
	System.out.println(" Captured pieces:");
	System.out.print(" White: ");
	System.out.print(ANSI_WHITE);
	System.out.println(Arrays.toString(white.toArray())); //Imprime um array de valores (lista de pe�as brancas)
	System.out.print(ANSI_RESET);
	System.out.print(" Black: ");
	System.out.print(ANSI_YELLOW);
	System.out.println(Arrays.toString(black.toArray())); //Imprime um array de valores (lista de pe�as pretas)
	System.out.print(ANSI_RESET);
	System.out.println();
	}
	
}