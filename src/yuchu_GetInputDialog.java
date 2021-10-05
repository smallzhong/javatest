
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class yuchu_GetInputDialog extends JDialog implements KeyListener {
    JTextField pwd = null;
    JButton okButton = null;

    // 设置登录的账号和密码
//    String USERNAME = "钟雨初";
//    String PASSWORD = "123456";

    public yuchu_GetInputDialog(JFrame owner, String my_title) {
        super(owner, my_title);
        this.setSize(300, 100);

        // 增加按键捕捉
        addKeyListener(this);

        add(new JLabel("密码"));
        pwd = new JTextField(7);
        add(pwd);

        // 如果在输入密码的时候按了enter则视为按了登录键
        pwd.addKeyListener(new KeyListener() {
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

        JButton okButton = new JButton("登录");
        add(okButton);

        // 设置为流式布局
        this.setLayout(new FlowLayout());

//        this.setLocation(500, 500);
        // 设置居中显示
        this.setLocationRelativeTo(null);

        okButton.addActionListener(e ->
        {
            ServerMessageShow.showMessage(getInput(), getInput(), JOptionPane.INFORMATION_MESSAGE);
            try {
                SOrderExcute.GetFileList(MainFrame.getInstance().getClient());
            } catch (MyException ex) {
                ex.printStackTrace();
            }
            setVisible(false);
        });

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    public String getInput()
    {
        return pwd.getText();
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
