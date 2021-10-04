


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JOptionPane;

/*
 * �����Ӷˣ�����˿ڿ������ͽ�������
 *
 */
public class ClientOrderReceiver {
    private DatagramSocket orderUDP = null;//�������UDP
    //ClientStatus mystatus=new ClientStatus(null);//����״̬ͳ��

    /*
     * Ҫ�����̶���UDP�˿�
     */
    public ClientOrderReceiver() throws MyException {
        boolean success = openOrderUDPPort();//�����˿�
        if (!success) {
            ClientMessageShow.showMessage("���Ӷ˳�ʼ��ʧ��", "����UDP����˿�ʧ��", JOptionPane.WARNING_MESSAGE);
            throw new MyException("����UDP����˿�ʧ��");
        }
        //������������߳�
        startReceiverOrder();

    }

    /*
     * ��3��UDP�˿���ѡ��һ��,�ɹ�����true,ʧ�ܷ���false
     */
    public boolean openOrderUDPPort() {
        int length = Parameter.ORDER_UDP_PORT.length;
        for (int i = 0; i < length; i++) {
            try {
                openUDPPort(Parameter.ORDER_UDP_PORT[i]);
                tools.print("�ɹ�����:" + Parameter.ORDER_UDP_PORT[i]);
                return true;
            } catch (SocketException e) {
                continue;
            }
        }
        return false;

    }

    /*
     * ���Կ���UDP�˿�
     */
    private void openUDPPort(int port) throws SocketException {
        orderUDP = new DatagramSocket(port);
    }

    /*
     * ����N����������߳�
     */
    private void startReceiverOrder() {
        int length = Parameter.OrderExcuteNums;
        for (int i = 0; i < length; i++)
            new orderReceiver(orderUDP).start();
    }

    /*
     * ����ҵ�UDP�˿�
     */
    public int getMyUDPPort() {
        return this.orderUDP.getLocalPort();

    }

}

//��������߳���
class orderReceiver extends Thread {
    private DatagramSocket udpOrder = null;//���ڶ�ȡ�����UDP�׽���
    byte hs[] = new byte[500];//���ڴ����������500�ֽ�
    DatagramPacket packet = new DatagramPacket(hs, hs.length);//�������ݰ�

    public orderReceiver(DatagramSocket udpOrder) {
        this.udpOrder = udpOrder;
    }

    public void run() {
        tools.print(tools.getSystemTime() + "--������������߳�--");
        while (true) {
            try {
                udpOrder.receive(packet);
                String s_message = new String(packet.getData(), 0, packet.getLength());
                tools.print(tools.getSystemTime() + " �յ�����:" + s_message);
                COrderHandle.handleOrder(packet.getAddress(), s_message, Client.getInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }//while

    }

}





















