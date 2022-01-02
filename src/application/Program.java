package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
		

		ChessMatch chessMatch = new ChessMatch();
		UI.printBoard(chessMatch.getPieces()); // Função para imprimir as peças da partida.
	 // UI = classe user interface. printBoard = método da classe UI para imprimir as peças da partida
	}

}
