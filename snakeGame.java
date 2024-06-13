import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//import java.awt.image.BufferedImageFilter;
//import javax.swing.event.*;
import java.io.*;
import java.util.Random;

public class snakeGame implements ActionListener, KeyListener {
    /** Properties
    /** JFrame for the frame */
    static JFrame theframe = new JFrame("Snake");

    /** Start panel */
    StartPanel startPanel = new StartPanel();

    /** Animation panel */
    AnimationPanel panel = new AnimationPanel();
    
    /** SuperSocketMaster */
    static SuperSocketMaster ssm;
	
    /** JLabel for the ip */
    JLabel ipLabel = new JLabel("IP");
	
    /** JTextField for the ip field*/
    JTextField ipField = new JTextField();
    
    /**  JLabel for the port*/
    JLabel portLabel = new JLabel("Port");
    
    /** JTextField for the port field */
    JTextField portField = new JTextField();
    
    /** JButton for creating the server as a host */
    JButton hostButton = new JButton("Create Server");
    
    /** JButton for joining the server */
    JButton joinButton = new JButton("Join Server");
    
    /** JTextArea displaying the connection status */
    JTextArea connectStat = new JTextArea();
    
    /** JLabel for the username */
    JLabel usernameLabel = new JLabel("Username");
    
    /** JTextField for entering a username */
    JTextField usernameField = new JTextField();
    
    /** JButton to select easy difficulty */
    JButton easyButton = new JButton("Easy");
    
    /** JButton to select normal difficulty */
    JButton normButton = new JButton("Normal");
    
    /** JButton to select hard difficulty */
    JButton hardButton = new JButton("Hard");
    
    /** JButton to start playing */
    JButton playButton = new JButton("Ready");
    
    /** JButton to select the Star Wars theme */
    JButton theme1Button = new JButton("Star Wars");
    
    /** JButton to select the Zelda theme */
    JButton theme2Button = new JButton("Zelda");
    
    /** JButton to go to help screen */
    JButton helpButton = new JButton("?");
    
    /** Timer for the animation */
    static Timer animationTimer = new Timer(1000/60, null);
    
    /** Timer for the movement of the snake */
    static Timer moveTimer = new Timer(100, null);

    /** JButton for the chat */
    JButton chatButton = new JButton("Chat");
    
    /** JTextArea for the chat */
    static JTextArea chat = new JTextArea();
    
    /** JScrollPane to scroll through chat messages */
    JScrollPane scrollPane = new JScrollPane(chat);
    
    /** JTextField to enter a message */
	static JTextField message = new JTextField();
	
    /** Boolean to determine if the chat is disabled */
    boolean chatEnabled = false;
    
    /** Help screen */
    helpPanel helpScreen = new helpPanel(theframe, startPanel);
    
    /** String to hold the message */
    String strLine;
    
    /** String to split the message */
    String strSplit[];
    
    /** String for the message type */
    String strMsgType;
    
    /** String for the username who sent the message */
    String strMsgUser;
	
    /** String for the message command type */
    String strMsgCmd;
	
    /** Argument for the message */
    String strMsgArg;
	
    /** Second argument for the message */
    String strMsgArg2;
	
    /** Boolean to determine if a player is connected */
    boolean playerConnected = false;
	
    /** X coordinate for the message */
    int intMsgX;
	
    /** Y coordinate for the message */
    int intMsgY;
	
    /** String for the message being sent */
    String strMsgSent;
	
    /** String for Player 1's username */
    static String strUsername1;
	
    /** String for Player 2's username */
    static String strUsername2;
	
    /** Int for if Player 1 is ready */
    int intReady1 = 0;
	
    /** Int for if Player 2 is ready */
    int intReady2 = 0;
	
    /** Int to determine which player is which */
    int intSelf;
	
    /** Int to determine the theme */
    int intTheme = 1;
	
    /** String to determine map file */
    String strMapFile = "Map - Easy.csv";
	
    /** Int to determine starting length of snake */
    int intLength = 3;
	
    /** Temporary 2D array to hold the snake's location */
    int intTemp[][] = new int[160][2];
	
    /** Int to determine the direction the snake is going */
    static int intDirection = 4;
	
    /** 2D array to hold the snake's location*/
    int intSnakeSelf[][] = new int[160][2];
	
    /** Boolean to enable commands */
    boolean commandsEnabled = false;

    // Methods
	/** Method to look out for actions performed */
    public void actionPerformed(ActionEvent evt) {
        // create a server
        if (evt.getSource() == hostButton) {
            int intPort = (int) (Math.random() * 65535);
            while (intPort < 1024 || intPort > 49151) { // ensure it's a valid and non-privileged port
                intPort = (int) (Math.random() * 65535);
            }
            ssm = new SuperSocketMaster(intPort, this);
            if (ssm.connect() == true) {
                playButton.setEnabled(true);
                intSelf = 1;
            } else {
                ssm.disconnect();
            }
            portField.setText(String.valueOf(intPort));
            String ip = ssm.getMyAddress();
            ipField.setText(ip);
            connectStat.setText("Waiting for player to connect...\nYou are the snake in the top left corner.");
            joinButton.setEnabled(false);
            hostButton.setEnabled(false);
            ipField.setEnabled(false);
            portField.setEnabled(false);
        }

        // join a server
        if (evt.getSource() == joinButton) {
            ssm = new SuperSocketMaster(ipField.getText(), Integer.parseInt(portField.getText()), this);
            if (ssm.connect() == true) {
                playButton.setEnabled(true);
                intSelf = 2;
            
            } else {
                ssm.disconnect();
            }

            ssm.sendText("Connect, Player connected");
            connectStat.setText("Connected to server\nYou are the snake in the botton right corner. ");
            joinButton.setEnabled(false);
            hostButton.setEnabled(false);
            ipField.setEnabled(false);
            portField.setEnabled(false);
            easyButton.setEnabled(false);
            normButton.setEnabled(false);
            hardButton.setEnabled(false);
            theme1Button.setEnabled(false);
            theme2Button.setEnabled(false);
        }

        // network message
        if (evt.getSource() == ssm) {
            strLine = ssm.readText();
            System.out.println(strLine);
            strSplit = strLine.split(",");
            strMsgType = strSplit[0];
            strMsgUser = strSplit[1];
            if (strMsgType.equals("Connect") && !playerConnected) {
                playerConnected = true;
                connectStat.append("\nPlayer connected");
            }

            if (strMsgType.equals("Game")) {
                intMsgX = Integer.parseInt(strSplit[2]);
                intMsgY = Integer.parseInt(strSplit[3]);
                if(intSelf == 1){
                    for(int intCount = 0; intCount < panel.intLength2; intCount++){
                        intTemp[intCount][0] = panel.intSnake2[intCount][0];
                        intTemp[intCount][1] = panel.intSnake2[intCount][1];
                    }
                    panel.intSnake2[0][0] = intMsgX;
                    panel.intSnake2[0][1] = intMsgY;
                    for(int intCount = 0; intCount < (panel.intLength2 - 1); intCount++){
                        panel.intSnake2[intCount + 1][0] = intTemp[intCount][0];
                        panel.intSnake2[intCount + 1][1] = intTemp[intCount][1];
                    }
                }else if(intSelf == 2){
                    for(int intCount = 0; intCount < panel.intLength1; intCount++){
                        intTemp[intCount][0] = panel.intSnake1[intCount][0];
                        intTemp[intCount][1] = panel.intSnake1[intCount][1];
                    }
                    panel.intSnake1[0][0] = intMsgX;
                    panel.intSnake1[0][1] = intMsgY;
                    for(int intCount = 0; intCount < (panel.intLength1 - 1); intCount++){
                        panel.intSnake1[intCount + 1][0] = intTemp[intCount][0];
                        panel.intSnake1[intCount + 1][1] = intTemp[intCount][1];
                    }
                }
            } else if (strMsgType.equals("Message")) {
                strMsgSent = strSplit[2];
				chat.append(strMsgUser+": "+strMsgSent+"\n");
            } else if (strMsgType.equals("System")) {
                System.out.println(strLine);
                strMsgCmd = strSplit[2];
                strMsgArg = strSplit[3];
                strMsgArg2 = strSplit[4];
                if (strMsgCmd.equals("sentUsername")) {
                    if (intSelf == 1) {
                        strUsername2 = strMsgUser;
                        intReady2 = 1;
                        intDirection = 2;
                        
                    } else if (intSelf == 2) {
                        strUsername2 = strMsgUser;
                        intReady1 = 1;
                        intDirection = 4;
                    }

                    System.out.println(intReady1 + " " + intReady2);
				}else if (strMsgCmd.equals("startGame")) {
                    panel.loadThemeImages(intTheme);
                    panel.loadMap(strMapFile);
					panel.add(scrollPane);
					panel.add(message);
                    theframe.setContentPane(panel);
                    theframe.validate();
                    theframe.repaint();
                    animationTimer.start();
                    moveTimer.start();
                }else if(strMsgCmd.equals("spawnFood")){
                    panel.mapData[Integer.parseInt(strMsgArg)][Integer.parseInt(strMsgArg2)] = "food";
                }else if(strMsgCmd.equals("foodEaten")){
                    panel.mapData[Integer.parseInt(strMsgArg)][Integer.parseInt(strMsgArg2)] = "f";
                    if(intSelf == 1){
                        panel.intLength2 = panel.intLength2 + 1;
                    }else if(intSelf == 2){
                        panel.intLength1 = panel.intLength1 + 1;
                    }
                }else if(strMsgCmd.equals("clearMap")){
                    clearMap();
                    forceRepaint();
                }else if (strMsgCmd.equals("stopGame")){
                    if (!strMsgUser.equals(strUsername1)){
                        winGame(strUsername1);
                    }else{
                        winGame(strUsername2);
                    }
                }else if (strMsgCmd.equals("stopTime")){
                    moveTimer.stop();
                }
            } else if (strMsgType.equals("diff")) {
                strMapFile = strSplit[1];
            } else if (strMsgType.equals("theme")) {
                intTheme = Integer.parseInt(strSplit[1]);
            }
        }
		
		/*players ready*/
		if (evt.getSource() == playButton) {
            if (usernameField.getText().equals("")) {
                connectStat.append("\nPlease Enter a Username, do not try to break the game :(");
            } else {
                if (usernameField.getText().equals("admin")){
                    commandsEnabled = true;
                }
                if (intSelf == 1) {
                    strUsername1 = usernameField.getText();
                    intReady1 = 1;
                    theme1Button.setEnabled(false);
                    theme2Button.setEnabled(false);
					ssm.sendText("diff," + strMapFile+",null,null");
                    ssm.sendText("theme," + intTheme+",null,null");
                    if (intReady1 == 1 && intReady2 == 1) {
                        panel.loadThemeImages(intTheme);
                        panel.loadMap(strMapFile);
						panel.add(scrollPane);
						panel.add(message);
                        theframe.setContentPane(panel);
                        theframe.validate();
                        theframe.repaint();
                        ssm.sendText("System," + strUsername1 + ",startGame,null,null");
                        animationTimer.start();
                        moveTimer.start();
                        spawnFood();
                    }
                } else if (intSelf == 2) {
                    strUsername2 = usernameField.getText();
                    theme1Button.setEnabled(false);
                    theme2Button.setEnabled(false);
                    ssm.sendText("System," + strUsername2 + ",sentUsername,null,null");
                    intReady2 = 1;
                    if (intReady1 == 1 && intReady2 == 1) {
                        panel.loadThemeImages(intTheme);
                        panel.loadMap(strMapFile);
						panel.add(scrollPane);
						panel.add(message);
                        theframe.setContentPane(panel);
                        theframe.validate();
                        theframe.repaint();
                        animationTimer.start();
                        moveTimer.start();
                    }
                }
            }
        }
		/*theme choosing */
		if (evt.getSource() == theme1Button) {
            intTheme = 1;
			theme1Button.setEnabled(false);
			theme2Button.setEnabled(false);
        } else if (evt.getSource() == theme2Button) {
            intTheme = 2;
			theme1Button.setEnabled(false);
			theme2Button.setEnabled(false);
        } 
		
		/*difficulty */
		if (evt.getSource() == easyButton) {
            strMapFile = "Map - Easy.csv";
			easyButton.setEnabled(false);
			normButton.setEnabled(false);
			hardButton.setEnabled(false);
        } else if (evt.getSource() == normButton) {
            strMapFile = "Map - Normal.csv";
			easyButton.setEnabled(false);
			normButton.setEnabled(false);
			hardButton.setEnabled(false);
        } else if (evt.getSource() == hardButton) {
            strMapFile = "Map - Hard.csv";
			easyButton.setEnabled(false);
			normButton.setEnabled(false);
			hardButton.setEnabled(false);
        }

		/*message sending */
		if (evt.getSource() == message){
			String strMessage = message.getText();
			if(!strMessage.equals("")){
                chat.append("You: "+strMessage+"\n");
                if(intSelf == 1){
                    ssm.sendText("Message,"+strUsername1+","+strMessage);
                }else if(intSelf == 2){
                    ssm.sendText("Message,"+strUsername2+","+strMessage);
                }
            }
			message.setText("");
				
		}
        if(evt.getSource() == animationTimer){
            if(intSelf == 1){
                if(panel.eatFood1() == true){
                    ssm.sendText("System,null,foodEaten,"+panel.intSnake1[0][0]+","+panel.intSnake1[0][1]);
                    spawnFood();
                    intSnakeSelf = panel.intSnake1.clone();
                }
            }else if(intSelf == 2){
                if(panel.eatFood2() == true){
                    ssm.sendText("System,null,foodEaten,"+panel.intSnake2[0][0]+","+panel.intSnake2[0][1]);
                    spawnFood();
                    intSnakeSelf = panel.intSnake2.clone();
                }
            }
            panel.removeSnake();
            panel.paintSnake();
            panel.repaint();
            //System.out.println("repainted");
        }

        if(evt.getSource() == helpButton){
            theframe.setContentPane(helpScreen);
            theframe.validate();
            theframe.repaint();
        }

        if(evt.getSource() == chatButton){
            if (chatEnabled == false){
                message.setEnabled(true);
                chatEnabled = true;
            }else{
                message.setEnabled(false);
                chatEnabled = true;
            }
        }
        
        if(evt.getSource() == moveTimer){
            if(intSelf == 1){
                if(intDirection == 1 && panel.intSnake1[0][1] > 0){
                    for(int intCount = 0;intCount < panel.intLength1; intCount++){
                        intTemp[intCount][0] = panel.intSnake1[intCount][0];
                        intTemp[intCount][1] = panel.intSnake1[intCount][1];
                    }
                    panel.intSnake1[0][1] = panel.intSnake1[0][1] - 1;
                    for(int intCount = 0;intCount < (panel.intLength1 - 1); intCount++){
                        panel.intSnake1[intCount + 1][0] = intTemp[intCount][0];
                        panel.intSnake1[intCount + 1][1] = intTemp[intCount][1];
                    }
                }else if(intDirection == 2 && panel.intSnake1[0][0] < 39){
                    for(int intCount = 0;intCount < panel.intLength1; intCount++){
                        intTemp[intCount][0] = panel.intSnake1[intCount][0];
                        intTemp[intCount][1] = panel.intSnake1[intCount][1];
                    }
                    panel.intSnake1[0][0] = panel.intSnake1[0][0] + 1;
                    for(int intCount = 0;intCount < (panel.intLength1 - 1); intCount++){
                        panel.intSnake1[intCount + 1][0] = intTemp[intCount][0];
                        panel.intSnake1[intCount + 1][1] = intTemp[intCount][1];
                    }
                }else if(intDirection == 3 && panel.intSnake1[0][1] < 39){
                    for(int intCount = 0;intCount < panel.intLength1; intCount++){
                        intTemp[intCount][0] = panel.intSnake1[intCount][0];
                        intTemp[intCount][1] = panel.intSnake1[intCount][1];
                    }
                    panel.intSnake1[0][1] = panel.intSnake1[0][1] + 1;
                    for(int intCount = 0;intCount < (panel.intLength1 - 1); intCount++){
                        panel.intSnake1[intCount + 1][0] = intTemp[intCount][0];
                        panel.intSnake1[intCount + 1][1] = intTemp[intCount][1];
                    }
                }else if(intDirection == 4 && panel.intSnake1[0][0] > 0){
                    for(int intCount = 0;intCount < panel.intLength1; intCount++){
                        intTemp[intCount][0] = panel.intSnake1[intCount][0];
                        intTemp[intCount][1] = panel.intSnake1[intCount][1];
                    }
                    panel.intSnake1[0][0] = panel.intSnake1[0][0] - 1;
                    for(int intCount = 0;intCount < (panel.intLength1 - 1); intCount++){
                        panel.intSnake1[intCount + 1][0] = intTemp[intCount][0];
                        panel.intSnake1[intCount + 1][1] = intTemp[intCount][1];
                    }
                }else{
                    stopGame(strUsername1, strUsername2);
                }
                ssm.sendText("Game,"+strUsername1+","+panel.intSnake1[0][0]+","+panel.intSnake1[0][1]);
            }
            if(intSelf == 2){
                //System.out.println("Snake Paint");
                if(intDirection == 1 && panel.intSnake2[0][1] > 0){
                    for(int intCount = 0;intCount < panel.intLength2; intCount++){
                        intTemp[intCount][0] = panel.intSnake2[intCount][0];
                        intTemp[intCount][1] = panel.intSnake2[intCount][1];
                    }
                    panel.intSnake2[0][1] = panel.intSnake2[0][1] - 1;
                    for(int intCount = 0;intCount < (panel.intLength2 - 1); intCount++){
                        panel.intSnake2[intCount + 1][0] = intTemp[intCount][0];
                        panel.intSnake2[intCount + 1][1] = intTemp[intCount][1];
                    }
                }else if(intDirection == 2 && panel.intSnake2[0][0] < 39){
                    for(int intCount = 0;intCount < panel.intLength2; intCount++){
                        intTemp[intCount][0] = panel.intSnake2[intCount][0];
                        intTemp[intCount][1] = panel.intSnake2[intCount][1];
                    }
                    panel.intSnake2[0][0] = panel.intSnake2[0][0] + 1;
                    for(int intCount = 0;intCount < (panel.intLength2 - 1); intCount++){
                        panel.intSnake2[intCount + 1][0] = intTemp[intCount][0];
                        panel.intSnake2[intCount + 1][1] = intTemp[intCount][1];
                    }
                }else if(intDirection == 3 && panel.intSnake2[0][1] < 39){
                    for(int intCount = 0;intCount < panel.intLength2; intCount++){
                        intTemp[intCount][0] = panel.intSnake2[intCount][0];
                        intTemp[intCount][1] = panel.intSnake2[intCount][1];
                    }
                    panel.intSnake2[0][1] = panel.intSnake2[0][1] + 1;
                    for(int intCount = 0;intCount < (panel.intLength2 - 1); intCount++){
                        panel.intSnake2[intCount + 1][0] = intTemp[intCount][0];
                        panel.intSnake2[intCount + 1][1] = intTemp[intCount][1];
                    }
                }else if(intDirection == 4 && panel.intSnake2[0][0] > 0){
                    for(int intCount = 0;intCount < panel.intLength2; intCount++){
                        intTemp[intCount][0] = panel.intSnake2[intCount][0];
                        intTemp[intCount][1] = panel.intSnake2[intCount][1];
                    }
                    panel.intSnake2[0][0] = panel.intSnake2[0][0] - 1;
                    for(int intCount = 0;intCount < (panel.intLength2 - 1); intCount++){
                        panel.intSnake2[intCount + 1][0] = intTemp[intCount][0];
                        panel.intSnake2[intCount + 1][1] = intTemp[intCount][1];
                    }
                }else{
                    stopGame(strUsername2, strUsername1);
                }
                ssm.sendText("Game,"+strUsername2+","+panel.intSnake2[0][0]+","+panel.intSnake2[0][1]);
            }   
        }
    }
	/** Method to look out for keys released */
    public void keyReleased(KeyEvent evt) {

    }
    
	/** Method to look out for keys pressed */
    public void keyPressed(KeyEvent evt) {
        System.out.println("Key Pressed");
        if(evt.getKeyChar() == 'w' && intDirection != 3 && intSnakeSelf[0][1] != (intSnakeSelf[1][1] - 1)){
            intDirection = 1;
        }else if(evt.getKeyChar() == 'd' && intDirection != 4 && intSnakeSelf[0][0] != (intSnakeSelf[1][0] + 1)){
            intDirection = 2;
        }else if(evt.getKeyChar() == 's' && intDirection != 1 && intSnakeSelf[0][1] != (intSnakeSelf[1][1] + 1)){
            intDirection = 3;
        }else if(evt.getKeyChar() == 'a' && intDirection != 2 && intSnakeSelf[0][0] != (intSnakeSelf[1][0] - 1)){
            intDirection = 4;
        }else if(evt.getKeyChar() == 'p' && commandsEnabled == true){
            clearMap();
            ssm.sendText("System,null,clearMap,null,null");
        }else if(evt.getKeyChar() == 'o' && commandsEnabled == true){
            spawnFood();
        }else if(evt.getKeyChar() == 'i' && commandsEnabled == true){
            ssm.sendText("System,null,stopTime,null,null");
        }
    }
	/** Method to clear map */
    public void clearMap(){
        for(int i = 0; i < 40; i++){
            for(int j = 0; j < 40; j++){
                panel.mapData[i][j] = "f";
            }
        }
        spawnFood();
        panel.repaint();
    }
	/** Method to look out for key typed */
    public void keyTyped(KeyEvent evt) {
        
    }
	/** Method to force repaint */
    public void forceRepaint(){
        panel.removeSnake();
        panel.paintSnake();
        panel.repaint();
    }
	/** Method to spawn food */
    public void spawnFood(){
        Random rand = new Random();
        int intRandX = rand.nextInt(39);
        int intRandY = rand.nextInt(39);
        if(panel.mapData[intRandX][intRandY].equals("f")){
            panel.mapData[intRandX][intRandY] = "food";
            panel.intFood[0][0] = intRandY;
            panel.intFood[0][1] = intRandX;
            ssm.sendText("System,null,spawnFood,"+intRandX+","+intRandY);
            System.out.println("Food Spawned: "+intRandX+","+intRandY);
        }else{
            spawnFood();
        }
    }
	/** Method to read the map csv file */
    public static String[][] readFile(int intCol, String strFileName) {
        int intRow = 0;
        
        try {
            InputStream inputStream = snakeGame.class.getResourceAsStream("/" + strFileName);
            if (inputStream == null) {
                System.out.println("File not found: " + strFileName);
                return new String[0][0];
            }
            BufferedReader file = new BufferedReader(new InputStreamReader(inputStream));
            String strLine;
            while ((strLine = file.readLine()) != null) {
                intRow++;
            }
            file.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new String[0][0];
        }
    
        String[][] strMap = new String[intRow][intCol];
        
        try {
            InputStream inputStream = snakeGame.class.getResourceAsStream("/" + strFileName);
            if (inputStream == null) {
                System.out.println("File not found: " + strFileName);
                return new String[0][0];
            }
            BufferedReader map = new BufferedReader(new InputStreamReader(inputStream));
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new String[0][0];
        }
    
        return strMap;
    }

	/** Method to stop game */
    public static void stopGame (String strLoser, String strWinner){
        animationTimer.stop();
        moveTimer.stop();
        ssm.sendText("System,"+strLoser+",stopGame,null,null");
        chat.append("UH OH YOU HAVE DIED\n");
        chat.append("YOU'VE LOST\n");
    }

	/** Method to stop the game when someone loses */
    public static void winGame(String strWinner){
        animationTimer.stop();
        moveTimer.stop();
        chat.append("Your opponent has died\n");
        chat.append("YOU ARE THE WINNER\n");
    }

    // constructor
    /**Constructor Method */
    public snakeGame() {
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
        ipField.setSize(new Dimension(350, 50));
        ipField.setLocation(50, 500);

        startPanel.add(portLabel);
        portLabel.setSize(new Dimension(350, 50));
        portLabel.setLocation(505, 450);

        startPanel.add(portField);
        portField.setSize(new Dimension(350, 50));
        portField.setLocation(500, 500);

        startPanel.add(hostButton);
        hostButton.setSize(new Dimension(350, 50));
        hostButton.setLocation(50, 400);
        hostButton.addActionListener(this);

        startPanel.add(joinButton);
        joinButton.addActionListener(this);
        joinButton.setSize(new Dimension(350, 50));
        joinButton.setLocation(500, 400);

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

        startPanel.add(helpButton);
        helpButton.addActionListener(this);
        helpButton.setSize(new Dimension(100, 70));
        helpButton.setLocation(1130, 600);

        startPanel.repaint();

        theframe.pack();
        theframe.setResizable(false);
        theframe.setVisible(true);

        panel.add(chatButton);
        chatButton.setSize(new Dimension(100,25));
        chatButton.setLocation(770, 10);
        chatButton.addActionListener(this);

        scrollPane.setSize(new Dimension(460, 570));
		scrollPane.setLocation(770, 50);

		message.setSize(new Dimension(460, 50));
		message.setLocation(770, 640);
		message.addActionListener(this);
        message.setEnabled(false);

        chat.setEditable(false);

        animationTimer.addActionListener(this);
        moveTimer.addActionListener(this);

        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.addKeyListener(this);
    
        theframe.addKeyListener(this);
        theframe.setFocusable(true);
        theframe.requestFocusInWindow();
    }

    // main program
    /**Main Method */
    public static void main(String[] args) {
        new snakeGame();
    }
}
