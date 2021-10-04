import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
/*
 * 客户端状态信息
 */
public class ClientStatus implements Serializable{
    private InetAddress ip=null;//被监视端IP地址
    private int port=-1;//端口号
    private double screen_height=0;//屏幕高
    private double screen_width=0;//屏幕宽
    private ControlInfo control=null;//控制套接
    
    
    public ClientStatus(InetAddress inet){
    	this.ip=inet;
    }
    /*
     * 向该客户端发送 命令
     */
    public void sendMyOrder(String order){
    	if(port==-1){//向3个端口都发送消息
    		int portlength=Parameter.ORDER_UDP_PORT.length;
    	      for(int i=0;i<portlength;i++)//向每个可能的端口发送消息
    	    		sendOrder(this.ip,Parameter.ORDER_UDP_PORT[i],order);
       	}else
    		sendOrder(this.ip,this.port,order);
    }
    
	/*
	 * 向某客户端某端口发送命令
	 */
    private static void sendOrder(InetAddress ip,int port,String order){
	     byte sp[]=order.getBytes();  
		 DatagramPacket packet=new DatagramPacket(sp,sp.length,ip,port);
		 try {
			DatagramSocket sd=new DatagramSocket();
			sd.send(packet); 
		} catch (Exception e) {
	         tools.print("向:"+ip+" 发送命令:"+order+"失败");
			//e.printStackTrace();
		}
		
	}
	/*
	 * 设置信息，用于向服务器发送自己当前状态
	 */
    public static ClientStatus reportMystatus(int udpport,double hei,double wid){
    	ClientStatus cc=new ClientStatus(null);
    	cc.setPort(udpport);
    	cc.setScreen_height(hei);
    	cc.setScreen_width(wid);
    
    	return cc;
    	
    } 
    /*
     *设置端口
     */
    public void setPort(int port){
    	this.port=port;
    	
    }
    /*
     * 获取端口
     */
    public int getPort(){
    	return this.port;
    	
    }
	public double getScreen_height() {
		return screen_height;
	}
	public void setScreen_height(double screen_height) {
		this.screen_height = screen_height;
	}
	public double getScreen_width() {
		return screen_width;
	}
	public void setScreen_width(double screen_width) {
		this.screen_width = screen_width;
	}
	public ControlInfo getControl() {
		return control;
	}
	public void setControl(ControlInfo control) {
		this.control = control;
	}
	public InetAddress getIp() {
		return ip;
	}
    
}
