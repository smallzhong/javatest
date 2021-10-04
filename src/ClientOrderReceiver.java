



import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JOptionPane;

/*
 * 被监视端，命令端口开启，和接收命令
 * 
 */
public class ClientOrderReceiver{
	private DatagramSocket orderUDP=null;//命令接受UDP
	//ClientStatus mystatus=new ClientStatus(null);//自身状态统计
	
	/*
	 * 要开启固定的UDP端口
	 */
	public ClientOrderReceiver() throws MyException{
		boolean success=openOrderUDPPort();//开启端口
		if(success==false){
			ClientMessageShow.showMessage("监视端初始化失败","开启UDP命令端口失败",JOptionPane.WARNING_MESSAGE);
		    throw new MyException("开启UDP命令端口失败");
		}
		//启动命令接收线程
		startReceiverOrder();
		
	}
	
	/*
	 * 在3个UDP端口中选择一个,成功返回true,失败返回false
	 */
	public boolean openOrderUDPPort(){
		int length=Parameter.ORDER_UDP_PORT.length;
		for(int i=0;i<length;i++){
		    try {
				openUDPPort(Parameter.ORDER_UDP_PORT[i]);
				tools.print("成功开启:"+Parameter.ORDER_UDP_PORT[i]);
				return true;
		    } catch (SocketException e) {
			   continue;
			}
		}
		return false;
		
	}
	/*
	 * 尝试开启UDP端口
	 */
	private void openUDPPort(int port) throws SocketException{
		orderUDP=new DatagramSocket(port);
	}
	/*
	 * 启动N个命令接收线程
	 */
	private void startReceiverOrder(){
		int length=Parameter.OrderExcuteNums;
		for(int i=0;i<length;i++)
			  new orderReceiver(orderUDP).start();
	}
	/*
	 * 获得我的UDP端口
	 */
	public int getMyUDPPort(){
		return this.orderUDP.getLocalPort();
		
	}
	
}
//命令接收线程序
class orderReceiver extends Thread{
	private DatagramSocket udpOrder=null;//用于读取命令的UDP套接字
	byte hs[]=new byte[500];//用于存放命令，命令最长500字节
	DatagramPacket packet=new DatagramPacket(hs,hs.length);//接受数据包 
	public orderReceiver(DatagramSocket udpOrder){
		this.udpOrder=udpOrder;
	}
	
	public void run(){
	  tools.print(tools.getSystemTime()+"--启动命令接收线程--");
		 while(true){
				try{  
					udpOrder.receive(packet);
			        String s_message=new String(packet.getData(),0,packet.getLength());
				    tools.print(tools.getSystemTime()+" 收到命令:"+s_message); 
				    COrderHandle.handleOrder(packet.getAddress(),s_message,Client.getInstance());
			    } catch (Exception e){
			    	e.printStackTrace();
			    }
		}//while 
		
	}
	
}





















