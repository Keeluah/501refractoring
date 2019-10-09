import java.awt.*;

/**
 * This contains methods that operate on elements, such as Players
 * and Walls.
 * @author Caelan Hilferty
 * @author Jacob Goodwin
 * @version 0.02
 * @see Player
 * @see Wall
 * @since Mar 16, 2017
 */

public class Element {
	private final Dimension screenSize =
		Toolkit.getDefaultToolkit().getScreenSize();
	private final int WINDOW_WIDTH = screenSize.width;
	private final int WINDOW_HEIGHT = screenSize.height;

    private int xCoordinate;
    private int yCoordinate;
    private Color color;

	/**
	* Constructs a new Element with an x coordinate, a y coordinate,
	* and a colour.
	* @param x the x coordinate of the element
	* @param y the y coordinate of the element
	* @param c the colour of the element
	*/
    public Element(int x, int y, Color c) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.color = c;
    }

	/**
	* Returns the x coordinate of the element.
	* @return the x coordinate of the element
	*/
    public int getElementX() {
        return xCoordinate;
    }

	/**
	* Returns the y coordinate of the element.
	* @return the y coordinate of the element
	*/
    public int getElementY() {
        return yCoordinate;
    }

	/**
	* Sets the elements x coordinate to the parameter x as long as
	* the parameter is within the bounds of the game window.
	* @param x the value the x coordinate is to be set to
	*/
    public void setElementX(int x) {
        if(x >= 0 && x <= WINDOW_WIDTH) {
            xCoordinate = x;
        }
    }

	/**
	* Sets the elements y coordinate to the parameter y as long as
	* the parameter is within the bounds of the game window.
	* @param y the value the y coordinate is to be set to
	*/
    public void setElementY(int y) {
        if(y >= 0 && y <= WINDOW_HEIGHT) {
            yCoordinate = y;
        }
    }

	/**
	* Sets the color of the element.
	* @param clr the new color of the element
	*/
    public void setColor(Color clr){
      color = clr;
    }

	/**
	* Returns the colour of the element.
	* @return the colour of the element
	*/
    public Color getColor() {
        return color;
    }

}
