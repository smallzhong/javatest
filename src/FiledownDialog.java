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

    private TextField text = null;

    public FiledownDialog(Frame f, String name, boolean moshi) {
        super(f, name, moshi);
        this.setLayout(new FlowLayout());
        Button button = new Button("����");
        Label lable = new Label("����·��:");
        text = new TextField("test", 50);
        this.add(lable);
        this.add(text);
        this.add(button);
        button.addActionListener(new ButtonDownFile(this));
        this.setSize(500, 70);
        this.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        MainFrame.getInstance().showFileDownDialog(false);
                    }
                });

        tools.setInCenter(this);//�м���ʾ
	

    }

    public TextField getMyText() {

        return this.text;
    }

}

class ButtonDownFile implements ActionListener {
    private TextField text = null;//�ļ�·��

    public ButtonDownFile(FiledownDialog fdg) {
        this.text = fdg.getMyText();
    }


    public void actionPerformed(ActionEvent arg0) {
        try {
            MainFrame.getInstance().showFileDownDialog(false);//�Ի�����ʧ
            //	MainFrame.getInstance().showMessage("�����ļ�..","���ڼ���ļ��Ƿ����",JOptionPane.WARNING_MESSAGE);
            boolean b = testFileExits();
            tools.print("�ļ�������" + b);
            if (!b) throw new MyException("�ļ�������\n" + this.text.getText());
            //���������ļ���Ĭ��Ŀ¼
            SOrderExcute.downFile(this.text.getText(), MainFrame.getInstance().getClient());

        } catch (MyException e) {
            MainFrame.getInstance().showMessage("ѯ�ʽ��", e.toString(), JOptionPane.WARNING_MESSAGE);
            //e.printStackTrace();
        } catch (IOException e) {
            MainFrame.getInstance().showMessage("�ļ����س���", "�����쳣IOException �ļ���\n" + this.text.getText() + "����ʧ��", JOptionPane.WARNING_MESSAGE);
            //e.printStackTrace();
        }

    }

    /*
     * �����ļ��Ƿ����
     */
    private boolean testFileExits() throws MyException, IOException {

        return SOrderExcute.IsExitesFile(this.text.getText(), MainFrame.getInstance().getClient());

    }


}