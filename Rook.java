package chess;

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

		//Checks for pieces in the path of the rook in the event it moves up
		if (move.fromRow < move.toRow && move.fromColumn == move.toColumn) {
			for (int i = move.fromRow; i <= move.toRow; i++) {
				if (board[i][move.fromColumn] != null) {
					//if piece is opposite color, take the piece
					//if piece is the same color, declare the move as false
					break;
				}
			}
		}

		//Checks for pieces in the path of the rook in the event it moves down
		if (move.fromRow > move.toRow && move.fromColumn == move.toColumn) {
			for (int i = move.fromRow; i >= move.toRow; i--) {
				if (board[i][move.fromColumn] != null) {
					//if piece is opposite color, take the piece
					//if piece is the same color, declare the move as false
					break;
				}
			}
		}

		//Checks for pieces in the path of the rook in the event it moves right
		if (move.fromColumn < move.toColumn && move.fromRow == move.toRow) {
			for (int i = move.fromColumn; i <= move.toColumn; i++) {
				if (board[move.fromRow][i] != null) {
					//if piece is opposite color, take the piece
					//if piece is the same color, declare the move as false
					break;
				}
			}
		}

		//Checks for pieces in the path of the rook in the event it moves left
		if (move.fromColumn > move.toColumn && move.fromRow == move.toRow) {
			for (int i = move.fromColumn; i >= move.toColumn; i--) {
				if (board[move.fromRow][i] != null) {
					//if piece is opposite color, take the piece
					//if piece is the same color, declare the move as false
				}
			}
		}


		return true;
		
	}
	
}
