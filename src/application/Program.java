package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
		

		ChessMatch chessMatch = new ChessMatch();
		UI.printBoard(chessMatch.getPieces()); // Fun��o para imprimir as pe�as da partida.
	 // UI = classe user interface. printBoard = m�todo da classe UI para imprimir as pe�as da partida
	}

}
