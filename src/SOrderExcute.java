import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

/*
 * ������������ִ��
 */
public class SOrderExcute {

    /*
     * ��ȡ�ļ��б�
     */
    public static void GetFileList(ClientStatus clientstatus) throws MyException {
        ServerSocket server = NewRadomSocket.openNewPort(); // �����¶˿�
        Socket socket = null;
        try {
            server.setSoTimeout(Parameter.TCP_TIME_OUT);//���ó�ʱ

            String order = OrderMap.toOrder(OrderMap.YC_GET_FILE_LIST, server.getLocalPort());
            clientstatus.sendMyOrder(order);//��������

            socket = server.accept(); // ����
            ObjectInputStream readin = new ObjectInputStream(socket.getInputStream());//��װ����׼����ȡһ������
            Object ob = readAObject(readin);

            if (ob != null) {
//                try {
//
//                } catch (Exception e)
//                {
//                    ClientMessageShow.showWARNING("��������", "δ�ܻ�ȡobject");
//                    return;
//                }

                ClientMessageShow.showMessage("���ͳɹ�",  "���ͳɹ�", JOptionPane.INFORMATION_MESSAGE);
//                Vector<String> v = (Vector<String>) ob;
//                for (String s : v) {
//                    tools.print("���·���� " + s);
//                }

//                ClientStatus cs = (ClientStatus) ob;
//                //tools.print("�ɹ���ȡ��Ϣ:"+cs.getPort());
//                clientstatus.setPort(cs.getPort());
//
//                clientstatus.setScreen_height(cs.getScreen_height());//������Ļ��С��Ϣ
//                clientstatus.setScreen_width(cs.getScreen_width());//������Ļ��С��Ϣ
//                MainFrame.getInstance().setJPanelSize(cs.getScreen_width(), cs.getScreen_height());//���ձ������û�����С

            }//if

        } catch (Exception e) {
            throw new MyException("����ʧ��:\n-Ŀ��������ɴ�");
        } finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    /*
     * ��ñ����Ӷ�״̬��
     * clientstatus�м�¼�˿ͻ��˵�IP��ַ��״̬��Ϣ
     */
    public static void getClientStatus(ClientStatus clientstatus) throws MyException {
        ServerSocket server = NewRadomSocket.openNewPort();//�����¶˿�
        Socket socket = null;
        try {
            server.setSoTimeout(Parameter.TCP_TIME_OUT);//���ó�ʱ
            String order = OrderMap.toOrder(OrderMap.INIT_STATUS, server.getLocalPort());
            clientstatus.sendMyOrder(order);//��������
            socket = server.accept();//����
            ObjectInputStream readin = new ObjectInputStream(socket.getInputStream());//��װ����׼����ȡһ������
            Object ob = readAObject(readin);

            if (ob != null) {
                ClientStatus cs = (ClientStatus) ob;
                //tools.print("�ɹ���ȡ��Ϣ:"+cs.getPort());
                clientstatus.setPort(cs.getPort());

                clientstatus.setScreen_height(cs.getScreen_height());//������Ļ��С��Ϣ
                clientstatus.setScreen_width(cs.getScreen_width());//������Ļ��С��Ϣ
                MainFrame.getInstance().setJPanelSize(cs.getScreen_width(), cs.getScreen_height());//���ձ������û�����С

            }//if

        } catch (Exception e) {
            throw new MyException("����ʧ��:\n-Ŀ��������ɴ�");
        } finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }


    }

    /*
     *������Ļ�����߳�
     */
    public static void startGetScreen(ClientStatus clientstatus) throws MyException {
        ServerSocket server = null;
        try {
            server = NewRadomSocket.openNewPort();
            server.setSoTimeout(Parameter.TCP_TIME_OUT);//���ó�ʱ
            clientstatus.sendMyOrder(OrderMap.toOrder(OrderMap.SCREEN_SHOW, server.getLocalPort()));//��������
            Socket socket = server.accept();    //����
            tools.print(socket.getRemoteSocketAddress() + " �Ѿ����Ӷ˿ڣ�" + socket.getLocalPort() + " �ȴ�����,����ͼ�δ���");
            //socket.getInputStream().read();
            new GetImageThread(socket).start();//����ͼ����ʾ
        } catch (Exception e) {
            throw new MyException(e.toString());
        }


    }

    /*
     * ���������׽���
     */
    public static boolean startControlSocket(ClientStatus clientstatus) {
        ServerSocket server = null;
        try {
            server = NewRadomSocket.openNewPort();
            server.setSoTimeout(Parameter.TCP_TIME_OUT);//���ó�ʱ
            clientstatus.sendMyOrder(OrderMap.toOrder(OrderMap.SCREEN_CONTROL, server.getLocalPort()));//��������
            Socket socket = server.accept();    //����
            tools.print(socket.getRemoteSocketAddress() + " �Ѿ����Ӷ˿ڣ�" + socket.getLocalPort() + " �����׽ӿ���");
            clientstatus.setControl(new ControlInfo(socket));//ע����ƶ�����������
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * �ϴ��ļ�
     */
    public static void upLoadFile(String filepath, ClientStatus clientstatus) throws MyException, IOException {
        ServerSocket server = null;
        File file = new File(filepath);
        if (!file.isFile()) throw new MyException("���ļ�������");
        server = NewRadomSocket.openNewPort();
        server.setSoTimeout(Parameter.TCP_TIME_OUT);//���ó�ʱ
        clientstatus.sendMyOrder(OrderMap.toOrder(OrderMap.FILE_UP, server.getLocalPort()));//��������
        Socket socket = server.accept();    //����
        tools.print(socket.getRemoteSocketAddress() + " �Ѿ����Ӷ˿ڣ�" + socket.getLocalPort() + " ׼���ϴ��ļ���" + filepath);
        new SFileUpThread(socket, file, Parameter.FILE_UP).start();
    }

    /*
     * ѯ��path���ļ��Ƿ����
     */
    public static boolean IsExitesFile(String filepath, ClientStatus clientstatus) throws MyException, IOException {

        ServerSocket server = null;
        server = NewRadomSocket.openNewPort();
        server.setSoTimeout(Parameter.TCP_TIME_OUT);//���ó�ʱ
        clientstatus.sendMyOrder(OrderMap.FILE_EXITS + "?" + server.getLocalPort() + "?" + filepath);//��������
        Socket socket = server.accept();    //����
        tools.print(socket.getRemoteSocketAddress() + " �Ѿ����Ӷ˿ڣ�" + socket.getLocalPort() + "ѯ���ļ��Ƿ���ڣ�" + filepath);
        InputStream in = socket.getInputStream();

        int exits = in.read();//exits=-1��ʾ������1��ʾ����2��ʾ��һ���ļ���
        tools.print("������" + exits);
        switch (exits) {
            case Parameter.FILE_NOT_EXITS:
                throw new MyException("�ļ�������\n-" + filepath);
            case Parameter.IS_DOCTIONRY:
                throw new MyException("�����ļ������ļ���\n-" + filepath);
            case Parameter.FILE_EXITS:
                return true;
        }
        return false;

    }

    /*
     * �����ļ�
     */
    public static void downFile(String filepath, ClientStatus clientstatus) throws MyException, IOException {
        ServerSocket server = null;
        server = NewRadomSocket.openNewPort();
        server.setSoTimeout(Parameter.TCP_TIME_OUT);//���ó�ʱ
        clientstatus.sendMyOrder(OrderMap.FILE_DOWN + "?" + server.getLocalPort() + "?" + filepath);//��������
        Socket socket = server.accept();    //����
        tools.print(socket.getRemoteSocketAddress() + " �Ѿ����Ӷ˿ڣ�" + socket.getLocalPort() + " �����ļ���" + filepath);


        new CStoreFileThread(socket, Parameter.FILE_DOWN).start();

    }

    /*
     * ִ��DOS����,����Ҫ������Ϣ
     */
    public static void excuseDOSOrderWithout(String order, ClientStatus clientstatus) {
        clientstatus.sendMyOrder(order);

    }

    /*
     * ִ��DOS�����ʾ������Ϣ
     */
    public static void excuseDOSOrder(String order, ClientStatus clientstatus) throws IOException, MyException {
        ServerSocket server = null;
        server = NewRadomSocket.openNewPort();
        server.setSoTimeout(Parameter.TCP_TIME_OUT);//���ó�ʱ
        clientstatus.sendMyOrder(OrderMap.DOS_ORDER + "?" + server.getLocalPort() + "?" + order);//��������
        Socket socket = server.accept();    //����
        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
        ServerDOSOrderUI show = new ServerDOSOrderUI(clientstatus.getIp() + " ִ������" + order);
        byte b[] = new byte[1024];//����
        int m = 0;
        String s = null;
        while ((m = in.read(b)) != -1) {
            s = new String(b, 0, m);
            show.setMessage(s);
        }
        // show.closeMe();
    }

    /*
     * ������Ϣ
     */
    public static void sendMessage(String message, ClientStatus clientstatus) {
        clientstatus.sendMyOrder(OrderMap.toOrder(OrderMap.MESSAGE, message));
    }


    /*
     * �����ж�ȡһ������
     */
    private static Object readAObject(ObjectInputStream in) throws MyException {
        try {
            return in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("�ͻ��˳�ʼ��ʧ�ܣ��޷���ȡ״̬����");
        }

    }

}
