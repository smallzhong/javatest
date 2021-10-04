




import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class GetImageThread extends Thread{
	private Socket socket=null;
	private BufferedImage image=null;
	private JPEGImageDecoder de=null;
	private InputStream in=null;
	public GetImageThread(Socket socket) throws MyException{
		//try {
//			socket.setReceiveBufferSize(Parameter.IMAGE_CACHE);//接收缓冲
//			in=socket.getInputStream();
//			de=JPEGCodec.createJPEGDecoder(in);
         this.socket=socket;
         
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new MyException("无法获取图象输入流--");
//			//
//		}
	}
	
//	public void run(){
//		 while(true){
//		 	 try{
//	       	 //image=(BufferedImage)readin.readObject();
//	    	  //image=ImageIO.read(in);
//	    		image= de.decodeAsBufferedImage();
//	    	    System.out.println("whidth="+image.getWidth()+" height="+image.getHeight());
//	    	  }catch(Exception sa){
//	    		     sa.printStackTrace();
//	    		     tools.print("不能读取图象~~");
//	    	  }
//	    if(image!=null)
//	    	MainFrame.getInstance().ShowImage(image);
//	    }
//		
//		
//	}//while
	
	public void run(){
		BufferedImage image=null;
    	JPEGImageDecoder de=null;
    	InputStream in=null;
    		try {
    			socket.setReceiveBufferSize(1024);//接收缓冲
    			socket.setSoTimeout(60000);
    			in=socket.getInputStream();
				de=JPEGCodec.createJPEGDecoder(in);

    		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    		int wrongtime=0;
    while(true){
    	wrongtime++;
    	if(wrongtime>=200)break;
    	 try{
    		image= de.decodeAsBufferedImage();
    	  }catch(Exception sa){
    		     sa.printStackTrace();
    		  tools.print("不能读取对象~~");
    	  
    	  }
       if(image!=null)
         MainFrame.getInstance().ShowImage(image);
 
        }//while
     try {
		socket.close();
	} catch (Exception e) {
		
		e.printStackTrace();
	} 
	}
	
}
