

import java.io.IOException;
import java.net.ServerSocket;

/*
 * ����������һ������˿ںõ�ServerSocket
 */
public class NewRadomSocket{
	
	public static ServerSocket openNewPort()throws MyException{
		ServerSocket sockets=null;
		boolean con=true;
		int count=0;
		while(con){
			count++;
			//����10�Σ����Ӳ��ɹ��ͷ���
			if(count>=10)throw new MyException("ʧ�ܣ��޷������������˿�..");
		     int c=(int)(Math.random()*10000);
			 int port=(int)(c-65530*(c/65530));
			 if(port<1024)continue;//ѡ��Ķ˿ں�ҪС��1024
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
