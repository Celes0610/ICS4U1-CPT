import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class AnimationPanel extends JPanel {
    // Properties
    private String[][] mapData;

    // Methods
    // Override paintComponent - the way the JPanel is drawn
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapData != null) {
            drawMap(g);
        }
    }

    // Method to load map data
    public void loadMap(String filePath) {
        mapData = snakeGame.readFile(40, filePath); // Assuming the map has 20 columns
        repaint();
    }

    // Method to draw the map
    private void drawMap(Graphics g) {
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                if ("f".equals(mapData[i][j])) {
                    g.setColor(Color.BLACK);
                    g.fillRect(j * 18, i * 18, 18, 18); // Assuming each cell is 32x32 pixels
                } else if ("w".equals(mapData[i][j])) {
                    g.setColor(Color.GREEN);
                    g.fillRect(j * 18, i * 18, 18, 18);
                }
            }
        }
    }

    // Constructor
    public AnimationPanel() {
        super();
    }
}
