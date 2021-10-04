

import java.awt.Canvas;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseOnPanel implements MouseMotionListener, MouseListener, KeyListener {
    private ClientStatus client = null;//������ͼ���¼������͵��ÿͻ���
    private ControlInfo control = null;//���ƶ���
    Canvas panel = MainFrame.getInstance().getPanel();//����

    public MouseOnPanel(ClientStatus client) {
        this.client = client;
        control = this.client.getControl();
    }

    /*
     * ������������¼�]
     * eventΪԴ�¼�
     * panelΪ������С����ϵ�����ɵ��������
     * */
    private MouseEvent RefershEvent(MouseEvent event) {
        Point point1 = panel.getLocationOnScreen();//��û���λ��
        double PanelX = point1.getX();//���������Ͻ�X����
        double PanelY = point1.getY();//���������Ͻ�Y����
        double MouseX = PanelX + event.getX();//���X����
        double MouseY = PanelY + event.getY();//���Y����
        //event.get
//		tools.print("���λ�ü��������PanelX="+PanelX+" PanelY="+PanelY+" MouseX="+MouseX+
//				" MouseY="+MouseY+" Ŀ�����width="+client.getScreen_width()+" Ŀ�����height="+client.getScreen_height()
//				+" panel.getWidth()="+panel.getWidth()+" panel.getHeight()="+panel.getHeight());
        double realX = (double) (client.getScreen_width() * (MouseX - PanelX)) / (double) panel.getWidth(); //Ŀ�����X����
        double realY = (double) (client.getScreen_height() * (MouseY - PanelY)) / (double) panel.getHeight(); //Ŀ�����X����
        //tools.print("����ʵ��λ��Ϊ��realX:"+realX+" realY:"+realY);
        MouseEvent realMouse = new MouseEvent(event.getComponent(), event.getID(), event.getWhen(), event.getModifiers(),
                (int) realX, (int) realY, event.getClickCount(), false);
        return realMouse;
    }


    public void mouseDragged(MouseEvent arg0) {
        //tools.print("mouseDragged"+" arg0:"+arg0.getX()+" "+arg0.getY()+" whichbutton="+arg0.getButton());

    }

    public void mouseMoved(MouseEvent arg0) {
        //tools.print("mouseMoved"+" arg0:"+arg0.getX()+" "+arg0.getY());
//		tools.print("control==null>?"+(control==null));

        control.sendMyAction(RefershEvent(arg0));
    }

    public void mouseClicked(MouseEvent arg0) {
        //tools.print("mouseClicked"+" arg0:"+arg0.getX()+" "+arg0.getY()+" whichbutton="+arg0.getButton()+" clickcount="+arg0.getClickCount());
        //MouseEvent sendevent=new MouseEvent();
        control.sendMyAction(RefershEvent(arg0));
    }

    public void mousePressed(MouseEvent arg0) {
        tools.print("mousePressed" + " arg0:" + arg0.getX() + " " + arg0.getY());
        control.sendMyAction(RefershEvent(arg0));
    }

    public void mouseReleased(MouseEvent arg0) {
        //tools.print("mouseReleased"+" arg0:"+arg0.getX()+" "+arg0.getY());
        control.sendMyAction(RefershEvent(arg0));
    }

    public void mouseEntered(MouseEvent arg0) {
        //tools.print("mouseEntered"+" arg0:"+arg0.getX()+" "+arg0.getY());

    }

    public void mouseExited(MouseEvent arg0) {
        //tools.print("mouseExited"+" arg0:"+arg0.getX()+" "+arg0.getY());

    }

    public void keyTyped(KeyEvent arg0) {
//		tools.print("presskey"+arg0.getKeyCode());
//		control.sendMyAction(arg0);
    }

    public void keyPressed(KeyEvent arg0) {
        //tools.print("keyPressed"+arg0.getKeyCode());
        control.sendMyAction(arg0);
    }

    public void keyReleased(KeyEvent arg0) {
        //tools.print("keyReleased"+arg0.getKeyCode());
        control.sendMyAction(arg0);
    }

    public ClientStatus getClient() {
        return client;
    }

    public void setClient(ClientStatus client) {
        this.client = client;
    }

    public ControlInfo getControl() {
        return control;
    }

    public void setControl(ControlInfo control) {
        this.control = control;
    }


}
