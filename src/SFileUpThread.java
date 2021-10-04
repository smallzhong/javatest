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
    private File file = null;//要发送的文件对象
    private Socket socket = null;//文件信息，文件数据读取流
    private ObjectOutputStream fileinfoout = null;//用于发送文件信息
    private DataOutputStream filedataout = null;//用于发送文件实体数据
    //type指示是上传还是下载，因为这都是用的同一个对象

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
                MainFrame.getInstance().showMessage("成功", "文件：\n" + file.getAbsolutePath() + " 成功上传!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            MainFrame.getInstance().showMessage("文件传输错误",
                    "传输过程中发生网络异常", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }

    /*
     * 发送文件信息对象
     */
    private void sendFileInfo() throws IOException {
        fileinfoout.writeObject(file);
    }

    /*
     * 传输文件实体信息
     */
    private void sendFiledate() throws IOException {
        byte b[] = new byte[Parameter.FILE_CACHE];//缓冲
        FileInputStream filein = new FileInputStream(file.getAbsolutePath());

        while (true) {
            int length = filein.read(b);
            //tools.print("读入文件数据:"+length+"  ");
            if (length == -1) break;
            filedataout.write(b, 0, length);

        }

        //filedataout.write()

    }

}
