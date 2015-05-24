import java.awt.AWTException;
import java.awt.Robot;
import org.opencv.core.Mat;   
import org.opencv.core.MatOfPoint;
import java.util.List;
import java.util.ArrayList;
import org.opencv.imgproc.*;
import org.opencv.core.Core;
import java.awt.event.InputEvent;


public class Mouse {
	private Robot mouse;
	Mouse() throws AWTException{
		
		mouse = new Robot();
	}
	public int getX(List<MatOfPoint> contours){
		List<Moments> mu = new ArrayList<Moments>(contours.size());
		int x=0;
        for (int i = 0; i < contours.size(); i++) {
            mu.add(i, Imgproc.moments(contours.get(i), false));
            Moments p = mu.get(i);
            x = (int) (p.get_m10() / p.get_m00());
        }
        return x;
	}
	public int getY(List<MatOfPoint> contours){
		List<Moments> mu = new ArrayList<Moments>(contours.size());
		int y=0;
        for (int i = 0; i < contours.size(); i++) {
            mu.add(i, Imgproc.moments(contours.get(i), false));
            Moments p = mu.get(i);
            y = (int) (p.get_m01() / p.get_m00());
        }
        return y;
	}
	public void moveMouse(int x, int y){
		
            mouse.mouseMove(x, y);
            
        
	}
	public void rightClick(){
		mouse.mousePress(InputEvent.BUTTON3_MASK);
		mouse.mouseRelease(InputEvent.BUTTON3_MASK);
	}
	
	public void holdObject(){
		mouse.mousePress(InputEvent.BUTTON1_MASK);
	}
}
