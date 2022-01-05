package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();

		while (true) { // REPETE indefinitamente,
			try {
				UI.clearScreen();

				UI.printBoard(chessMatch.getPieces()); // Função para imprimir as peças e o tabuleiro da partida.
				// UI = classe user interface. printBoard = método da classe UI para imprimir as
				// peças da partida

				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc); // Digitar a posição de origem

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc); // Digitar a posição de destino

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target); // Chama a peça saindo da origem
																						// para o destino
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				System.out.println("Please, press ENTER to continue.");
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				System.out.println("Please, press ENTER to continue.");
				sc.nextLine();
			}
		}

	}

}
