package application;

import chess.ChessPiece;


public class UI {
	
	public static void printBoard(ChessPiece[][] pieces) { // Método para imprimir as peças da partida.
	for (int i =0; i<pieces.length; i++) { 
		System.out.print((8 - i) + " "); // imprimi primeiro os números do tabuleiro
		for (int j=0; j<pieces.length; j++) { // imprimi a peça.
			printPiece(pieces[i][j]);
		}
		System.out.println(); // leva para a próxima linha.
	}
	System.out.println("  a b c d e f g h"); // imprimi a parte de baixo do tabuleiro
	}
	
	private static void printPiece(ChessPiece piece) { // Método para imprimir uma única peça.
		if (piece == null) { // Se não ter peça na posição, imprime []
			System.out.print("[]");
		} else { 
			System.out.print(piece); // Se ter peça na posição, imprime a peça.
		}
		System.out.print(" ");
	}
}


