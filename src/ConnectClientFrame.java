
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/*
 * ����"����"�Ի�������ɣе�ַ
 * ����ģʽŶ
 */
public class ConnectClientFrame implements ActionListener {
    private static ConnectClientFrame ccf = new ConnectClientFrame();
    private JFrame jf = new JFrame("������Ŀ��IP��ַ");
    private JTextField jtext = null;//����ɣе�ַ��
    private MainFrame mf = null;//������������

    private ConnectClientFrame() {
        initJF();
    }

    public static ConnectClientFrame getInstance() {
        return ccf;
    }

    /*
     * ��ʼ���öԻ���
     */
    private void initJF() {
        Container con = jf.getContentPane();
        con.setLayout(new FlowLayout());
        jtext = new JTextField(10);//10�п�������
        jtext.setText("127.0.0.1");//����
        con.add(jtext, BorderLayout.CENTER);//��������
        JButton jb = new EnterInButton("ȷ��");//��ť
        jf.setSize(350, 150);//���ô�С
        jf.setLocation(350, 200);//����λ��
        con.add(jb, BorderLayout.EAST);
    }



    /*
     * ��ʾ��
     */
    private void showMe() {
        this.jf.setVisible(true);
    }

    /*
     * ������
     */
    public void hideMe() {
        this.jf.setVisible(false);
    }

    /*
     *�����"����"�˵�,��ʾ��
     **/
    public void actionPerformed(ActionEvent arg0) {
        showMe();
    }

    /*
     * �������ģɣе�ַ
     */
    public InetAddress getEnterInetAddress() throws UnknownHostException {
        //tools.print("IP��"+jtext.getText());
        return InetAddress.getByName(jtext.getText());
    }

}

//�����ť�����ӵ�ĳ���ɣе�ַָ���Ŀͻ���
class EnterInButton extends JButton implements ActionListener {

    public EnterInButton(String name) {
        super(name);
        super.addActionListener(this);//��Ӽ���
    }

    public void actionPerformed(ActionEvent arg0) {
        InetAddress inet = null;
        try {
            ConnectClientFrame.getInstance().hideMe();//���������
            inet = ConnectClientFrame.getInstance().getEnterInetAddress();//���������е�IP��ַ�ַ���
            ClientStatus client = new ClientStatus(inet);//����Client״̬ͳ����
            SOrderExcute.getClientStatus(client);//���ͳ��Ի�����
            MainFrame.getInstance().setClient(client);//ע�ᵽMainFrame
            SOrderExcute.startGetScreen(client);//������Ļ����
            SOrderExcute.startControlSocket(client);//������������
            MainFrame.getInstance().addListenerForPanel();//ΪMainFrame��Panel����¼�������׼�����Ϳ����¼�
        } catch (MyException my) {
            MainFrame.getInstance().showMessage("����", my.toString(), JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {//IP��ַ���ܽ���
            ex.printStackTrace();
            if (inet != null)
                MainFrame.getInstance().showMessage("����", "IP��ַ���ܽ���" + inet.toString()
                        , JOptionPane.WARNING_MESSAGE);
            else
                MainFrame.getInstance().showMessage("����", "IP��ַ���ܽ���"
                        , JOptionPane.WARNING_MESSAGE);
        }
    }


}
