import java.awt.*;

/**
* This class contains methods relating to a players health bar.
* @author Jacob Goodwin
* @version 0.03
* @see Player
* @since Mar 23, 2017
*/

public class HealthBar extends Element{

	private int prevHealth = 100;
	private int counter = 0;
	private final int COUNTER_MAX = 2;


  /**
  * Constructs a new health bar using an x coordinate, a y coordinate,
  * and a color.
  * @param x     the x coordinate of the health bar
  * @param y     the y coordinate of the health bar
  * @param color the color of the health bar
  */
  public HealthBar(int x, int y, Color color){
    super(x, y, color);
  }

  /**
  * Constructs a new health bar using another health bar.
  * @param healthBar the health bar that this health bar will be a
  *                  copy of
  */
  public HealthBar(HealthBar healthBar){
    this(healthBar.getElementX(), healthBar.getElementY(), healthBar.getColor());
  }

  /**
  * Draws the health bar onto the canvas.
  * @param graphics the graphics object used to draw the health bar onto
  *                 the canvas
  * @param player   the player that the health bar belongs to
  */
  public void drawHealthBar(Graphics2D graphics, Player player){

	Color transBlack = new Color(0, 0, 0, 150);
	Color transWhite = new Color(255, 255, 255, 200);

	drawHealthNum(player, graphics);

	drawBar(3, 31, 27, 7, transBlack, graphics, player);

    if(player.getHealth() <= 15){
      setColor(Color.RED);
    } else if (player.getHealth() <= 30){
      setColor(Color.ORANGE);
    } else if (player.getHealth() <= 45){
      setColor(Color.YELLOW);
    }

	drawBar(4, 30, player.getHealth() / 4, 5, getColor(), graphics, player);
	drawBar(4, 30, player.getHealth() / 4, 3, transWhite, graphics, player);
	
  }

  /**
  * Draws a bar.
  * @param numToAddX      the number to add to the x coordinate
  * @param numToSubtractY the number to subtract from the y coordinate
  * @param width          the width of the bar
  * @param height         the height of the bar
  * @param color          the color of the bar
  * @param graphics       the graphics object used to draw the bar onto the canvas
  * @param player         the player the health bar belongs to
  */
  private void drawBar(int numToAddX, int numToSubtractY, int width,
		int height, Color color, Graphics2D graphics, Player player){
	graphics.setColor(color);
    graphics.fillRect(player.getElementX() - (player.getPlayerRadius() / 2)
		+ numToAddX, player.getElementY() - numToSubtractY, width, height);
  }

  /**
  * Draws the health number onto the canvas.
  * @param player   the player that the health bar belongs to
  * @param graphics the graphics object used to draw the health number
  *                 onto the canvas
  */
  private void drawHealthNum(Player player, Graphics2D graphics){
	int numToSubtract;
	int numPulseSize;
	graphics.setColor(Color.BLACK);
	if (prevHealth > player.getPlayerLives()){
		if(player.getHealth() <= 15){
			numPulseSize = 15;
		} else if (player.getHealth() <= 30){
			numPulseSize = 14;
		} else if (player.getHealth() <= 45){
			numPulseSize = 13;
		} else {
			numPulseSize = 12;
		}
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("Consolas", Font.BOLD, numPulseSize));
		numToSubtract = numPulseSize - 10;
		if (counter < COUNTER_MAX){
			counter++;
		} else {
			counter = 0;
			prevHealth -= 5;
		}
	} else {
		graphics.setFont(new Font("Consolas", Font.BOLD, 11));
		numToSubtract = 2;
	}
	if (player.getPlayerLives() == 100){
		graphics.drawString(Integer.toString(player.getPlayerLives()),
			player.getElementX() - player.getPlayerRadius() - 9, player.getElementY() - 24);
	} else if(player.getPlayerLives() <= 9){
		graphics.drawString(Integer.toString(player.getPlayerLives()),
			player.getElementX() - player.getPlayerRadius() + 3, player.getElementY() - 24);
	} else {
		graphics.drawString(Integer.toString(player.getPlayerLives()),
			player.getElementX() - player.getPlayerRadius() - numToSubtract,
			player.getElementY() - 24);
	}
  }

}
