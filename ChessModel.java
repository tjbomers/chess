import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***********************************************
 * This class will establish the chess model, which will detail the general
 * rules of the game, the piece locations, and an AI that a single player can
 * play against.  There are also methods that provide warnings if the appropriate
 * player is in check, and also a method to let a player know if he or she is
 * in checkmate.
 *
 * Project created by Tim Bomers, Matt Hendrick, and Maggie Oliver
 ***********************************************/
public class ChessModel implements IChessModel {
	//Creates an object with which we can place new piece objects
    private IChessPiece[][] board;
	//This will add a backup array to be used
	private List<IChessPiece[][]> backups = new ArrayList<IChessPiece[][]>();
	private IChessPiece[][] tempBackup = new IChessPiece[8][8];
	//Creates a player object to be used in game situations
	private Player player;


	/**
	 * This constructor will establish the locations on which the new piece objects
	 * will be placed, as well as create a backup copy of the board.
	 */
	public ChessModel() {
		//Creates an 8x8 array for the board
		board = new IChessPiece[8][8];
		//Chess tradition dictates that the person playing white goes first
		player = Player.WHITE;

		//Creates white piece objects in the proper board locations and establishes
		//the owner of said pieces
        board[7][0] = new Rook(Player.WHITE);
        board[7][1] = new Knight(Player.WHITE);
        board[7][2] = new Bishop(Player.WHITE);
        board[7][3] = new Queen(Player.WHITE);
        board[7][4] = new King(Player.WHITE);
        board[7][5] = new Bishop(Player.WHITE);
        board[7][6] = new Knight(Player.WHITE);
        board[7][7] = new Rook(Player.WHITE);
        for (int i = 0; i < 8; i++) {
        	board[6][i] = new Pawn(Player.WHITE);
		}

		//Creates black piece objects in the proper board locations and establishes
		//the owner of said pieces
		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight(Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn(Player.BLACK);
		}
		//Creates a backup of the board
		backups.add(deepCopy(board));
	}


	/**
	 * This method will check to see if a player in check is actually in checkmate, in which
	 * case they will lose the game.
	 *
	 * @return Returs true if the player is in checkmate, completing the game
	 */
	public boolean isComplete() {

		if(inCheck(player)) {
			Move testMove;
			for (int fromRow = 0; fromRow < 8; fromRow++) {
				for (int fromCol = 0; fromCol < 8; fromCol++) {
					//Loops through the board and checks for non-empty spaces owned by the current player.
					if (board[fromRow][fromCol] != null && board[fromRow][fromCol].player() == player) {
						for (int toRow = 0; toRow < 8; toRow++) {
							for (int toCol = 0; toCol < 8; toCol++) {
								//If a given move is valid, execute the move and check to see if the
								//player is still in check
								testMove = new Move(fromRow, fromCol, toRow, toCol);
								if (isValidMove(testMove)) {
									evaluateMove(testMove);
									//If the player is no longer in check, the game is not complete
									if(!inCheck(player)) {
										undo(1);
										return false;
									}else {
										undo(1);
									}
								}
							}
						}
					}
				}
			}
			//If all possible moves keep the player in check, the game is complete
			JOptionPane.showMessageDialog(null,
					"" + player + "  King is currently in checkmate! \n GAME OVER");
			return true;
		}
		return false;
	}

	/**
	 * This method checks for a valid move by the current player.
	 *
	 * @param move a (@link W18project3.Move) object describing the move to be made.
	 * @return move is valid
	 */
	public boolean isValidMove(Move move) {
		//Move is invalid by default and must meet conditions to make it valid
		boolean valid = false;

		//First, it checks the given space for a piece, then it checks to see if that
		//piece is owned by the current player.  If both are true, then it checks to
		//see if the move is valid.  If so, the move is approved and the player can move
		//the piece.
		if (board[move.fromRow][move.fromColumn] != null)
			if (board[move.fromRow][move.fromColumn].player() == currentPlayer()) {
				if (board[move.fromRow][move.fromColumn].isValidMove(move, board))
					valid = true;
			}
		//Returns whether or not the move is valid
		return valid;
	}

	/**
	 * This method will establish a move and will then transition control to the next
	 * player.
	 *
	 * @param move a (@link W18project3.Move) object describing the move to be made.
	 */
	public void move(Move move) {
		//Changes the board state after the move is made and creates a backup in
		//its image
		System.out.println("testing");
		board[move.toRow][move.toColumn] =  board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
		backups.add(deepCopy(board));
		//Transitions control to the next player.  If the next player is controlling
		//the black pieces, the AI method will be called.
		if (inCheck(Player.BLACK)) {
			JOptionPane.showMessageDialog(null,
					"BLACK  King is currently in check!");
		}
		if (inCheck(Player.WHITE)) {
			JOptionPane.showMessageDialog(null,
					"WHITE  King is currently in check!");
		}
		isComplete();
		this.setNextPlayer();
		isComplete();
		//if (player == Player.BLACK) {
		//	AI();
		//}
	}

	private void evaluateMove(Move move) {
		board[move.toRow][move.toColumn] =  board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
	}

	/**
	 * This method will allow players to undo multiple moves, effectively rewinding
	 * the game state all the way to the beginning of the game.  This is done by
	 * accessing the arraylist of backups, which players can go through one at a time.
	 *
	 * @param d The stop condition for the loop
	 */
	public void undo(int d) {
		//Grabs the appropriate backup board state
		if(backups.size() > d) {
			board = backups.get(backups.size() - 1 - d);
			//Establishes the correct player and will remove any board states ahead
			//of it (so any future board states from the desired backup location will
			//be backed up appropriately.
			for(int i = 0; i < d; i++) {
				player.next();
				backups.remove(backups.size() - 1);
			}
		}

	}

	/**
	 * This method will check the board to see if a king is in check.  When a player's
	 * piece has a valid move that can attack the enemy colored king directly, that king
	 * is considered to be "in check".  The enemy king will then have to move out of the
	 * way of the piece(s) or have another piece block the threatening piece.
	 *
	 * @param  p (@link W18project3.Move) the Player being checked
	 * @return Whether or not the king is in check
	 */
	public boolean inCheck(Player p) {
		//This will pass the turn to the next player and then have that player attempt to
		//remove their king from check.
		boolean fixPlayer = false;
		if (player == p) {
			setNextPlayer();
			fixPlayer = true;
		}
		//Loops through the board and checks for pieces that are not owned by the current player
	    Move testMove;
        for(int fromRow = 0; fromRow < 8; fromRow ++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                if (board[fromRow][fromCol] != null && board[fromRow][fromCol].player() != p) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
							//Checks the test moves to see if they are valid.  If so, then check
							//to see if those moves target the king.  If so, notify the player that
							//the king is in check.
                            testMove = new Move(fromRow, fromCol, toRow, toCol);
                            if (isValidMove(testMove)) {
                            	if (board[toRow][toCol] != null && board[toRow][toCol].type().equals("King")) {
									//Pass the turn
									if(fixPlayer)
										setNextPlayer();
									return true;
								}
                            }
                        }
                    }
                }
            }
        }
		//Pass the turn
        if(fixPlayer)
        	setNextPlayer();
	    return false;
	}


	/**
	 * Method that establishes player as the current player.
	 *
	 * @return current player
	 */
	public Player currentPlayer() {

		return player;
	}

	/**
	 * Sets the number of rows at 8
	 * @return 8
	 */
	public int numRows() {

		return 8;
	}

	/**
	 * Sets the number of columns at 8
	 * @return 8
	 */
	public int numColumns() {

		return 8;
	}

	/**
	 * Method that tells the game where a piece is located on the board
	 * @param row Row on which the piece is located
	 * @param column Column on which the piece is located
	 *
	 * @return Current location of the piece
	 */
	public IChessPiece pieceAt(int row, int column) {

				return board[row][column];
	}

	/**
	 * Method that transitions control from one player to the next
	 */
	public void setNextPlayer() {

		player = player.next();
	}

	public void setPiece(int row, int column, IChessPiece piece) {

		board[row][column] = piece;
	}

	/**
	 * This is the AI method for the computer, which controls the black pieces.  The AI
	 * will prioritize pieces it can take based on their overall power level as well as
	 * whether or not it is in danger of losing the game.
	 */
	public void AI() {
		//AI called
		Move theMove = new Move(0, 0, 0, 0);
		//The test moves that will determine the main move
		Move testMove;
		IChessPiece[][] testBoard = new IChessPiece[8][8];

		//if the AI is in check, gets out of check with the first solution it sees
		if(inCheck(Player.BLACK)) {
			//Loops through the board and checks for pieces controlled by the AI
			for(int fromRow = 0; fromRow < 8; fromRow ++) {
				for (int fromCol = 0; fromCol < 8; fromCol++) {
					if(board[fromRow][fromCol] != null && board[fromRow][fromCol].player() == Player.BLACK) {
						//Loops through possible destinations
						for (int toRow = 0; toRow < 8; toRow ++) {
							for (int toCol = 0; toCol < 8; toCol++) {
								testMove = new Move(fromRow, fromCol, toRow, toCol);
								//If the given move is valid, first check to see if it removes the king from
								//check.  If not, undo the move and check other moves.
								if(isValidMove(testMove)) {
									move(testMove);
									if(inCheck(Player.BLACK)) {
										undo(1);
									} else {
										return;
									}
								}
							}
						}
					}
				}
			}
		} else {
		}


		//if the AI is not in check it tries to kill an enemy piece
		for(int fromRow = 0; fromRow < 8; fromRow ++) {
			for (int fromCol = 0; fromCol < 8; fromCol++) {
				if(board[fromRow][fromCol] != null && board[fromRow][fromCol].player() == Player.BLACK) {
					for (int toRow = 0; toRow < 8; toRow ++) {
						for (int toCol = 0; toCol < 8; toCol++) {
							testMove = new Move(fromRow, fromCol, toRow, toCol);
							if(isValidMove(testMove) && (board[toRow][toCol] != null) && (board[toRow][toCol].player() == Player.WHITE)) {
							    if(!(isValidMove(theMove))) {
                                    theMove.toColumn = testMove.toColumn;
                                    theMove.toRow = testMove.toRow;
                                    theMove.fromColumn = testMove.fromColumn;
                                    theMove.fromRow = testMove.fromRow;
                                } else if(board[testMove.toRow][testMove.toColumn].type()
										!= board[theMove.toRow][theMove.toColumn].type()) {
									//Prioritizes the Queen as the main enemy target
							        if (board[testMove.toRow][testMove.toColumn].type().equals("Queen")) {
                                        move(testMove);
                                        return;
                                    } else if (board[theMove.toRow][theMove.toColumn].type().equals("Pawn")) {
                                        theMove.toColumn = testMove.toColumn;
                                        theMove.toRow = testMove.toRow;
                                        theMove.fromColumn = testMove.fromColumn;
                                        theMove.fromRow = testMove.fromRow;
                                    } else if(board[theMove.toRow][theMove.toColumn].type().equals("Knight")) {
                                        theMove.toColumn = testMove.toColumn;
                                        theMove.toRow = testMove.toRow;
                                        theMove.fromColumn = testMove.fromColumn;
                                        theMove.fromRow = testMove.fromRow;
                                    } else if(board[theMove.toRow][theMove.toColumn].type().equals("Bishop")) {
                                        theMove.toColumn = testMove.toColumn;
                                        theMove.toRow = testMove.toRow;
                                        theMove.fromColumn = testMove.fromColumn;
                                        theMove.fromRow = testMove.fromRow;
                                    }
                                }

							}
						}
					}
				}
			}
		}
		//Makes its final decision and moves its piece
		if(isValidMove(theMove)) {
		    move(theMove);
		    return;
        }

		//random valid move
		for(int fromRow = 7; fromRow >=0; fromRow --) {
			for (int fromCol = 0; fromCol < 8; fromCol++) {
				if (board[fromRow][fromCol] != null && board[fromRow][fromCol].player() == Player.BLACK) {
					for (int toRow = fromRow - 1; toRow >= 0; toRow--) {
						for (int toCol = 0; toCol < 8; toCol++) {
							testMove = new Move(fromRow, fromCol, toRow, toCol);
							if(isValidMove(testMove)) {
								move(testMove);
								return;
							}
						}
					}
				}
			}
		}
		for(int fromRow = 7; fromRow >=0; fromRow --) {
			for (int fromCol = 0; fromCol < 8; fromCol++) {
				if (board[fromRow][fromCol] != null && board[fromRow][fromCol].player() == Player.BLACK) {
					for (int toRow = 0; toRow < 8; toRow++) {
						for (int toCol = 0; toCol < 8; toCol++) {
							testMove = new Move(fromRow, fromCol, toRow, toCol);
							if(isValidMove(testMove)) {
								move(testMove);
								return;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This method creates copies of the board states created throughout the game.
	 *
	 * @param original This is the original board state
	 *
	 * @return Null or the result
	 */
	private static IChessPiece[][] deepCopy(IChessPiece[][] original) {
		if (original == null) {
			return null;
		}

		final IChessPiece[][] result = new IChessPiece[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				result[i][j] = original[i][j];
			}
		}
		return result;
	}
}
