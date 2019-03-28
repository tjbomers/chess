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

        int magnitude = Math.abs(move.fromRow - move.toRow + move.fromColumn - move.toColumn);
        int direction = -1;
        if (move.toRow - move.fromRow < 0  && move.toColumn - move.fromColumn == 0) {
            direction = 1;
        } else if (move.toRow - move.fromRow == 0  && move.toColumn - move.fromColumn > 0) {
            direction = 2;
        } else if (move.toRow - move.fromRow > 0  && move.toColumn - move.fromColumn == 0) {
            direction = 3;
        } else if(move.toRow - move.fromRow == 0  && move.toColumn - move.fromColumn < 0) {
            direction = 4;
        }

        for (int i = 1; i < magnitude; i++) {
            if(direction == 1) {
                if(board[move.fromRow - i][move.fromColumn] != null) {
                    return false;
                }
            } else if(direction == 2) {
                if(board[move.fromRow][move.fromColumn + i] != null) {
                    return false;
                }
            } else if(direction == 3) {
                if(board[move.fromRow + i][move.fromColumn] != null) {
                    return false;
                }
            } else  if(direction == 4){
                if(board[move.fromRow][move.fromColumn - i] != null) {
                    return false;
                }
            }
        }

		//Checks to see if the Rook is trying to move on a diagonal, which is
		//illegal for that piece.
		if (move.fromRow != move.toRow && move.fromColumn != move.toColumn) {
			return false;
		}

		//This will check to see if the Rook is moving left, right, up, or down.  This
		//is accomplished by checking to see if it moves 0 spaces horizontally OR
		//0 spaces vertically
		if (board[move.fromRow][move.fromColumn] != board[move.toRow][move.toColumn]) {
			if ((move.fromRow - move.toRow == 0) || (move.fromColumn - move.toColumn == 0)) {
				if (board[move.toRow][move.toColumn] != null)
					if (board[move.toRow][move.toColumn].player() == (board[move.fromRow][move.fromColumn].player())) {
						return false;
					}
				return true;
			}
		}
		return false;
	}
	public int score() {
		return 5;
	}
}
