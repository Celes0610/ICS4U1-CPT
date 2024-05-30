import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.Graphics;

public class snake implements ActionListener{
    //properties
    int[][] intSegments;
    char direction;
    boolean isAlive;
    int length;
    int[] intTemp;
    int intLength;
    int intDirection;
    Timer thetimer = new Timer(1000,null);

    //methods
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == thetimer){
            if(intDirection == 1){
                for(int intCount = 0;intCount <= intLength; intCount++){
                    intTemp[intCount] = intSegments[intCount][1];
                }
                intSegments[0][1] = intSegments[0][1] - 1;
                for(int intCount = 0; intCount <= (intLength - 1); intCount++){
                    intSegments[intCount + 1][1] = intTemp[intCount];
                }
            }else if(intDirection == 2){
                for(int intCount = 0;intCount <= intLength; intCount++){
                    intTemp[intCount] = intSegments[intCount][0];
                }
                intSegments[0][0] = intSegments[0][0] + 1;
                for(int intCount = 0; intCount <= (intLength - 1); intCount++){
                    intSegments[intCount + 1][0] = intTemp[intCount];
                }
            }else if(intDirection == 3){
                for(int intCount = 0;intCount <= intLength; intCount++){
                    intTemp[intCount] = intSegments[intCount][1];
                }
                intSegments[0][1] = intSegments[0][1] + 1;
                for(int intCount = 0; intCount <= (intLength - 1); intCount++){
                    intSegments[intCount + 1][1] = intTemp[intCount];
                }
            }else if(intDirection == 4){
                for(int intCount = 0;intCount <= intLength; intCount++){
                    intTemp[intCount] = intSegments[intCount][0];
                }
                intSegments[0][0] = intSegments[0][0] - 1;
                for(int intCount = 0; intCount <= (intLength - 1); intCount++){
                    intSegments[intCount + 1][0] = intTemp[intCount];
                }
            }
        }
    }
    public void move(){

    }

    public void setDirection(String newDirection){

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
    public snake(int startX, int startY, int intStartDirection){
        thetimer.addActionListener(this);
        intSegments[0][0] = startX;
        intSegments[0][1] = startY;
        this.intDirection = intStartDirection;
    }
}
