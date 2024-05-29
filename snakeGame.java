import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.DuplicateFormatFlagsException;

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
	JButton playButton = new JButton("Ready");
	JButton theme1Button = new JButton("Star Wars");
	JButton theme2Button = new JButton("Zelda");
	
	/*chat*/
	JTextArea chat = new JTextArea();
	
	/*network message*/
	String strLine;
	String strSplit[];
	String strMsgType;
	String strMsgUser;
	String strMsgCmd;
	String strMsgArg;
	boolean playerConnected = false;
	int intMsgX;
	int intMsgY;
	String strMsgSent;
	String strUsername1;
	String strUsername2;
	int intSnake1[][] = new int[100][2];
	int intSnake2[][] = new int[100][2];
	int intReady1 = 0;
	int intReady2 = 0;
	int intSelf;
	int intTheme = 1;
	int intDiff = 1;
	String strMapFile = "Map - Easy.csv";

	//methods
	public void actionPerformed(ActionEvent evt){
		//create a server 
		if (evt.getSource() == hostButton) {
            int intPort = (int) (Math.random() * 65535);
            while (intPort < 1024 || intPort > 49151) { // ensure it's a valid and non-privileged port
                intPort = (int) (Math.random() * 65535);
            }
			ssm = new SuperSocketMaster(intPort, this);
			if(ssm.connect() == true){
				playButton.setEnabled(true);
				intSelf = 1;
			}else{
				ssm.disconnect();
			}
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
			if (ssm.connect() == true){
				playButton.setEnabled(true);
				intSelf = 2;
			}else{
				ssm.disconnect();
			}
			ssm.sendText("Connect, Player connected");
			easyButton.setEnabled(false);
			normButton.setEnabled(false);
			hardButton.setEnabled(false);
		}

		//network message
        if (evt.getSource() == ssm) {
            strLine = ssm.readText();
            System.out.println(strLine);
            strSplit = strLine.split(",");
            strMsgType = strSplit[0];
            strMsgUser = strSplit[1];
            if (strMsgType.equals("Connect") && !playerConnected) {
                playerConnected = true;
                connectStat.setText("Player connected");
            }

            if (strMsgType.equals("Game")) {
                intMsgX = Integer.parseInt(strSplit[2]);
                intMsgY = Integer.parseInt(strSplit[3]);
            } else if (strMsgType.equals("Message")) {
                strMsgSent = strSplit[2];
            } else if (strMsgType.equals("System")) {
                strMsgCmd = strSplit[2];
                strMsgArg = strSplit[3];
                if (strMsgCmd.equals("sentUsername")) {
                    if (intSelf == 1) {
                        strUsername2 = strMsgUser;
                        intReady2 = 1;
                    } else if (intSelf == 2) {
                        strUsername2 = strMsgUser;
                        intReady1 = 1;
                    }
                    if (!"null".equals(strMsgArg)) {
                        intTheme = Integer.parseInt(strMsgArg);
                        System.out.println("Theme: " + intTheme);
                    }
                    System.out.println(intReady1 + " " + intReady2);
                } else if (strMsgCmd.equals("startGame")) {
                    theframe.setContentPane(panel);
                    panel.loadMap(strMapFile);
                    theframe.repaint();
                }
            } else if (strMsgType.equals("draw")) {
                strMapFile = strSplit[1];
            }
        } else if (evt.getSource() == playButton) {
            if (usernameField.getText().equals("")) {
                connectStat.append("\nPlease Enter a Username, do not try to break the game :(");
            } else {
                if (intSelf == 1) {
                    strUsername1 = usernameField.getText();
                    ssm.sendText("System," + strUsername1 + ",sentUsername," + intTheme + "," + intDiff);
                    intReady1 = 1;
                    theme1Button.setEnabled(false);
                    theme2Button.setEnabled(false);
					ssm.sendText("draw," + strMapFile);
                    if (intReady1 == 1 && intReady2 == 1) {
                        theframe.setContentPane(panel);
						panel.loadThemeImages(intTheme);
                        panel.loadMap(strMapFile);
                        theframe.repaint();
                        ssm.sendText("System," + strUsername1 + ",startGame," + intTheme + "," + intDiff);
                    }
                } else if (intSelf == 2) {
                    strUsername2 = usernameField.getText();
                    theme1Button.setEnabled(false);
                    theme2Button.setEnabled(false);
                    ssm.sendText("System," + strUsername2 + ",sentUsername,null");
                    intReady2 = 1;
                    if (intReady1 == 1 && intReady2 == 1) {
                        theframe.setContentPane(panel);
						ssm.readText();
						panel.loadThemeImages(intTheme);
                        panel.loadMap(strMapFile);
                        theframe.repaint();
                    }
                }
            }
        } else if (evt.getSource() == theme1Button) {
            intTheme = 1;
        } else if (evt.getSource() == theme2Button) {
            intTheme = 2;
        } else if (evt.getSource() == easyButton) {
            intDiff = 1;
            strMapFile = "Map - Easy.csv";
        } else if (evt.getSource() == normButton) {
            intDiff = 2;
            strMapFile = "Map - Normal.csv";
        } else if (evt.getSource() == hardButton) {
            intDiff = 3;
            strMapFile = "Map - Hard.csv";
        }

    }

    public void keyReleased(KeyEvent evt) {

    }

    public void keyPressed(KeyEvent evt) {

    }

    public void keyTyped(KeyEvent evt) {

    }

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
		playButton.setSize(new Dimension(330, 50));
		playButton.setLocation(900, 500);
		playButton.setEnabled(false);

		startPanel.add(theme1Button);
		theme1Button.addActionListener(this);
		theme1Button.setSize(new Dimension(140, 50));
		theme1Button.setLocation(900, 425);

		startPanel.add(theme2Button);
		theme2Button.addActionListener(this);
		theme2Button.setSize(new Dimension(140, 50));
		theme2Button.setLocation(1090, 425);

		startPanel.repaint();
		
		theframe.pack();
		theframe.setResizable(false);
		theframe.setVisible(true);
	}
	
	//main program
	public static void main (String[] args){
		new snakeGame();
	}
}
