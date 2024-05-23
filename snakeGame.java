import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

public class snakeGame implements ActionListener, KeyListener{
	//properties
	JFrame frame = new JFrame("Snake");
	AnimationPanel panel = new AnimationPanel();
	SuperSocketMaster ssm;
	
	//methods
	public void actionPerformed(ActionEvent evt){
		
	}
	public void keyReleased(KeyEvent evt){

	}
	public void keyPressed(KeyEvent evt){
		
	}
	public void keyTyped(KeyEvent evt){
	
	}
	
	//constructor
	public snakeGame(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.setPreferredSize(new Dimension(1280, 720));
		frame.add(panel);
		
		startScreen();
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public void startScreen(){
		
	}
	
	public void mainGame(){
		
	}
	
	//main program
	public static void main (String[] args){
		new snakeGame();
	}
}
