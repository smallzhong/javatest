

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * 客户端消息显示
 */
public class ClientMessageShow {
	private static JFrame cjf=new JFrame("消息");//主界面,只有一个
	public static void showMessage(String title,String message,int messagetype){
		JOptionPane.showMessageDialog(cjf,message,title,messagetype);
		
	}
	public static void showWARNING(String title,String message){
		JOptionPane.showMessageDialog(cjf,message,title,JOptionPane.WARNING_MESSAGE);
		
	}
}