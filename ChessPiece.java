/********************************************
 * Pieces written by Matt Hendrick
 ********************************************/

/********************************************
 * This class will formulate the main framework of a chess
 * piece.  All code written here will be common to all pieces
 * (i.e. it will never be possible for a piece to go outside
 * the bounds of the chessboard.
 ********************************************/

public abstract class ChessPiece implements IChessPiece {

	//Establishes the player as the owner
	private Player owner;

	/**
	 * This method will set the player as the owner of a given piece
	 * @param player The owner of the chess piece
	 */

	protected ChessPiece(Player player) {

		this.owner = player;

	}

	/**
	 * This is an abstract type that will be elaborated upon when the individual
	 * pieces are programmed.
	 *
	 * @return Nothing to return in the abstract version of this method
	 */
	public abstract String type();

	/**
	 * This method will determine what pieces belong to which players.
	 *
	 * @return This will return the owner of the piece in question.
	 */

	public Player player() {

		return owner;

	}



	/**
	 * This method will determine whether a move is valid or not.  This framework
	 * will be common for all different types of pieces, and the methods for individual
	 * piece types will add additional rules to what constitutes a valid move.
	 *
	 * @param move  a (@link W18project3.Move) object describing the move to be made.
	 * @param board the (@link W18project3.IChessBoard) in which this piece resides.
	 *
	 * @return Returns whether or not a move is valid
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {

		// Sets the boundaries of the piece so they can't move outside the board's range
		if (move.toRow < 0 || move.toRow > 7 || move.toColumn < 0 || move.toColumn > 7) {
			return false;
		}

		// Checks to see if an actual move has been made.  If so, mark as true.
		if (!((move.fromRow == move.toRow) && (move.fromColumn == move.toColumn))) {
			return true;
		}

		//Verify that this piece is located at the location from which it is moving.
		if (board[move.fromRow][move.fromColumn].player() != this.player()) {
			return false;
		}
		if (board[move.toRow][move.toColumn].player() != this.player()) {
			return false;
		}

		return true;

	}
}
