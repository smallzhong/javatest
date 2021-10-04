import java.io.File;

public class Parameter {
    //客户将会开启其中任何一个端口
	public static final int ORDER_UDP_PORT[]={2225,3225,4225};//UDP命令端口,3个选一个吧
	public static final int OrderExcuteNums=3;//命令接收线程数，越多，命令响应越快
   
	//
	public static final int TCP_TIME_OUT=15000;//套接字超时时间
	public static final char ORDER_SEPRATER=':';//命令名和值分隔符

	//
	public static final int IMAGE_CACHE=1024*5;//接收图象缓冲大小
	public static final int FILE_CACHE=1024;//文件发送，接收缓存
	public static final int EVENT_CACHE=1024*5;//事件发送，接收缓存大小
	
	public static final int IMAGE_get_time=1000;//采集图象时间间隔
	private  static  String UPFILE_PATH="C://abc"+File.separator;//文件上传默认保存路径
	
	public static final int FILE_NOT_EXITS=1;//文件不存在
	public static final int FILE_EXITS=3;//文件存在
	public static final int IS_DOCTIONRY=2;//是文件夹
	
	public static final int FILE_DOWN=1;//文件下载
	public static final int FILE_UP=2;//文件上传
	
	public static String getUPFILE_PATH() {
		return UPFILE_PATH;
	}
	public static void setUPFILE_PATH(String upfile_path) {
		UPFILE_PATH = upfile_path;
	}
	
	
}
