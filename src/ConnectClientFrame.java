
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
 * 弹出"连接"对话框，输入ＩＰ地址
 * 单子模式哦
 */
public class ConnectClientFrame implements ActionListener {
    private static ConnectClientFrame ccf = new ConnectClientFrame();
    private JFrame jf = new JFrame("请输入目标IP地址");
    private JTextField jtext = null;//输入ＩＰ地址的
    private MainFrame mf = null;//依赖的主界面

    private ConnectClientFrame() {
        initJF();
    }

    public static ConnectClientFrame getInstance() {
        return ccf;
    }

    /*
     * 初始化该对话框
     */
    private void initJF() {
        Container con = jf.getContentPane();
        con.setLayout(new FlowLayout());
        jtext = new JTextField(10);//10列宽的输入框
        jtext.setText("127.0.0.1");//测试
        con.add(jtext, BorderLayout.CENTER);//添加输入框
        JButton jb = new EnterInButton("确定");//按钮
        jf.setSize(350, 150);//设置大小
        jf.setLocation(350, 200);//设置位置
        con.add(jb, BorderLayout.EAST);
    }



    /*
     * 显示它
     */
    private void showMe() {
        this.jf.setVisible(true);
    }

    /*
     * 隐藏它
     */
    public void hideMe() {
        this.jf.setVisible(false);
    }

    /*
     *点击了"连接"菜单,显示它
     **/
    public void actionPerformed(ActionEvent arg0) {
        showMe();
    }

    /*
     * 获得输入的ＩＰ地址
     */
    public InetAddress getEnterInetAddress() throws UnknownHostException {
        //tools.print("IP："+jtext.getText());
        return InetAddress.getByName(jtext.getText());
    }

}

//这个按钮会连接到某个ＩＰ地址指定的客户端
class EnterInButton extends JButton implements ActionListener {

    public EnterInButton(String name) {
        super(name);
        super.addActionListener(this);//添加监听
    }

    public void actionPerformed(ActionEvent arg0) {
        InetAddress inet = null;
        try {
            ConnectClientFrame.getInstance().hideMe();//隐藏输入框
            inet = ConnectClientFrame.getInstance().getEnterInetAddress();//获得输入框中的IP地址字符串
            ClientStatus client = new ClientStatus(inet);//生成Client状态统计类
            SOrderExcute.getClientStatus(client);//发送初试化命令
            MainFrame.getInstance().setClient(client);//注册到MainFrame
            SOrderExcute.startGetScreen(client);//开启屏幕监视
            SOrderExcute.startControlSocket(client);//开启控制连接
            MainFrame.getInstance().addListenerForPanel();//为MainFrame的Panel添加事件监听，准备发送控制事件
        } catch (MyException my) {
            MainFrame.getInstance().showMessage("错误", my.toString(), JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {//IP地址不能解析
            ex.printStackTrace();
            if (inet != null)
                MainFrame.getInstance().showMessage("错误", "IP地址不能解析" + inet.toString()
                        , JOptionPane.WARNING_MESSAGE);
            else
                MainFrame.getInstance().showMessage("错误", "IP地址不能解析"
                        , JOptionPane.WARNING_MESSAGE);
        }
    }


}
