import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/*
 *���������,��ȡ���,���̿��������ִ��
 */
public class CTableControl extends Thread{

	private Socket socket=null;//�����ȡ�׽���
	private ObjectInputStream in=null;//�¼�������
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
				Object ob=in.readObject();//��ȡ
			//	tools.print(tools.getSystemTime()+" �ɹ���ȡ�¼�");
				if(ob!=null){
					handleEvent((InputEvent)ob);//����
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
	 * �����¼�
	 */
	private void handleEvent(InputEvent event){
		//tools.print(tools.getSystemTime()+" �����¼� �¼�ID:"+event.getID()); 
		MouseEvent mevent=null;//����¼�
		KeyEvent kevent=null;//�����¼�
		int mousebuttonmask=-100;//��갴��
		switch (event.getID()){//���� �¼����ͷ��� �� MOUSE_PRESSED , REALSE ,CLICK,ENTER,��
		      case MouseEvent.MOUSE_MOVED:
		    	   mevent=(MouseEvent)event;
		    	  this.action.mouseMove(mevent.getX(),mevent.getY());
		    	  break;
		  
		      case MouseEvent.MOUSE_CLICKED:
		    	   tools.print("��갴��MOUSE_CLICKED");
		    	   mevent=(MouseEvent)event;
		    	   this.action.mouseMove(mevent.getX(),mevent.getY());
		    	   mousebuttonmask=MouseClickTanslate(mevent.getButton());
		    	   if(mousebuttonmask!=-100)
		    	   mouseClick(mousebuttonmask);
		    	  break;
		      case MouseEvent.MOUSE_RELEASED:
		    	  tools.print("����ͷ�MOUSE_RELEASED");
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
		    //�����Ǽ����¼�	  
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
    * ��갴������
    */
	private int MouseClickTanslate(int button){
		 if(button==MouseEvent.BUTTON1) //���  //�м��ΪBUTTON2
		    return InputEvent.BUTTON1_MASK;
		 if(button==MouseEvent.BUTTON3) //���  //�м��ΪBUTTON2
			return InputEvent.BUTTON3_MASK;
	    return -100;
	}
	/*
	 * ��굥��
	 */
	public void mouseClick(int mousebuttonmask){
		   this.action.mousePress(mousebuttonmask);
		   this.action.mouseRelease(mousebuttonmask);
	}
	
}
