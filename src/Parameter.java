import java.io.File;

public class Parameter {
    //�ͻ����Ὺ�������κ�һ���˿�
	public static final int ORDER_UDP_PORT[]={2225,3225,4225};//UDP����˿�,3��ѡһ����
	public static final int OrderExcuteNums=3;//��������߳�����Խ�࣬������ӦԽ��
   
	//
	public static final int TCP_TIME_OUT=15000;//�׽��ֳ�ʱʱ��
	public static final char ORDER_SEPRATER=':';//��������ֵ�ָ���

	//
	public static final int IMAGE_CACHE=1024*5;//����ͼ�󻺳��С
	public static final int FILE_CACHE=1024;//�ļ����ͣ����ջ���
	public static final int EVENT_CACHE=1024*5;//�¼����ͣ����ջ����С
	
	public static final int IMAGE_get_time=1000;//�ɼ�ͼ��ʱ����
	private  static  String UPFILE_PATH="C://abc"+File.separator;//�ļ��ϴ�Ĭ�ϱ���·��
	
	public static final int FILE_NOT_EXITS=1;//�ļ�������
	public static final int FILE_EXITS=3;//�ļ�����
	public static final int IS_DOCTIONRY=2;//���ļ���
	
	public static final int FILE_DOWN=1;//�ļ�����
	public static final int FILE_UP=2;//�ļ��ϴ�
	
	public static String getUPFILE_PATH() {
		return UPFILE_PATH;
	}
	public static void setUPFILE_PATH(String upfile_path) {
		UPFILE_PATH = upfile_path;
	}
	
	
}
