import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

/*
 * 保存文件
 */
public class CStoreFileThread extends Thread {
     private int type=0;
	 private File f=null;//要保存的文件
	 private Socket socket=null;//文件信息，文件数据套接
	 private DataInputStream datain=null;//用于读取文件数据
	 private ObjectInputStream infoin=null;//用于读取文件信息
	 
//	public CStoreFileThread(Socket socket) throws IOException{
//		this.socket=socket;
//		datain=new DataInputStream(socket.getInputStream());
//		infoin=new ObjectInputStream(socket.getInputStream());
//	}
	public CStoreFileThread(Socket socket,int type) throws IOException{
		this.type=type;
		this.socket=socket;
		datain=new DataInputStream(socket.getInputStream());
		infoin=new ObjectInputStream(socket.getInputStream());
	}
	
	 public void run(){
		 try {
			getFileInfo();
			tools.print(f.getName()+" "+f.length());
			storeFiledate();
			if(this.type==Parameter.FILE_DOWN)
			MainFrame.getInstance().showMessage("成功","下载文件成功",JOptionPane.WARNING_MESSAGE);
		} catch (IOException e) {
			ClientMessageShow.showMessage("上传文件错误",
					"传输过程中发生网络异常",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			ClientMessageShow.showMessage("上传文件错误",
					"并未发送文件信息(java.io.File)",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		 
		 
		 
	 }
	 private void getFileInfo() throws IOException, ClassNotFoundException{
			Object ob=infoin.readObject();
			if(ob!=null)f=(File)ob;
			File f1=new File(Parameter.getUPFILE_PATH());
			tools.print("建立文件:"+f.getAbsolutePath()+" name:"+f.getName());
			//在默认路径下建立文件夹
			 if(!f1.exists())
	        	 f1.mkdirs();
			 File f2=new File(Parameter.getUPFILE_PATH()+f.getName());
			 f2.createNewFile();//建立文件
			
			 f=f2;
			
	 }
	 /*
	  * 保存文件数据信息
	  */
	 private void storeFiledate() throws IOException{
		
		 FileOutputStream fileout=new FileOutputStream(f,true);//写文件的
		 byte b[]=new byte[Parameter.FILE_CACHE];
		 int readlength=0;
		 while((readlength=datain.read(b))!=-1){
			 fileout.write(b,0,readlength);
			 
		 }
		 
		 
	 }
	
}
