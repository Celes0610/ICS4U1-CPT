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
	int intSnake1[][] = new int[100][2];
	int intSnake2[][] = new int[100][2];
	JTextField ipField = new JTextField();
	JTextField portField = new JTextField();
	JButton hostButton = new JButton("Create Server");
	JButton joinButton = new JButton("Join Server");
	JButton connectButton = new JButton("Connect");
	JTextField usernameField = new JTextField();
	JButton mapButton1 = new JButton("Map1");
	JButton mapButton2 = new JButton("Map2");
	JButton mapButton3 = new JButton("Map3");
	JButton playButton = new JButton("Play");

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

		panel.setPreferredSize(new Dimension(1280, 720));
		theframe.add(panel);
		theframe.add(startPanel);
		theframe.setContentPane(startPanel);

		startPanel.setPreferredSize(new Dimension(1280, 720));
		startPanel.setLayout(null);
		startPanel.add(ipField);
		ipField.setSize(new Dimension(200,50));
		ipField.setLocation(0, 0);

		startPanel.add(portField);
		portField.setSize(new Dimension(200,50));
		portField.setLocation(200,0);

		startPanel.add(hostButton);
		hostButton.setSize(new Dimension(200,50));
		hostButton.setLocation(0,200);
		hostButton.addActionListener(this);

		startPanel.add(joinButton);
		joinButton.setSize(new Dimension(200,50));
		joinButton.setLocation(200,200);

		startPanel.add(connectButton);
		connectButton.addActionListener(this);


		startPanel.add(usernameField);
		startPanel.add(mapButton1);
		mapButton1.addActionListener(this);
		startPanel.add(mapButton2);
		mapButton2.addActionListener(this);
		startPanel.add(mapButton3);
		mapButton3.addActionListener(this);
		startPanel.add(playButton);
		playButton.addActionListener(this);


		
		
		

		startPanel.repaint();
		
		startScreen();
		
		theframe.pack();
		theframe.setResizable(false);
		theframe.setVisible(true);
	}
	
	public void startScreen(){
		panel.add(Title);
		panel.add(Host);
		Host.addActionListener(this);
		panel.add(Guest);
		Guest.addActionListener(this);
	}
	
	public void mainGame(){
		
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

	
	//main program
	public static void main (String[] args){
		new snakeGame();
	}
}
