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
	JTextArea connectStat = new JTextArea();
	JLabel usernameLabel = new JLabel("Username");
	JTextField usernameField = new JTextField();
	JButton easyButton = new JButton("Easy");
	JButton normButton = new JButton("Normal");
	JButton hardButton = new JButton("Hard");
	JButton playButton = new JButton("Play");
	
	/*chat*/
	JTextArea chat = new JTextArea();
	
	/*network message*/
	String strLine;
	String strSplit[];
	String strMsgType;
	String strMsgUser;
	boolean playerConnected = false;
	int intMsgX;
	int intMsgY;
	String strMsgSent;
	String strUsername1;
	String strUsername2;
	int intSnake1[][] = new int[100][2];
	int intSnake2[][] = new int[100][2];

	//methods
	public void actionPerformed(ActionEvent evt){
		//create a server 
		if (evt.getSource() == hostButton) {
            int intPort = (int) (Math.random() * 65535);
            while (intPort < 1024 || intPort > 49151) { // ensure it's a valid and non-privileged port
                intPort = (int) (Math.random() * 65535);
            }
			ssm = new SuperSocketMaster(intPort, this);
			ssm.connect();
			portField.setText(String.valueOf(intPort));
			String ip = ssm.getMyAddress();
			ipField.setText(ip);
			connectStat.setText("Waiting for player to connect...");
			ipField.setEnabled(false);
			portField.setEnabled(false);
        }

		//join a server
		if (evt.getSource() == joinButton){
			ssm = new SuperSocketMaster(ipField.getText(), Integer.parseInt(portField.getText()), this);
			ssm.connect();
			ssm.sendText("Connect, Player connected");
			easyButton.setEnabled(false);
			normButton.setEnabled(false);
			hardButton.setEnabled(false);
		}

		//network message
		if(evt.getSource() == ssm){
			strLine = ssm.readText();
			strSplit = strLine.split(",");
			strMsgType = strSplit[0];
			strMsgUser = strSplit[1];
			if (strMsgType.equals("Connect") && !playerConnected) {
                playerConnected = true;
                connectStat.setText("Player connected");
			}

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
		ipLabel.setLocation(55, 450);
		
		startPanel.add(ipField);
		ipField.setSize(new Dimension(350,50));
		ipField.setLocation(50, 500);

		startPanel.add(portLabel);
		portLabel.setSize(new Dimension(350, 50));
		portLabel.setLocation(505, 450);
		
		startPanel.add(portField);
		portField.setSize(new Dimension(350,50));
		portField.setLocation(500,500);

		startPanel.add(hostButton);
		hostButton.setSize(new Dimension(350,50));
		hostButton.setLocation(50,400);
		hostButton.addActionListener(this);

		startPanel.add(joinButton);
		joinButton.addActionListener(this); 
		joinButton.setSize(new Dimension(350,50));
		joinButton.setLocation(500,400);

		startPanel.add(connectStat);
		connectStat.setSize(new Dimension(800, 70));
		connectStat.setLocation(50, 600);
		connectStat.setEnabled(false);

		startPanel.add(usernameLabel);
		usernameLabel.setSize(new Dimension(200, 50));
		usernameLabel.setLocation(900, 50);

		startPanel.add(usernameField);
		usernameField.setSize(new Dimension(330, 50));
		usernameField.setLocation(900, 100);
		
		startPanel.add(easyButton);
		easyButton.addActionListener(this);
		easyButton.setSize(new Dimension(330, 50));
		easyButton.setLocation(900, 200);
		
		startPanel.add(normButton);
		normButton.addActionListener(this);
		normButton.setSize(new Dimension(330, 50));
		normButton.setLocation(900, 275);
		
		startPanel.add(hardButton);
		hardButton.addActionListener(this);
		hardButton.setSize(new Dimension(330, 50));
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
