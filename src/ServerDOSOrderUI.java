import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*
 * ���Ӷ���Ϣ��ʾ
 */
public class ServerDOSOrderUI  {
	
	JFrame cjf=new JFrame("��Ϣ");//������,ֻ��һ��
	TextArea textinfo=new TextArea(10,20);
	String message=null;
	public ServerDOSOrderUI(String title){
		cjf.setTitle(title);
		textinfo.setEditable(false);
		Button bt=new Button("�ر�");
		bt.addActionListener(new btlis(this));
		Container con=cjf.getContentPane();
		con.setLayout(new FlowLayout());
//		con.add(textinfo,BorderLayout.CENTER);
		con.add(textinfo);
//		con.add(bt,BorderLayout.SOUTH);
		con.add(bt);
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
