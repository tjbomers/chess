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
	 * @return Will show Bishops
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
		if (board[move.fromRow][move.fromColumn] == board[move.toRow][move.toColumn]) {
			return false;
		}
		//This will check the absolute value when comparing the number of rows moved and
		//the number of columns moved.  If they are unequal, this is an invalid move since
		//the Bishop is not moving on a diagonal.
		if (Math.abs(move.fromRow - move.toRow) != (Math.abs(move.fromColumn - move.toColumn))) {
			return false;
		}
		//This will check and see if there is a piece in the way.  If that piece is the player's
		//own piece, then the Bishop cannot move there.
		if (board[move.toRow][move.toColumn] != null) {
			if (board[move.toRow][move.toColumn].player() == (board[move.fromRow][move.fromColumn].player())) {
				return false;
			}
		}

		//This will check to see how many rows the Bishop is moving in total.  This will
		//be used in a later for loop to check for pieces in the way.
		int magnitude = Math.abs(move.fromRow - move.toRow);

		//This separates the space surrounding the Bishop into four quadrants.  With each
		//quadrant comes a different set of math to determine the validity of a move.
		int quadrant = -1;
		if (move.toRow - move.fromRow < 0  && move.toColumn - move.fromColumn > 0) {
			quadrant = 1;
		} else if (move.toRow - move.fromRow > 0  && move.toColumn - move.fromColumn > 0) {
			quadrant = 2;
		} else if (move.toRow - move.fromRow > 0  && move.toColumn - move.fromColumn < 0) {
			quadrant = 3;
		} else if (move.toRow - move.fromRow < 0  && move.toColumn - move.fromColumn < 0){
			quadrant = 4;
		}

		//This for loop will iterate through each step in the Bishop's path, with different
		//conditions depending on which quadrant the move falls under.  If at any point in
		//the move the Bishop runs into a piece (!= null), the proposed move is invalid.
		for (int i = 1; i < magnitude; i++) {
			//Down and to the right
			if(quadrant == 1) {
				if(board[move.fromRow - i][move.fromColumn + i] != null) {
					return false;
				}
			//Up and to the right
			} else if(quadrant == 2) {
				if(board[move.fromRow + i][move.fromColumn + i] != null) {
					return false;
				}
			//Up and to the left
			} else if(quadrant == 3) {
				if(board[move.fromRow + i][move.fromColumn - i] != null) {
					return false;
				}
			//Down and to the left
			} else if(quadrant == 4) {
				if(board[move.fromRow - i][move.fromColumn - i] != null) {
					return false;
				}
			}
		}

		return true;
	}

}
