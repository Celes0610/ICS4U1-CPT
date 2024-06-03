//import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//import javax.swing.event.*;
//import java.io.*;
import java.awt.Graphics;

public class snake implements ActionListener{
    //properties
    int[][] intSegments;
    char direction;
    boolean isAlive;
    int length;
    int[] intTemp = new int[160];
    int intLength;
    static int intDirection;
    Timer thetimer = new Timer(100,null);

    //methods
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == thetimer){
            move();
            //System.out.println("Move");
        }
    }
    public void move() {
        if (intDirection == 1) { // Up
            System.out.println("Moved Up");
            for (int intCount = 0; intCount <= (intLength - 1); intCount++) {
                intTemp[intCount] = intSegments[intCount][1];
            }
            intSegments[0][1] = intSegments[0][1] - 1; // Move head up
            for (int intCount = 0; intCount <= (intLength - 1); intCount++) {
                intSegments[intCount + 1][1] = intTemp[intCount];
            }
        } else if (intDirection == 2) { // Right
            System.out.println("Moved Right");
            for (int intCount = 0; intCount <= (intLength - 1); intCount++) {
                intTemp[intCount] = intSegments[intCount][0];
            }
            intSegments[0][0] = intSegments[0][0] + 1; // Move head right
            for (int intCount = 0; intCount <= (intLength - 1); intCount++) {
                intSegments[intCount + 1][0] = intTemp[intCount];
            }
        } else if (intDirection == 3) { // Down
            System.out.println("Moved Down");
            for (int intCount = 0; intCount <= (intLength - 1); intCount++) {
                intTemp[intCount] = intSegments[intCount][1];
            }
            intSegments[0][1] = intSegments[0][1] + 1; // Move head down
            for (int intCount = 0; intCount <= (intLength - 1); intCount++) {
                intSegments[intCount + 1][1] = intTemp[intCount];
            }
        } else if (intDirection == 4) { // Left
            System.out.println("Moved Left");
            for (int intCount = 0; intCount <= (intLength - 1); intCount++) {
                intTemp[intCount] = intSegments[intCount][0];
            }
            intSegments[0][0] = intSegments[0][0] - 1; // Move head left
            for (int intCount = 0; intCount <= (intLength - 1); intCount++) {
                intSegments[intCount + 1][0] = intTemp[intCount];
            }
        }
    }

    public static int setDirection(String newDirection) {
        if (newDirection.equals("up") && intDirection != 3) { // Prevent moving directly down if currently moving up
            intDirection = 1;
        } else if (newDirection.equals("right") && intDirection != 4) { // Prevent moving directly left if currently moving right
            intDirection = 2;
        } else if (newDirection.equals("down") && intDirection != 1) { // Prevent moving directly up if currently moving down
            intDirection = 3;
        } else if (newDirection.equals("left") && intDirection != 2) { // Prevent moving directly right if currently moving left
            intDirection = 4;
        }
        return intDirection;
    }
    public void startGame(){
        thetimer.start();
    }
    public void stopGame(){
        thetimer.stop();
    }

    public void grow(){
        intLength = intLength + 1;
    }

    public boolean collision(){
        return true;
    }

    public void drawSnake(Graphics g){
        
    }

    public int headXPos(){
        return intSegments[0][0];
    }
    public int headYPox(){
        return intSegments[0][1];
    }

    public boolean isAlive(){
        return true;
    }
    
    
    //constructor
    public snake(int startX, int startY, int intStartDirection, int intLength){
        this.intSegments = new int[160][2];
        thetimer.addActionListener(this);
        this.intSegments[0][0] = startX;
        this.intSegments[0][1] = startY;
        this.intSegments[1][0] = startX;
        this.intSegments[1][1] = startY;
        this.intSegments[2][0] = startX;
        this.intSegments[2][1] = startY;
        intDirection = intStartDirection;
        System.out.println(intSegments[0][0]+" "+intSegments[0][1]+" "+intDirection);
        thetimer.addActionListener(this);
    }
}
