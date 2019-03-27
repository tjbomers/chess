/*********************************************
 * The Rook is a piece that can move multiple spaces, as long as it is moving
 * sideways, forwards, or backwards.  It cannot move through other pieces, but
 * otherwise has full range of the board.
 *********************************************/
public class Rook extends ChessPiece {

	public Rook(Player player) {
		
		super(player);
		
	}

	/**
	 * Shows the piece type to the player, in this case "Rook"
	 *
	 * @return Type Rook
	 */
	public String type() {
		
		return "Rook";
		
	}

	/**
	 * This method will set the rules for how a rook can move.
	 * @param move  a (@link W18project3.Move) object describing the move to be made.
	 * @param board the (@link W18project3.IChessBoard) in which this piece resides.
	 *
	 * @return
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {

		//Checks to see if the rook is trying to move on a diagonal, which is
		//illegal for that piece.
		if (move.fromRow != move.toRow && move.fromColumn != move.toColumn) {
			return false;
		}

		//Checks for pieces in the path of the rook in the event it moves up or down
		if (move.fromRow - move.toRow == 0) {
			for (int i = 0; i <= Math.abs(move.toColumn - move.fromColumn); i++) {
				if (board[move.toRow][move.toColumn] != null) {
					if (board[move.toRow][move.toColumn].player() != this.player()) {
						return true;
					}
				}
			}
		}

		//Checks for pieces in the path of the rook in the event it moves left or right
		if (move.fromColumn - move.toColumn == 0) {
			for (int i = 0; i <= Math.abs(move.toRow - move.fromRow); i++) {
				if (board[move.toRow][move.toColumn] != null) {
					if (board[move.toRow][move.toColumn].player() != this.player()) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
