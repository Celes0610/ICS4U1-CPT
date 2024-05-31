import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class helpPanel extends JPanel implements ActionListener {
    // properties
    BufferedImage help1;
    BufferedImage help2;
    BufferedImage currentImage;
	JButton prevButton = new JButton("<");
    JButton nextButton = new JButton(">");

    // Methods
    // Override paintComponent - the way the JPanel is drawn
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImage, 0, 100, null);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == nextButton) {
            currentImage = help2;
            repaint(); // This will call paintComponent and update the image
        }else if (evt.getSource() == prevButton){
			currentImage = help1;
			repaint();
		}
    }

    // Constructor
    public helpPanel() {
        super();
        try {
            help1 = ImageIO.read(new File("help1.png"));
            help2 = ImageIO.read(new File("help2.png"));
            currentImage = help1; // Set the initial image
        } catch (IOException e) {
            System.out.println("Unable to load image");
            System.out.println(e.toString());
        }

        // Add the button to the panel and set its action listener
        add(prevButton);
		prevButton.addActionListener(this);
		add(nextButton);
        nextButton.addActionListener(this);
    }
}
