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
 * �����ļ�
 */
public class CStoreFileThread extends Thread {
     private int type=0;
	 private File f=null;//Ҫ������ļ�
	 private Socket socket=null;//�ļ���Ϣ���ļ������׽�
	 private DataInputStream datain=null;//���ڶ�ȡ�ļ�����
	 private ObjectInputStream infoin=null;//���ڶ�ȡ�ļ���Ϣ
	 
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
			MainFrame.getInstance().showMessage("�ɹ�","�����ļ��ɹ�",JOptionPane.WARNING_MESSAGE);
		} catch (IOException e) {
			ClientMessageShow.showMessage("�ϴ��ļ�����",
					"��������з��������쳣",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			ClientMessageShow.showMessage("�ϴ��ļ�����",
					"��δ�����ļ���Ϣ(java.io.File)",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		 
		 
		 
	 }
	 private void getFileInfo() throws IOException, ClassNotFoundException{
			Object ob=infoin.readObject();
			if(ob!=null)f=(File)ob;
			File f1=new File(Parameter.getUPFILE_PATH());
			tools.print("�����ļ�:"+f.getAbsolutePath()+" name:"+f.getName());
			//��Ĭ��·���½����ļ���
			 if(!f1.exists())
	        	 f1.mkdirs();
			 File f2=new File(Parameter.getUPFILE_PATH()+f.getName());
			 f2.createNewFile();//�����ļ�
			
			 f=f2;
			
	 }
	 /*
	  * �����ļ�������Ϣ
	  */
	 private void storeFiledate() throws IOException{
		
		 FileOutputStream fileout=new FileOutputStream(f,true);//д�ļ���
		 byte b[]=new byte[Parameter.FILE_CACHE];
		 int readlength=0;
		 while((readlength=datain.read(b))!=-1){
			 fileout.write(b,0,readlength);
			 
		 }
		 
		 
	 }
	
}
