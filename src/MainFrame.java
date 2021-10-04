
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
    private JFrame mainframe = new JFrame("Զ�̼��ϵͳ1.0");//������,ֻ��һ��
    //private JPanel panel=null;//��ʾ����
    private Canvas panel = null;//��ʾ����
    private FileDialog fileup = null;//new FileDialog(mainframe,"�ļ��ϴ�",FileDialog.LOAD);//�ļ��ϴ��Ի���
    private FileDialog filedown = null;//new FileDialog(mainframe,"�ļ��ϴ�",FileDialog.SAVE);//�ļ����ضԻ���
    private FiledownDialog fdwnDialog = null;//�����ļ�����·��
    private DosOrderInUI dosOrderDialog = null;//dos����Ի���

    private ClientStatus client = null;//�����Ӷ�״̬��Ϣ�����ƾ��

    private MainFrame() {
        showIt();//��ʾ
        initJPanel();//��ӻ���
        initMunuBar();//��Ӳ˵�
        mainframe.setVisible(true);//��ʾ������
    }

    /*
     * ����
     */
    public static MainFrame getInstance() {
        return mainf;
    }

    /*
     * ��ʼ���˵�
     */
    private void initMunuBar() {
        OpreationMenu.initMenuBar(mainframe);
    }

    /*
     * ��ӻ���
     */
    public void initJPanel() {
        //panel=new JPanel();
        panel = new Canvas();
//		MouseOnPanel mp=new MouseOnPanel(client);
//		panel.addMouseMotionListener(mp);//���������
//		panel.addMouseListener(mp);
//		panel.addKeyListener(mp);
        mainframe.add(panel, BorderLayout.CENTER);
    }

    /*
     * Ϊ���������꣬�����¼�����
     */
    public void addListenerForPanel() {
        MouseOnPanel mp = new MouseOnPanel(client);
        panel.addMouseMotionListener(mp);//���������
        panel.addMouseListener(mp);
        panel.addKeyListener(mp);

    }

    /*
     * ���ò���,���ڴ�С,������,����ʾ
     */
    private void showIt() {
        Container con = mainframe.getContentPane();
        con.setLayout(new BorderLayout());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // mainframe.setSize((int)(dim.getWidth()-100),(int)(dim.getHeight()-150));//����Ϊȫ��Ļ
        // mainframe.setSize(300,100);
        mainframe.setSize(900, 100);

        mainframe.setLocation(0, 0);
        mainframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);//�رճ���
            }
        });
    }

    /*
     * ������Ϣ��
     */
    public void showMessage(String title, String message, int messagetype) {
        JOptionPane.showMessageDialog(mainframe, message, title, messagetype);

    }
    /*
     * ��ʾͼ��
     */

    public void ShowImage(BufferedImage image) {
        if (image == null) return;
        Graphics g = panel.getGraphics();
        g.drawImage(image, panel.getX(), panel.getY(), panel.getWidth() + panel.getX(), panel.getHeight() + panel.getY(), 0, 0, image.getWidth(), image.getHeight(), null);
    }
    /*
     * ���û�����С
     * target_widthΪĿ������Ŀ�,target_heightΪĿ������ĸ�
     */

    public void setJPanelSize(double target_width, double target_height) {
        double mywidth = mainframe.getSize().getWidth();//�������Ŀ�
        double panelheight = (double) (target_height * mywidth / target_width);
        tools.print("���õĴ�СΪ? mywidth=" + mywidth + " panelheight=" + panelheight + " target_height=" + target_height + " " +
                " target_width=" + target_width);
        mainframe.setSize((int) mywidth, (int) panelheight + 50);
        //this.panel.setSize((int)mywidth,(int)panelheight);
        tools.print("panel ʵ�ʴ�С��Width:" + panel.getWidth() + " Height:" + panel.getHeight());
        mainframe.setVisible(false);
        mainframe.setVisible(true);


    }

    /*
     * ��ʾ�ļ��ϴ��Ի���
     */
    public void showFileUpload() {
        this.fileup.setVisible(true);
    }

    /*
     * ��ʾ�ļ����ؿ�
     */
    public void showFileDownDialog(boolean show) {
        this.fdwnDialog.setVisible(show);
    }

    /*
     * ��ʾDOS�����
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
        OpreationMenu.setEnableTrue();//���ܼ�����
        fileup = new FileDialog(mainframe, client.getIp() + ":�ļ��ϴ�", FileDialog.LOAD);//�ļ��ϴ��Ի���
        filedown = new FileDialog(mainframe, client.getIp() + ":ѡ�񱣴�Ŀ¼", FileDialog.SAVE);//�ļ����ضԻ���
        fdwnDialog = new FiledownDialog(this.mainframe, client.getIp() + "���������ļ���·��", false);
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
