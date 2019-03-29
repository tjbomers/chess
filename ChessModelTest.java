import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class ChessModelTest {
 /*


    @Test
    public void testMoves(){
        ChessModel m = new ChessModel();

        // testing isValidMove and move for the pawn
        assertTrue(m.isValidMove(new Move(6, 1, 4, 1)));
        m.move(new Move(6, 1, 4, 1));
        assertTrue(m.pieceAt(4, 1).type().equals("Pawn"));

        // testing isValidMove and move for the bishop
        assertTrue(m.isValidMove(new Move(7, 2, 6, 1)));
        m.move(new Move(7, 2, 6, 1));
        assertTrue(m.pieceAt(6, 1).type().equals("Bishop"));

        // testing isValidMove and move for the knight
        assertTrue(m.isValidMove(new Move(7, 1, 5, 2)));
        m.move(new Move(7, 1, 5, 2));
        assertTrue(m.pieceAt(5, 2).type().equals("Knight"));

        m.move(new Move(6, 0, 4, 0));

        // testing isValid Move and move for the rook
        assertTrue(m.isValidMove(new Move(7, 0, 5, 0)));
        m.move(new Move(7, 0, 5, 0));
        assertTrue(m.pieceAt(5, 0).type().equals("Rook"));

        m.move(new Move(6, 4, 4, 4));

        //testing isValidMove and move for the Queen
        assertTrue(m.isValidMove(new Move(7, 3, 4, 6)));
        m.move(new Move(7, 3, 4, 6));
        assertTrue(m.pieceAt(4, 6).type().equals("Queen"));

        //testing isValidMove and move for the King
        assertTrue(m.isValidMove(new Move(7, 4, 7, 3)));
        m.move(new Move(7, 4, 7, 3));
        assertTrue(m.pieceAt(7, 3).type().equals("King"));

    }

    @Test
    public void testCheck(){
        // testing the inCheck method
        ChessModel m = new ChessModel();
        m.setPiece(7, 3, new Rook(Player.BLACK));
        m.setPiece(7, 5, new Rook(Player.BLACK));
        m.setPiece(6, 4, new Rook(Player.BLACK));
        m.setPiece(6, 5, new Bishop(Player.BLACK));
        m.setPiece(6, 3, new Bishop(Player.BLACK));

        assertTrue(m.inCheck(Player.WHITE));

    }

    @Test
    public void testUndo(){
        //testing the undo method
        ChessModel m = new ChessModel();
        m.move(new Move(6, 1, 4, 1));
        m.move(new Move(7, 2, 6, 1));
        m.move(new Move(6, 1, 5, 0));
        m.undo(3);
        assertTrue(m.pieceAt(7, 2).type().equals("Bishop"));


    }

*/
}
