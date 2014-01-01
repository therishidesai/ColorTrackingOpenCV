package tests;
//import acm.graphics.*;
//import acm.program.*;
//import acm.util.*;
//import acm.io.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Frame;
import java.util.Random;

import javax.swing.JDialog;

public class Autonomus_mouse {
	private static Random rand = new Random();

	public static void main(String args[])throws AWTException{
		
		Robot robot = new Robot();
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}
}
