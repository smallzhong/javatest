

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
 * 操作目录
 */
public class OpreationMenu {
    private static MenuItem filedown = new MenuItem("文件下载");
    private static MenuItem fileup = new MenuItem("文件上传");
    private static MenuItem connect = new MenuItem("连接");//会更换为实现各自的listener的JMenuItem的子类
    private static MenuItem restart = new MenuItem("重启");
    private static MenuItem shutdown = new MenuItem("关机");
    private static MenuItem dosorder = new MenuItem("命令");

    private static MenuItem btn_test = new MenuItem("测试");
    private static MenuItem btn_sync_file = new MenuItem("同步文件");

    public OpreationMenu() {
    }

    /*
     * 控制目录,
     * 生成控制目录，包括:连接,重启,关机,命令
     * 添加到bar中
     */
    private static void initControlMenu(MenuBar bar) {
        Menu control = new Menu("控制");

//    	MenuItem item1=new MenuItem("连接");//会更换为实现各自的listener的JMenuItem的子类
//    	MenuItem item2=new MenuItem("重启");
//    	MenuItem item3=new MenuItem("关机");
//    	MenuItem item4=new MenuItem("命令");
        connect.addActionListener(ConnectClientFrame.getInstance());//添加监听

//        btn_test.addActionListener(e -> {
//            ClientMessageShow.showMessage("测试", "测试", JOptionPane.INFORMATION_MESSAGE);
//            clientstatus.sendMyOrder(order);
//        });

        restart.setEnabled(false);//还不能使用
        shutdown.setEnabled(false);
        dosorder.setEnabled(false);
//        btn_test.setEnabled(false);

        DOSorder ddd = new DOSorder();
        restart.addActionListener(ddd);
        shutdown.addActionListener(ddd);
        dosorder.addActionListener(ddd);
        btn_test.addActionListener(ddd);

        control.add(connect);//添加到JMenu
        control.add(restart);//添加到JMenu
        control.add(shutdown);//添加到JMenu
        control.add(dosorder);//添加到JMenu

        control.add(btn_test);


        bar.add(control);//添加到总菜单

        //item4.addActionListener()

    }

    /*
     * 文件操作目录，
     * 包括:文件上传，文件下载
     * 添加到bar中
     */
    private static void initFileMenu(MenuBar bar) {
        Menu fileop = new Menu("文件");

        fileup.setEnabled(false);//还不能使用
        fileup.addActionListener(new FileupLoad());//添加监听
        filedown.setEnabled(false);
        filedown.addActionListener(new FileDown());

        fileop.add(filedown);
        fileop.add(fileup);
        bar.add(fileop);
    }

    /*
     * 生成菜单条
     */
    public static void initMenuBar(JFrame jf) {
        Container con = jf.getContentPane();
        MenuBar bar = new MenuBar();
        initControlMenu(bar);//初始化"控制"菜单
        initFileMenu(bar);//初始化"文件"菜单
        initOtherMenu(bar);

        jf.setMenuBar(bar);//添加到主框架
    }

    public static void initOtherMenu(MenuBar bar) {
        Menu t = new Menu("测试");
        btn_sync_file.setEnabled(false);
        t.add(btn_sync_file);
        t.addActionListener(e -> {
            yuchu_GetInputDialog aaa = new yuchu_GetInputDialog(MainFrame.getInstance().getMainframe(), "test");
        });
        bar.add(t);
    }

    /*
     * 已经连接，设置为可用
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

//文件上传事件响应
class FileupLoad implements ActionListener {

    public FileupLoad() {
    }

    public void actionPerformed(ActionEvent arg0) {
        MainFrame.getInstance().showFileUpload();//显示上传文件对话框
        FileDialog fileup = MainFrame.getInstance().getFileup();
        String path = fileup.getDirectory() + fileup.getFile();
        tools.print(tools.getSystemTime() + " 将要上传文件：" + path);

        try {
            SOrderExcute.upLoadFile(path, MainFrame.getInstance().getClient());
        } catch (MyException e) {
            MainFrame.getInstance().showMessage("错误", e.toString(), JOptionPane.WARNING_MESSAGE);

        } catch (SocketException e) {
            MainFrame.getInstance().showMessage("错误", "无法建立连接", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            MainFrame.getInstance().showMessage("错误", "传输中发生网络错误，文件上传失败", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }

    }


}

//文件下载事件响应
class FileDown implements ActionListener {


    public void actionPerformed(ActionEvent arg0) {
        MainFrame.getInstance().showFileDownDialog(true);
    }


}

//重起，关机器等
class DOSorder implements ActionListener {
    public void actionPerformed(ActionEvent arg0) {
        MenuItem me = (MenuItem) arg0.getSource();
        //tools.print("name="+me.getLabel());excuseDOSOrder
        String opreationname = me.getLabel();
        if (opreationname.equals("关机")) {
            SOrderExcute.excuseDOSOrderWithout(OrderMap.DOS_ORDER_shutdown, MainFrame.getInstance().getClient());
        } else if (opreationname.equals("重启")) {
            SOrderExcute.excuseDOSOrderWithout(OrderMap.DOS_ORDER_restart, MainFrame.getInstance().getClient());
        } else if (opreationname.equals("测试")) {
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

                if (opreationname.equals("命令")) {
                    MainFrame.getInstance().showDosOrderDialog(true);
                    order = MainFrame.getInstance().getDosOrderDialog().getTextValue();

                    SOrderExcute.excuseDOSOrder(order, MainFrame.getInstance().getClient());
                }
            } catch (IOException e) {
                MainFrame.getInstance().showMessage("执行DOS命令：" + order + "错误", "网络IO错误", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            } catch (MyException e) {
                MainFrame.getInstance().showMessage("执行DOS命令：" + order + "错误", e.toString(), JOptionPane.WARNING_MESSAGE);

                e.printStackTrace();
            }
        }


    }


}
