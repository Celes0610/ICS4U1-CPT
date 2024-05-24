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
	
	/*start screen*/
	JButton Host = new JButton("Host");
	JButton Guest = new JButton("Guest");
	JLabel Title = new JLabel("Snake");
	
	/*chat*/
	JTextField chat = new JTextField();
	
	/*network message*/
	String strLine;
	String strSplit[];
	String strMsgType;
	String strMsgUser;
	int intMsgX;
	int intMsgY;
	String strMsgSent;
	String strUsername1;
	String strUsername2;
	String strUsername3;
	String strUsername4;
	int intSnake1[][] = new int[100][2];
	int intSnake2[][] = new int[100][2];
	int intSnake3[][] = new int[100][2];
	int intSnake4[][] = new int[100][2];
	

	//methods
	public void actionPerformed(ActionEvent evt){
		//network message
		if(evt.getSource() == ssm){
			strLine = ssm.readText();
			strSplit = strLine.split(",");
			strMsgType = strSplit[0];
			strMsgUser = strSplit[1];
			if(strMsgType.equals("Game")){
				intMsgX = Integer.parseInt(strSplit[2]);
				intMsgY = Integer.parseInt(strSplit[3]);
			}else if(strMsgType.equals("Message")){
				strMsgSent = strSplit[2];
			}
		}
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
		panel.add(Title);
		panel.add(Host);
		panel.add(Guest);
	}
	
	public void mainGame(){
		
	}
	
	//main program
	public static void main (String[] args){
		new snakeGame();
	}
}
