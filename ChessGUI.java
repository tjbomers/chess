import java.awt.Dimension;

import javax.swing.JFrame;

/**************************************
 * This is the class that will set up the GUI for the game.
 *
 * Project created by Tim Bomers, Matt Hendrick, and Maggie Oliver
 *
 **************************************/
public class ChessGUI {

    /**
     * This is the main method that will start up the game
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChessPanel panel = new ChessPanel();
        frame.getContentPane().add(panel);
        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(800, 637));
        frame.pack();
        frame.setVisible(true);
    }
}
