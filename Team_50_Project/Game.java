import java.io.IOException;
import javax.swing.*;
import java.awt.*;

/**
 * This class contains the main method which runs the game.
 * @author Caelan Hilferty
 * @author Masroor Syed
 * @author Jacob Goodwin
 * @version 0.03
 * @since Feb 23, 2017
 */

public class Game{
    /**
     * Creates game window and sets contents of window to GamePanel.
     */
     private static final Dimension screenSize =
		Toolkit.getDefaultToolkit().getScreenSize();
     private static final int WIDTH = screenSize.width;
     private static final int HEIGHT = screenSize.height;

    /**
    * Runs the game.
    * @param args the array of strings from the command line
    */
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
        JFrame menuWindow = new JFrame("TEAM 50 GAME");
        menuWindow.setSize(WIDTH,HEIGHT);
        menuWindow.setUndecorated(true);
        menuWindow.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.add(menu);
        menuWindow.setResizable(false);
        menuWindow.setVisible(true);
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Waits until the user presses start to proceed.
        try{
          while(!menu.getRunning()){
            Thread.sleep(1000);
          }
        } catch(InterruptedException e){

        }
        menuWindow.dispose();
        GamePanel gamePanel = new GamePanel(
			menu.getPlayerOneColor(),menu.getPlayerTowColor(),
			menu.getPlayerOneName(),menu.getPlayerTwoName(),menu.getMapNum());
        JFrame gameWindow = new JFrame("TEAM 50 CPSC 233 PROJECT");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setUndecorated(true);
        gameWindow.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        gameWindow.setContentPane(gamePanel);
        gameWindow.pack();
        gameWindow.setVisible(true);
        gameWindow.setEnabled(true);
    }
}
