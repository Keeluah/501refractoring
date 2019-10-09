import java.awt.*;

/**
* Contains methods relating to the versus graphic that displays at the beginning of the game.
* @author Jacob Goodwin
* @version 0.01
* @since Mar 24, 2017
*/

public class VersusGraphic{
	
	private final static Dimension screenSize =
		Toolkit.getDefaultToolkit().getScreenSize();
    private final static int WINDOW_WIDTH = screenSize.width - 25;
    private final static int WINDOW_HEIGHT = screenSize.height - 125;
	
	private final int INIT_FADE_COUNTER = 300;
	private int fadeCounter = INIT_FADE_COUNTER;
	private final int INIT_SIZE_COUNTER = 400;
	private int sizeCounter = INIT_SIZE_COUNTER;
	private Player player1, player2;
	private Color p1Color;
	private Color p2Color;
	private Color fadeBlack = new Color(
		Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 255);
	private Color fadeWhite = new Color(
		Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), 255);
	private Color sheen = new Color(
		Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), 50);
	
	/**
	* Constructs a new versus graphic using player 1 and player 2.
	*/
	public VersusGraphic(Player p1, Player p2){
		player1 = new Player(p1);
		player2 = new Player(p2);
	}
	
	/**
	* Draws the versus graphic to the canvas.
	* @param graphics the graphics object used to draw the versus graphic to the canvas
	* @param playerName  player 1s name
	* @param player2Name player 2s name
	*/
	public void drawVersusGraphic(
		Graphics2D graphics, String playerName, String player2Name){
		setColors();
		if (fadeCounter < 255){
			setColors(fadeCounter);
		}
		if (fadeCounter < 200){
			sizeCounter--;
		}
		
		if (fadeCounter > 0){

			graphics.setColor(fadeBlack);
			if (sizeCounter < 400){
				graphics.fillRoundRect(
					0 + (3200 - 8 * sizeCounter), WINDOW_HEIGHT / 2 - 50,
					WINDOW_WIDTH - (6400 - 16 * sizeCounter), 70, 25600/sizeCounter,
					25600/sizeCounter);
			} else {
				graphics.fillRect(
					0 + (3200 - 8 * sizeCounter), WINDOW_HEIGHT / 2 - 50,
					WINDOW_WIDTH - (6400 - 16 * sizeCounter), 70);
			}
			

			graphics.setFont(new Font("Consolas", Font.BOLD, 60));

			graphics.setColor(p1Color);

			//Draws player 1 health.
			graphics.drawString(
				playerName, WINDOW_WIDTH / 2 - ((playerName.length() * 33) + 40),
				WINDOW_HEIGHT / 2);

			graphics.setColor(p2Color);

			graphics.drawString(player2Name, WINDOW_WIDTH / 2 + 60, WINDOW_HEIGHT / 2);

			graphics.setColor(fadeWhite);
			graphics.setFont(new Font("Consolas", Font.BOLD, 60));
			graphics.drawString("VS.", WINDOW_WIDTH / 2 - 33, WINDOW_HEIGHT / 2);

			graphics.setColor(sheen);
			graphics.fillRect(0, WINDOW_HEIGHT / 2 - 50, WINDOW_WIDTH, 35);

			fadeCounter--;
			if (fadeCounter < 255 && !(fadeCounter <= 1)){
				fadeCounter--;
			}
		}
	}
	
	/**
	* Sets all of the custom colors to the same colour they already are with a variable opacity.
	* @param fc the fade counter used to determine the opacity
	*/
	private void setColors(int fc){
		p1Color = new Color(
			player1.getColor().getRed(), player1.getColor().getGreen(),
			player1.getColor().getBlue(), fc);
		p2Color = new Color(
			player2.getColor().getRed(), player2.getColor().getGreen(),
			player2.getColor().getBlue(), fc);
		fadeBlack = new Color(
			Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), fc);
		fadeWhite = new Color(
			Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), fc);
		if (fc - 205 > 0){
				sheen = new Color(
					Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(),
					fc - 205);
			} else {
				sheen = new Color(
					Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), 0);
			}
	}
	
	/**
	* Sets all of the custom colors to the same color they already are but 100% opaque.
	*/
	private void setColors(){
		p1Color = new Color(
			player1.getColor().getRed(), player1.getColor().getGreen(),
			player1.getColor().getBlue(), 255);
		p2Color = new Color(
			player2.getColor().getRed(), player2.getColor().getGreen(),
			player2.getColor().getBlue(), 255);
		fadeBlack = new Color(
			Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 255);
		fadeWhite = new Color(
			Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), 255);
		sheen = new Color(
			Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), 50);
	}
	
	/**
	* Resets the counters to their original states.
	*/
	public void resetCounters(){
		fadeCounter = INIT_FADE_COUNTER;
		sizeCounter = INIT_SIZE_COUNTER;
	}
	
	
}