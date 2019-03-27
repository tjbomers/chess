package chess;

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
	public boolean isValidMove(Move move, IChessPiece[][] board){

		//Checks to see if the knight moved up two rows and right one column
		if (board[move.fromRow + 2][move.fromColumn + 1] == board[move.toRow][move.toColumn]) {
			if (board[move.toRow][move.toColumn].player() != this.player()) {
				return true;
			}
			return true;
		}

		//Checks to see if the knight moved up one row and right two columns
		if (board[move.fromRow + 1][move.fromColumn + 2] == board[move.toRow][move.toColumn]) {
			if (board[move.toRow][move.toColumn].player() != this.player()) {
				return true;
			}
			return true;
		}

		//Checks to see if the knight moved up two rows and left one column
		if (board[move.fromRow + 2][move.fromColumn - 1] == board[move.toRow][move.toColumn]) {
			if (board[move.toRow][move.toColumn].player() != this.player()) {
				return true;
			}
			return true;
		}

		//Checks to see if the knight moved up one row and left two columns
		if (board[move.fromRow + 1][move.fromColumn - 2] == board[move.toRow][move.toColumn]) {
			if (board[move.toRow][move.toColumn].player() != this.player()) {
				return true;
			}
			return true;
		}

		//Checks to see if the knight moved down two rows and right one column
		if (board[move.fromRow - 2][move.fromColumn + 1] == board[move.toRow][move.toColumn]) {
			if (board[move.toRow][move.toColumn].player() != this.player()) {
				return true;
			}
			return true;
		}

		//Checks to see if the knight moved down one row and right two columns
		if (board[move.fromRow - 1][move.fromColumn + 2] == board[move.toRow][move.toColumn]) {
			if (board[move.toRow][move.toColumn].player() != this.player()) {
				return true;
			}
			return true;
		}

		//Checks to see if the knight moved down two rows and left one column
		if (board[move.fromRow - 2][move.fromColumn - 1] == board[move.toRow][move.toColumn]) {
			if (board[move.toRow][move.toColumn].player() != this.player()) {
				return true;
			}
			return true;
		}

		//Checks to see if the knight moved down one row and left two columns
		if (board[move.fromRow - 1][move.fromColumn - 2] == board[move.toRow][move.toRow]) {
			if (board[move.toRow][move.toColumn].player() != this.player()) {
				return true;
			}
			return true;
		}

		return false;
	}

}
