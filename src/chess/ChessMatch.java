package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch { // Regras do jogo

	private int turn; 
	private Color currentPlayer;
	private Board board;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>(); //Instacia uma lista para controlar as pe�as restantes do tabuleiro
	private List<Piece> capturedPieces = new ArrayList<>(); // Instancia uma lista para controlar as pe�as capturadas.

	
	public ChessMatch() { // Quem sabe a dimens�o do tabuleiro � a class "ChessMatch"
		board = new Board(8, 8); // Inicia a partida com este tabuleiro.
		turn = 1; // O jogo inicia no turno 1.
		currentPlayer = Color.WHITE; // O primeiro jogador a iniciar o jogo � o branco
		initialSetup(); // Chama a configura��o inicial do tabuleiro
		}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	public ChessPiece[][] getPieces() { // Retorna a matriz de pe�as de xadrez correspondente a partida.
	
	// O "Board" tem uma matriz do tipo "Piece", mas o m�todo retorna o "ChessPiece" (Camada de xadrez).
	// Para o programa, n�o deve ser liberado pe�as do tipo "Piece", mas do tipo "ChessPiece". Pois � um desenvolvimento em camadas.
	// O programa deve enxergar a pe�a de xadrez, e n�o a pe�a interna do tabuleiro. Ent�o, deve transformar o "Piece" e ChessPiece.
	ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		
		for (int i=0; i<board.getRows(); i++) { // Percorre a matriz de pe�as do tabuleiro e pra cada pe�a, ser� feita um downcasting para "ChessPiece".
			for  (int j=0; j<board.getColumns(); j++) {
				mat [i][j] = (ChessPiece)board.piece(i, j);
				//Downcasting para ChessPiece. O programa interpreta que a pe�a � de "ChessPiece" e n�o s� "Piece"
			}
		}
		return mat; // Por fim, este c�digo retorna a matriz de pe�as da partida de xadrez.
	}
	
	public boolean [][]possibleMoves(ChessPosition sourcePosition) { // Avisa ao jogador quais posi��es a pe�a selecionada poder� ir.
		Position position = sourcePosition.toPosition(); // converte a posi��o do xadrez para uma posi��o normal
		validateSourcePosition(position); // valida a posi��o de origem assim que o jogador informar a pe�a
		return board.piece(position).possibleMoves(); // Retorna os movimentos poss�veis da posi��o
		
	}
	public ChessPiece performChessMove (ChessPosition sourcePosition, ChessPosition targetPosition) {
	// 	Move a pe�a da posi��o de origem para a posi��o de destino e se for o caso, tambem a posi��o de captura
		Position source = sourcePosition.toPosition(); //Converte posi�oes de origem para posi��es da matriz		
		Position target = targetPosition.toPosition(); //Converte posi�oes de destino para posi��es da matriz		
		validateSourcePosition(source); // Valida se a posi��o de origem havia uma pe�a. Chama o m�todo "validateSourcePosition"
		validateTargetPosition(source, target); // Valida se a posi��o de destino, para saber se o programa est� colocando a pe�a na posi��o final corretamente. Chama o m�todo "validateTargetPosition"
		Piece capturedPiece = makeMove(source, target); //Realiza o movimento da pe�a, baseado na posi��o de origem e destino.
		nextTurn(); // Chama o pr�ximo turno.
		return (ChessPiece) capturedPiece; // Retorna a pe�a capturada, com DownCasting da pe�a capturada do tipo "ChessPiece"
		}
	
	private Piece makeMove(Position source, Position target) { //L�gica de realizar um movimento baseado na posi��o de origem e de destino
		Piece p = board.removePiece(source); // Retira a pe�a na posi��o de origem
		Piece capturedPiece = board.removePiece(target); //Remove a pe�a que est� na posi��o de destino (Pe�a capturada)
		board.placePiece(p, target); // Coloca a pe�a "p" na posi��o da pe�a de destino.
		
		if (capturedPiece != null) { // se capturou uma pe�a
			piecesOnTheBoard.remove(capturedPiece); // remove a pe�a capturada da lista de pe�as no tabuleiro.
			capturedPieces.add(capturedPiece); // adiciona a pe�a capturada na lista de pe�as capturadas
		}
		
		return capturedPiece; //retorna a pe�a capturada.
	}
	
	private void validateSourcePosition(Position position) { // Exce��o para testar se existe uma pe�a na posi��o de origem 
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position"); //thereIsAPiece lan�a uma BoardException e aqui � uma ChessException, que tamb�m � uma BoardEception (Mais espec�fica).
																			  // Para facilitar para o programa, troca-se a "RuntimeException" na "ChessException" por "BoardException"
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { // Verifica se o turno pertence a cor da pe�a selecionada no turno.
			throw new ChessException("Wrong color piece selected. This turn belongs to the other player"); //Pega a pe�a do tabuleiro na posi��o "position", faz um DownCasting para "ChessPiece" e testa a cor dela.
			//Se a cor for diferente da cor do jogador do turno atual, ent�o seria uma pe�a do jogador advers�rio 
		}
		if (!board.piece(position).IsThereAnyPossibleMove()) { // Testa se existe movimentos poss�veis para pe�a. Se n�o ter movimentos (a pe�a estiver presa) deve retornar uma exce��o.
			throw new ChessException("There is no piece on source position");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) { // Valida se a posi��o de destino � um movimento poss�vel em rela��o � pe�a que est� na posi��o de origem
		if (!board.piece(source).possibleMove(target)) { // Testa se o movimento da pe�a na posi��o de destino n�o � um movimento poss�vel, em rela��o � posi��o de origem.
			throw new ChessException("The chosen piece can not move to target position."); // Caso seja um movimento n�o poss�vel, deve lan�ar a exce��o.
		}
	}
	
	private void nextTurn() {
		turn ++; //Incrementa o n�mero de turnos
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // Alterna os jogadores
		
	}
	private void placeNewPiece(char column, int row, ChessPiece piece) { //Opera��o de colocar as pe�as. Recebe as coordenadas do xadrez.
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); //instancia a pe�a na posi��o baseada da matriz.
		piecesOnTheBoard.add(piece); // Adiciona uma pe�a para a lista "piecesOnTheBoard"
	}
	private void initialSetup() { //Assim que iniciar� no tabuleiro
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
        }
}

