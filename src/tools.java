

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Calendar;
import java.util.StringTokenizer;

public class tools {

    public static void print(String s) {
        //System.out.println("###"+s+"###");
    }

    /*
     * ������ת��Ϊ�ַ���
     */
    public static String millsTODateString(long millons) {
        Calendar cc = Calendar.getInstance();
        cc.setTimeInMillis(millons);
        return cc.get(Calendar.YEAR) + "-" + (cc.get(Calendar.MONTH) + 1) + "-" + cc.get(Calendar.DAY_OF_MONTH) + " "
                + cc.get(Calendar.HOUR_OF_DAY) + ":" + cc.get(Calendar.MINUTE) + ":" + cc.get(Calendar.SECOND);

    }

    /*
     * �������ʱ��
     */
    public static String getSystemTime() {
        return millsTODateString(System.currentTimeMillis());
    }

    /*
     * ��ȡvalue
     */
    public static String getValue(String namevalue) {
        if (namevalue == null || namevalue.equals("")) return null;
        StringTokenizer toke = new StringTokenizer(namevalue, Parameter.ORDER_SEPRATER + "");
        toke.nextToken();
        return toke.nextToken();

    }

    /*
     * ��ͣ3��
     */
    public static void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    /*
     * ������λ������Ļ�м�
     */
    public static void setInCenter(Window window) {
        Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());//����ȫ��

        window.setLocation(rect.width / 3, rect.height / 3);

    }


}
