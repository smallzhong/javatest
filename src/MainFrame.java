
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame {
    private static MainFrame mainf = new MainFrame();
    private JFrame mainframe = new JFrame("远程监控系统1.0");//主界面,只有一个
    //private JPanel panel=null;//显示画布
    private Canvas panel = null;//显示画布
    private FileDialog fileup = null;//new FileDialog(mainframe,"文件上传",FileDialog.LOAD);//文件上传对话框
    private FileDialog filedown = null;//new FileDialog(mainframe,"文件上传",FileDialog.SAVE);//文件下载对话框
    private FiledownDialog fdwnDialog = null;//下载文件输入路径
    private DosOrderInUI dosOrderDialog = null;//dos命令对话框

    private ClientStatus client = null;//被监视端状态信息，控制句柄

    private MainFrame() {
        showIt();//显示
        initJPanel();//添加画布
        initMunuBar();//添加菜单
        mainframe.setVisible(true);//显示主界面
    }

    /*
     * 单子
     */
    public static MainFrame getInstance() {
        return mainf;
    }

    /*
     * 初始化菜单
     */
    private void initMunuBar() {
        OpreationMenu.initMenuBar(mainframe);
    }

    /*
     * 添加画布
     */
    public void initJPanel() {
        //panel=new JPanel();
        panel = new Canvas();
//		MouseOnPanel mp=new MouseOnPanel(client);
//		panel.addMouseMotionListener(mp);//添加鼠标监听
//		panel.addMouseListener(mp);
//		panel.addKeyListener(mp);
        mainframe.add(panel, BorderLayout.CENTER);
    }

    /*
     * 为画布添加鼠标，键盘事件监听
     */
    public void addListenerForPanel() {
        MouseOnPanel mp = new MouseOnPanel(client);
        panel.addMouseMotionListener(mp);//添加鼠标监听
        panel.addMouseListener(mp);
        panel.addKeyListener(mp);

    }

    /*
     * 设置布局,调节大小,监听器,并显示
     */
    private void showIt() {
        Container con = mainframe.getContentPane();
        con.setLayout(new BorderLayout());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // mainframe.setSize((int)(dim.getWidth()-100),(int)(dim.getHeight()-150));//设置为全屏幕
        // mainframe.setSize(300,100);
        mainframe.setSize(900, 100);

        mainframe.setLocation(0, 0);
        mainframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);//关闭程序
            }
        });
    }

    /*
     * 弹出消息框
     */
    public void showMessage(String title, String message, int messagetype) {
        JOptionPane.showMessageDialog(mainframe, message, title, messagetype);

    }
    /*
     * 显示图象
     */

    public void ShowImage(BufferedImage image) {
        if (image == null) return;
        Graphics g = panel.getGraphics();
        g.drawImage(image, panel.getX(), panel.getY(), panel.getWidth() + panel.getX(), panel.getHeight() + panel.getY(), 0, 0, image.getWidth(), image.getHeight(), null);
    }
    /*
     * 设置画布大小
     * target_width为目标机器的宽,target_height为目标机器的高
     */

    public void setJPanelSize(double target_width, double target_height) {
        double mywidth = mainframe.getSize().getWidth();//本机器的宽
        double panelheight = (double) (target_height * mywidth / target_width);
        tools.print("设置的大小为? mywidth=" + mywidth + " panelheight=" + panelheight + " target_height=" + target_height + " " +
                " target_width=" + target_width);
        mainframe.setSize((int) mywidth, (int) panelheight + 50);
        //this.panel.setSize((int)mywidth,(int)panelheight);
        tools.print("panel 实际大小：Width:" + panel.getWidth() + " Height:" + panel.getHeight());
        mainframe.setVisible(false);
        mainframe.setVisible(true);


    }

    /*
     * 显示文件上传对话框
     */
    public void showFileUpload() {
        this.fileup.setVisible(true);
    }

    /*
     * 显示文件下载框
     */
    public void showFileDownDialog(boolean show) {
        this.fdwnDialog.setVisible(show);
    }

    /*
     * 显示DOS命令窗口
     */
    public void showDosOrderDialog(boolean show) {
        this.dosOrderDialog.setVisible(show);
    }

    ///////////////////////////////////////////////////////////
    public ClientStatus getClient() {
        return client;
    }

    public void setClient(ClientStatus client) {
        this.client = client;
        OpreationMenu.setEnableTrue();//功能键可用
        fileup = new FileDialog(mainframe, client.getIp() + ":文件上传", FileDialog.LOAD);//文件上传对话框
        filedown = new FileDialog(mainframe, client.getIp() + ":选择保存目录", FileDialog.SAVE);//文件下载对话框
        fdwnDialog = new FiledownDialog(this.mainframe, client.getIp() + "输入下载文件的路径", false);
        dosOrderDialog = new DosOrderInUI(this.mainframe, this.client.getIp().toString());

    }

    public Canvas getPanel() {
        return panel;
    }

    public void setPanel(Canvas panel) {
        this.panel = panel;
    }

    public FileDialog getFiledown() {
        return filedown;
    }

    public FileDialog getFileup() {
        return fileup;
    }

    public DosOrderInUI getDosOrderDialog() {
        return dosOrderDialog;
    }

}
