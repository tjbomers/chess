/*********************************************
 * This is the class which will govern the Pawn piece.  This will take the
 * existing rules of the base ChessPiece method and expand upon them.
 */
public class Pawn extends ChessPiece {

	/**
	 * Invokes the Player class
	 *
	 * @param player
	 */
	public Pawn(Player player) {

		super(player);
	}

	/**
	 * This will let the user know what piece type has been clicked.
	 *
	 * @return The type of piece, in this case Pawn
	 */
	public String type() {

		return "Pawn";
	}

	/**
	 * This method will determine what moves a pawn can legally make.  Since pawns
	 * have multiple sets of rules for moving depending upon the circumstances of the
	 * game, it is necessary for us to write multiple boolean cases to determine which
	 * set of rules applies to the current situation.
	 *
	 * @param move  a (@link W18project3.Move) object describing the move to be made.
	 * @param board the (@link W18project3.IChessBoard) in which this piece resides.
	 *
	 * @return Whether the pawn's move is valid or not
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {

		if (board[move.fromRow][move.fromColumn].player() == Player.WHITE)
			return isValidMoveWhite(move, board);
		else
			return isValidMoveBlack(move, board);
	}

	/**
	 * This method will check to see if this is a valid move for a white Pawn.
	 * The reason why we have to have separate methods for white and black Pawns
	 * is because Pawns can only move forward, which will necessitate separate rules
	 * for each color.
	 *
	 * @param move The action the Pawn takes
	 * @param board The board on which the game is played
	 * @return Whether or not the move is valid
	 */
	private boolean isValidMoveWhite(Move move, IChessPiece[][] board) {
		//This will check to see if the pawn is in its initial position. If so,
		//it will have the option to move two spaces forward (from 6 to 4)
		if(move.fromRow == 6) {
			//Checks to make sure it is in the same column (moving straight ahead)
			if((move.fromColumn - move.toColumn) == 0) {
				if(move.toRow == 4) {
					//Checks to see if there is a piece in the way
					if(board[move.toRow][move.toColumn] == null){
						return true;
					}
				}
			}
		}
		//Otherwise, the pawn can move one space at a time, with the usual check
		//to see if there is a piece in the way.
		if(move.fromRow - move.toRow == 1) {
			if (move.fromColumn - move.toColumn == 0) {
				if(board[move.toRow][move.toColumn] == null) {
					return true;
				}
			}
			//This checks to see if there is a black piece in the space one square
			//forward and one square to the left or right (from the Math.abs value)
			//If so, this is a legal attack.
			if(Math.abs(move.fromColumn - move.toColumn) == 1) {
				if (board[move.toRow][move.toColumn] != null) {
					if (board[move.toRow][move.toColumn].player() == Player.BLACK) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * This method will check to see if this is a valid move for a black Pawn.
	 * The reason why we have to have separate methods for white and black Pawns
	 * is because Pawns can only move forward, which will necessitate separate rules
	 * for each color.
	 *
	 * @param move The action the Pawn takes
	 * @param board The board on which the game is played
	 * @return Whether or not the move is valid
	 */
	private boolean isValidMoveBlack(Move move, IChessPiece[][] board) {
		//This will check to see if the pawn is in its initial position.  If so,
		//it has the option of moving two squares forward.
		if(move.fromRow == 1) {
			if((move.fromColumn - move.toColumn) == 0) {
				if(move.toRow == 3) {
					//Checking to make sure there is not a piece in the way
					if(board[move.toRow][move.toColumn] == null){
						return true;
					}
				}
			}
		}
		//Otherwise, the pawn can move one space at a time, with the usual check
		//to see if there is a piece in the way.
		if(move.fromRow - move.toRow == -1) {
			if (move.fromColumn - move.toColumn == 0) {
				if(board[move.toRow][move.toColumn] == null) {
					return true;
				}
			}
			//This checks to see if there is a black piece in the space one square
			//forward and one square to the left or right (from the Math.abs value)
			//If so, this is a legal attack.
			if(Math.abs(move.fromColumn - move.toColumn) == 1) {
				if (board[move.toRow][move.toColumn] != null) {
					if (board[move.toRow][move.toColumn].player() == Player.WHITE) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
