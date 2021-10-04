

import java.awt.event.InputEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*
 * 控制线程
 */
public class ControlInfo {//extends Thread {

   private Socket socket=null;
   private ObjectOutputStream out=null;//事件对象发送封装
   public ControlInfo(Socket socket) throws IOException{
	   this.socket=socket;
	   this.socket.setSendBufferSize(Parameter.EVENT_CACHE);
	   out=new ObjectOutputStream(this.socket.getOutputStream());
	  // out.flush();//刷新
   }
   /*
    * 发送事件
    */
   public void sendMyAction(InputEvent event){
	   //看是否有必要对事件进行筛选
   try {
		out.writeObject(event);
	 
	   } catch (IOException e) {
		e.printStackTrace();
	   }
	   
   }
   
   
   
   public void run(){
	 
   }
   
   
   
   

}
