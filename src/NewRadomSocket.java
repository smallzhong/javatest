

import java.io.IOException;
import java.net.ServerSocket;

/*
 * 开启本机器一个随机端口好的ServerSocket
 */
public class NewRadomSocket{
	
	public static ServerSocket openNewPort()throws MyException{
		ServerSocket sockets=null;
		boolean con=true;
		int count=0;
		while(con){
			count++;
			//尝试10次，连接不成功就放弃
			if(count>=10)throw new MyException("失败：无法开启本机器端口..");
		     int c=(int)(Math.random()*10000);
			 int port=(int)(c-65530*(c/65530));
			 if(port<1024)continue;//选择的端口号要小于1024
			 con=false;
			try {
				sockets=new ServerSocket(port);
			} catch (IOException e) {
			    con=true;	
//				tools.print(e.getMessage());
//				e.printStackTrace();
			}
			  
		}//while
		
		return sockets;
	}

}
