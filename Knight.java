/***************************************
 * This class will implement the moveset for the knight.  Unlike other
 * pieces with multi-square move potential, the knight will be able to jump
 * over other pieces.  The only concern is if a same-colored piece resides
 * at one of the eight possible landing spots for the knight.
 ***************************************/

public class Knight extends ChessPiece {

	public Knight(Player player) {
		super(player);
	}

	/**
	 * Shows the piece type to the player, in this case Knight
	 *
	 * @return Returns knight
	 */
	public String type() {
		return "Knight";
	}

	/** This method will check to see if a given move for the knight is valid.
	 * The knight moves by moving a total of three spaces, one horizontally and two
	 * vertically, or two horizontally and one vertically.  The eight if conditions
	 * at the beginning mark all possible landing spots for the knight.
	 *
	 * @param move  a (@link W18project3.Move) object describing the move to be made.
	 * @param board the (@link W18project3.IChessBoard) in which this piece resides.
	 *
	 * @return Returns a valid move if the move is valid
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		//This will check to see if the knight jumps two rows and one column or one row
		//and two columns in any direction.
		if ((Math.abs(move.fromRow - move.toRow) == 2 && Math.abs(move.fromColumn - move.toColumn) == 1)
				|| (Math.abs(move.fromRow - move.toRow) == 1 && Math.abs(move.fromColumn - move.toColumn) == 2)){
			//This will check to see if there is a piece in the way of the knight's landing spot.
			//If it's an enemy, take the piece.  If it's the same color piece, the move is invalid.
			if (board[move.toRow][move.toColumn] != null) {
				if ((board[move.toRow][move.toColumn].player() != board[move.fromRow][move.fromColumn].player())) {
					return true;
				} else return false;
			} else return true;
		}
		return false;
	}

}
