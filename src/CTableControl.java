import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/*
 *桌面控制类,读取鼠标,键盘控制命令，并执行
 */
public class CTableControl extends Thread{

	private Socket socket=null;//命令读取套接字
	private ObjectInputStream in=null;//事件对象流
    private Robot action=null; 
	
	public CTableControl(Socket socket) throws IOException, AWTException{
		action=new Robot();
		this.socket=socket;
		this.socket.setReceiveBufferSize(Parameter.EVENT_CACHE);
		in=new ObjectInputStream(socket.getInputStream());
		
	}
	
	public void run(){
	 
		while(true){
			 try {
				Object ob=in.readObject();//读取
			//	tools.print(tools.getSystemTime()+" 成功读取事件");
				if(ob!=null){
					handleEvent((InputEvent)ob);//处理
				}
			} catch (IOException e) {
			  e.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				 break;
			}
		
		 }//while

	}
	/*
	 * 处理事件
	 */
	private void handleEvent(InputEvent event){
		//tools.print(tools.getSystemTime()+" 处理事件 事件ID:"+event.getID()); 
		MouseEvent mevent=null;//鼠标事件
		KeyEvent kevent=null;//键盘事件
		int mousebuttonmask=-100;//鼠标按键
		switch (event.getID()){//根据 事件类型分类 有 MOUSE_PRESSED , REALSE ,CLICK,ENTER,等
		      case MouseEvent.MOUSE_MOVED:
		    	   mevent=(MouseEvent)event;
		    	  this.action.mouseMove(mevent.getX(),mevent.getY());
		    	  break;
		  
		      case MouseEvent.MOUSE_CLICKED:
		    	   tools.print("鼠标按下MOUSE_CLICKED");
		    	   mevent=(MouseEvent)event;
		    	   this.action.mouseMove(mevent.getX(),mevent.getY());
		    	   mousebuttonmask=MouseClickTanslate(mevent.getButton());
		    	   if(mousebuttonmask!=-100)
		    	   mouseClick(mousebuttonmask);
		    	  break;
		      case MouseEvent.MOUSE_RELEASED:
		    	  tools.print("鼠标释放MOUSE_RELEASED");
		    	   mevent=(MouseEvent)event;
		    	   this.action.mouseMove(mevent.getX(),mevent.getY());
		    	   mousebuttonmask=MouseClickTanslate(mevent.getButton());
		    	   if(mousebuttonmask!=-100)
		    	   this.action.mouseRelease(mousebuttonmask);
		    	  break;
		      case MouseEvent.MOUSE_WHEEL:
//		    	   mevent=(MouseEvent)event;
//		    	   this.action.mouseMove(mevent.getX(),mevent.getY());
//		    	   mousebuttonmask=MouseClickTanslate(mevent.getButton());
//		    	   if(mousebuttonmask!=-100)
//		    	   this.action.mousePress(mousebuttonmask);
		    	  break;
		    //以下是键盘事件	  
		      case KeyEvent.KEY_PRESSED:
		    	  kevent=(KeyEvent)event;
		    	  this.action.keyPress(kevent.getKeyCode());
		         break;
		      case KeyEvent.KEY_RELEASED:
		    	  kevent=(KeyEvent)event;
		    	  this.action.keyRelease(kevent.getKeyCode());
		    	  break;
		    	  
		    	  
		      default:
	              break;
		 }
		
		
		
	}
   /*
    * 鼠标按键翻译
    */
	private int MouseClickTanslate(int button){
		 if(button==MouseEvent.BUTTON1) //左键  //中间键为BUTTON2
		    return InputEvent.BUTTON1_MASK;
		 if(button==MouseEvent.BUTTON3) //左键  //中间键为BUTTON2
			return InputEvent.BUTTON3_MASK;
	    return -100;
	}
	/*
	 * 鼠标单击
	 */
	public void mouseClick(int mousebuttonmask){
		   this.action.mousePress(mousebuttonmask);
		   this.action.mouseRelease(mousebuttonmask);
	}
	
}
