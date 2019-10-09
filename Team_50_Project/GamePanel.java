import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class consists of methods that relate to the actual running of the
 * game.
 * @author Caelan Hilferty
 * @author Jacob Goodwin
 * @version 0.03
 * @since March 18, 2017
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    private final static Dimension screenSize =
		Toolkit.getDefaultToolkit().getScreenSize();
    private final static int WINDOW_WIDTH = screenSize.width;
    private final static int WINDOW_HEIGHT = screenSize.height;
    private final static Color BACK_GROUND_COLOR = Color.WHITE;

    private Thread gameThread;
    private String playerName;
    private String player2Name;
    private Color player1Color;
    private Color player2Color;


    private BufferedImage image;
    private Graphics2D graphics;


    private int FPS = 60;

    private Player player1;
    private Player player2;

    private WallRegistry registry = new WallRegistry();

    private static LinkedList<Bullet> bullets;

    private int mapNum;

	private VersusGraphic versusGraphic;


    /**
     * Constructs a new GamePanel with the color of player 1, the
     * port number (networking not implemented yet), and the name
     * of player 1.
     * @param p1clr  the colour of player 1
	 * @param p2clr  the colour of player 2
     * @param p1Name the name of player 1
	 * @param p2Name the name of player 2
	 * @param map    the map to be loaded
     */
    public GamePanel(String p1clr,String p2clr, String p1Name,
		String p2Name,int map) {
        playerName = p1Name;
        player2Name = p2Name;
        player1Color = toColor(p1clr);
        player2Color = toColor(p2clr);
        mapNum = map;
        super.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        super.setFocusable(true);
        super.requestFocus();
    }

    /**
     * Connects GamePanel to the native system screen. Called internally by
     * AWT. Starts the game thread and key listeners for input.
     */
    public void addNotify() {
        super.addNotify();
        addKeyListener(this);
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    /**
     * Contains the game loop. Updates, renders, and draws elements of the
     * game.
     */
    public void run() {
        boolean playAgain = true;
        boolean gameRunning;

        while(playAgain) {

            gameRunning = true;

            //Creates the game image/drawing surface with the size of the window.
            image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
            //Creates the graphics object.
            graphics = (Graphics2D) image.getGraphics();

            //Creates all objects in the game.
            gameStart();

            long startTime;
            long diffTimeMillis;
            long waitTime;
            long totalTime = 0;
            int frameCount = 0;
            int maxFrameRate = 60;
            long targetTime = 1000 / FPS;

			versusGraphic.resetCounters();

            // Main game loop. Everything is updated in this loop. Thread pauses
			// for a short period every time the loop iterates to maintain
			// a constant update frequency/FPS.
            while (gameRunning && player1.getPlayerLives() >
				0 && player2.getPlayerLives() > 0) {
                startTime = System.nanoTime();

                gameUpdate();
                gameRender();
                gameDraw();

                diffTimeMillis = (System.nanoTime() - startTime) / 1000000;
                waitTime = targetTime - diffTimeMillis;

                try {
                    Thread.sleep(waitTime);
                } catch (Exception e) {
                }

                totalTime += System.nanoTime() - startTime;
                frameCount++;

                if (frameCount == maxFrameRate) {
                    frameCount = 0;
                    totalTime = 0;
                }
            }
            gameOver();
        }
        return;
    }

    /**
    * Decides what color the given string correlates to and returns it.
    * @param clr the string to be evaluated
    * @return    the color corresponding to the string
    */
    private Color toColor(String clr){
      Color color = Color.RED;
      switch(clr){
        case "Red":
          color = Color.RED;
          break;
        case "Green":
          color = Color.GREEN;
          break;
        case "Blue":
          color = Color.BLUE;
          break;
        case "Orange":
          color = Color.ORANGE;
          break;
        case "Magenta":
          color = Color.MAGENTA;
		  break;
      }
      return color;
    }

    /**
     * Initializes all major instances used in the game.
     */
    private void gameStart() {
        Map map = new Map(WINDOW_WIDTH,WINDOW_HEIGHT,mapNum);
        try {
            map.readTxt();

        } catch (FileNotFoundException e) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(JOptionPane.getFrameForComponent(null),
                    "Map File not Found! Reverting to Default Map.", "Error",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					options, null);
        }

        Wall wall = new Wall(
                0, 0, 0, 0, Color.BLACK);
        registry = wall.getWallRegistry();
        // ***
        player1 = new Player(registry, 100, 100, player1Color);
        player2 = new Player(
          registry, WINDOW_WIDTH - 100, WINDOW_HEIGHT - 100, player2Color);

		versusGraphic = new VersusGraphic(player1, player2);

        bullets = new LinkedList<>();
    }
	
	/**
	* Updates the bullets and players positions and adds new bullets if the player
	* is firing.
	* @param player the player being updated and who may or may not be shooting
	*/
    private void updateBullets(Player player){
      player.playerUpdate();
      if(player.getPlayerIsFiring()) {
          long timeElapsed = (
			System.nanoTime() - player.getFiringTimer()) / 1000000;
          if(timeElapsed > player.getFiringDelay()) {
              bullets.add(new Bullet(player.getFiringAngle(),
				player.getElementX(), player.getElementY()));
              player.setFiringTimer(System.nanoTime());
          }
      }
    }

    /**
     * Updates the game (Player positions, bound checking, bullets etc.)
     */
    private void gameUpdate() {
        //Moves each individual player.

        updateBullets(player1);
        updateBullets(player2);

        // Moves every bullet then checks to see if it is colliding with a wall,
		// edge of map or a player.
        for (int counter = 0; counter < bullets.size(); counter++) {
            boolean removeBullet = bullets.get(counter).update(registry);
            boolean hitP1 = bullets.get(counter).hitPlayer(player1);
            boolean hitP2 = bullets.get(counter).hitPlayer(player2);

            if (removeBullet) {
                bullets.remove(counter);
                counter--;
            }

            if (hitP1) {
                counter = bulletHit(player1, counter);
            }

            if (hitP2) {
               counter = bulletHit(player2, counter);
            }

        }
    }

    /**
     * Draws players, walls, bullets, health, and start of game animation.
     */
    private void gameRender() {


        Collection<Wall> wallRegistry = registry.getRegistry();

		//Draws the background
        graphics.setColor(BACK_GROUND_COLOR);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        //Draws both players
        player1.drawPlayer(graphics);
        player2.drawPlayer(graphics);

        //Draws each wall
		for(Wall wall: wallRegistry){
		    wall.drawWall(graphics);
		}

        //Draws each bullet in list
        for (int counter = 0; counter < bullets.size(); counter++) {
            bullets.get(counter).draw(graphics);
        }

        player1.getHealthBar().drawHealthBar(graphics, player1);
        player2.getHealthBar().drawHealthBar(graphics, player2);

        //Text anti-aliasing for top health bar.
        graphics.setRenderingHint(
			RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		versusGraphic.drawVersusGraphic(graphics, playerName, player2Name);

    }

    /**
     * Draws the image to the window.
     */
    private void gameDraw() {
        Graphics graphics2 = this.getGraphics();
        graphics2.drawImage(image, 0, 0, null);
        graphics2.dispose();
    }

    /**
     * Deals damage to the player and removes the bullet from the list.
     * @param player  the bullet that hit the player
     * @param counter the location of the bullet in the list
     * @return        returns counter subtracted by one
     */
    private int bulletHit(Player player, int counter) {
        player.hit();
        bullets.remove(counter);
        counter--;
        return counter;
    }

    /**
     * Brings up the pause menu, which allows the player to either
     * resume the game, or exit the game.
     */
    private void inGameMenu() {
        Object[] options = {"Resume Game.", "Quit Game."};

        int play = JOptionPane.showOptionDialog(JOptionPane.getFrameForComponent(null),
                "What Would You Like to do?", "GAME PAUSED", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, null);
        if (play == 1) {
          System.exit(0);
        } else {
          for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).setCanMove(true);
          }
        }
    }

    /**
     * Brings up the game over menu, which states which player won, and
     * allows the player to choose whether to play again or to exit the
     * game.
     */
    private void gameOver() {
        String winner;

        if (player1.getPlayerLives() == 0 && player2.getPlayerLives() == 0){
          winner = "Tie Game!";
        }
        else if (player1.getPlayerLives() == 0) {
            winner = player2Name + " Won!";
        }

        else {
            winner = playerName + " Won!";
        }

        Object[] options = {"Play Again.", "Quit Game."};
        int play = JOptionPane.showOptionDialog(JOptionPane.getFrameForComponent(null),
                "Game Over, " + winner, "Game Over!", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);

        if (play == 1) {
            System.exit(0);
        }
    }

    /**
     * Sets player firing angle, and makes it so that the player is firing.
     * @param angle  the direction in which to fire the bullet
     * @param player the player who is firing
     */
    private void fire(int angle, Player player) {
        player.setFiringAngle(angle);
        player.setPlayerIsFiring(true);
    }

    /**
     * Inhibits the players ability to move or shoot.
     * @param player the player to be inhibited
     */
    private void setAllFalse(Player player) {
        player.setMoveLeft(false);
        player.setMoveRight(false);
        player.setMoveDown(false);
        player.setMoveUp(false);
        player.setPlayerIsFiring(false);
    }

    /**
     * Determines what to do when a key is typed (nothing).
     * @param keyEvent the key that is typed
     */
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
    * Determines what to do when a key is pressed. Makes player
    * move or shoot.
    * @param keyEvent the key that is pressed
    */
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            player1.setMoveLeft(true);
        }
        if (keyCode == KeyEvent.VK_D) {
            player1.setMoveRight(true);
        }
        if (keyCode == KeyEvent.VK_W) {
            player1.setMoveUp(true);
        }
        if (keyCode == KeyEvent.VK_S) {
            player1.setMoveDown(true);
        }
        if (keyCode == KeyEvent.VK_I) {
            fire(270, player1);
        }
        if (keyCode == KeyEvent.VK_J) {
            fire(180, player1);
        }
        if (keyCode == KeyEvent.VK_K) {
            fire(90, player1);
        }
        if (keyCode == KeyEvent.VK_L) {
            fire(0, player1);
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            player2.setMoveLeft(true);
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            player2.setMoveRight(true);
        }
        if (keyCode == KeyEvent.VK_UP) {
            player2.setMoveUp(true);
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            player2.setMoveDown(true);
        }
        if (keyCode == KeyEvent.VK_NUMPAD8) {
            fire(270, player2);
        }
        if (keyCode == KeyEvent.VK_NUMPAD4) {
            fire(180, player2);
        }
        if (keyCode == KeyEvent.VK_NUMPAD5) {
            fire(90, player2);
        }
        if (keyCode == KeyEvent.VK_NUMPAD6) {
            fire(0, player2);
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            setAllFalse(player1);
            setAllFalse(player2);
            for(int i = 0; i < bullets.size(); i++){
              bullets.get(i).setCanMove(false);
            }
            inGameMenu();
        }
    }

    /**
    * Determines what to do when a key is released. Ends player movement
    * or shooting.
    * @param keyEvent the key that is released
    */
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            player1.setMoveLeft(false);
        }
        if (keyCode == KeyEvent.VK_D) {
            player1.setMoveRight(false);
        }
        if (keyCode == KeyEvent.VK_W) {
            player1.setMoveUp(false);
        }
        if (keyCode == KeyEvent.VK_S) {
            player1.setMoveDown(false);
        }
        if (keyCode == KeyEvent.VK_I) {
            player1.setPlayerIsFiring(false);
        }
        if (keyCode == KeyEvent.VK_J) {
            player1.setPlayerIsFiring(false);
        }
        if (keyCode == KeyEvent.VK_K) {
            player1.setPlayerIsFiring(false);
        }
        if (keyCode == KeyEvent.VK_L) {
            player1.setPlayerIsFiring(false);
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            player2.setMoveLeft(false);
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            player2.setMoveRight(false);
        }
        if (keyCode == KeyEvent.VK_UP) {
            player2.setMoveUp(false);
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            player2.setMoveDown(false);
        }
        if (keyCode == KeyEvent.VK_NUMPAD8) {
            player2.setPlayerIsFiring(false);
        }
        if (keyCode == KeyEvent.VK_NUMPAD4) {
            player2.setPlayerIsFiring(false);
        }
        if (keyCode == KeyEvent.VK_NUMPAD5) {
            player2.setPlayerIsFiring(false);
        }
        if (keyCode == KeyEvent.VK_NUMPAD6) {
            player2.setPlayerIsFiring(false);
        }
    }
}
