

import com.sun.corba.se.spi.oa.OADefault;

import javax.swing.*;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

/*
 * �����Ӷ��������
 */
public class COrderHandle {

    /*
     * serveripΪ������IP�����������ڶԶ�����Ӷ˽�����Ӧ
     */
    public static void handleOrder(InetAddress serverip, String order, Client client) throws IOException {
        try {
            if (order.contains(OrderMap.INIT_STATUS))//��ʼ������
                sendMystatus(serverip, order, client);
            if (order.contains(OrderMap.SCREEN_SHOW))//�������
                startScreenShut(serverip, order);
            if (order.contains(OrderMap.SCREEN_CONTROL))//�������
                startScreenControl(serverip, order);
            if (order.contains(OrderMap.FILE_UP))//�ļ��ϴ�
                startStoreFile(serverip, order);
            if (order.contains(OrderMap.FILE_DOWN)) //�ļ�����
                startDownFile(serverip, order);
            if (order.contains(OrderMap.FILE_EXITS)) //�ļ��Ƿ����
                IsExitsFile(serverip, order);
            if (order.contains(OrderMap.DOS_ORDER_shutdown)) //�ػ�
                excuteDOSOrder("shutdown -s");
            if (order.contains(OrderMap.DOS_ORDER_restart)) //����
                excuteDOSOrder("shutdown -t 1");
            if (order.contains(OrderMap.DOS_ORDER)) //����
                excuteDOSOrderInfo(serverip, order);
            if (order.contains(OrderMap.YC_ORDER)) {
                ServerMessageShow.showMessage("����", "����", JOptionPane.INFORMATION_MESSAGE);
            }
//            if (order.contains(OrderMap.YC_GET_FILE_LIST)) {
//                returnFileList(serverip, order);
//            }
            if (order.contains(OrderMap.YC_GET_ALL_FILE)) {
                returnAllFile(serverip, order);
            }

        } catch (Exception e) {
            tools.print("ִ������:" + order + "ʧ��");
        }


    }

    /*
     *
     */
    private static void returnFileList(InetAddress serverip, String order) throws IOException {
//        tools.print((String)serverport);
//        System.out.println(serverport);
        // TOOD:

//        int serverport = Integer.parseInt(tools.getValue(order));
//        Socket socket = new Socket(serverip, serverport);
//        ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream()); // ��װ��
//        ObjectInputStream get = new ObjectInputStream(socket.getInputStream());
//
//        String s = get.readUTF();
//        tools.print("��ȡͬ��·���ɹ�:" + s);
//        Vector<String> v = yuchu_ListAllFile.getAllFile(s);
//
//        send.writeObject(v); //����
//        send.close(); // �ر���
//        socket.close(); // �ر��׽�
    }

    private static void returnAllFile(InetAddress serverip, String order) throws IOException {
        int serverport = Integer.parseInt(tools.getValue(order));
        Socket socket = new Socket(serverip, serverport);
        ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream()); // ��װ��
        ObjectInputStream get = new ObjectInputStream(socket.getInputStream());

        String path = get.readUTF();
        tools.print("��ȡͬ��·���ɹ�:" + path);
        Vector<String> v = yuchu_ListAllFile.getAllFile(path);

        tools.print("��ʼ�����ļ�");
        // TODO: ����123123123
        for (String s : v) {
            File file = new File(s);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                send.writeUTF(file.getName());
                send.flush();
                send.writeLong(file.length());
                send.flush();

                byte[] bytes = new byte[1024];
                int length = 0;
                long progress = 0;

                while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    send.write(bytes, 0, length);
                    send.flush();
                    progress += length;
//                    tools.print("| " + (100 * progress / file.length()) + "% |");
                }
                send.flush();
                tools.print(file.getName() + "����ɹ�");
            }

        }

        send.writeUTF(OrderMap.YC_FILE_TRANSFER_END);
        send.flush();

        send.close(); // �ر���
        socket.close(); // �ر��׽�
    }

    /*
     * �����Լ�����Ϣ
     */
    private static void sendMystatus(InetAddress serverip, String order, Client client) throws IOException {
        int serverport = Integer.parseInt(tools.getValue(order));
        Socket socket = new Socket(serverip, serverport);
        ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());//��װ��
        int myUDPport = client.getInstance().getORC().getMyUDPPort();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߺͿ�
        ClientStatus cc = ClientStatus.reportMystatus(myUDPport, dim.getHeight(), dim.getWidth());//����Ҫ���͵�״̬����
        send.writeObject(cc);//����
        send.close();//�ر���
        socket.close();//�ر��׽�
    }

    /*
     * ����ͼ����
     */
    private static void startScreenShut(InetAddress serverip, String order) throws IOException, AWTException {
        int serverport = Integer.parseInt(tools.getValue(order));//��ȡTCP�˿ں�
        tools.print("���ӣ�" + serverip + " port=" + serverport + " ����ͼ��");
        //tools.sleep(3000);
        new SendImageThread(serverip, serverport).start();
    }

    /*
     * �������������׽���
     */
    private static void startScreenControl(InetAddress serverip, String order) throws IOException, AWTException {
        int serverport = Integer.parseInt(tools.getValue(order));//��ȡ�����еĶ˿ں�
        Socket socket = new Socket(serverip, serverport);//���ӷ�����
        tools.print(tools.getSystemTime() + " �������ӽ����ɹ�.IP:" + serverip);
        new CTableControl(socket).start();
    }

    /*
     * �ļ��ϴ�
     */
    private static void startStoreFile(InetAddress serverip, String order) throws IOException {
        int serverport = Integer.parseInt(tools.getValue(order));//��ȡ�����еĶ˿ں�
        Socket socket = new Socket(serverip, serverport);//���ӷ�����
        tools.print(tools.getSystemTime() + " �ļ���������.������IP:" + serverip);
        new CStoreFileThread(socket, Parameter.FILE_UP).start();

    }

    /*
     * �鿴�ļ��Ƿ����
     */
    private static void IsExitsFile(InetAddress serverip, String order) throws IOException {
        int serverport = getport(order);
        String filepath = getfilepath(order);
        Socket socket = new Socket(serverip, serverport);//���ӷ�����
        tools.print(tools.getSystemTime() + " �鿴�ļ��Ƿ���� file:" + filepath);
        File file = new File(filepath);

        socket.getOutputStream().flush();
        if (!file.exists()) {
            tools.print("�ļ������� " + Parameter.FILE_NOT_EXITS);

            socket.getOutputStream().write(Parameter.FILE_NOT_EXITS);//������
            socket.close();
            return;
        }

        if (!file.isFile()) {
            tools.print("���ļ���");
            socket.getOutputStream().write(Parameter.IS_DOCTIONRY);//���ļ���
            socket.close();
            return;
        }
        tools.print(file.length() + " ����");
        socket.getOutputStream().write(Parameter.FILE_EXITS);//������
//		


    }

    /*
     * �ļ�����
     *
     */
    private static void startDownFile(InetAddress serverip, String order) throws IOException {

        int serverport = getport(order);
        String filepath = getfilepath(order);
        Socket socket = new Socket(serverip, serverport);//���ӷ�����
        tools.print(tools.getSystemTime() + " �����ļ��� IP:" + serverip);
        new SFileUpThread(socket, new File(filepath), Parameter.FILE_DOWN).start();
    }

    /*
     * ִ��DOS����
     */
    public static void excuteDOSOrder(String order) {
        Runtime run = Runtime.getRuntime();
        try {
            run.exec(order);
            tools.print("ִ������--" + order);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /*
     * ִ��DOS�����ȡִ�н���ʹ�����
     */
    public static void excuteDOSOrderInfo(InetAddress serverip, String order) throws IOException {
        int serverport = getport(order);
        String dosorder = getfilepath(order);//Ҳ����������ȡ������������ʽ��һ��dos?123?shutdown -s
        Socket socket = new Socket(serverip, serverport);//���ӷ�����
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        try {

            out.write((tools.getSystemTime() + "-�����Ϣ��\n").getBytes());
            DOSExcuter.excuteDosOrderInputstream(dosorder, out);
            out.write((tools.getSystemTime() + "-������Ϣ��\n").getBytes());
            DOSExcuter.excuteDosOrderERRORstream(dosorder, out);

        } catch (Exception e) {
            out.write(("ִ�����" + dosorder + "ʧ��,ԭ��:\n" + e.getMessage() + "\n" + e.toString()).getBytes());

        } finally {
            out.close();
            socket.close();
        }
    }


    /*
     * �ļ����������ַ�������
     */
    private static int getport(String order) {
        StringTokenizer toke = new StringTokenizer(order, "?");
        toke.nextToken();
        return Integer.parseInt(toke.nextToken());
    }

    private static String getfilepath(String order) {
        StringTokenizer toke = new StringTokenizer(order, "?");
        toke.nextToken();
        toke.nextToken();
        return toke.nextToken();
    }

}
