import java.awt.*;
import javax.swing.*;
import java.util.Collection;

/**
 * This class consists of methods that relate to the bullets the
 * players fire.
 * @author Caelan Hilferty
 * @author Jacob Goodwin
 * @author Kevin Ng
 * @version 0.03
 * @since March 17, 2017
 */
public class Bullet {

	private final Dimension screenSize =
		Toolkit.getDefaultToolkit().getScreenSize();
	private final int WINDOW_WIDTH = screenSize.width;
	private final int WINDOW_HEIGHT = screenSize.height;

    private double xCoordinate;
    private double yCoordinate;
    private int radius;

    private double dx;
    private double dy;
    private double radian;
    private double speed;

    private Color bulletColor;

    private boolean canMove = true;

    /**
     * Constructs a new bullet with the angle (direction),
     * x coordinate, and y coordinate.
     * @param angle the direction of the bullet
     * @param x     the x coordinate of the starting position of the
     *              bullet
     * @param y     the y coordinate of the starting position of the
     *              bullet
     */
    public Bullet(double angle, int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
        radius = 5;

        radian = Math.toRadians(angle);
        speed = 20;
        dx = Math.cos(radian) * speed;
        dy = Math.sin(radian) * speed;

        bulletColor = Color.black;
    }

    /**
     * Moves the bullet across the map and checks if it is hitting a wall or
     * the edges of the map. Returns <code>true</code> if the bullet hits the
     * side of the map.
     * @param reg the registry of walls that the bullet could possibly
     *            collide with
     * @return    <code>true</code> if the bullet is colliding with any
     *            wall or the edge of the map;
     *            <code>false</code> if the bullet is not colliding with
     *            any wall or edge of the map
     *
     */
    public boolean update(WallRegistry reg) {
        if (canMove){
          xCoordinate += dx;
          yCoordinate += dy;
        }

        Collection<Wall> wallRegistry = reg.getRegistry();
        boolean result = false;

        for (Wall wall: wallRegistry) {
            if (getxCoordinate() >= wall.getElementX() && getxCoordinate()
					<= wall.getElementX() + wall.getWallWidth() &&
                    getyCoordinate() >= wall.getElementY() && getyCoordinate()
					<= wall.getElementY() + wall.getWallHeight()) {
                result = true;
            }
        }

        if (getxCoordinate() < -radius || getxCoordinate() >
			WINDOW_WIDTH + radius || getyCoordinate() < - radius ||
                getyCoordinate() > WINDOW_HEIGHT + radius) {
            result = true;
        }

        return result;
    }

    /**
     * Returns <code>true</code> if the bullet hits the player.
     * @param player the player that the collision is concerned with
     * @return       <code>true</code> if the bullet is colliding with the
     *               given player;
     *               <code>false</code> if the bullet is not colliding with
     *               the given player
     */
    public boolean hitPlayer(Player player) {
        boolean result = false;

        if (getxCoordinate() > player.getxCoordinateLeft() && getxCoordinate() <
                player.getxCoordinateRight() && getyCoordinate() > 
				player.getyCoordinateTop() && getyCoordinate()
				< player.getyCoordinateBottom()) {
            result = true;
        }

        return result;
    }

    /**
     * Draws the bullet to the canvas.
     * @param graphics the Graphics object that allows us to draw the
     *                 bullet onto the canvas
     */
    public void draw(Graphics2D graphics) {
        graphics.setColor(bulletColor);
        graphics.fillOval((int) (getxCoordinate() - radius),
			(int) (getyCoordinate() - radius), 2 * radius, 2 * radius);
    }

    /**
	 * Returns the x coordinate of the bullet.
     * @return the xCoordinate
     */
    public double getxCoordinate() {
        return xCoordinate;
    }

    /**
	 * Returns the y coordinate of the bullet.
     * @return the yCoordinate
     */
    public double getyCoordinate() {
        return yCoordinate;
    }

    /**
	 * Returns the angle the bullet is traveling at in radians.
     * @return the radian
     */
    public double getRadian() {
        return radian;
    }
	
	/**
	* Determines whether the bullet can move or not.
	* @param newCanMove sets the bullets ability to move to <code>true</code>
	*                   or <code>false</code>
	*/
    public void setCanMove(boolean newCanMove){
      canMove = newCanMove;
    }
}
