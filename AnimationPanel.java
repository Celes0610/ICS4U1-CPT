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
    int intSnake1[][];
    int intSnake2[][];
    int intLength = 3;

    // Methods
    // Override paintComponent - the way the JPanel is drawn
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapData != null) {
            drawMap(g);
        }
        for(int intCount = 0; intCount <= intLength; intCount++){
            g.fillRect(intSnake1[intCount][0]*18, intSnake1[intCount][1]*18, 18, 18);
            g.fillRect(intSnake2[intCount][0]*18, intSnake2[intCount][1]*18, 18, 18);
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
    //Method to Draw Snake
    public void paintSnake(int intX, int intY, Graphics g){
        g.fillRect(intX, intY, 18, 18);
    }

    // Constructor
    public AnimationPanel() {
        super();
        loadThemeImages(currentTheme); // Load default theme images
    }
}
