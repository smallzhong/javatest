import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class SFileUpThread extends Thread {

    private int type = -1;
    private File file = null;//Ҫ���͵��ļ�����
    private Socket socket = null;//�ļ���Ϣ���ļ����ݶ�ȡ��
    private ObjectOutputStream fileinfoout = null;//���ڷ����ļ���Ϣ
    private DataOutputStream filedataout = null;//���ڷ����ļ�ʵ������
    //typeָʾ���ϴ��������أ���Ϊ�ⶼ���õ�ͬһ������

    public SFileUpThread(Socket socket, File file, int type) throws IOException {
        this.type = type;
        this.socket = socket;
        fileinfoout = new ObjectOutputStream(socket.getOutputStream());
        filedataout = new DataOutputStream(socket.getOutputStream());
        this.file = file;
    }

    public void run() {
        try {
            sendFileInfo();
            socket.getOutputStream().flush();
            sendFiledate();
            if (type == Parameter.FILE_UP)
                MainFrame.getInstance().showMessage("�ɹ�", "�ļ���\n" + file.getAbsolutePath() + " �ɹ��ϴ�!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            MainFrame.getInstance().showMessage("�ļ��������",
                    "��������з��������쳣", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }

    /*
     * �����ļ���Ϣ����
     */
    private void sendFileInfo() throws IOException {
        fileinfoout.writeObject(file);
    }

    /*
     * �����ļ�ʵ����Ϣ
     */
    private void sendFiledate() throws IOException {
        byte b[] = new byte[Parameter.FILE_CACHE];//����
        FileInputStream filein = new FileInputStream(file.getAbsolutePath());

        while (true) {
            int length = filein.read(b);
            //tools.print("�����ļ�����:"+length+"  ");
            if (length == -1) break;
            filedataout.write(b, 0, length);

        }

        //filedataout.write()

    }

}
