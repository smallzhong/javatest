import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
/*
 * 服务器端命令执行
 */
public class SOrderExcute {

	/*
	 * 获得被监视端状态。
	 * clientstatus中记录了客户端的IP地址等状态信息
	 */
	 public static void getClientStatus(ClientStatus clientstatus) throws MyException{
		 ServerSocket server=NewRadomSocket.openNewPort();//开启新端口
		 Socket socket=null;
		 try {
			server.setSoTimeout(Parameter.TCP_TIME_OUT);//设置超时
			String order=OrderMap.toOrder(OrderMap.INIT_STATUS,server.getLocalPort());
		    clientstatus.sendMyOrder(order);//发送命令
			 socket=server.accept();//开启
			ObjectInputStream readin=new ObjectInputStream(socket.getInputStream());//封装流，准备读取一个对象
			Object ob=readAObject(readin);
		
			if(ob!=null){
				ClientStatus cs=(ClientStatus)ob;
				//tools.print("成功获取信息:"+cs.getPort());
				clientstatus.setPort(cs.getPort());
				
				clientstatus.setScreen_height(cs.getScreen_height());//更新屏幕大小信息
				clientstatus.setScreen_width(cs.getScreen_width());//更新屏幕大小信息
                MainFrame.getInstance().setJPanelSize(cs.getScreen_width(),cs.getScreen_height());//按照比列设置画布大小
			
			}//if
			
		 } catch (Exception e) {
			throw new MyException("连接失败:\n-目标机器不可达");
		 }finally{
			 if(socket!=null)
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				    e.printStackTrace();
				}
		 }
		 
		 
		 
	 }
	 /*
	  *开启屏幕监视线程 
	  */
	 public static void startGetScreen(ClientStatus clientstatus) throws MyException{
		ServerSocket server=null;
		try {
			server = NewRadomSocket.openNewPort();
			server.setSoTimeout(Parameter.TCP_TIME_OUT);//设置超时
			clientstatus.sendMyOrder(OrderMap.toOrder(OrderMap.SCREEN_SHOW,server.getLocalPort()));//发送命令
			Socket socket=server.accept();	//连接
			tools.print(socket.getRemoteSocketAddress()+" 已经连接端口："+socket.getLocalPort()+" 等待连接,进行图形传送");
			//socket.getInputStream().read();
			new GetImageThread(socket).start();//启动图象显示
		} catch (Exception e) {
		    throw new MyException(e.toString());
		}
		 
		 
	 }
	 /*
	  * 开启控制套接字
	  */
	 public static boolean startControlSocket(ClientStatus clientstatus){
			ServerSocket server=null;
			try {
				server = NewRadomSocket.openNewPort();
				server.setSoTimeout(Parameter.TCP_TIME_OUT);//设置超时
				clientstatus.sendMyOrder(OrderMap.toOrder(OrderMap.SCREEN_CONTROL,server.getLocalPort()));//发送命令
				Socket socket=server.accept();	//连接
				tools.print(socket.getRemoteSocketAddress()+" 已经连接端口："+socket.getLocalPort()+" 控制套接开启");
				clientstatus.setControl(new ControlInfo(socket));//注入控制对象，启动控制
				
				
		         return true;	
			}catch(Exception e){
				
				return false;
			}
		
	 }
	 /*
	  * 上传文件
	  */
	 public static void upLoadFile(String filepath,ClientStatus clientstatus) throws MyException, IOException{
		 ServerSocket server=null;
		 File file=new File(filepath);	
		 if (!file.isFile())throw new MyException("该文件不存在");
		 server = NewRadomSocket.openNewPort();
		 server.setSoTimeout(Parameter.TCP_TIME_OUT);//设置超时
		 clientstatus.sendMyOrder(OrderMap.toOrder(OrderMap.FILE_UP,server.getLocalPort()));//发送命令
		 Socket socket=server.accept();	//连接
		 tools.print(socket.getRemoteSocketAddress()+" 已经连接端口："+socket.getLocalPort()+" 准备上传文件："+filepath);
		 new SFileUpThread(socket,file,Parameter.FILE_UP).start();
		 
		 
		 
	 }
	 /*
	  * 询问path的文件是否存在
	  */
	 public static boolean IsExitesFile(String filepath,ClientStatus clientstatus) throws MyException, IOException{
	
		 ServerSocket server=null;
		 server = NewRadomSocket.openNewPort();
		 server.setSoTimeout(Parameter.TCP_TIME_OUT);//设置超时
		 clientstatus.sendMyOrder(OrderMap.FILE_EXITS+"?"+server.getLocalPort()+"?"+filepath);//发送命令
		 Socket socket=server.accept();	//连接
		 tools.print(socket.getRemoteSocketAddress()+" 已经连接端口："+socket.getLocalPort()+"询问文件是否存在："+filepath);
		 InputStream in=socket.getInputStream();
		
		 int exits=in.read();//exits=-1表示不存在1表示存在2表示是一个文件夹
		 tools.print("独到："+exits);
		 switch (exits){
		    case Parameter.FILE_NOT_EXITS:
		    	 throw  new MyException("文件不存在\n-"+filepath);
		    case Parameter.IS_DOCTIONRY:
		    	 throw  new MyException("不是文件，是文件夹\n-"+filepath);
		    case Parameter.FILE_EXITS:
		         return true;
		 }
		return false;
		 
	 }
	 /*
	  * 下栽文件
	  */
	 public static void downFile(String filepath,ClientStatus clientstatus) throws MyException, IOException{
		 ServerSocket server=null;
		 server = NewRadomSocket.openNewPort();
		 server.setSoTimeout(Parameter.TCP_TIME_OUT);//设置超时
		 clientstatus.sendMyOrder(OrderMap.FILE_DOWN+"?"+server.getLocalPort()+"?"+filepath);//发送命令
		 Socket socket=server.accept();	//连接
		 tools.print(socket.getRemoteSocketAddress()+" 已经连接端口："+socket.getLocalPort()+" 下载文件："+filepath);
		 
		 
		 new CStoreFileThread(socket,Parameter.FILE_DOWN).start();
		 
	 }
	 /*
	  * 执行DOS命令,不需要返回信息
	  */
	 public static void excuseDOSOrderWithout(String order,ClientStatus clientstatus){
		 clientstatus.sendMyOrder(order);
		 
	 }
	 /*
	  * 执行DOS命令，显示返回信息
	  */
	 public static void excuseDOSOrder(String order,ClientStatus clientstatus) throws IOException, MyException{
		 ServerSocket server=null;
		 server = NewRadomSocket.openNewPort();
		 server.setSoTimeout(Parameter.TCP_TIME_OUT);//设置超时
		 clientstatus.sendMyOrder(OrderMap.DOS_ORDER+"?"+server.getLocalPort()+"?"+order);//发送命令
		 Socket socket=server.accept();	//连接
		 BufferedInputStream in=new BufferedInputStream(socket.getInputStream());
		 ServerDOSOrderUI show=new ServerDOSOrderUI(clientstatus.getIp()+" 执行命令"+order);
		  byte b[]=new byte[1024];//缓冲
          int m=0;String s=null;
          while((m=in.read(b))!=-1){
         	 s=new String(b,0,m);
         	 show.setMessage(s);
          }
		// show.closeMe();
	 }
	 /*
	  * 发送消息
	  */
	 public static void sendMessage(String message,ClientStatus clientstatus){
		 clientstatus.sendMyOrder(OrderMap.toOrder(OrderMap.MESSAGE,message));
	 }
	 
	 
	 /*
	  * 从流中读取一个对象
	  */
	private static Object readAObject(ObjectInputStream in) throws MyException{
		try{   
		return in.readObject();
		}catch(Exception e){
			e.printStackTrace();
			throw new MyException("客户端初始化失败：无法读取状态对象");
		}
		
	}
	 
}
