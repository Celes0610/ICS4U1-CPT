import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

public class snakeGame implements ActionListener, KeyListener{
	//properties
	JFrame theframe = new JFrame("Snake");
	StartPanel startPanel = new StartPanel();
	AnimationPanel panel = new AnimationPanel();
	SuperSocketMaster ssm;
	
	/*start screen*/
	JLabel ipLabel = new JLabel("IP");
	JTextField ipField = new JTextField();
	JLabel portLabel = new JLabel("Port");
	JTextField portField = new JTextField();
	JButton hostButton = new JButton("Create Server");
	JButton joinButton = new JButton("Join Server");
	JButton connectButton = new JButton("Connect");
	JTextField usernameField = new JTextField();
	JButton easyButton = new JButton("Easy");
	JButton normButton = new JButton("Normal");
	JButton hardButton = new JButton("Hard");
	JButton playButton = new JButton("Play");
	
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
	int intSnake1[][] = new int[100][2];
	int intSnake2[][] = new int[100][2];

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
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.setLayout(null);

		panel.setPreferredSize(new Dimension(1280, 720));
		theframe.add(panel);
		theframe.add(startPanel);
		theframe.setContentPane(startPanel);

		startPanel.setPreferredSize(new Dimension(1280, 720));
		startPanel.setLayout(null);
		
		startPanel.add(ipLabel);
		ipLabel.setSize(new Dimension(350, 50));
		ipLabel.setLocation(55, 400);
		
		startPanel.add(ipField);
		ipField.setSize(new Dimension(350,50));
		ipField.setLocation(50, 450);

		startPanel.add(portLabel);
		portLabel.setSize(new Dimension(350, 50));
		portLabel.setLocation(505, 400);
		
		startPanel.add(portField);
		portField.setSize(new Dimension(350,50));
		portField.setLocation(500,450);

		startPanel.add(hostButton);
		hostButton.setSize(new Dimension(350,50));
		hostButton.setLocation(50,550);
		hostButton.addActionListener(this);

		startPanel.add(joinButton);
		joinButton.setSize(new Dimension(350,50));
		joinButton.setLocation(500,550);

		startPanel.add(connectButton);
		connectButton.addActionListener(this);
		connectButton.setSize(new Dimension(350, 50));
		connectButton.setLocation(275, 625);
		
		startPanel.add(usernameField);
		usernameField.setSize(new Dimension(350, 50));
		usernameField.setLocation(900, 50);
		usernameField.setText("Username");
		
		startPanel.add(easyButton);
		easyButton.addActionListener(this);
		easyButton.setSize(new Dimension(350, 50));
		easyButton.setLocation(900, 200);
		
		startPanel.add(normButton);
		normButton.addActionListener(this);
		normButton.setSize(new Dimension(350, 50));
		normButton.setLocation(900, 275);
		
		startPanel.add(hardButton);
		hardButton.addActionListener(this);
		hardButton.setSize(new Dimension(350, 50));
		hardButton.setLocation(900, 350);		
		
		startPanel.add(playButton);
		playButton.addActionListener(this);

		startPanel.repaint();
		
		theframe.pack();
		theframe.setResizable(false);
		theframe.setVisible(true);
	}
	
	public void mainGame(){
		
	}
	
	//method to read from csv file and load into array
	public static String[][] readFile(int intCol, String strFileName) {
        int intRow = 0;
        try {
            BufferedReader file = new BufferedReader(new FileReader(strFileName));
            String strLine;

            while ((strLine = file.readLine()) != null) {
                intRow++;
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        String[][] strMap = new String[intRow][intCol];
        try {
            BufferedReader map = new BufferedReader(new FileReader(strFileName));
            for (int row = 0; row < intRow; row++) {
                String strLine = map.readLine();
                String[] strSplit = strLine.split(",");

                for (int col = 0; col < intCol; col++) {
                    if (col < strSplit.length) {
                        strMap[row][col] = strSplit[col];
                    }
                }
            }
            map.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return strMap;
    }
	
	//main program
	public static void main (String[] args){
		new snakeGame();
	}
}
