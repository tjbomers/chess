import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessModel implements IChessModel {
    private IChessPiece[][] board;
	private List<IChessPiece[][]> backups = new ArrayList<IChessPiece[][]>();
	private Player player;

	// declare other instance variables as needed

	public ChessModel() {
		board = new IChessPiece[8][8];
		player = Player.BLACK;

        board[7][0] = new Rook(Player.WHITE);
        board[7][1] = new Knight(Player.WHITE);
        board[7][2] = new Bishop(Player.WHITE);
        board[7][3] = new Queen(Player.WHITE);
        board[7][4] = new King(Player.WHITE);
        board[7][5] = new Bishop(Player.WHITE);
        board[7][6] = new Knight(Player.WHITE);
        board[7][7] = new Rook(Player.WHITE);
        for (int i = 0; i < 8; i++) {
        	board[6][i] = new Pawn(Player.WHITE);
		}


		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight(Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn(Player.BLACK);
		}
		backups.add(deepCopy(board));
	}

	public boolean isComplete() {
		boolean valid = false;
		return valid;
	}

	public boolean isValidMove(Move move) {
		boolean valid = false;

		if (board[move.fromRow][move.fromColumn] != null)
			if (board[move.fromRow][move.fromColumn].player() != currentPlayer()) {
				if (board[move.fromRow][move.fromColumn].isValidMove(move, board))
					valid = true;
			}
		return valid;
	}

	public void move(Move move) {
		board[move.toRow][move.toColumn] =  board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
		backups.add(deepCopy(board));
		this.setNextPlayer();
		if (player == Player.BLACK) {
			AI();
		}
	}

	public static void staticMove(IChessPiece[][] board, Move move) {
		board[move.toRow][move.toColumn] =  board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
	}

	public void undo(int d) {
		if(backups.size() > d ) {
			board = backups.get(backups.size() - 1 - d);
			backups.remove(backups.size() - 1);
		}
	}

	public boolean inCheck(Player p) {
		boolean valid = false;
		return valid;
	}


	public Player currentPlayer() {

		return player;
	}

	public int numRows() {

		return 8;
	}

	public int numColumns() {

		return 8;
	}

	public IChessPiece pieceAt(int row, int column) {

				return board[row][column];
	}

	public void setNextPlayer() {

		player = player.next();
	}

	public void setPiece(int row, int column, IChessPiece piece) {

		board[row][column] = piece;
	}

	public void AI() {
		Move theMove;
		Move testMove;
		IChessPiece[][] testBoard = new IChessPiece[8][8];

		//if the AI is in check, gets out of check with the first solution it sees
		if(inCheck(Player.BLACK)) {
			for(int fromRow = 0; fromRow < 8; fromRow ++) {
				for (int fromCol = 0; fromCol < 8; fromCol++) {
					if(board[fromRow][fromCol].player() == Player.BLACK) {
						for (int toRow = 0; toRow < 8; toRow ++) {
							for (int toCol = 0; toCol < 8; toCol++) {
								testMove = new Move(fromRow, fromCol, toRow, toCol);

								if(isValidMove(testMove)) {

								}
							}
						}
					}
				}
			}
		}

		//if the AI is not in check it tries to kill an enemy piece
		for(int fromRow = 0; fromRow < 8; fromRow ++) {
			for (int fromCol = 0; fromCol < 8; fromCol++) {
				if(board[fromRow][fromCol].player() == Player.BLACK) {
					for (int toRow = 0; toRow < 8; toRow ++) {
						for (int toCol = 0; toCol < 8; toCol++) {
							testMove = new Move(fromRow, fromCol, toRow, toCol);
							if(isValidMove(testMove) && board[toRow][toCol].player() == Player.BLACK) {
								//TODO
							}
						}
					}
				}
			}
		}
		/*
		 * b. Attempt to put opponent into check (or checkmate).
		 * 		i. Attempt to put opponent into check without losing your piece
		 *		ii. Perhaps you have won the game.
		 *
		 *c. Determine if any of your pieces are in danger,
		 *		i. Move them if you can.
		 *		ii. Attempt to protect that piece.
		 *
		 *d. Move a piece (pawns first) forward toward opponent king
		 *		i. check to see if that piece is in danger of being removed, if so, move a different piece.
		 */

	}

	private static IChessPiece[][] deepCopy(IChessPiece[][] original) {
		if (original == null) {
			return null;
		}

		final IChessPiece[][] result = new IChessPiece[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				result[i][j] = original[i][j];
			}
		}
		return result;
	}
}
