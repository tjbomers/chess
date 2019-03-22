package chess;

/******************************************
 * This class will detail how the Bishop moves.  Much like the Rook
 * the Bishop can move multiple spaces at once in a diagonal.  Also,
 * like the Rook, we have to check for pieces in the way of travel.
 ******************************************/

public class Bishop extends ChessPiece {

	public Bishop(Player player) {
		super(player);
	}

	/**
	 * Shows the piece type to the player, in this case "Bishop"
	 *
	 * @return Will show Bishop
	 */
	public String type() {

		return "Bishop";
	}

	/**
	 * This method will set the rules on how a bishop can move
	 *
	 * @param move  a (@link W18project3.Move) object describing the move to be made.
	 * @param board the (@link W18project3.IChessBoard) in which this piece resides.
	 *
	 * @return Will return true if the move is valid
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {

		//This will check and see if the Bishop is trying to move straight up,
		//down, left, or right.  If so, the move is invalid.
		if (move.fromRow == move.toRow || move.fromColumn == move.toColumn) {
			return false;
		}

		//This will check to see if the Bishop is moving at a 45 degree angle.  This
		//is accomplished by checking to see if it moves the same number of horizontal
		//spaces as it does vertical spaces
		if (board[move.fromRow][move.fromColumn] != board[move.toRow][move.toColumn]) {
			if ((move.fromRow - move.toRow) == ((move.fromColumn - move.toColumn) ||
					(move.toColumn - move.fromColumn)) {
				return true;
			}
		}

		if (board[move.fromRow][move.fromColumn] != board[move.toRow][move.toColumn]) {
			if ((move.toRow - move.fromRow) == ((move.fromColumn - move.toColumn) ||
					(move.toColumn - move.fromColumn))) {
				return true;
			}
		}

		return false;
	}
}
