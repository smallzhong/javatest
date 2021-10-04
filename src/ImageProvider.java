

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/*
 * 产生图象
 */
public class ImageProvider {
	
	private Robot robot=null;//图象采集类
	private Rectangle rect=null;//要复制的 屏幕区域
	/*
	 * 
	 */
	/*
	 * 构造函数，输入要采集的屏幕的 矩形信息
	 */
	public ImageProvider() throws AWTException{
		rect=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());//复制全屏
		robot=new Robot();//创建Robot对象
	}
   /*
    * 复制全屏幕，返回BufferedImage对象
    */
	public BufferedImage CopyScreen(){
		BufferedImage image=robot.createScreenCapture(rect);//
	    return image;
	}
   

}
