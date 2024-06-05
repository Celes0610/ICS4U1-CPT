import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class AnimationPanel extends JPanel {
    // Properties
    private String[][] mapData;
    private BufferedImage imgFloor;
    private BufferedImage imgWall;
    private int currentTheme = 1;
    int intSnake1[][] = new int[160][2];
    int intSnake2[][] = new int[160][2];
    int intLength1 = 3;
    int intLength2 = 3;
    Color snake1Color;
    Color snake2Color;
    int[][][] setColor = new int[2][2][3];

    // Methods
    // Override paintComponent - the way the JPanel is drawn
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapData != null) {
            drawMap(g);
        }
    }

    // Method to load map data
    public void loadMap(String filePath) {
        mapData = snakeGame.readFile(40, filePath);
        repaint();
    }

    // Method to draw the map
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
                }
            }
        }
    }

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
                snake1Color = new Color(setColor[0][0][0], setColor[0][0][1], setColor[0][0][2]);
                snake2Color = new Color(setColor[0][1][0], setColor[0][1][1], setColor[0][1][2]);
            } else if (theme == 2) { // Zelda theme
                imgFloor = ImageIO.read(new File("grass.png"));
                imgWall = ImageIO.read(new File("wall.png"));
                snake1Color = new Color(setColor[1][0][0], setColor[1][0][1], setColor[1][0][2]);
                snake2Color = new Color(setColor[1][1][0], setColor[1][1][1], setColor[1][1][2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
    }

    // Method to Draw Snake
    public void paintSnake() {
        for (int intCount = 0; intCount < Math.max(intLength1, intLength2); intCount++) {
            if (intCount < intLength1) {
                if (!mapData[intSnake1[intCount][1]][intSnake1[intCount][0]].equals("w") ){
                    mapData[intSnake1[intCount][1]][intSnake1[intCount][0]] = "s1";
                }else{
                    snakeGame.stopGame();
                }
            }
            if (intCount < intLength2) {
                if (!mapData[intSnake2[intCount][1]][intSnake2[intCount][0]].equals("w")){
                    mapData[intSnake2[intCount][1]][intSnake2[intCount][0]] = "s2";
                }else{
                    snakeGame.stopGame();
                }
            }
        }
    }

    // Constructor
    public AnimationPanel() {
        super();
        // Initialize the snakes with a default position
        intSnake1 = new int[][]{{0, 0}, {1, 0}, {2, 0}};
        intSnake2 = new int[][]{{39, 39}, {38, 39}, {37, 39}};
        loadThemeImages(currentTheme); // Load default theme images
    }
}
