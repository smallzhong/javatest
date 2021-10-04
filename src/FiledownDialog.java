import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

public class FiledownDialog extends Dialog {
  
	private TextField text=null;
	 public FiledownDialog(Frame f,String name,boolean moshi){
		 super(f,name,moshi);
		 this.setLayout(new FlowLayout());
		 Button button=new Button("下载");
		 Label lable=new Label("输入路径:");
		 text=new TextField("test",50);
		 this.add(lable);
		 this.add(text);
		 this.add(button);
		 button.addActionListener(new ButtonDownFile(this));
		 this.setSize(500,70);
		 this.addWindowListener(
				 new WindowAdapter(){
	         public void windowClosing(WindowEvent e){
	        	 MainFrame.getInstance().showFileDownDialog(false);
	         }
         });
		
		 tools.setInCenter(this);//中间显示
		 
		 
	 }
	public TextField getMyText(){
		
		return this.text;
	}

}
class ButtonDownFile implements ActionListener{
    private  TextField text=null;//文件路径
	 public ButtonDownFile(FiledownDialog fdg){
	    this.text=fdg.getMyText();
	 }
	 
	
	public void actionPerformed(ActionEvent arg0) {
		try {
			MainFrame.getInstance().showFileDownDialog(false);//对话框消失
		//	MainFrame.getInstance().showMessage("下载文件..","正在检测文件是否存在",JOptionPane.WARNING_MESSAGE);
			boolean b=testFileExits();
		    tools.print("文件存在吗？"+b);
			if(!b) throw new MyException("文件不存在\n"+this.text.getText());
			//否则，下载文件到默认目录
			SOrderExcute.downFile(this.text.getText(),MainFrame.getInstance().getClient());
			
		} catch (MyException e) {
			MainFrame.getInstance().showMessage("询问结果",e.toString(),JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
		} catch (IOException e) {
			MainFrame.getInstance().showMessage("文件下载出错","网络异常IOException 文件：\n"+this.text.getText()+"下载失败",JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
		}
		
	}
	/*
	 * 测试文件是否存在
	 */
	private boolean testFileExits() throws MyException, IOException{
	
		return SOrderExcute.IsExitesFile(this.text.getText(),MainFrame.getInstance().getClient());
		
	}
	
	
}