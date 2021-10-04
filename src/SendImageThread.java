

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class SendImageThread extends Thread{
	
	ImageProvider impv=null;
	Socket client=null;
	int PORT;
  BufferedImage image=null;
	
	ObjectOutputStream send=null;
	
	SendImageThread(InetAddress server_ip,int port) throws IOException, AWTException{
     
       client=new Socket(server_ip,port);  //建立套接并初始化 图象发送流
       client.setSendBufferSize(1024);//设置发送缓冲
      // send=new ObjectOutputStream(client.getOutputStream()); 
       impv=new ImageProvider();

 }  
	public void run(){
	    int i=0;
	    OutputStream out=null;
	    JPEGImageEncoder encoder=null;//压缩为JEPG
		try {
			out = client.getOutputStream();
			encoder = JPEGCodec.createJPEGEncoder (out) ; 
		} catch (IOException e1) {
		    
			e1.printStackTrace();
		}
         while(true){
		 image=impv.CopyScreen();
		 try{
		// send.writeObject(image);
		 //ImageIO.write(image,"bmp",out);
	     encoder.encode(image) ; 
		 Thread.sleep(Parameter.IMAGE_get_time);
		}catch(IOException e){
		         i++; 
                  tools.print("无法写入对象~"+i);
		    e.printStackTrace();
                    if(i==10)
                      break;
		}
		catch(InterruptedException sd){
			tools.print("INTERRUPTED~");
		}
   
      
      }
  
  }
}
