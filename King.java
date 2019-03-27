/****************************************
 * This class will detail the legal moveset of the King.  Unlike the
 * Queen, the King can only move in one square each direction.  Also
 * unlike the Queen, it cannot put itself into a position to be attacked
 * the next turn (or in other words put itself in check).
 *
 *****************************************/
public class King extends ChessPiece {

	public King(Player player) {
		super(player);
	}

	/**
	 * This method will let the player know the given piece is the king
	 *
	 * @return Returns the King value
	 */
	public String type() {
		return "King";
	}

	/**
	 * This method will determine the legal moveset of the King piece.  Unlike the
	 * other pieces, we have to check and see if a given move will put the King in
	 * check.
	 *
	 * @param move  a (@link W18project3.Move) object describing the move to be made.
	 * @param board the (@link W18project3.IChessBoard) in which this piece resides.
	 *
	 * @return
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		//This loop will check all of the squares surrounding the king for legal moves
		if (Math.abs(move.fromRow - move.toRow) <= 1 && Math.abs(move.fromColumn - move.toColumn) <= 1){
			if (board[move.toRow][move.toColumn] != null) {
				if ((board[move.toRow][move.toColumn].player() != board[move.fromRow][move.fromColumn].player())) {
					return true;
				} else return false;
			} else return true;
		}
		return false;
	}
}
