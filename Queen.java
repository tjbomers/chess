/***********************************************
 * This class will establish the legal moves for the Queen piece.  As the Queen
 * is essentially the Bishop and Rook pieces rolled into one, if we take the
 * movesets of each and combine them into this class, that will complete the
 * Queen's moveset.
 ************************************************/
public class Queen extends ChessPiece {

	public Queen(Player player) {
		super(player);

	}

	/**
	 * This will let the player know this is the Queen piece
	 *
	 * @return Will return value Queen
	 */
	public String type() {
		return "Queen";
		
	}

	/**
	 * The Bishop and Rook classes will lay the foundation for the Queen piece.  By
	 * combining their movesets, the Queen will have her own moveset enabled properly.
	 *
	 * @param move  a (@link W18project3.Move) object describing the move to be made.
	 * @param board the (@link W18project3.IChessBoard) in which this piece resides.
	 *
	 * @return Returns a valid move
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		//Incorporates the bishop moveset

		Bishop move1 = new Bishop(board[move.fromRow][move.fromColumn].player());
		//Incorporates the rook moveset
		Rook move2 = new Rook(board[move.fromRow][move.fromColumn].player());
		//If either moveset is followed, this is a valid move

		return (move1.isValidMove(move, board) || move2.isValidMove(move, board));
	}

	public int score() {
		return 9;
	}
}
