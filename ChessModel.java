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

	public boolean isValidMove(Move move) {
		boolean valid = false;

		if (board[move.fromRow][move.fromColumn] != null)
			if (board[move.fromRow][move.fromColumn].player() == currentPlayer()) {
				if (board[move.fromRow][move.fromColumn].isValidMove(move, board))
					valid = true;
			}
		return valid;
	}

	public void move(Move move) {
		System.out.println("testing");
		board[move.toRow][move.toColumn] =  board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
		backups.add(deepCopy(board));
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

	public static void staticMove(IChessPiece[][] board, Move move) {
		board[move.toRow][move.toColumn] =  board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
	}

	public void undo(int d) {
		if(backups.size() > d) {
			board = backups.get(backups.size() - 1 - d);
			for(int i = 0; i < d; i++) {
				player.next();
				backups.remove(backups.size() - 1);
			}
		}

	}

	public boolean inCheck(Player p) {
		boolean fixPlayer = false;
		if (player  == p) {
			setNextPlayer();
			fixPlayer = true;
		}
	    Move testMove;
        for(int fromRow = 0; fromRow < 8; fromRow ++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                if (board[fromRow][fromCol] != null && board[fromRow][fromCol].player() != p) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            testMove = new Move(fromRow, fromCol, toRow, toCol);
                            if (isValidMove(testMove)) {
                            	if (board[toRow][toCol] != null && board[toRow][toCol].type().equals("King")) {
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
        if(fixPlayer)
        	setNextPlayer();
	    return false;
	}


	public Player currentPlayer() {

		return player;
	}

	public int numRows() {

		return 8;
	}

	public int numColumns() {

		return 8;
	}

	public IChessPiece pieceAt(int row, int column) {

				return board[row][column];
	}

	public void setNextPlayer() {

		player = player.next();
	}

	public void setPiece(int row, int column, IChessPiece piece) {

		board[row][column] = piece;
	}

	public void AI() {
		//AI called
		Move theMove = new Move(0, 0, 0, 0);
		Move testMove;
		IChessPiece[][] testBoard = new IChessPiece[8][8];

		//if the AI is in check, gets out of check with the first solution it sees
		if(inCheck(Player.BLACK)) {
			for(int fromRow = 0; fromRow < 8; fromRow ++) {
				for (int fromCol = 0; fromCol < 8; fromCol++) {
					if(board[fromRow][fromCol] != null && board[fromRow][fromCol].player() == Player.BLACK) {
						for (int toRow = 0; toRow < 8; toRow ++) {
							for (int toCol = 0; toCol < 8; toCol++) {
								testMove = new Move(fromRow, fromCol, toRow, toCol);
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
