import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/*
 * DOS����ִ����
 */
public class DOSExcuter {

    public static void excuteDosOrderInputstream(String order, BufferedOutputStream out) throws IOException {
        Runtime run = Runtime.getRuntime();
        // Process proc=run.exec("java -version");
        // Process proc=run.exec("netstat -a");
        // Process proc=run.exec("ping www.163.com");
        Process proc = run.exec(order);
        BufferedInputStream in = new BufferedInputStream(proc.getInputStream());
        byte b[] = new byte[1024];//����
        int m = 0;
        String s = null;
        while ((m = in.read(b)) != -1) {
            s = new String(b, 0, m);
            System.out.println(s);
            out.write(b, 0, m);//д����
        }

    }

    public static void excuteDosOrderERRORstream(String order, BufferedOutputStream out) throws IOException {
        Runtime run = Runtime.getRuntime();
        // Process proc=run.exec("java -version");
        // Process proc=run.exec("netstat -a");
        // Process proc=run.exec("ping www.163.com");
        Process proc = run.exec(order);
        BufferedInputStream in = new BufferedInputStream(proc.getErrorStream());
        byte b[] = new byte[1024];//����
        int m = 0;
        String s = null;
        while ((m = in.read(b)) != -1) {
            s = new String(b, 0, m);
            System.out.println(s);
            out.write(b, 0, m);//д����
        }

    }

    public static void main(String ss[]) throws IOException {
        BufferedOutputStream out = null;
        excuteDosOrderInputstream("set", out);


    }


}
