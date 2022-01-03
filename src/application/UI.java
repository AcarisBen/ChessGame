package application;

import chess.ChessPiece;
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

	public static void printBoard(ChessPiece[][] pieces) { // Método para imprimir as peças da partida.
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " "); // imprimi primeiro os números do tabuleiro
			for (int j = 0; j < pieces.length; j++) { // imprimi a peça.
				printPiece(pieces[i][j]);
			}
			System.out.println(); // leva para a próxima linha.
		}
		System.out.println("  a b c d e f g h"); // imprimi a parte de baixo do tabuleiro
	}

	private static void printPiece(ChessPiece piece) {
		
    	if (piece == null) {
            System.out.print("-");
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
}
