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


		return true;
	}
}
