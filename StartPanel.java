import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class StartPanel extends JPanel{
	// Properties
	BufferedImage imgSnake;
	
	// Methods
	// Override paintCompoent - the way the JPanel is drawn
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(imgSnake, 0, -20, null);
	}


	// Constructor
	public StartPanel(){
		super();
		InputStream imageclass = null;
                //Attempts to read from .jar file
                imageclass = this.getClass().getResourceAsStream("Temp-Title.png");
                if(imageclass == null){
                        System.out.println("Image File not found in .jar");
                        try{
                                imgSnake = ImageIO.read(new File("Temp-Title.png"));
                        }catch(IOException e){
                                System.out.println("Unable to load local image");
                        }
                }
                try{
                        imgSnake = ImageIO.read(imageclass);
                }catch(IOException e){

                }
	}
}
