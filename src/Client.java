

import javax.swing.JOptionPane;

/*
 * 运行在被监视端
 */
public class Client {
    private static Client client = new Client();
    private ClientOrderReceiver oRC = null;//命令接收者
    private ClientMessageShow show = null;//消息提示

    private Client() {
        try {
            oRC = new ClientOrderReceiver();//开启UDP端口

        } catch (MyException e) {
            show.showWARNING("错误", e.toString());
        }//建立命令接收对象

    }

    public static Client getInstance() {
        return client;

    }

    public static void main(String ss[]) {
        System.out.println("test");
    }


    /////////////////////////////////////////////////
    public ClientOrderReceiver getORC() {
        return oRC;
    }

    public void setORC(ClientOrderReceiver orc) {
        oRC = orc;
    }
}
