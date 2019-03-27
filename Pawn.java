/*********************************************
 * This is the class which will govern the Pawn piece.  This will take the
 * existing rules of the base ChessPiece method and expand upon them.
 */
public class Pawn extends ChessPiece {

	//Pawns attack differently from how they move, hence the creation of a separate boolean
	public boolean isAttackMode;
	//Pawns also move differently depending upon whether it is their first move
	public boolean isFirstMove;

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

		if ((board[move.fromRow + 1][move.fromColumn + 1].player() != this.player())
			|| board[move.fromRow + 1][move.fromColumn - 1].player() != this.player()) {
			isAttackMode = true;
		}
		else {
			isAttackMode = false;
		}

		//If it is the pawn's first move, it can move up to two spaces forward instead of
		//the normal one space.
		if (isFirstMove == true) {

			if (((move.fromRow + 1 == move.toRow) && (move.fromColumn == move.toColumn))
				|| ((move.fromRow + 2 == move.toRow) && (move.fromColumn == move.toColumn))) {

				//Sets the first move flag to false so it can no longer move two spaces forward
				isFirstMove = false;
				return true;
			}
		}

		//Pawns attack diagonally, not straight forward.  This boolean reflects that change
		//in moving pattern
		if (isAttackMode == true) {

			if ((move.fromRow + 1 == move.toRow) && ((move.fromColumn + 1 == move.toColumn) ||
					(move.fromColumn - 1 == move.toColumn))) {

				//Sets the first move flag to false in case the pawn is attacking from its
				//initial position
				isFirstMove = false;
				return true;
			}
		}

		//Pawns can normally move only one square forward.  This case covers all other
		//instances of its ability to move.
		if (isAttackMode == false && isFirstMove == false) {

			if ((move.fromRow + 1 == move.toRow) && (move.fromColumn == move.toColumn)) {

				return true;
			}
		}

		return false;
	}
}
