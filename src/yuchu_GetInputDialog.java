
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class yuchu_GetInputDialog extends JDialog implements KeyListener {
    JTextField dirPath = null;
    JButton okButton = null;

    // ���õ�¼���˺ź�����
//    String USERNAME = "�����";
//    String PASSWORD = "123456";

    public yuchu_GetInputDialog(JFrame owner, String my_title) {
        super(owner, my_title);
        this.setSize(300, 100);

        // ���Ӱ�����׽
        addKeyListener(this);

        add(new JLabel("����"));
        dirPath = new JTextField(7);
        add(dirPath);

        // ��������������ʱ����enter����Ϊ���˵�¼��
        dirPath.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
//                if ((e.getKeyCode() == KeyEvent.VK_ENTER)) {
//                    setVisible(false);
//                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        JButton okButton = new JButton("��¼");
        add(okButton);

        // ����Ϊ��ʽ����
        this.setLayout(new FlowLayout());

//        this.setLocation(500, 500);
        // ���þ�����ʾ
        this.setLocationRelativeTo(null);

        okButton.addActionListener(e ->
        {
//            ServerMessageShow.showMessage(getInput(), getInput(), JOptionPane.INFORMATION_MESSAGE);
            try {
                // TODO: ����ʶ������·��
                if (yuchu_checkPathAvailable.checkPathValid(getInput(), "windows")) {
//                    SOrderExcute.GetFileList(MainFrame.getInstance().getClient(), getInput());
                    SOrderExcute.GetAllFile(MainFrame.getInstance().getClient(), getInput());
                }
                else {
                    ServerMessageShow.showWARNING("�����·�����Ϸ���", "�����·�����Ϸ���");
                }
                setVisible(false);
            } catch (MyException ex) {
                ex.printStackTrace();
            }
            setVisible(false);
        });

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    public String getInput() {
        return dirPath.getText();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
