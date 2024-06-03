import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class helpPanel extends JPanel implements ActionListener {
    // properties
    BufferedImage[] help = new BufferedImage[4];
    BufferedImage currentImage;
    int intCurr = 0;
    JButton prevButton = new JButton("<");
    JButton nextButton = new JButton(">");
    JButton homeButton = new JButton("Home");
    JFrame parentFrame;
    JPanel startPanel;

    // Constructor
    public helpPanel(JFrame frame, JPanel startPanel) {
        super();
        this.parentFrame = frame;
        this.startPanel = startPanel;

        try {
            help[0] = ImageIO.read(new File("help1.png"));
            help[1] = ImageIO.read(new File("help2.png"));
            help[2] = ImageIO.read(new File("help3.png"));
            help[3] = ImageIO.read(new File("help4.png"));
            currentImage = help[intCurr]; // Set the initial image
        } catch (IOException e) {
            System.out.println("Unable to load image");
            System.out.println(e.toString());
        }

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
            nextButton.setEnabled(intCurr < 3);
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
