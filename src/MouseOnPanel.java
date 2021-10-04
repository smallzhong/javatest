

import java.awt.Canvas;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseOnPanel implements MouseMotionListener, MouseListener, KeyListener {
    private ClientStatus client = null;//产生的图形事件将发送到该客户端
    private ControlInfo control = null;//控制对象
    Canvas panel = MainFrame.getInstance().getPanel();//画布

    public MouseOnPanel(ClientStatus client) {
        this.client = client;
        control = this.client.getControl();
    }

    /*
     * 生成新坐标的事件]
     * event为源事件
     * panel为画布大小，关系到生成的相对坐标
     * */
    private MouseEvent RefershEvent(MouseEvent event) {
        Point point1 = panel.getLocationOnScreen();//获得画布位置
        double PanelX = point1.getX();//画布的左上角X坐标
        double PanelY = point1.getY();//画布的左上角Y坐标
        double MouseX = PanelX + event.getX();//鼠标X坐标
        double MouseY = PanelY + event.getY();//鼠标Y坐标
        //event.get
//		tools.print("相对位置计算参数：PanelX="+PanelX+" PanelY="+PanelY+" MouseX="+MouseX+
//				" MouseY="+MouseY+" 目标机器width="+client.getScreen_width()+" 目标机器height="+client.getScreen_height()
//				+" panel.getWidth()="+panel.getWidth()+" panel.getHeight()="+panel.getHeight());
        double realX = (double) (client.getScreen_width() * (MouseX - PanelX)) / (double) panel.getWidth(); //目标机器X坐标
        double realY = (double) (client.getScreen_height() * (MouseY - PanelY)) / (double) panel.getHeight(); //目标机器X坐标
        //tools.print("计算实际位置为：realX:"+realX+" realY:"+realY);
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
