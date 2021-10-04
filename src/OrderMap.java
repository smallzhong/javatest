
/*
 * 所有命令
 */
public class OrderMap {
    public static final String INIT_STATUS="youstatus";//询问被监视端开启的端口号,状态等信息
    public static final String SCREEN_SHOW="screen";//建立图象发送套接字
    public static final String SCREEN_CONTROL="control";//建立控制套接
    public static final String FILE_UP="fileup";//文件上传
    public static final String FILE_EXITS="fileexits";//询问文件是否存在
    public static final String FILE_DOWN="filedown";//下载文件,命令格式为filedown?123?c://www/abc/test.txt
                                                    //123为端口号，c://www/abc/test.txt为文件路径名
    public static final String DOS_ORDER_shutdown="dos:shutdown -s";//关机
    public static final String DOS_ORDER_restart="dos:shutdown -t 1";//重启
    public static final String DOS_ORDER="dos";//DOS命令格式为dos?123?ping www.163.com
                                               //123为端口号，ping www.163.com为命令名字
    
    public static final String MESSAGE="message";//消息
    
    //public static final String DOS_ORDER_shutdowm="dos:shutdown -s";//关机
    //public static final String DOS_ORDER_shutdowm="dos:shutdown -s";//关机
    
    
	
	/*
	 * 命令以name:value为格式
	 */
    public static String toOrder(String name,String value){
		    return name+":"+value;
		
	}
    public static String toOrder(String name,int value){
	    return name+":"+value;
	
}
}
