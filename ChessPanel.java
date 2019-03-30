import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/***************************************
 * This class will establish the panel for the chess game.  This will
 * establish the icons and place them in the proper locations on the board.
 * The listener below will allow for interaction with the visible board
 ***************************************/
public class ChessPanel extends JPanel {

    //Sets up the board as a series of JButtons
    private JButton[][] board;
    //Sets up the undo button to cancel previous moves
    private JButton undoButton;
    //Establishes a model object
    private ChessModel model;
    //Creates image icons of all the available pieces in the game
    private ImageIcon wRook;
    private ImageIcon wBishop;
    private ImageIcon wQueen;
    private ImageIcon wKing;
    private ImageIcon wPawn;
    private ImageIcon wKnight;
    private ImageIcon bRook;
    private ImageIcon bBishop;
    private ImageIcon bQueen;
    private ImageIcon bKing;
    private ImageIcon bPawn;
    private ImageIcon bKnight;

    //Checks if it is the first turn
    private boolean firstTurnFlag;
    //Establishes the row and column variables that will be used in many calculations
    //when determining proper movesets of pieces.
    private int fromRow;
    private int toRow;
    private int fromCol;
    private int toCol;

    //Establishes a listener object
    private listener listener;

    /**
     * This method will serve as the constructor for the ChessPanel.  This will establish
     * the undo button as well as the main board on which the pieces will be placed.
     */
    public ChessPanel() {
        model = new ChessModel();
        board = new JButton[model.numRows()][model.numColumns()];
        listener = new listener();
        createIcons();

        //Creating separate panels for the board and the buttons
        JPanel boardpanel = new JPanel();
        JPanel buttonpanel = new JPanel();
        boardpanel.setLayout(new GridLayout(model.numRows(), model.numColumns(), 1, 1));

        //Creates the undo button
        undoButton = new JButton("Undo");
        undoButton.addActionListener(listener);


        //This loop will iterate through each square of the board.  If it finds a spot where
        //no piece belongs at the beginning of the game, it will not place a piece.  Otherwise,
        //it will place an appropriate piece of the correct color.
        for (int r = 0; r < model.numRows(); r++) {
            for (int c = 0; c < model.numColumns(); c++) {
                //Places an empty space if no piece belongs there at the start of the game
                if (model.pieceAt(r, c) == null) {
                    board[r][c] = new JButton("", null);
                    board[r][c].addActionListener(listener);
                //Places white pieces in their proper locations
                } else if (model.pieceAt(r, c).player() == Player.WHITE) {
                    placeWhitePieces(r, c);
                //Places black pieces in their proper locations
                } else if (model.pieceAt(r, c).player() == Player.BLACK) {
                    placeBlackPieces(r, c);
                }

                //Sets the proper background color depending upon the square
                setBackGroundColor(r, c);
                boardpanel.add(board[r][c]);
            }
        }
        //Customizing the location and the size of the board, as well as the button location
        add(boardpanel, BorderLayout.WEST);
        boardpanel.setPreferredSize(new Dimension(600, 600));
        add(buttonpanel);
        add(undoButton);
        displayBoard();
        firstTurnFlag = true;
    }

    /**
     * This method will set the color of each background square alternating between
     * light gray and white.
     *
     * @param r Row
     * @param c Column
     */
    private void setBackGroundColor(int r, int c) {
        //The math formula within will make sure the squares alternate in color
        if ((c % 2 == 1 && r % 2 == 0) || (c % 2 == 0 && r % 2 == 1)) {
            board[r][c].setBackground(Color.LIGHT_GRAY);
        } else if ((c % 2 == 0 && r % 2 == 0) || (c % 2 == 1 && r % 2 == 1)) {
            board[r][c].setBackground(Color.WHITE);
        }
    }

    /**
     * This method will place the white pieces in their proper location
     * @param r Row
     * @param c Column
     */
    private void placeWhitePieces(int r, int c) {
        //This will check what piece is on the board.  After that, it will add the
        //action listener for that piece.
        if (model.pieceAt(r, c).type().equals("Pawn")) {
            board[r][c] = new JButton(null, wPawn);
            board[r][c].addActionListener(listener);
        } else if (model.pieceAt(r, c).type().equals("Rook")) {
            board[r][c] = new JButton(null, wRook);
            board[r][c].addActionListener(listener);
        } else if (model.pieceAt(r, c).type().equals("Knight")) {
            board[r][c] = new JButton(null, wKnight);
            board[r][c].addActionListener(listener);
        } else if (model.pieceAt(r, c).type().equals("Bishop")) {
            board[r][c] = new JButton(null, wBishop);
            board[r][c].addActionListener(listener);
        } else if (model.pieceAt(r, c).type().equals("Queen")) {
            board[r][c] = new JButton(null, wQueen);
            board[r][c].addActionListener(listener);
        } if (model.pieceAt(r, c).type().equals("King")) {
            board[r][c] = new JButton(null, wKing);
            board[r][c].addActionListener(listener);
        }
    }

    /**
     * This method will place the black pieces in their proper locations
     * @param r Row
     * @param c Column
     */
    private void placeBlackPieces(int r, int c) {
        //This will check what piece is on the board.  After that, it will add the
        //action listener for that piece.
        if (model.pieceAt(r, c).type().equals("Pawn")) {
            board[r][c] = new JButton(null, bPawn);
            board[r][c].addActionListener(listener);
        } else if (model.pieceAt(r, c).type().equals("Rook")) {
            board[r][c] = new JButton(null, bRook);
            board[r][c].addActionListener(listener);
        } else if (model.pieceAt(r, c).type().equals("Knight")) {
            board[r][c] = new JButton(null, bKnight);
            board[r][c].addActionListener(listener);
        } else if (model.pieceAt(r, c).type().equals("Bishop")) {
            board[r][c] = new JButton(null, bBishop);
            board[r][c].addActionListener(listener);
        } else if (model.pieceAt(r, c).type().equals("Queen")) {
            board[r][c] = new JButton(null, bQueen);
            board[r][c].addActionListener(listener);
        } else if (model.pieceAt(r, c).type().equals("King")) {
            board[r][c] = new JButton(null, bKing);
            board[r][c].addActionListener(listener);
        }
    }

    /**
     * This method will search the location as directed in order to find
     * the appropriate image to represent each piece.
     */
    private void createIcons() {
        // Sets the Image for white player pieces
        wRook = new ImageIcon("wRook.png");
        wBishop = new ImageIcon("wBishop.png");
        wQueen = new ImageIcon("wQueen.png");
        wKing = new ImageIcon("wKing.png");
        wPawn = new ImageIcon("wPawn.png");
        wKnight = new ImageIcon("wKnight.png");
        // Sets the Image for black player pieces
        bRook = new ImageIcon("bRook.png");
        bBishop = new ImageIcon("bBishop.png");
        bQueen = new ImageIcon("bQueen.png");
        bKing = new ImageIcon("bKing.png");
        bPawn = new ImageIcon("bPawn.png");
        bKnight = new ImageIcon("bKnight.png");
    }

    /**
     * This method will display the board and then refresh it as needed, all
     * while keeping track of their current locations
     */
    private void displayBoard() {

        //Looping through each square to determine whether or not to place a piece
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++)
                //Place nothing in the event of null
                if (model.pieceAt(r, c) == null)
                    board[r][c].setIcon(null);
                //Places white image icons of the appropriate type
                else if (model.pieceAt(r, c).player() == Player.WHITE) {
                    if (model.pieceAt(r, c).type().equals("Pawn"))
                        board[r][c].setIcon(wPawn);

                    if (model.pieceAt(r, c).type().equals("Rook"))
                        board[r][c].setIcon(wRook);

                    if (model.pieceAt(r, c).type().equals("Knight"))
                        board[r][c].setIcon(wKnight);

                    if (model.pieceAt(r, c).type().equals("Bishop"))
                        board[r][c].setIcon(wBishop);

                    if (model.pieceAt(r, c).type().equals("Queen"))
                        board[r][c].setIcon(wQueen);

                    if (model.pieceAt(r, c).type().equals("King"))
                        board[r][c].setIcon(wKing);

                    //Places black image icons of the appropriate type
                } else if (model.pieceAt(r, c).player() == Player.BLACK)  {
                    if (model.pieceAt(r, c).type().equals("Pawn"))
                        board[r][c].setIcon(bPawn);

                    else if (model.pieceAt(r, c).type().equals("Rook"))
                        board[r][c].setIcon(bRook);

                    else if (model.pieceAt(r, c).type().equals("Knight"))
                        board[r][c].setIcon(bKnight);

                    else if (model.pieceAt(r, c).type().equals("Bishop"))
                        board[r][c].setIcon(bBishop);

                    else if (model.pieceAt(r, c).type().equals("Queen"))
                        board[r][c].setIcon(bQueen);

                    else if (model.pieceAt(r, c).type().equals("King"))
                        board[r][c].setIcon(bKing);
                }
        }
        //Refreshes the board
        repaint();
    }

    // inner class that represents action listener for buttons
    private class listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            for (int r = 0; r < model.numRows(); r++)
                for (int c = 0; c < model.numColumns(); c++)
                    if (board[r][c] == event.getSource())
                        if (firstTurnFlag == true && model.pieceAt(r, c) != null) {
                            fromRow = r;
                            fromCol = c;
                            firstTurnFlag = false;
                        } else {
                            toRow = r;
                            toCol = c;
                            firstTurnFlag = true;
                            Move m = new Move(fromRow, fromCol, toRow, toCol);
                            if ((model.isValidMove(m))) {
                                model.move(m);
                                displayBoard();
                            }
                        }
            //This will undo the previous move and can be done multiple times in
            //the course of the game
            if (undoButton == event.getSource()) {
                model.undo(1);
                displayBoard();
            }
        }
    }
}
