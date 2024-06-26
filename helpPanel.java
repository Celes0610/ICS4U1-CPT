import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class helpPanel extends JPanel implements ActionListener {
    // properties
    BufferedImage[] help = new BufferedImage[5];
    BufferedImage currentImage;
    int intCurr = 0;
    JButton prevButton = new JButton("<");
    JButton nextButton = new JButton(">");
    JButton homeButton = new JButton("Home");
    JFrame parentFrame;
    JPanel startPanel;

    /**Loads image based on String path */
    public BufferedImage loadImage(String strFileName){  
        // Try to read the file from the jar file
        InputStream imageclass = null;
        imageclass = this.getClass().getResourceAsStream(strFileName);
        if(imageclass == null){
            
        }else{
          try{
            return ImageIO.read(imageclass);
          }catch(IOException e){
            System.out.println(e.toString());
            System.out.println("Unable to load image file: \""+strFileName+"\"");
            return null;
          }
        }
        // Then try to read the local file
        try{
          BufferedImage theimage = ImageIO.read(new File(strFileName));
          return theimage;
        }catch(IOException e){
          System.out.println("Unable to load local image file: \""+strFileName+"\"");
          return null;
        }
    }

    // Constructor
    public helpPanel(JFrame frame, JPanel startPanel) {
        super();
        this.parentFrame = frame;
        this.startPanel = startPanel;

        help[0] = loadImage("help1.png");
        help[1] = loadImage("help2.png");
        help[2] = loadImage("help3.png");
        help[3] = loadImage("help4.png");
        help[4] = loadImage("help5.png");
        currentImage = help[intCurr]; // Set the initial image


        setLayout(null); // Use null layout for absolute positioning

        // Add the buttons to the panel and set their action listeners
        prevButton.setBounds(50, 10, 50, 30);
        add(prevButton);
        prevButton.addActionListener(this);

        nextButton.setBounds(110, 10, 50, 30);
        add(nextButton);
        nextButton.addActionListener(this);

        homeButton.setBounds(1150, 10, 80, 30);
        add(homeButton);
        homeButton.addActionListener(this);
    }

    // Override paintComponent - the way the JPanel is drawn
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImage, 0, 100, null);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == nextButton) {
            if (intCurr < help.length - 1) {
                intCurr++;
                currentImage = help[intCurr];
                repaint();
            }
            // Enable prev button if it's not the first image
            prevButton.setEnabled(intCurr > 0);
            // Disable next button if it's the last image
            nextButton.setEnabled(intCurr < 4);
        } else if (evt.getSource() == prevButton) {
            if (intCurr > 0) {
                intCurr--;
                currentImage = help[intCurr];
                repaint();
            }
            // Enable next button if it's not the last image
            nextButton.setEnabled(intCurr < 3);
            // Disable prev button if it's the first image
            prevButton.setEnabled(intCurr > 0);
        } else if (evt.getSource() == homeButton) {
            // Switch back to the start screen
            parentFrame.setContentPane(startPanel);
            parentFrame.validate();
            parentFrame.repaint();
        }
    }
}
