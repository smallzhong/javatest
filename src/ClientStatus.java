import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 * �ͻ���״̬��Ϣ
 */
public class ClientStatus implements Serializable {
    private InetAddress ip = null;//�����Ӷ�IP��ַ
    private int port = -1;//�˿ں�
    private double screen_height = 0;//��Ļ��
    private double screen_width = 0;//��Ļ��
    private ControlInfo control = null;//�����׽�


    public ClientStatus(InetAddress inet) {
        this.ip = inet;
    }

    /*
     * ��ÿͻ��˷��� ����
     */
    public void sendMyOrder(String order) {
        if (port == -1) {//��3���˿ڶ�������Ϣ
            int portlength = Parameter.ORDER_UDP_PORT.length;
            for (int i = 0; i < portlength; i++)//��ÿ�����ܵĶ˿ڷ�����Ϣ
                sendOrder(this.ip, Parameter.ORDER_UDP_PORT[i], order);
        } else
            sendOrder(this.ip, this.port, order);
    }

    /*
     * ��ĳ�ͻ���ĳ�˿ڷ�������
     */
    private static void sendOrder(InetAddress ip, int port, String order) {
        byte sp[] = order.getBytes();
        DatagramPacket packet = new DatagramPacket(sp, sp.length, ip, port);
        try {
            DatagramSocket sd = new DatagramSocket();
            sd.send(packet);
        } catch (Exception e) {
            tools.print("��:" + ip + " ��������:" + order + "ʧ��");
            //e.printStackTrace();
        }

    }

    /*
     * ������Ϣ������������������Լ���ǰ״̬
     */
    public static ClientStatus reportMystatus(int udpport, double hei, double wid) {
        ClientStatus cc = new ClientStatus(null);
        cc.setPort(udpport);
        cc.setScreen_height(hei);
        cc.setScreen_width(wid);

        return cc;

    }

    /*
     *���ö˿�
     */
    public void setPort(int port) {
        this.port = port;

    }

    /*
     * ��ȡ�˿�
     */
    public int getPort() {
        return this.port;

    }

    public double getScreen_height() {
        return screen_height;
    }

    public void setScreen_height(double screen_height) {
        this.screen_height = screen_height;
    }

    public double getScreen_width() {
        return screen_width;
    }

    public void setScreen_width(double screen_width) {
        this.screen_width = screen_width;
    }

    public ControlInfo getControl() {
        return control;
    }

    public void setControl(ControlInfo control) {
        this.control = control;
    }

    public InetAddress getIp() {
        return ip;
    }

}
