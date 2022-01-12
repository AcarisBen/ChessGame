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

		while (!chessMatch.getCheckMate()) { // REPETE indefinitamente, até que não tenha um xeque-mate
			try {
				UI.clearScreen();
				System.out.println();
				UI.printMatch(chessMatch, captured); //Imprime o tabuleiro, o turno e a vez dedeterminado jogador e a lista de peças capturadas
				// UI.printBoard(chessMatch.getPieces()); // Antes era uma Função para imprimir as peças e o tabuleiro da partida.
				// UI = classe user interface. printBoard = método da classe UI para imprimir as peças da partida

				System.out.println();
				System.out.print(" Source: ");
				ChessPosition source = UI.readChessPosition(sc); // Digitar a posição de origem
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source); // Declara uma matriz booleana para receber o método "possbleMoves"
				UI.clearScreen(); //Limpa a tela
				UI.printBoard(chessMatch.getPieces(), possibleMoves); // Imprime uma outra versão do tabuleiro, agora colorindo as posições para onde a peca pode se mover.
				
				System.out.println();
				System.out.print(" Target: ");
				ChessPosition target = UI.readChessPosition(sc); // Digitar a posição de destino

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target); // Chama a peça saindo da origem para o destino
				if (capturedPiece != null) { // Se realizar um movimento e resultar em uma "capturedPiece"
					captured.add(capturedPiece); // adicionar a capturedPiece na lista "captured"
				}
				
				if (chessMatch.getPromoted() != null) { // Se nessa partida, ocorrer uma promoção.
					System.out.print("Promotion! Please, enter the piece for promotion (B/N/R/Q): "); //Pergunta pro jogador qual peça ele quer adicionar
					String type = sc.nextLine(); //jogador deve decidir qual peça
					chessMatch.replacePromotedPiece(type); //Troca a peça no método "replacePromotedPiece"
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
