package application;

import chess.ChessPiece;


public class UI {
	
	public static void printBoard(ChessPiece[][] pieces) { // M�todo para imprimir as pe�as da partida.
	for (int i =0; i<pieces.length; i++) { 
		System.out.print((8 - i) + " "); // imprimi primeiro os n�meros do tabuleiro
		for (int j=0; j<pieces.length; j++) { // imprimi a pe�a.
			printPiece(pieces[i][j]);
		}
		System.out.println(); // leva para a pr�xima linha.
	}
	System.out.println("  a b c d e f g h"); // imprimi a parte de baixo do tabuleiro
	}
	
	private static void printPiece(ChessPiece piece) { // M�todo para imprimir uma �nica pe�a.
		if (piece == null) { // Se n�o ter pe�a na posi��o, imprime []
			System.out.print("[]");
		} else { 
			System.out.print(piece); // Se ter pe�a na posi��o, imprime a pe�a.
		}
		System.out.print(" ");
	}
}


