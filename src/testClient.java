


public class testClient {
    public static void main(String ss[]) throws Exception {

        Client.getInstance();
        try {
            new autostart("�����Ӷ�.jar").set_it_autostart();//���Լ���Ϊ������,,����Ҫ�̶�
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//��������
    }
}
