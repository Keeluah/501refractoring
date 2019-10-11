import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Contains methods relating to the computer of the game.
 * @author Masroor Hussain Syed
 * @author Jacob Goodwin
 * @author Caelan Hilferty
 * @version 0.04
 * @since April 09, 2017
 */
public class Menu extends JPanel {

	private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int WIDTH = screenSize.width;
	private final int HEIGHT = screenSize.height;
    private static final int VERTICAL_SPACING = 100;
    private int BUTTON_FONT_SIZE = 50;
    private int TITLE_FONT_SIZE = 100;
    private int buttonX = (int) (0.10* WIDTH);
    private int buttonY = (int) (0.20* HEIGHT);
    private String playerOneColor;
    private String playerTwoColor;
    private String playerOneName = "Player 1";
    private String playerTwoName = "Player 2";
    private int mapNum = 1;
    private boolean running = false;
    private  String[] playerColorBox = {"Red", "Blue", "Green", "Orange", "Magenta"};
    private final String[] maps = {"Windmill", "Camera", "Snake", "Empty"};
    private String mapName = "Windmill";

    /**
     * Constructs a new menu.
     */
    public Menu() {
        setBackground(Color.BLACK);
        add(Box.createVerticalStrut(VERTICAL_SPACING));
        startMenu();
    }

    /**
     * Lays out the first menu. Creates new game and quit buttons.
     */
    private void startMenu() {

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        JLabel gameName = new JLabel();
        JButton start = new JButton();
        JButton exit = new JButton();
        JButton credits = new JButton();
        
        gameName.setAlignmentX(CENTER_ALIGNMENT);
        initMenuLabelSetup(gameName, "Team 50",1);

        initMenuButtonSetup(start, "New Game");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMenu();
                playerOneInfoMenu();
            }
        });

        initMenuButtonSetup(exit, "Quit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // break the loop
                System.exit(0);
            }
        });

        initMenuButtonSetup(credits, "Credits");
        credits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMenu();
                credits();
            }
        });

        add(gameName);
        add(Box.createRigidArea(new Dimension(0,VERTICAL_SPACING/2)));
        add(start);
        add(Box.createRigidArea(new Dimension(0,VERTICAL_SPACING/2)));
		add(credits);
        add(Box.createRigidArea(new Dimension(0,VERTICAL_SPACING/2)));
        add(exit);
        add(Box.createRigidArea(new Dimension(0,VERTICAL_SPACING/2)));
    }

    /**
     * Clears any buttons, fields, etc... off of the screen.
     */
    private void clearMenu(){
        removeAll();
        repaint();
        revalidate();
    }

    /**
     * Formats a button to be a dark grey button with text on it.
     * @param button the button that we are formatting
     * @param text   the text to be displayed on the face of of the button
     */
    private void initMenuButtonSetup(JButton button, String text) {
        button.setBackground(Color.DARK_GRAY);
        button.setFont(new Font("Consolas",Font.ITALIC, BUTTON_FONT_SIZE));
        button.setForeground(Color.white);
        button.setText(text);
        button.setMaximumSize(new Dimension(buttonX*3,buttonY));
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
    }
	
	/**
	* Formats a label with white text and italics.
	* @param label       the label to formatting
	* @param text        the text that the label will displayed
	* @param scaleFactor the factor to scale the font size by
	*/
    private void initMenuLabelSetup(JLabel label, String text,int scaleFactor) {
        label.setFont(new Font("Consolas",Font.ITALIC, TITLE_FONT_SIZE/scaleFactor));
        label.setText(text);
        label.setForeground(Color.white);
    }
	
	/**
	* Formats a text field with a size of 200 x 30.
	* @param textField the text field to be formatted
	* @param text      the text to be displayed within the text field
	*/
    private void initMenuTextFieldSetup(JTextField textField, String text) {
        textField.setFont(new Font("Consolas",Font.BOLD, 20));
        textField.setPreferredSize(new Dimension(200,30));
        textField.setText(text);
    }

    /**
     * Sets the coordinates of the grid bag constraints to x and y.
     * @param gbc the GridBagConstraints that we are setting the coordinates
     *            of
     * @param x   the x coordinate that we want our GridBagConstraints to have
     * @param y   the y coordinate that we want our GridBagConstraints to have
     */
    private void setGBCCoordinates(GridBagConstraints gbc, int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }
	
	/**
	* Lays out the credits page.
	*/
    private void credits() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,50,10);

        JLabel top = new JLabel();
        initMenuLabelSetup(top, "Team 50 - CPSC 233 - University of Calgary 2017",3);

        JLabel Caelan = new JLabel();
        initMenuLabelSetup(Caelan, "Caelan Hilferty: Team Lead (Game Loop Guru)",4);

        JLabel Jacob = new JLabel();
        initMenuLabelSetup(Jacob, "Jacob Goodwin: Architect (Spirit Guide)",4);

        JLabel Masroor = new JLabel();
        initMenuLabelSetup(Masroor, "Masroor Hussain: Designer (Fountain of Creativity)",4);

        JLabel Kevin = new JLabel();
        initMenuLabelSetup(Kevin, "Kevin Ng: Designer (Gameplay Overlord)",4);

        JButton back = new JButton();
        initMenuButtonSetup(back, "Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMenu();
                startMenu();
            }
        });

        setGBCCoordinates(gbc, -1, 0);
		top.setForeground(Color.RED);
        add(top, gbc);
        setGBCCoordinates(gbc, -1, 1);
        add(Caelan, gbc);
        setGBCCoordinates(gbc, -1, 2);
        add(Jacob, gbc);
        setGBCCoordinates(gbc, -1, 3);
        add(Masroor, gbc);
        setGBCCoordinates(gbc, -1, 4);
        add(Kevin, gbc);
        setGBCCoordinates(gbc, -1, 5);
        add(back, gbc);

    }
	
	/**
	* Lays out the menu that takes in player 1s info.
	*/
    private void playerOneInfoMenu() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,50,10);

        JLabel gameName = new JLabel();
        initMenuLabelSetup(gameName, "Team 50",1);

        setGBCCoordinates(gbc, 1, 0);
        add(gameName,gbc);

        JLabel playerOneNameLabel = new JLabel();
        initMenuLabelSetup(playerOneNameLabel, "Player Name: ",4);

        setGBCCoordinates(gbc, -1, 1);
        add(playerOneNameLabel, gbc);

        JTextField playerOneNameField =  new JTextField();
        initMenuTextFieldSetup(playerOneNameField, "Player 1");
        playerOneNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                playerOneNameField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        playerOneNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerOneName = playerOneNameField.getText();
            }
        });

        setGBCCoordinates(gbc, 2, 1);
        add(playerOneNameField,gbc);

        JLabel playerOneColorlabel = new JLabel();
        initMenuLabelSetup(playerOneColorlabel, "Choose a Color: ",4);

        setGBCCoordinates(gbc, -1, 2);
        add(playerOneColorlabel,gbc);

        JComboBox color = new JComboBox(playerColorBox);
        color.setPreferredSize(new Dimension(200,30));
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerOneColor = (String) color.getSelectedItem();
            }
        });

        setGBCCoordinates(gbc, 2, 2);
        add(color,gbc);

        JLabel mapLabel = new JLabel();
        initMenuLabelSetup(mapLabel, "Select Map: ",4);

        setGBCCoordinates(gbc, -1, 3);
        add(mapLabel,gbc);

        JComboBox userMap = new JComboBox(maps);
        userMap.setPreferredSize(new Dimension(200,30));
        userMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapName = (String) userMap.getSelectedItem();
            }
        });

        setGBCCoordinates(gbc, 2, 3);
        add(userMap,gbc);

        JButton startButton = new JButton();
        initMenuButtonSetup(startButton, "Next");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerOneName = playerOneNameField.getText();
                playerOneColor = (String) color.getSelectedItem();
                running = false;
                clearMenu();
                playerTwoInfoMenu();

            }
        });

        setGBCCoordinates(gbc, 1, 4);
        add(startButton,gbc);
    }
	
	/**
	* Lays out the menu that takes player 2s info.
	*/
    private void playerTwoInfoMenu() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,50,10);

        JLabel gameName = new JLabel();
        initMenuLabelSetup(gameName, "Team 50",1);

        setGBCCoordinates(gbc, 1, 0);
        add(gameName,gbc);

        JLabel playerTwoNameLabel = new JLabel();
        initMenuLabelSetup(playerTwoNameLabel, "Player Name: ",4);

        setGBCCoordinates(gbc, -1, 1);
        add(playerTwoNameLabel, gbc);

        JTextField playerTwoNameField =  new JTextField();
        initMenuTextFieldSetup(playerTwoNameField, "Player 2");
        playerTwoNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                playerTwoNameField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        playerTwoNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerTwoName = playerTwoNameField.getText();
            }
        });

        setGBCCoordinates(gbc, 2, 1);
        add(playerTwoNameField,gbc);

        JLabel playerTwoColorlabel = new JLabel();
        initMenuLabelSetup(playerTwoColorlabel, "Choose a Color: ",4);

        setGBCCoordinates(gbc, -1, 2);
        add(playerTwoColorlabel,gbc);

        playerColorBox[0] = "Blue";
        playerColorBox[1] = "Red";


        JComboBox color = new JComboBox(playerColorBox);
        color.setPreferredSize(new Dimension(200,30));
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerTwoColor = (String) color.getSelectedItem();
            }
        });

        setGBCCoordinates(gbc, 2, 2);
        add(color,gbc);

        JButton startButton = new JButton();
        initMenuButtonSetup(startButton, "Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerTwoName = playerTwoNameField.getText();
                playerTwoColor = (String) color.getSelectedItem();
                running = true;
                clearMenu();
            }
        });

        setGBCCoordinates(gbc, 1, 4);
        add(startButton,gbc);
    }

    /**
     * Returns the color the player one selected.
     * @return the color the player one selected
     */
    public String getPlayerOneColor(){
        return playerOneColor;
    }

    /**
     * Returns the name the player one entered.
     * @return the name the player one entered
     */
    public String getPlayerOneName() {
        return playerOneName;
    }

    /**
     * Returns the color the player two selected.
     * @return the color the player two selected
     */
    public String getPlayerTowColor(){
        return playerTwoColor;
    }

    /**
     * Returns the name the player two entered.
     * @return the name the player two entered
     */
    public String getPlayerTwoName() {
        return playerTwoName;
    }
	
	/**
	* Gets the number of the map to load based on the name of the map
	* that the player has chosen.
	* @return the number of the map to load
	*/
    public int getMapNum() {
    	if(mapName.equalsIgnoreCase("Windmill")) {
    		mapNum = 1;
    	} else if(mapName.equalsIgnoreCase("Camera")) {
    		mapNum = 2;
    	} else if(mapName.equalsIgnoreCase("Snake")) {
    		mapNum = 3;
    	} else if(mapName.equalsIgnoreCase("Empty")) {
    		mapNum = 4;
    	}
        return mapNum;
    }
	
	/**
	* Returns <code>true</code> if the player is still in the menu.
	* @return <code>true</code> if the player is still in the menu;
	*         <code>false</code> if they are done with the menu
	*/
    public boolean getRunning () {
        return running;
    }


}
