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

				UI.printBoard(chessMatch.getPieces()); // Fun��o para imprimir as pe�as e o tabuleiro da partida.
				// UI = classe user interface. printBoard = m�todo da classe UI para imprimir as
				// pe�as da partida

				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc); // Digitar a posi��o de origem

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc); // Digitar a posi��o de destino

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target); // Chama a pe�a saindo da origem
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
