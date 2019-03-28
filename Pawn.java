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

		if (board[move.fromRow][move.fromColumn].player() == Player.WHITE)
			return isValidMoveWhite(move, board);
		else
			return isValidMoveBlack(move, board);
	}

	private boolean isValidMoveWhite(Move move, IChessPiece[][] board) {
		if(move.fromRow == 6) {
			if((move.fromColumn - move.toColumn) == 0) {
				if(move.toRow == 4) {
					if(board[move.toRow][move.toColumn] == null){
						return true;
					}
				}
			}
		}
		if(move.fromRow - move.toRow == 1) {
			if (move.fromColumn - move.toColumn == 0) {
				if(board[move.toRow][move.toColumn] == null) {
					return true;
				}
			}
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

	private boolean isValidMoveBlack(Move move, IChessPiece[][] board) {
		if(move.fromRow == 1) {
			if((move.fromColumn - move.toColumn) == 0) {
				if(move.toRow == 3) {
					if(board[move.toRow][move.toColumn] == null){
						return true;
					}
				}
			}
		}
		if(move.fromRow - move.toRow == -1) {
			if (move.fromColumn - move.toColumn == 0) {
				if(board[move.toRow][move.toColumn] == null) {
					return true;
				}
			}
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

	public int score() {
		return 1;
	}
}
