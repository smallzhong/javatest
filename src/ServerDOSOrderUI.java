import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*
 * 监视端消息显示
 */
public class ServerDOSOrderUI  {
	
	JFrame cjf=new JFrame("消息");//主界面,只有一个
	TextArea textinfo=new TextArea(10,20);
	String message=null;
	public ServerDOSOrderUI(String title){
		cjf.setTitle(title);
		textinfo.setEditable(false);
		Button bt=new Button("关闭");
		bt.addActionListener(new btlis(this));
		Container con=cjf.getContentPane();
		con.setLayout(new BorderLayout());
		con.add(textinfo,BorderLayout.CENTER);
		con.add(bt,BorderLayout.SOUTH);
		cjf.setSize(300,300);
		tools.setInCenter(cjf);
		cjf.setVisible(true);
		
	}
	
	public JFrame getCjf() {
		return cjf;
	}
	public void setMessage(String message) {
		this.message =this.message+message;
		this.textinfo.setText(this.message);
	}
	public void closeMe(){
		 this.cjf.setVisible(false);
		
	}
	public static void main(String ss[]){
		new ServerDOSOrderUI("123");
		
	}
}
class btlis implements ActionListener{
	ServerDOSOrderUI show=null;
	public btlis(ServerDOSOrderUI show){
    	  this.show=show;
     }
	public void actionPerformed(ActionEvent arg0) {
	 	show.getCjf().setVisible(false);
	}
	
	
	
}
