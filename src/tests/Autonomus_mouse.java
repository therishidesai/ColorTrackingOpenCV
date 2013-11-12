package tests;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
//import acm.io.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Frame;
import javax.swing.JDialog;

public class Autonomus_mouse extends GraphicsProgram {

	public void run(){
		try {
		    // These coordinates are screen coordinates
		    int xCoord = 500;
		    int yCoord = 500;

		    // Move the cursor
		    Robot robot = new Robot();
		    robot.mouseMove(xCoord, yCoord);
		} catch (AWTException e) {
		}
	}
}
