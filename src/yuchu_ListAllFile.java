
import com.sun.deploy.uitoolkit.ToolkitStore;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;
import java.util.Vector;

public class yuchu_ListAllFile {

    public static String getFilePath() {
        JFileChooser fileChooser = new JFileChooser("f:\\");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(fileChooser);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();//这个就是你选择的文件夹的路径

            tools.print("filePath = " + filePath);
            return filePath;
        } else {
            return null;
        }
    }

    public static Vector<String> getAllFile(String filePath) {
        // String path = "D:\\JAVA";        //要遍历的路径
        File file = new File(filePath);        //获取其file对象
        Vector<String> v = func(file);

//        for (int i = 0; i < v.size(); i++) {
//            v.set(i, v.get(i).substring(filePath.length() + 1));
//        }
        return v;
    }

    private static Vector<String> func(File file) {
        Vector<String> v = new Vector<String>();

        File[] fs = file.listFiles();
        try {
            assert fs != null;
            for (File f : fs) {
                if (f.isDirectory())    //若是目录，则递归打印该目录下的文件
                {
                    Vector<String> t = func(f);
                    v.addAll(t);
                }
                if (f.isFile())        //若是文件，直接打印
                {
                    v.add(f.getPath());
                }
            }
        } catch (Exception e) {
            ClientMessageShow.showMessage("错误", "目录不存在！", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("目录不存在");
        }
        return v;
    }
}
