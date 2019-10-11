import java.awt.*;
import java.util.Collection;

/**
 * This class consists of methods related to a player such as movement,
 * drawing and bound checking.
 * @author Caelan Hilferty
 * @author Jacob Goodwin
 * @version 0.08
 * @since March 18, 2017
 */
public class Player extends Element {
	private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int WINDOW_WIDTH = screenSize.width;
	private final int WINDOW_HEIGHT = screenSize.height;


    private int playerRadius;
    private int dx;
    private int dy;
    private int speed;
    private int lives;
    private int damage;

    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;

    private boolean playerIsFiring;
    private long firingTimer;
    private long firingDelay;
    private double firingAngle;

    private WallRegistry registry;
    private HealthBar healthBar;

	private int counter = 0;
	private int pulseDir = 1;

	/**
	* Constructs a new Player with a registry of walls, an x coordinate,
	* a y coordinate, and a color.
	* @param reg   the registry that contains all of the walls in the map
    * @param x     the x coordinate where the player will initially be placed
    * @param y     the y coordinate where the player will initially be placed
    * @param color the color of the player
	*/
    public Player(WallRegistry reg, int x, int y, Color color) {
        super(x, y, color);

        playerRadius = 20;
        dx = 0;
        dy = 0;
        speed = 10;

        // Health
        lives = 100;
        damage = 5;

        playerIsFiring = false;
        firingTimer = System.nanoTime();
        firingDelay = 100;

        registry = reg;
        healthBar = new HealthBar(x, y, Color.GREEN);
    }

	/**
	* Constructs a new player using another player.
	* @param p the player which this player will be a copy of
	*/
    public Player(Player p){
      this(p.getWallRegistry(), p.getElementX(), p.getElementY(), p.getColor());
    }

    /**
    * Updates the position of the player and makes sure that
	* they are within the bounds.
    */
    public void playerUpdate() {
        if (moveLeft) {
            dx = -speed;
        }

        if (moveRight) {
            dx = speed;
        }

        if (moveUp) {
            dy = -speed;
        }

        if (moveDown) {
            dy = speed;
        }

        //Moves player
        setElementX(getElementX() + dx);
        setElementY(getElementY() + dy);

        // Wall bound checking
        isWithinWall(registry);

        //Edges of map bound checking.
        boundCheck();

        //Reset player speed back to zero
        dx = 0;
        dy = 0;
    }

    /**
    * Subtracts damage from the players total health.
    */
    public void hit() {
        lives = lives - damage;
    }

    /**
    * Draws the player to the canvas.
    * @param graphics the Graphics object that allows us to draw the player onto
    *                 the canvas
    */
    public void drawPlayer(Graphics2D graphics) {

		Color transPlayerColor = new Color(
			getColor().getRed(), getColor().getGreen(), getColor().getBlue(), 100);
		Color pulsingBlack = new Color(0, 0, 0, counter * 17);

		graphics.setColor(transPlayerColor);

		Color sheen = new Color(255, 255, 255, 75);

		graphics.fillRect(getElementX() - playerRadius, getElementY() - playerRadius,
                2 * playerRadius, 2 * playerRadius);

		if (playerIsFiring && counter < 15 && pulseDir == 1){
			graphics.setColor(pulsingBlack);
			counter++;
		} else if (playerIsFiring && counter < 15 && counter > 0&& pulseDir == 0){
			graphics.setColor(pulsingBlack);
			counter--;
		} else if (playerIsFiring && counter == 15 && pulseDir == 1) {
			counter--;
			pulseDir = 0;
		} else if (playerIsFiring && counter == 0 && pulseDir == 0) {
			counter++;
			pulseDir = 1;
		}

		colorPlayer(graphics, sheen);
    }

	private void colorPlayer(Graphics2D graphics, Color sheen) {
		graphics.setColor(graphics.getColor());
		graphics.fillRect(getElementX() - playerRadius + 2, getElementY() - playerRadius + 2,
                2 * playerRadius - 4, 2 * playerRadius - 4);

        graphics.setColor(getColor());
        graphics.fillRect(getElementX() - playerRadius + 5, getElementY() - playerRadius + 5,
                2 * playerRadius - 10, 2 * playerRadius - 10);

		graphics.setColor(sheen);
		graphics.fillRect(getElementX() - playerRadius, getElementY() - playerRadius,
                2 * playerRadius, 20);
	}

    /**
    * Checks if player is hitting the edges/bounds of the map. Adjusts player
	* position accordingly.
    */
    public void boundCheck() {
        //Edge of map bound Checking (left side)
        if (getElementX() < playerRadius) {
            setElementX(playerRadius);
        }

        //Edge of map bound checking (top side)
        if (getElementY() < playerRadius) {
            setElementY(playerRadius);
        }

        //Edge of map bound checking (right side)
        if (getElementX() > (WINDOW_WIDTH - playerRadius)) {
            setElementX(WINDOW_WIDTH - playerRadius);
        }

        //Edge of map bound checking (bottom side)
        if (getElementY() > (WINDOW_HEIGHT - playerRadius)) {
            setElementY(WINDOW_HEIGHT - playerRadius);
        }
    }


	/**
	* Determines if the player is within a wall; if the player is within a wall,
	* it calls hitWall to push the player outside of the wall in the appropriate direction.
	* @param reg the registry of walls that the player could possibly collide with
	*/
    public void isWithinWall(WallRegistry reg) {
        Collection<Wall> wallRegistry = reg.getRegistry();

        for (Wall wall: wallRegistry) {
            if (getElementX() + playerRadius / 2 >= wall.getElementX() && getElementX() - playerRadius / 2
                    <= wall.getElementX() + wall.getWallWidth() &&  getElementY() + playerRadius / 2 >=
                    wall.getElementY() && getElementY() - playerRadius / 2 <= wall.getElementY() +
                    wall.getWallHeight()) {

                hitWall(wall);
            }
        }
    }

	/**
	* Returns <code>true</code> if the element is hitting the right side of the wall.
	* @param wall the Wall in which the element is either hitting or not
	* @return     <code>true</code> if the element is hitting the right side
	*             of the wall;
	*             <code>false</code> if the element is not hitting the right
	*             side of the wall
	*/
	public boolean isHittingWallRight(Wall wall){
		boolean result = false;
		if(getElementX() > wall.getElementX() + wall.getWallWidth()){
			result = true;
		}
		return result;
	}

	/**
	* Returns <code>true</code> if the element is hitting the left side of the wall.
	* @param wall the Wall in which the element is either hitting or not
	* @return     <code>true</code> if the element is hitting the left side
	*             of the wall;
	*             <code>false</code> if the element is not hitting the left
	*             side of the wall
	*/
	public boolean isHittingWallLeft(Wall wall){
		boolean result = false;
		if(getElementX() < wall.getElementX()){
			result = true;
		}
		return result;
	}

	/**
	* Returns <code>true</code> if the element is hitting the bottom of the wall.
	* @param wall the Wall in which the element is either hitting or not
	* @return     <code>true</code> if the element is hitting the bottom of
	*             the wall;
	*             <code>false</code> if the element is not hitting the bottom
	*             of the wall
	*/
	public boolean isHittingWallBottom(Wall wall){
		boolean result = false;
		if(getElementY() >= wall.getElementY() + wall.getWallHeight()){
			result = true;
		}
		return result;
	}

	/**
	* Returns <code>true</code> if the element is hitting the top of the wall.
	* @param wall the Wall in which the element is either hitting or not
	* @return     <code>true</code> if the element is hitting the top of
	*             the wall;
	*             <code>false</code> if the element is not hitting the top
	*             of the wall
	*/
	public boolean isHittingWallTop(Wall wall){
		boolean result = false;
		if(getElementY() <= wall.getElementY()){
			result = true;
		}
		return result;
	}

	/**
	* Prevents the player from going into the wall if they collide with it.
	* @param wall The wall in which the player might collide with
	*/
    public void hitWall(Wall wall) {
        if (isHittingWallRight(wall) && !(isHittingWallBottom(wall) || isHittingWallTop(wall))) {
            setElementX(wall.getElementX() + wall.getWallWidth() + playerRadius);
        }

        if (isHittingWallLeft(wall) && !(isHittingWallBottom(wall) || isHittingWallTop(wall))) {
            setElementX(wall.getElementX() - playerRadius);
        }

		if (isHittingWallBottom(wall)){
			setElementY(wall.getElementY() + wall.getWallHeight() + playerRadius);
		}

		if (isHittingWallTop(wall)){
			setElementY(wall.getElementY() - playerRadius);
		}
    }

    /**
     * Returns the x coordinate of the right side of the player.
     * @return the x coordinate of the right side of the player
     */
    public int getxCoordinateRight() {
        return (getElementX() + playerRadius);
    }

    /**
     * Returns the x coordinate of the left side of the player.
     * @return the x coordinate of the left side of the player
     */
    public int getxCoordinateLeft() {
        return (getElementX() - playerRadius);
    }

    /**
     * Returns the y coordinate of the bottom side of the player.
     * @return the y coordinate of the bottom side of the player
     */
    public int getyCoordinateBottom() {
        return (getElementY() + playerRadius);
    }

    /**
     * Returns the y coordinate of the top side of the player.
     * @return the y coordinate of the top side of the player
     */
    public int getyCoordinateTop() {
        return (getElementY() - playerRadius);
    }

    /**
     * Returns the player radius of the player.
     * @return the radius of the player
     */
    public int getPlayerRadius() {
        return playerRadius;
    }

    /**
     * Returns the health of the player.
     * @return player health
     */
    public int getPlayerLives() {
        return lives;
    }

    /**
     * Sets move left to true or false.
     * @param b whether to move left or not
     */
    public void setMoveLeft(boolean b) {
        moveLeft = b;
    }

    /**
     * Sets move right to true or false.
     * @param b whether to move right or not
     */
    public void setMoveRight(boolean b) {
        moveRight = b;
    }

    /**
     * Sets move up to true or false.
     * @param b whether to move up or not
     */
    public void setMoveUp(boolean b) {
        moveUp = b;
    }

    /**
     * Sets move down to true or false.
     * @param b whether to move down or not
     */
    public void setMoveDown(boolean b) {
        moveDown = b;
    }

    /**
     * Sets player firing to true or false.
     * @param b whether to player is firing or not
     */
    public void setPlayerIsFiring(boolean b) {
        playerIsFiring = b;
    }

	/**
	* Returns <code>true</code> if the player is firing.
	* @return <code>true</code> if the player is firing;
	*         <code>false</code> otherwise
	*/
	public boolean getPlayerIsFiring(){
		return playerIsFiring;
	}

    /**
     * Sets firing angle in degrees.
     * @param angle the angle at which the player is firing bullets
     */
    public void setFiringAngle(int angle) {
        if (angle>= 0 && angle <= 360) {
            firingAngle = angle;
        }
    }

	/**
	* Returns a copy of the registry of walls.
	* @return a copy of the registry of walls
	*/
    public WallRegistry getWallRegistry(){
      return new WallRegistry(registry);
    }

	/**
	* Returns the amount of lives the player has left.
	* @return the amount of lives the player has left
	*/
    public int getHealth(){
      return lives;
    }

	/**
	* Returns the players health bar.
	* @return the players health bar
	*/
    public HealthBar getHealthBar(){
	  //intentional privacy leak
      //return new HealthBar(healthBar);
	  return healthBar;
    }
	
	/**
	* Returns the angle the player is firing at.
	* @return the angle that the player is firing at
	*/
	public double getFiringAngle(){
		return firingAngle;
	}
	
	/**
	* Returns the firing timer.
	* @return the firing timer
	*/
	public long getFiringTimer(){
		return firingTimer;
	}
	
	/**
	* Returns the firing delay.
	* @return the firing delay
	*/
	public long getFiringDelay(){
		return firingDelay;
	}
	
	/**
	* Sets the firing timer to the specified timer.
	* @param timer the timer that the firing timer will be set to
	*/
	public void setFiringTimer(long timer){
		firingTimer = timer;
	}
}
