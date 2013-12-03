package tests;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
//import acm.io.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Frame;
import java.util.Random;

import javax.swing.JDialog;

public class Autonomus_mouse extends GraphicsProgram {
	Random rand = new Random();

	public void run(){
		
		while(true){
			try {
			    // These coordinates are screen coordinates
			    int xCoord = rand.nextInt(1000);
			    int yCoord = rand.nextInt(1000);
	
			    // Move the cursor
			    Robot robot = new Robot();
			    robot.mouseMove(xCoord, yCoord);
			} catch (AWTException e) {
			}
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
}
