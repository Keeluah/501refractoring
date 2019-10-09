import java.awt.*;

/**
 * This class consists of methods that relate to a two-dimensional wall.
 * @author Caelan Hilferty
 * @author Jacob Goodwin
 * @version 0.02
 * @since Mar 6, 2017
 */
public class Wall extends Element {
    private int wallWidth;
    private int wallHeight;
	private static WallRegistry registry = new WallRegistry();

    /**
	* Constructs a new Wall with the x coordinate, y coordinate, width
	* height, and colour of the wall.
	* @param x      the x coordinate of the top-left hand corner of the wall
	* @param y      the y coordinate of the top-left hand corner of the wall
	* @param width  the width of the wall
	* @param height the height of the wall
	* @param color  the colour of the wall
	*/
    public Wall(int x, int y, int width, int height, Color color) {
        super(x, y, color);
        if(width > 0){
            wallWidth = width;
        }
        else{
            wallWidth = 1;
        }
        if(height > 0){
            wallHeight = height;
        }
        else{
            wallHeight = 1;
        }
		registry.addWall(this);
    }

    /**
	* Draws the wall to the canvas.
	* @param graphics the Graphics object that allows us to draw the wall onto
	*                 the canvas
	*/
    public void drawWall(Graphics2D graphics) {
		graphics.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getColor());
        graphics.fillRoundRect(getElementX(), getElementY(), wallWidth, wallHeight, 15, 15);
    }
	
	/**
	* Returns the width of the wall.
	* @return the width of the wall
	*/
    public int getWallWidth() {
        return wallWidth;
    }
	
	/**
	* Returns the height of the wall.
	* @return the height of the wall
	*/
    public int getWallHeight() {
        return wallHeight;
    }
	
	/**
	* Returns a copy of the registry of walls.
	* @return a copy of the registry of walls
	*/
	public WallRegistry getWallRegistry(){
		return new WallRegistry(registry);
	}
}
