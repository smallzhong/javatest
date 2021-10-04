

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/*
 * ����ͼ��
 */
public class ImageProvider {

    private Robot robot = null;//ͼ��ɼ���
    private Rectangle rect = null;//Ҫ���Ƶ� ��Ļ����

    /*
     *
     */
    /*
     * ���캯��������Ҫ�ɼ�����Ļ�� ������Ϣ
     */
    public ImageProvider() throws AWTException {
        rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());//����ȫ��
        robot = new Robot();//����Robot����
    }

    /*
     * ����ȫ��Ļ������BufferedImage����
     */
    public BufferedImage CopyScreen() {
        BufferedImage image = robot.createScreenCapture(rect);//
        return image;
    }


}
