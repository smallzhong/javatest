import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * DOS��������
 */
public class DosOrderInUI extends Dialog  {
	private TextField text=null;
	//ipΪĿ�����IP
	public DosOrderInUI(Frame f,String ip){
		 super(f,"��������",true);
		 this.setLayout(new FlowLayout());
		 Button button=new Button("�ύִ��");
		 Label lable=new Label("��������:");
		 text=new TextField("test",30);
		 this.add(lable);
		 this.add(text);
		 this.add(button);
		 button.addActionListener(new Buttondos());
		 this.setSize(500,70);
		 this.addWindowListener(
				 new WindowAdapter(){
	         public void windowClosing(WindowEvent e){
	        	 MainFrame.getInstance().showDosOrderDialog(false);
	         }
         });
		
		 tools.setInCenter(this);//�м���ʾ
		
	}
	public String getTextValue(){
		return this.text.getText();
		
	}
	
}
class  Buttondos implements ActionListener{

	 public Buttondos(){
		 
	 }
	
	public void actionPerformed(ActionEvent arg0) {
		
		MainFrame.getInstance().showDosOrderDialog(false);
	}


}
