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

	// Código copiado do Stackoverflow para alteração das cores do texto e do fundo
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
	
	public static ChessPosition readChessPosition(Scanner sc) { //método que le uma posição do usuário. Recebe o Scanner do programa principal
		try{
			String s = sc.nextLine(); // O usuario escreve a posição 
			char column = s.charAt(0); // ler o primeiro caracter do String
			int row = Integer.parseInt(s.substring(1)); //O segundo caracter é a linha. Recorta o String escrito e converte o resultado para inteiro
			return new ChessPosition(column, row); //Retorna os valores como "ChessPosition"
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8."); // Lança a exceção que foi um erro de entrada de dados.
		}
	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {//Imprime não só o tabuleiro, mas imprime agora toda a partida.
		printBoard(chessMatch.getPieces()); //Imprime o tabuleiro
		System.out.println();//Pula linha
		printCapturedPieces(captured);
		System.out.println(" Turn: " + chessMatch.getTurn()); //Informa o turno que está
		System.out.println(" Waiting player: " + chessMatch.getCurrentPlayer()); // Mensagem que está esperando o jogador jogar.
	}
		
	public static void printBoard(ChessPiece[][] pieces) { // Método para imprimir as peças da partida.
		System.out.println("   a b c d e f g h"); //Imprime a parte de cima do tabuleiro
		System.out.println();
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(" " + (8 - i) + "  "); // imprimi primeiro os números do tabuleiro
			for (int j = 0; j < pieces.length; j++) { // imprimi a peça.
				printPiece(pieces[i][j], false); // imprime as peças nas posições, mas sem o fundo colorido
			}
			System.out.print(" " + (8 - i));
						
			System.out.println(); // leva para a próxima linha.
		
		}
		System.out.println();
		System.out.println("   a b c d e f g h"); // imprimi a parte de baixo do tabuleiro
	}
	
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) { // Sobrecarga do métoda acima, mas com a matriz booleana "possibleMoves" para mostrar as possíveis posições da peça selecionada.
		System.out.println();
		System.out.println("   a b c d e f g h"); //Imprime a parte de cima do tabuleiro
		System.out.println();
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(" " + (8 - i) + "  "); // imprimi primeiro os números do tabuleiro
			for (int j = 0; j < pieces.length; j++) { // imprimi a peça.
				printPiece(pieces[i][j], possibleMoves[i][j]); //imprime as peças nas posições, mas com o fundo colorido indicando as futuras posições possíveis
			}
			System.out.print(" " + (8 - i));
						
			System.out.println(); // leva para a próxima linha.
		
		}
		System.out.println();
		System.out.println("   a b c d e f g h"); // imprimi a parte de baixo do tabuleiro
	}

	private static void printPiece(ChessPiece piece, boolean background) { // Método para imprimir a peça no tabuleiro.
		//Baseado na peça "Piece" da classe "ChessPiece" e no "boolean" para pintar as futuras posições possíveis da peça selecionada
		
		if (background) { //Testa se a variável booleana "background" é verdadeira
				System.out.print(ANSI_BLUE_BACKGROUND); //Pinta a cor do fundo da tela de rosa
			} 
				
    	if (piece == null) { // Se não ter uma peça no tabuleiro
            System.out.print("-" + ANSI_RESET); //Imprime  "-" e Reseta a cor original
        }
        else {
            if (piece.getColor() == Color.WHITE) { //pinta as pecas de branco
                System.out.print(ANSI_WHITE + piece + ANSI_RESET); //Reseta a cor original
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET); //Pinta as peças de amarelo. e depois Reseta a cor original
            }
        }
        System.out.print(" ");
	}
	
	private static void printCapturedPieces(List<ChessPiece> captured) { //método para imprimir as peças capturadas. Recebe uma lista de peças capturadas
		List <ChessPiece> white = captured.stream().filter(x -> x.getColor()== Color.WHITE).collect(Collectors.toList()); // Cria uma lista de peças capturadas brancas, com filtro com a função Lambda.
		List <ChessPiece> black = captured.stream().filter(x -> x.getColor()== Color.BLACK).collect(Collectors.toList()); // Cria uma lista de peças capturadas pretas, com filtro com a função Lambda.
	System.out.println(" Captured pieces:");
	System.out.print(" White: ");
	System.out.print(ANSI_WHITE);
	System.out.println(Arrays.toString(white.toArray())); //Imprime um array de valores (lista de peças brancas)
	System.out.print(ANSI_RESET);
	System.out.print(" Black: ");
	System.out.print(ANSI_YELLOW);
	System.out.println(Arrays.toString(black.toArray())); //Imprime um array de valores (lista de peças pretas)
	System.out.print(ANSI_RESET);
	System.out.println();
	}
	
}