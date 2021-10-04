

import java.awt.event.InputEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*
 * �����߳�
 */
public class ControlInfo {//extends Thread {

    private Socket socket = null;
    private ObjectOutputStream out = null;//�¼������ͷ�װ

    public ControlInfo(Socket socket) throws IOException {
        this.socket = socket;
        this.socket.setSendBufferSize(Parameter.EVENT_CACHE);
        out = new ObjectOutputStream(this.socket.getOutputStream());
        // out.flush();//ˢ��
    }

    /*
     * �����¼�
     */
    public void sendMyAction(InputEvent event) {
        //���Ƿ��б�Ҫ���¼�����ɸѡ
        try {
            out.writeObject(event);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void run() {

    }
}
