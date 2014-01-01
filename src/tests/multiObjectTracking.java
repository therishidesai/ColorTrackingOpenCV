package tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class multiObjectTracking {

	private static JFrame frame1 = new JFrame("Camera");
	private static Panel panel1 = new Panel();  
	private static JFrame frame2 = new JFrame("HSV");
	private static Panel panel2 = new Panel();   
	private static JFrame frame4 = new JFrame("Threshold");
	private static Panel panel4 = new Panel();
	private static int lastX =0;
	private static int lastY=0;
   public static void main(String arg[]) throws AWTException{  
		    // Load the native library.  
		         //int line = 0;
		    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
		  //JFrame frame1 = new JFrame("Camera");  
		      frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		      frame1.setSize(1366,768);  
		      frame1.setBounds(0, 0, frame1.getWidth(), frame1.getHeight());  
		      //Panel panel1 = new Panel();  
		      frame1.setContentPane(panel1);  
		      frame1.setVisible(true);  
		     // JFrame frame2 = new JFrame("HSV");  
		      frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		      frame2.setSize(1366,768);  
		      frame2.setBounds(300,100, frame2.getWidth()+300, 100+frame2.getHeight());  
		      //Panel panel2 = new Panel();  
		      frame2.setContentPane(panel2);  
		      frame2.setVisible(true);  
		      /*JFrame frame3 = new JFrame("S,V Distance");  
		      frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		      frame3.setSize(640,480);  
		      frame3.setBounds(600,200, frame3.getWidth()+600, 200+frame3.getHeight());  
		      Panel panel3 = new Panel();  
		      frame3.setContentPane(panel3);  
		      frame3.setVisible(true);*/  
		      //JFrame frame4 = new JFrame("Threshold");  
		      frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		      frame4.setSize(1366,768);  
		      frame4.setBounds(900,300, frame2.getWidth()+900, 300+frame2.getHeight());  
		      //Panel panel4 = new Panel();  
		      frame4.setContentPane(panel4);      
		      frame4.setVisible(true); 
		      VideoCapture capture =new VideoCapture(0);  
		      Mat webcam_image=new Mat();  
		      Mat hsv_image=new Mat();  
		      Mat thresholded=new Mat();  
		      Mat thresholded2=new Mat(); 
		      capture.set(3, 1366);
	            capture.set(4, 768);
		       capture.read(webcam_image);  
		       frame1.setSize(webcam_image.width()+40,webcam_image.height()+60);  
		       frame2.setSize(webcam_image.width()+40,webcam_image.height()+60);  
		       //frame3.setSize(webcam_image.width()+40,webcam_image.height()+60);  
		       frame4.setSize(webcam_image.width()+40,webcam_image.height()+60);  
		      // No need (and don't know how) to initialize it.  
		                   // The function later will do it... (to a 1*N*CV_32FC3)  
		      if( capture.isOpened())  
		      {
		    // It is better to group all frames together so cut and paste to  
		    // create more frames is easier
		    	  while(true){
		    		  capture.read(webcam_image);
		    	  		if(trackGreen(webcam_image, hsv_image, thresholded, thresholded2)){
		    	  			
		    	  			trackRed(webcam_image, hsv_image, thresholded, thresholded2);
		    	  		} 
		    	  }
		      }
		    return;  
		          }
	
private static boolean trackGreen(Mat webcam_image, Mat hsv_image, Mat thresholded, Mat thresholded2) throws AWTException{
     
      //-- 2. Read the video stream  
	Mat array255=new Mat(webcam_image.height(),webcam_image.width(),CvType.CV_8UC1);  
    array255.setTo(new Scalar(255));  
    /*Mat S=new Mat();  
    S.ones(new Size(hsv_image.width(),hsv_image.height()),CvType.CV_8UC1);  
    Mat V=new Mat();  
    V.ones(new Size(hsv_image.width(),hsv_image.height()),CvType.CV_8UC1);  
        Mat H=new Mat();  
    H.ones(new Size(hsv_image.width(),hsv_image.height()),CvType.CV_8UC1);*/  
    Mat distance=new Mat(webcam_image.height(),webcam_image.width(),CvType.CV_8UC1);  
    //new Mat();//new Size(webcam_image.width(),webcam_image.height()),CvType.CV_8UC1);  
    List<Mat> lhsv = new ArrayList<Mat>(3);      
    Mat circles = new Mat(); 
    	  Scalar hsv_min = new Scalar(49, 100, 100, 0);  
          Scalar hsv_max = new Scalar(55, 255, 255, 0);  
          Scalar hsv_min2 = new Scalar(60, 100, 100, 0);  
          Scalar hsv_max2 = new Scalar(70, 255, 255, 0);  
          double[] data=new double[3];  
         
      
           
         if( !webcam_image.empty() )  
          {  
           // One way to select a range of colors by Hue  
        	 colorTracker(2, webcam_image, hsv_image, thresholded, thresholded2, distance, hsv_min, hsv_max, hsv_min2, hsv_max2, lhsv, circles, array255, data);
           
                  //System.out.println(data[1]);
           panel1.setimagewithMat(webcam_image);  
             panel2.setimagewithMat(hsv_image);  
           //panel2.setimagewithMat(S);  
             //distance.convertTo(distance, CvType.CV_8UC1);  
             //panel3.setimagewithMat(distance);  
            panel4.setimagewithMat(thresholded);  
             frame1.repaint();  
             frame2.repaint();  
            // frame3.repaint();  
           frame4.repaint(); 
           return true;
           
          }  
          else  
          {  
            System.out.println(" --(!) No captured frame -- Break!");  
            //break;  
            return false;
          }  
         }  

private static void trackRed(Mat webcam_image, Mat hsv_image, Mat thresholded, Mat thresholded2) throws AWTException{
	  Mat array255=new Mat(webcam_image.height(),webcam_image.width(),CvType.CV_8UC1);  
      array255.setTo(new Scalar(255));  
      /*Mat S=new Mat();  
      S.ones(new Size(hsv_image.width(),hsv_image.height()),CvType.CV_8UC1);  
      Mat V=new Mat();  
      V.ones(new Size(hsv_image.width(),hsv_image.height()),CvType.CV_8UC1);  
          Mat H=new Mat();  
      H.ones(new Size(hsv_image.width(),hsv_image.height()),CvType.CV_8UC1);*/  
      Mat distance=new Mat(webcam_image.height(),webcam_image.width(),CvType.CV_8UC1);  
      //new Mat();//new Size(webcam_image.width(),webcam_image.height()),CvType.CV_8UC1);  
      List<Mat> lhsv = new ArrayList<Mat>(3);      
      Mat circles = new Mat(); // No need (and don't know how) to initialize it.  
                   // The function later will do it... (to a 1*N*CV_32FC3)  
      Scalar hsv_min = new Scalar(0, 50, 50, 0);  
      Scalar hsv_max = new Scalar(3, 255, 255, 0);  
      Scalar hsv_min2 = new Scalar(175, 50, 50, 0);  
      Scalar hsv_max2 = new Scalar(179, 255, 255, 0);  
      double[] data=new double[3];  
    
         if( !webcam_image.empty() )  
          {  
        	 colorTracker(1, webcam_image, hsv_image, thresholded, thresholded2, distance, hsv_min, hsv_max, hsv_min2, hsv_max2, lhsv, circles, array255, data);
           // One way to select a range of colors by Hue  
                 
                  //System.out.println(data[1]);
           panel1.setimagewithMat(webcam_image);  
             panel2.setimagewithMat(hsv_image);  
           //panel2.setimagewithMat(S);  
             //distance.convertTo(distance, CvType.CV_8UC1);  
             //panel3.setimagewithMat(distance);  
            panel4.setimagewithMat(thresholded);  
             frame1.repaint();  
             frame2.repaint();  
            // frame3.repaint();  
           frame4.repaint();  
           
          }  
          else  
          {
            System.out.println(" --(!) No captured frame -- Break!");  
            //break;  
          }  
         }  

private static void colorTracker(int name, Mat webcam_image, Mat hsv_image, Mat thresholded, Mat thresholded2, Mat distance, Scalar hsv_min, Scalar hsv_max, Scalar hsv_min2, Scalar hsv_max2, List<Mat> lhsv, Mat circles, Mat array255, double [] data) throws AWTException{
	
	
	Imgproc.cvtColor(webcam_image, hsv_image, Imgproc.COLOR_BGR2HSV);  
    Core.inRange(hsv_image, hsv_min, hsv_max, thresholded);           
    Core.inRange(hsv_image, hsv_min2, hsv_max2, thresholded2);
     Core.bitwise_or(thresholded, thresholded2, thresholded); 
     Imgproc.erode(thresholded, thresholded, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8,8)));
     Imgproc.dilate(thresholded, thresholded, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8)));
     // Notice that the thresholds don't really work as a "distance"  
    // Ideally we would like to cut the image by hue and then pick just  
    // the area where S combined V are largest.  
    // Strictly speaking, this would be something like sqrt((255-S)^2+(255-V)^2)>Range  
    // But if we want to be "faster" we can do just (255-S)+(255-V)>Range  
    // Or otherwise 510-S-V>Range  
    // Anyhow, we do the following... Will see how fast it goes...  
    Core.split(hsv_image, lhsv); // We get 3 2D one channel Mats  
    Mat S = lhsv.get(1);  
    Mat V = lhsv.get(2);  
    Core.subtract(array255, S, S);  
    Core.subtract(array255, V, V);  
    S.convertTo(S, CvType.CV_32F);  
    V.convertTo(V, CvType.CV_32F);  
    Core.magnitude(S, V, distance);  
    Core.inRange(distance,new Scalar(0.0), new Scalar(200.0), thresholded2);  
    Core.bitwise_and(thresholded, thresholded2, thresholded);  
    // Apply the Hough Transform to find the circles  
    Imgproc.GaussianBlur(thresholded, thresholded, new Size(9,9),0,0);  
   // Imgproc.HoughCircles(thresholded, circles, Imgproc.CV_HOUGH_GRADIENT, 2, thresholded.height()/4, 200, 100, 0, 0);   
    List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    Imgproc.HoughCircles(thresholded, circles, Imgproc.CV_HOUGH_GRADIENT, 2, thresholded.height()/8, 200, 100, 0, 0);   
      Imgproc.findContours(thresholded, contours, thresholded2, Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
      Imgproc.drawContours(webcam_image, contours, -1, new Scalar(0, 0, 0));

    //Imgproc.Canny(thresholded, thresholded, 500, 250);  
    //-- 4. Add some info to the image  
    //Core.line(webcam_image, new Point(150,50), new Point(202,200), new Scalar(100,10,10)/*CV_BGR(100,10,10)*/, 3);  
    //Core.circle(webcam_image, new Point(210,210), 10, new Scalar(100,10,10),3);  
    data=webcam_image.get(210, 210);  
    Core.putText(webcam_image,String.format("("+String.valueOf(data[0])+","+String.valueOf(data[1])+","+String.valueOf(data[2])+")"),new Point(30, 30) , 3 //FONT_HERSHEY_SCRIPT_SIMPLEX  
         ,1.0,new Scalar(100,10,10,255),3); 
    int thickness = 2;
           int lineType = 8;
         Point start = new Point(0,0);
         Point end=new Point(0,0);
         Scalar black = new Scalar( 100, 10, 10 );
         //getCoordinates(thresholded);
           
    /*try {
                       // These coordinates are screen coordinates
                       int xCoord = (int) data[0];
                       int yCoord = (int) data[1];
   
                       // Move the cursor
                       Robot robot = new Robot();
                       robot.mouseMove(xCoord, yCoord);
                   } catch (AWTException e) {
                   }*/        
    //int cols = circles.cols();  
     int rows = circles.rows();  
     int elemSize = (int)circles.elemSize(); // Returns 12 (3 * 4bytes in a float)  
     float[] data2 = new float[rows * elemSize/4];  
     if (data2.length>0){  
       circles.get(0, 0, data2); // Points to the first element and reads the whole thing  
                     // into data2  
       for(int i=0; i<data2.length; i=i+3) {  
         Point center= new Point(data2[i], data2[i+1]);  
         //Core.ellipse( this, center, new Size( rect.width*0.5, rect.height*0.5), 0, 0, 360, new Scalar( 255, 0, 255 ), 4, 8, 0 );  
         
                         // These coordinates are screen coordinates
                         int xCoord = (int) ((int) data2[i]);
                         int yCoord = (int) ((int) data2[i+1]);
                         
                         System.out.println("X: "+xCoord);
                       System.out.println("Y: "+yCoord);
                         //Red will do the clicking since green is more accurate
                       Robot robot = new Robot();
                      if(name ==1 ){
                    	 // robot.mousePress(InputEvent.BUTTON3_MASK);
                    	 //robot.mouseRelease(InputEvent.BUTTON3_MASK);
                    	  //Core.line(webcam_image, new Point(lastX, lastY), new Point(xCoord, yCoord), new Scalar(0,0,255));
                      } else{
                    	  //Core.line(webcam_image, new Point(lastX, lastY), new Point(xCoord, yCoord), new Scalar(0,255,0));
                    	 //robot.mouseMove(xCoord, yCoord);
                      }
                      lastX = xCoord;
                      lastY = yCoord;
                 
      //   Core.ellipse( webcam_image, center, new Size((double)data2[i+2], (double)data2[i+2]), 0, 0, 360, new Scalar( 255, 0, 255 ), 4, 8, 0 );  
         
       }  
     }  
    Core.line(hsv_image, new Point(150,50), new Point(202,200), new Scalar(100,10,10)/*CV_BGR(100,10,10)*/, 3);  
    Core.circle(hsv_image, new Point(210,210), 10, new Scalar(100,10,10),3);  
    data=hsv_image.get(210, 210);  
    Core.putText(hsv_image,String.format("("+String.valueOf(data[0])+","+String.valueOf(data[1])+","+String.valueOf(data[2])+")"),new Point(30, 30) , 3 //FONT_HERSHEY_SCRIPT_SIMPLEX  
         ,1.0,new Scalar(100,10,10,255),3);  
    
    distance.convertTo(distance, CvType.CV_8UC1);  
    Core.line(distance, new Point(150,50), new Point(202,200), new Scalar(100)/*CV_BGR(100,10,10)*/, 3);  
    Core.circle(distance, new Point(210,210), 10, new Scalar(100),3);  
    data=(double[])distance.get(210, 210);  
    //getCoordinates(thresholded);
    Core.putText(distance,String.format("("+String.valueOf(data[0])+")"),new Point(30, 30) , 3 //FONT_HERSHEY_SCRIPT_SIMPLEX  
         ,1.0,new Scalar(100),3); 
	
}



}  

	








