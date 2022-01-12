package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List <ChessPiece> captured = new ArrayList<>();

		while (!chessMatch.getCheckMate()) { // REPETE indefinitamente, at� que n�o tenha um xeque-mate
			try {
				UI.clearScreen();
				System.out.println();
				UI.printMatch(chessMatch, captured); //Imprime o tabuleiro, o turno e a vez dedeterminado jogador e a lista de pe�as capturadas
				// UI.printBoard(chessMatch.getPieces()); // Antes era uma Fun��o para imprimir as pe�as e o tabuleiro da partida.
				// UI = classe user interface. printBoard = m�todo da classe UI para imprimir as pe�as da partida

				System.out.println();
				System.out.print(" Source: ");
				ChessPosition source = UI.readChessPosition(sc); // Digitar a posi��o de origem
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source); // Declara uma matriz booleana para receber o m�todo "possbleMoves"
				UI.clearScreen(); //Limpa a tela
				UI.printBoard(chessMatch.getPieces(), possibleMoves); // Imprime uma outra vers�o do tabuleiro, agora colorindo as posi��es para onde a peca pode se mover.
				
				System.out.println();
				System.out.print(" Target: ");
				ChessPosition target = UI.readChessPosition(sc); // Digitar a posi��o de destino

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target); // Chama a pe�a saindo da origem para o destino
				if (capturedPiece != null) { // Se realizar um movimento e resultar em uma "capturedPiece"
					captured.add(capturedPiece); // adicionar a capturedPiece na lista "captured"
				}
				
				if (chessMatch.getPromoted() != null) { // Se nessa partida, ocorrer uma promo��o.
					System.out.print("Promotion! Please, enter the piece for promotion (B/N/R/Q): "); //Pergunta pro jogador qual pe�a ele quer adicionar
					String type = sc.nextLine(); //jogador deve decidir qual pe�a
					chessMatch.replacePromotedPiece(type); //Troca a pe�a no m�todo "replacePromotedPiece"
				}
			
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
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}

}
