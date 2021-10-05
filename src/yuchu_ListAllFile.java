
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
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();//���������ѡ����ļ��е�·��

            tools.print("filePath = " + filePath);
            return filePath;
        } else {
            return null;
        }
    }

    public static Vector<String> getAllFile(String filePath) {
        // String path = "D:\\JAVA";        //Ҫ������·��
        File file = new File(filePath);        //��ȡ��file����
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
                if (f.isDirectory())    //����Ŀ¼����ݹ��ӡ��Ŀ¼�µ��ļ�
                {
                    Vector<String> t = func(f);
                    v.addAll(t);
                }
                if (f.isFile())        //�����ļ���ֱ�Ӵ�ӡ
                {
                    v.add(f.getPath());
                }
            }
        } catch (Exception e) {
            ClientMessageShow.showMessage("����", "Ŀ¼�����ڣ�", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Ŀ¼������");
        }
        return v;
    }
}
