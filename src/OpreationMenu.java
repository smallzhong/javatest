

import javafx.scene.input.Mnemonic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/*
 * ����Ŀ¼
 */
public class OpreationMenu {
    private static MenuItem filedown = new MenuItem("�ļ�����");
    private static MenuItem fileup = new MenuItem("�ļ��ϴ�");
    private static MenuItem connect = new MenuItem("����");//�����Ϊʵ�ָ��Ե�listener��JMenuItem������
    private static MenuItem restart = new MenuItem("����");
    private static MenuItem shutdown = new MenuItem("�ػ�");
    private static MenuItem dosorder = new MenuItem("����");

    private static MenuItem btn_test = new MenuItem("����");
    private static MenuItem btn_sync_file = new MenuItem("ͬ���ļ�");

    public OpreationMenu() {
    }

    /*
     * ����Ŀ¼,
     * ���ɿ���Ŀ¼������:����,����,�ػ�,����
     * ��ӵ�bar��
     */
    private static void initControlMenu(MenuBar bar) {
        Menu control = new Menu("����");

//    	MenuItem item1=new MenuItem("����");//�����Ϊʵ�ָ��Ե�listener��JMenuItem������
//    	MenuItem item2=new MenuItem("����");
//    	MenuItem item3=new MenuItem("�ػ�");
//    	MenuItem item4=new MenuItem("����");
        connect.addActionListener(ConnectClientFrame.getInstance());//��Ӽ���

//        btn_test.addActionListener(e -> {
//            ClientMessageShow.showMessage("����", "����", JOptionPane.INFORMATION_MESSAGE);
//            clientstatus.sendMyOrder(order);
//        });

        restart.setEnabled(false);//������ʹ��
        shutdown.setEnabled(false);
        dosorder.setEnabled(false);
//        btn_test.setEnabled(false);

        DOSorder ddd = new DOSorder();
        restart.addActionListener(ddd);
        shutdown.addActionListener(ddd);
        dosorder.addActionListener(ddd);
        btn_test.addActionListener(ddd);

        control.add(connect);//��ӵ�JMenu
        control.add(restart);//��ӵ�JMenu
        control.add(shutdown);//��ӵ�JMenu
        control.add(dosorder);//��ӵ�JMenu

        control.add(btn_test);


        bar.add(control);//��ӵ��ܲ˵�

        //item4.addActionListener()

    }

    /*
     * �ļ�����Ŀ¼��
     * ����:�ļ��ϴ����ļ�����
     * ��ӵ�bar��
     */
    private static void initFileMenu(MenuBar bar) {
        Menu fileop = new Menu("�ļ�");

        fileup.setEnabled(false);//������ʹ��
        fileup.addActionListener(new FileupLoad());//��Ӽ���
        filedown.setEnabled(false);
        filedown.addActionListener(new FileDown());

        fileop.add(filedown);
        fileop.add(fileup);
        bar.add(fileop);
    }

    /*
     * ���ɲ˵���
     */
    public static void initMenuBar(JFrame jf) {
        Container con = jf.getContentPane();
        MenuBar bar = new MenuBar();
        initControlMenu(bar);//��ʼ��"����"�˵�
        initFileMenu(bar);//��ʼ��"�ļ�"�˵�
        initOtherMenu(bar);

        jf.setMenuBar(bar);//��ӵ������
    }

    public static void initOtherMenu(MenuBar bar) {
        Menu t = new Menu("����");
        btn_sync_file.setEnabled(false);
        t.add(btn_sync_file);
        t.addActionListener(e -> {
            yuchu_GetInputDialog aaa = new yuchu_GetInputDialog(MainFrame.getInstance().getMainframe(), "test");
        });
        bar.add(t);
    }

    /*
     * �Ѿ����ӣ�����Ϊ����
     */
    public static void setEnableTrue() {
        filedown.setEnabled(true);
        fileup.setEnabled(true);
        connect.setEnabled(false);
        restart.setEnabled(true);
        shutdown.setEnabled(true);
        dosorder.setEnabled(true);
        btn_test.setEnabled(true);
        btn_sync_file.setEnabled(true);
    }

}

//�ļ��ϴ��¼���Ӧ
class FileupLoad implements ActionListener {

    public FileupLoad() {
    }

    public void actionPerformed(ActionEvent arg0) {
        MainFrame.getInstance().showFileUpload();//��ʾ�ϴ��ļ��Ի���
        FileDialog fileup = MainFrame.getInstance().getFileup();
        String path = fileup.getDirectory() + fileup.getFile();
        tools.print(tools.getSystemTime() + " ��Ҫ�ϴ��ļ���" + path);

        try {
            SOrderExcute.upLoadFile(path, MainFrame.getInstance().getClient());
        } catch (MyException e) {
            MainFrame.getInstance().showMessage("����", e.toString(), JOptionPane.WARNING_MESSAGE);

        } catch (SocketException e) {
            MainFrame.getInstance().showMessage("����", "�޷���������", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            MainFrame.getInstance().showMessage("����", "�����з�����������ļ��ϴ�ʧ��", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }

    }


}

//�ļ������¼���Ӧ
class FileDown implements ActionListener {


    public void actionPerformed(ActionEvent arg0) {
        MainFrame.getInstance().showFileDownDialog(true);
    }


}

//���𣬹ػ�����
class DOSorder implements ActionListener {
    public void actionPerformed(ActionEvent arg0) {
        MenuItem me = (MenuItem) arg0.getSource();
        //tools.print("name="+me.getLabel());excuseDOSOrder
        String opreationname = me.getLabel();
        if (opreationname.equals("�ػ�")) {
            SOrderExcute.excuseDOSOrderWithout(OrderMap.DOS_ORDER_shutdown, MainFrame.getInstance().getClient());
        } else if (opreationname.equals("����")) {
            SOrderExcute.excuseDOSOrderWithout(OrderMap.DOS_ORDER_restart, MainFrame.getInstance().getClient());
        } else if (opreationname.equals("����")) {
            yuchu_GetInputDialog t = new yuchu_GetInputDialog(MainFrame.getInstance().getMainframe(), "test");

//            Vector<String> v = yuchu_ListAllFile.getAllFile(yuchu_ListAllFile.getFilePath());
//            for (String s : v )
//            {
//                tools.print(s);
//            }
////            MainFrame.getInstance().getClient().sendMyOrder("yc:test");
////            ClientMessageShow.showMessage("test", "test", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String order = null;
            try {

                if (opreationname.equals("����")) {
                    MainFrame.getInstance().showDosOrderDialog(true);
                    order = MainFrame.getInstance().getDosOrderDialog().getTextValue();

                    SOrderExcute.excuseDOSOrder(order, MainFrame.getInstance().getClient());
                }
            } catch (IOException e) {
                MainFrame.getInstance().showMessage("ִ��DOS���" + order + "����", "����IO����", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            } catch (MyException e) {
                MainFrame.getInstance().showMessage("ִ��DOS���" + order + "����", e.toString(), JOptionPane.WARNING_MESSAGE);

                e.printStackTrace();
            }
        }


    }


}
