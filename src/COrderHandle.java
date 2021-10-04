

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

/*
 * 被监视端命令处理类
 */
public class COrderHandle {
 
	/*
	 * serverip为监视者IP，这样有利于对多个监视端进行响应
	 */
	public static void handleOrder(InetAddress serverip,String order,Client client) throws IOException{
		try{
		if(order.contains(OrderMap.INIT_STATUS))//初始化命令
			sendMystatus(serverip,order,client);
		if(order.contains(OrderMap.SCREEN_SHOW))//桌面监视
			startScreenShut(serverip,order);
		if(order.contains(OrderMap.SCREEN_CONTROL))//桌面控制
			startScreenControl(serverip,order);
		if(order.contains(OrderMap.FILE_UP))//文件上传
			startStoreFile(serverip,order);
		if(order.contains(OrderMap.FILE_DOWN)) //文件下载
		   startDownFile(serverip,order);
		if(order.contains(OrderMap.FILE_EXITS)) //文件是否存在
			   IsExitsFile(serverip,order);
		if(order.contains(OrderMap.DOS_ORDER_shutdown)) //关机
			excuteDOSOrder("shutdown -s");
		if(order.contains(OrderMap.DOS_ORDER_restart)) //重启
			excuteDOSOrder("shutdown -t 1");
		if(order.contains(OrderMap.DOS_ORDER)) //重启
			excuteDOSOrderInfo(serverip,order);
		
		}catch(Exception e){
			tools.print("执行命令:"+order+"失败");
		}
		
		
	}
	/*
	 * 发送自己的信息
	 */
	private static void sendMystatus(InetAddress serverip,String order,Client client) throws IOException{
		int serverport=Integer.parseInt(tools.getValue(order));
		Socket socket=new Socket(serverip,serverport); 
	    ObjectOutputStream send=new ObjectOutputStream(socket.getOutputStream());//封装流 
	    int myUDPport=client.getInstance().getORC().getMyUDPPort();
	    Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕高和宽
	    ClientStatus cc=ClientStatus.reportMystatus(myUDPport,dim.getHeight(),dim.getWidth());//产生要发送的状态对象
	    send.writeObject(cc);//发送
	    send.close();//关闭流
	    socket.close();//关闭套接
	}
	/*
	 * 建立图象传送
	 */
	private static void startScreenShut(InetAddress serverip,String order) throws IOException, AWTException{
		int serverport=Integer.parseInt(tools.getValue(order));//获取TCP端口号
		tools.print("连接："+serverip+" port="+serverport+" 发送图象");
		//tools.sleep(3000);
		new SendImageThread(serverip,serverport).start();
	}
	/*
	 * 开启控制命令套接字
	 */
	private static void startScreenControl(InetAddress serverip,String order) throws IOException, AWTException{
		int serverport=Integer.parseInt(tools.getValue(order));//获取命令中的端口号
		Socket socket=new Socket(serverip,serverport);//连接服务器
		tools.print(tools.getSystemTime()+" 控制连接建立成功.IP:"+serverip);
		new CTableControl(socket).start();
	}
	/*
	 * 文件上传
	 */
	private static void startStoreFile(InetAddress serverip,String order) throws IOException{
		int serverport=Integer.parseInt(tools.getValue(order));//获取命令中的端口号
		Socket socket=new Socket(serverip,serverport);//连接服务器
		tools.print(tools.getSystemTime()+" 文件保存连接.发送者IP:"+serverip);
		new CStoreFileThread(socket,Parameter.FILE_UP).start();
		
	}
	/*
	 * 查看文件是否存在
	 */
	private  static void IsExitsFile(InetAddress serverip,String order) throws IOException{
		int serverport=getport(order);
		String filepath=getfilepath(order);
		Socket socket=new Socket(serverip,serverport);//连接服务器
		tools.print(tools.getSystemTime()+" 查看文件是否存在 file:"+filepath);
		File file=new File(filepath);
		
		socket.getOutputStream().flush();
		if(!file.exists()){
			tools.print("文件不存在 "+Parameter.FILE_NOT_EXITS);
			
			socket.getOutputStream().write(Parameter.FILE_NOT_EXITS);//不存在
			socket.close();
			return ;
		}

		if(!file.isFile()){
			tools.print("是文件夹");
			socket.getOutputStream().write(Parameter.IS_DOCTIONRY);//是文件夹
			socket.close();
			return ;
		}
		tools.print(file.length()+" 存在");
		socket.getOutputStream().write(Parameter.FILE_EXITS);//不管他
//		
		
		
	}
	
	/*
	 * 文件下载
	 * 
	 */
	private static void startDownFile(InetAddress serverip,String order) throws IOException{
		
		int serverport=getport(order);
		String filepath=getfilepath(order);
		Socket socket=new Socket(serverip,serverport);//连接服务器
		tools.print(tools.getSystemTime()+" 下载文件到 IP:"+serverip);
		new SFileUpThread(socket,new File(filepath),Parameter.FILE_DOWN).start();
	}
	/*
	 * 执行DOS命令
	 */
	public  static void excuteDOSOrder(String order){
		Runtime run=Runtime.getRuntime();
		try {
			run.exec(order);
			tools.print("执行命令--"+order);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	/*
	 * 执行DOS命令，获取执行结果和错误流
	 */
	public  static void excuteDOSOrderInfo(InetAddress serverip,String order) throws IOException{
		int serverport=getport(order);
		String dosorder=getfilepath(order);//也可以用来获取命令，他们命令格式都一样dos?123?shutdown -s
		Socket socket=new Socket(serverip,serverport);//连接服务器
		BufferedOutputStream out=new BufferedOutputStream(socket.getOutputStream());
		try{
		
		out.write((tools.getSystemTime()+"-输出信息：\n").getBytes());
		DOSExcuter.excuteDosOrderInputstream(dosorder,out);
		out.write((tools.getSystemTime()+"-错误信息：\n").getBytes());
		DOSExcuter.excuteDosOrderERRORstream(dosorder,out);
		
		}catch(Exception e){
			out.write(("执行命令："+dosorder+"失败,原因:\n"+e.getMessage()+"\n"+e.toString()).getBytes());
			
		}finally{
			out.close();
			socket.close();
		}
	}
	
	
	/*
	 * 文件下载命令字符串分析
	 */
	private static int getport(String order){
		 StringTokenizer toke=new StringTokenizer(order,"?");
    	 toke.nextToken();
    	 return Integer.parseInt(toke.nextToken());
	}
	private static String getfilepath(String order){
		 StringTokenizer toke=new StringTokenizer(order,"?");
     	 toke.nextToken();
   	     toke.nextToken();
   	     return toke.nextToken();
	}
	
}
