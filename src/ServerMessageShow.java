

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * �ͻ�����Ϣ��ʾ
 */
public class ServerMessageShow {
    private static JFrame cjf=new JFrame("��Ϣ");//������,ֻ��һ��
    public static void showMessage(String title,String message,int messagetype){
        JOptionPane.showMessageDialog(cjf,message,title,messagetype);

    }
    public static void showWARNING(String title,String message){
        JOptionPane.showMessageDialog(cjf,message,title,JOptionPane.WARNING_MESSAGE);

    }
}