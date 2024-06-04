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
                }else if("s1".equals(mapData[i][j])){
                    g.setColor(Color.RED);
                    g.fillRect(j*18, i*18, 18, 18);
                }else if("s2".equals(mapData[i][j])){
                    g.setColor(Color.BLUE);
                    g.fillRect(j*18, i*18, 18, 18);
                }
            }
        }
    }
    public void removeSnake(Graphics g) {
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                if("s1".equals(mapData[i][j])){
                    mapData[i][j] = "f";
                }else if("s2".equals(mapData[i][j])){
                    mapData[i][j] = "f";
                }
            }
        }
    }

    // Method to load images based on theme
    public void loadThemeImages(int theme) {
        currentTheme = theme;
        try {
            if (theme == 1) { // Star Wars theme
                imgFloor = ImageIO.read(new File("space.png"));
                imgWall = ImageIO.read(new File("comet.png"));
            } else if (theme == 2) { // Zelda theme
                imgFloor = ImageIO.read(new File("grass.png"));
                imgWall = ImageIO.read(new File("wall.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
    }

    // Method to Draw Snake
    public void paintSnake() {
        for(int intCount = 0; intCount < intLength1; intCount++){
            mapData[intSnake1[intCount][0]][intSnake1[intCount][1]] = "s1";
            mapData[intSnake2[intCount][0]][intSnake2[intCount][1]] = "s2";
        }
    }

    // Constructor
    public AnimationPanel() {
        super();
        // Initialize the snakes with a default position
        intSnake1 = new int[160][2];
        intSnake2 = new int[160][2];
        intSnake1[0][0] = 0;
        intSnake1[0][1] = 0;
        intSnake1[1][0] = 0;
        intSnake1[1][1] = 0;
        intSnake1[2][1] = 0;
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
