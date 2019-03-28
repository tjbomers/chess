import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ChessModel implements IChessModel {
    private IChessPiece[][] board;
	private List<IChessPiece[][]> backups = new ArrayList<IChessPiece[][]>();
	private Player player;

	// declare other instance variables as needed

	public ChessModel() {
		board = new IChessPiece[8][8];
		player = Player.WHITE;

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
		backups.add(deepCopy(board));
	}

	public boolean isComplete() {
		/*
		Move testMove;
		if (inCheck(player)) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (board[i][j].type().equals("King")) {
						for (int x = -1; x < 2; x++) {
							for (int y = -1; y < 2; y++) {
								testMove = new Move(i, j, i + x, i + y);
								if (isValidMove(testMove) && board[i + x][j + y].player() != player) {
									inCheck(player) = false;
									return false;
								}
							}
						}
					}
				}
			}
		*/
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
		board[move.toRow][move.toColumn] =  board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
		backups.add(deepCopy(board));
		this.setNextPlayer();
		if (player == Player.BLACK) {
			System.out.println("Calling AI");
			AI();
		}
	}

	public static void staticMove(IChessPiece[][] board, Move move) {
		board[move.toRow][move.toColumn] =  board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
	}

	public void undo(int d) {
		if(backups.size() > d ) {
			board = backups.get(backups.size() - 1 - d);
			for(int i = 0; i < d; i++) {
				player.next();
				backups.remove(backups.size() - 1);
			}
		}

	}

	public boolean inCheck(Player p) {

	    Move testMove;
        for(int fromRow = 0; fromRow < 8; fromRow ++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                if (board[fromRow][fromCol] != null && board[fromRow][fromCol].player() == p) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            testMove = new Move(fromRow, fromCol, toRow, toCol);
                            System.out.println(fromRow +" " +  fromCol + " " + toRow + " " + toCol);
                            if (isValidMove(testMove)) {
                            	if (board[toRow][toCol] != null && board[toRow][toCol].type().equals("King")) {
									JOptionPane.showMessageDialog(null,
											"" + p + "  King is currently in check!");
									return true;
								}
                            }
                        }
                    }
                }
            }
        }
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
		Move theMove = new Move(0, 0, 0, 0);
		Move testMove;
		IChessPiece[][] testBoard = new IChessPiece[8][8];

		//if the AI is in check, gets out of check with the first solution it sees
		System.out.println("Save King?");
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
										System.out.println("Dodged a Check!");
										return;
									}
								}
							}
						}
					}
				}
			}
		}

		//if the AI is not in check it tries to kill an enemy piece
		System.out.println("Murder?");
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
                                        System.out.println("Attacked the Queen!");
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
		    System.out.println("Attacked a Piece!");
		    return;
        }

		//random valid move
		System.out.println("Basic Move?");
		for(int fromRow = 7; fromRow >=0; fromRow --) {
			for (int fromCol = 0; fromCol < 8; fromCol++) {
				if (board[fromRow][fromCol] != null && board[fromRow][fromCol].player() == Player.BLACK) {
					for (int toRow = fromRow - 1; toRow >= 0; toRow--) {
						for (int toCol = 0; toCol < 8; toCol++) {
							testMove = new Move(fromRow, fromCol, toRow, toCol);
							if(isValidMove(testMove)) {
								move(testMove);
								System.out.println("Moved Smartly!");
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
								System.out.println("Moved like an Idiot!");
								return;
							}
						}
					}
				}
			}
		}
		System.out.println("AI did nothing like a useless idiot");
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
