package langteng.com.redpocketmoney.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lang on 16/11/18.
 */
public class FileRename {

    public static void main(String[] args) throws IOException {
        //文件绝对路径改成你自己的文件路径
        FileReader fr = new FileReader("/Users/lang/Desktop/imgs/127/蒙逼态.txt");
        FileWriter fw = new FileWriter("/Users/lang/Desktop/imgs/127/蒙逼态1.txt");
        //可以换成工程目录下的其他文本文件
        BufferedReader br = new BufferedReader(fr);
        BufferedWriter bw = new BufferedWriter(fw);
        int i = 1;
        String s;
        while ((s = br.readLine()) != null) {  //   生成渠道配置文件
            bw.write("UMENG_CHANNEL " + s + " " + i + "\n");
            System.out.println("-----channel-- : " + "UMENG_CHANNEL " + s + " " + i );
            i++;
        }
        br.close();
        bw.close();

        File file = new File("F:\\apps");
        String dirPath = file.getAbsolutePath();//目录路径
        if (file.isDirectory()) {
            File[] files = file.listFiles();//获取此目录下的文件列表
            long starttime = System.currentTimeMillis();
            for (File fileFrom : files) {
                String fromFile = fileFrom.getName();//文件名
                StringBuffer toFileName = new StringBuffer();
                fromFile = fromFile.replace("_sign", "");
                System.out.println("--------fromFile--" + fromFile.split("_310_UMENG_CHANNEL_")[1]);
                String s1 = fromFile.split("_310_UMENG_CHANNEL_")[1].substring(0,
                        fromFile.split("_310_UMENG_CHANNEL_")[1].lastIndexOf("_"));
                toFileName.append(dirPath + "\\");
                toFileName.append("mrsp_");
                toFileName.append(s1);
                toFileName.append("_3.1.0.apk");
                {
                    File toFile = new File(toFileName.toString());
                    if (fileFrom.exists() && !toFile.exists()) {
                        fileFrom.renameTo(toFile);
                    }
                }

            }
            long endtime = System.currentTimeMillis();
            System.out.println("Time:" + new Long(endtime - starttime));//耗时
        }
    }


}


