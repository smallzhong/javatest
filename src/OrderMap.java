
/*
 * ��������
 */
public class OrderMap {
    public static final String INIT_STATUS="youstatus";//ѯ�ʱ����Ӷ˿����Ķ˿ں�,״̬����Ϣ
    public static final String SCREEN_SHOW="screen";//����ͼ�����׽���
    public static final String SCREEN_CONTROL="control";//���������׽�
    public static final String FILE_UP="fileup";//�ļ��ϴ�
    public static final String FILE_EXITS="fileexits";//ѯ���ļ��Ƿ����
    public static final String FILE_DOWN="filedown";//�����ļ�,�����ʽΪfiledown?123?c://www/abc/test.txt
                                                    //123Ϊ�˿ںţ�c://www/abc/test.txtΪ�ļ�·����
    public static final String DOS_ORDER_shutdown="dos:shutdown -s";//�ػ�
    public static final String DOS_ORDER_restart="dos:shutdown -t 1";//����
    public static final String DOS_ORDER="dos";//DOS�����ʽΪdos?123?ping www.163.com
                                               //123Ϊ�˿ںţ�ping www.163.comΪ��������
    public static final String YC_ORDER = "yc";
    
    public static final String MESSAGE="message";//��Ϣ
    
    //public static final String DOS_ORDER_shutdowm="dos:shutdown -s";//�ػ�
    //public static final String DOS_ORDER_shutdowm="dos:shutdown -s";//�ػ�
    
    
	
	/*
	 * ������name:valueΪ��ʽ
	 */
    public static String toOrder(String name,String value){
		    return name+":"+value;
		
	}
    public static String toOrder(String name,int value){
	    return name+":"+value;
	
}
}
