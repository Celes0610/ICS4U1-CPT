import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class AnimationPanel extends JPanel {
    // Properties
    /**A 2D array containing the data reqired to draw the full map */
    public String[][] mapData;

    /**A private Buffered Image containing data for the Floor texture */
    private BufferedImage imgFloor;

    /**A private Buffered Image containing data for the Wall texture */
    private BufferedImage imgWall;
    
    /**A private Buffered Image containing data for the Food texture */
    private BufferedImage imgFood;
    
    /**A private integer representing the Theme */
    private int currentTheme = 1;
    
    /**A 2D array containing the data of the player 1 snake int as xy positions */
    int intSnake1[][] = new int[160][2];
    
    /**A 2D array containing the data of the player 2 snake int as xy positions */
    int intSnake2[][] = new int[160][2];
    
    /**An integer representing the length of the player 1 snake*/
    int intLength1 = 3;

    /**An integer representing the length of the player 2 snake*/
    int intLength2 = 3;

    /**A color object that represents the colour of the player 1 snake */
    Color snake1Color;

    /**A color object that represents the colour of the player 2 snake */
    Color snake2Color;

    /**A 3D array to set the colors of the snakes */
    int[][][] setColor = new int[2][2][3];

    /**A color object used to set the colors of the snakes */
    Color foodColor = new Color(0,0,0);

    /**A 2D array containing the data of the food location as xy positions */
    int intFood[][] = new int[1][2];

    /**The direction of the player 1 snake */
    int direction1 = 2; // Default direction for snake 1: right

    /**The direction of the player 2 snake */
    int direction2 = 4; // Default direction for snake 2: left


    // Methods
    // Override paintComponent - the way the JPanel is drawn
    /**Overrides the paintComponent to call the drawMap method */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapData != null) {
            drawMap(g);
        }
    }

    // Method to load map data
    /**Method to load the mapData from a text file */
    public void loadMap(String filePath) {
        mapData = snakeGame.readFile(40, filePath);
        repaint();
    }

    // Method to draw the map
    /**Method to draw the map from mapData */
    private void drawMap(Graphics g) {
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                if ("f".equals(mapData[i][j])) {
                    g.drawImage(imgFloor, j * 18, i * 18, null);
                } else if ("w".equals(mapData[i][j])) {
                    g.drawImage(imgWall, j * 18, i * 18, null);
                } else if ("s1".equals(mapData[i][j])) {
                    g.setColor(snake1Color);
                    g.fillRect(j * 18, i * 18, 18, 18);

                } else if ("s2".equals(mapData[i][j])) {
                    g.setColor(snake2Color);
                    g.fillRect(j * 18, i * 18, 18, 18);
                } else if ("food".equals(mapData[i][j])) {
                    g.drawImage(imgFood, j*18, i*18, null);
                }
            }
        }
    }

    /**Method to remove snake data from mapData */
    public void removeSnake() {
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                if ("s1".equals(mapData[i][j])) {
                    mapData[i][j] = "f";
                } else if ("s2".equals(mapData[i][j])) {
                    mapData[i][j] = "f";
                }
            }
        }
    }

    // Method to load images based on theme
    /**Method to load images based on the theme selected */
    public void loadThemeImages(int theme) {
        currentTheme = theme;
        try {
            BufferedReader colorReader = new BufferedReader(new FileReader("theme.txt"));
            for (int row = 0; row < 2; row++) {
                String strLine = colorReader.readLine();
                String[] strSplit = strLine.split(" ");
                for (int col = 0; col < 2; col++) {
                    String[] rgb = strSplit[col].split(",");
                    setColor[row][col][0] = Integer.parseInt(rgb[0]);
                    setColor[row][col][1] = Integer.parseInt(rgb[1]);
                    setColor[row][col][2] = Integer.parseInt(rgb[2]);
                }
            }
            colorReader.close();

            if (theme == 1) { // Star Wars theme
                imgFloor = ImageIO.read(new File("space.png"));
                imgWall = ImageIO.read(new File("comet.png"));
                imgFood = ImageIO.read(new File("1_food.png"));
                snake1Color = new Color(setColor[0][0][0], setColor[0][0][1], setColor[0][0][2]);
                snake2Color = new Color(setColor[0][1][0], setColor[0][1][1], setColor[0][1][2]);
            } else if (theme == 2) { // Zelda theme
                imgFloor = ImageIO.read(new File("grass.png"));
                imgWall = ImageIO.read(new File("wall.png"));
                imgFood = ImageIO.read(new File("2_food.png"));
                snake1Color = new Color(setColor[1][0][0], setColor[1][0][1], setColor[1][0][2]);
                snake2Color = new Color(setColor[1][1][0], setColor[1][1][1], setColor[1][1][2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
    }

    // Method to Draw Snake
    /**Method to add snake data to mapData based on intSnake1 and intSnake2 */
    public void paintSnake() {
        for (int intCount = 0; intCount < Math.max(intLength1, intLength2); intCount++) {
            if (intCount < intLength1) {
                if (!mapData[intSnake1[intCount][1]][intSnake1[intCount][0]].equals("w") && !mapData[intSnake1[intCount][1]][intSnake1[intCount][0]].equals("s2")){
                    mapData[intSnake1[intCount][1]][intSnake1[intCount][0]] = "s1";
                }else{
                    snakeGame.stopGame(snakeGame.strUsername1);
                }
            }
            if (intCount < intLength2) {
                if (!mapData[intSnake2[intCount][1]][intSnake2[intCount][0]].equals("w") && !mapData[intSnake2[intCount][1]][intSnake2[intCount][0]].equals("s1") ){
                    
                    mapData[intSnake2[intCount][1]][intSnake2[intCount][0]] = "s2";
                }else{
                    snakeGame.stopGame(snakeGame.strUsername2);
                }
            }
        }
    }

    /**Method to check if the player 1 snake has eaten a food */
    public boolean eatFood1(){
        if(intFood[0][0] == intSnake1[0][0] && intFood[0][1] == intSnake1[0][1]){
            intLength1 = intLength1 + 1;
            mapData[intSnake1[0][0]][intSnake1[0][1]] = "f";
            intSnake1[intLength1][0] = intSnake1[intLength1 - 1][0];
            intSnake1[intLength1][1] = intSnake1[intLength1 - 1][1];
            System.out.println("Player 1 ate a food");
            return true;
        }else{
            return false;
        }
    }

    /**Method to check if the player 2 snake has eaten a food */
    public boolean eatFood2(){
        if(intFood[0][0] == intSnake2[0][0] && intFood[0][1] == intSnake2[0][1]){
            intLength2 = intLength2 + 1;
            mapData[intSnake2[0][0]][intSnake2[0][1]] = "f";
            intSnake2[intLength2][0] = intSnake2[intLength2 - 1][0];
            intSnake2[intLength2][1] = intSnake2[intLength2 - 1][1];
            System.out.println("Player 2 ate a food");
            return true;
        }else{
            return false;
        }
    }
    

    // Constructor
    /**Constructor Method */
    public AnimationPanel() {
        super();
        // Initialize the snakes with a default position
        intSnake1 = new int[160][2];
        intSnake2 = new int[160][2];
        intSnake1[0][0] = 0;
        intSnake1[0][1] = 0;
        intSnake1[1][0] = 0;
        intSnake1[1][1] = 0;
        intSnake1[2][0] = 0;
        intSnake1[2][1] = 0;
        intSnake2[0][0] = 39;
        intSnake2[0][1] = 39;
        intSnake2[1][0] = 39;
        intSnake2[1][1] = 39;
        intSnake2[2][0] = 39;
        intSnake2[2][1] = 39;

        loadThemeImages(currentTheme); // Load default theme images
    }
}
