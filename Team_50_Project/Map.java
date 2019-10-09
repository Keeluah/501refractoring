import java.io.*;
import java.util.Scanner;
import java.awt.*;

/**
 * Contains methods related to loading a map of the users choice.
 * @author Kevin Ng
 * @author Jacob Goodwin
 * @version 0.02
 * @since Apr 4, 2017
 */
public class Map {
    private int WIDTH;
    private int HEIGHT;
    private int mapNum;
    
	/**
	* Constructs a new map using the height and width of the screen,
	* as well as the map the user has selected.
	* @param width  the width of the screen
	* @param height the height of the screen
	* @param map    the map the user has selected
	*/
    public Map(int width, int height, int map){
        WIDTH = width;
        HEIGHT = height;
        mapNum = map;
    }
	
	/**
	* Reads the specified map from a text file.
	*/
    public void readTxt() throws FileNotFoundException{
        Scanner s = null;
        int x, y, width, height;

        s = new Scanner(
			new BufferedReader(new FileReader(text(mapNum))));
        while(s.hasNext()){
            x = dimension(s.next());
            y = dimension(s.next());
            width = dimension(s.next());
            height = dimension(s.next());
            Wall wall = new Wall(x,y,width,height, Color.BLACK);
        }
		
        if (s != null){
            s.close();
        }
    }
    
	/**
	* Returns a value from a section of a line in the map file.
	* @param input the line in the map file where the wanted value is
	* @return      the value in the map file
	*/
    public int dimension(String input){
        int x = 0;
        if(isInteger(input)){
            x = Integer.parseInt(input);
        }
        else{
            if(input.charAt(0) == 'W'){
                x = WIDTH;
            }
            else if(input.charAt(0) == 'H'){
                x = HEIGHT;
            }
            for(int index = 0; index < input.length(); index++){
                if(input.charAt(index) == '/'){
                    x = parseAndOperate(index, input, 2, "/", x);
                }
                else if(input.charAt(index) == '*'){
                    x = parseAndOperate(index, input, 2, "*", x);
                }
                else if(input.charAt(index) == '-'){
                    x = addOrSubtract(index, input, "-", x);
                }
                else if(input.charAt(index) == '+'){
                    x = addOrSubtract(index, input, "+", x);
                }
            }
        }
        return x;
    }
	
	/**
	* Uses the addition or subtraction operator to operate on
	* the value we are reading from the map file.
	* @param index    the location of the 1st character of the value
	*                 we are parsing
	* @param input    the line we are reading in the file
	* @param operator the operator we are using to operate on the value
	* @param x        the value we are operating on
	* @return         the value that has been operated on
	*/
	private int addOrSubtract(
		int index, String input, String operator, int x){
		if (isInteger(input.substring(index+1,index+4))){
            x = parseAndOperate(index, input, 4, operator, x);
        } else {
            x = parseAndOperate(index, input, 3, operator, x);
        }
		return x;
	}
	
	/**
	* Parses the value from the file to an int and operates on it.
	* @param index    the location of the 1st character of the value
	*                 we are parsing
	* @param input    the line we are reading in the file
	* @param numToAdd the number to add to the index to get to the end
	*                 of the value
	* @param operator the operator to be used on the value
	* @param x        the value to be operated on
	* @return         the value that has been operated on
	*/
	private int parseAndOperate(
		int index, String input, int numToAdd, String operator, int x){
		int y = Integer.parseInt(
			input.substring(index+1, index+numToAdd));
		if (operator.equals("+")){
			x = x + y;
		} else if(operator.equals("-")){
			x = x - y;
		} else if (operator.equals("*")){
			x = x * y;
		} else {
			x = x / y;
		}
		return x;
	}
    
	/**
	* Returns <code>true</code> if the value is an integer and parses it
	* to an integer if it is.
	* @param input the string that may or may not be an integer
	* @return      <code>true</code> if the input is an integer;
	*              <code>false</code> if the input is not an integer
	*/
    public boolean isInteger(String input){
        try{
            int x = Integer.parseInt(input);
        }
        catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }
    
	/**
	* Returns the name of the map file to load.
	* @param mapNum the number corresponding to the map the user
	*               wants to load
	* @return       the name of the map file to load
	*/
    public String text(int mapNum){
        String map = "map1.txt";
        switch(mapNum){
            
            case 1: map = "map1.txt";
                    break;
            case 2: map = "map2.txt";
                    break;
            case 3: map = "map3.txt";
                    break;
            case 4: map = "map4.txt";
                    break;
        }
        return map;
    }
}
