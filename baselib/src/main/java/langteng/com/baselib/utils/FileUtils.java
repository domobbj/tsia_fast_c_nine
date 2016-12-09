package langteng.com.baselib.utils;


import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import langteng.com.baselib.GlobalApplication;

/***
 * 这里用一句话描述这个类的作用.
 */
public class FileUtils {

    public static final String APP_DIR = Environment.getExternalStorageDirectory()
            + File.separator + "dianyingba" + File.separator;
    public static final String meinvs = APP_DIR + "meinvs/";

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }


    public static void saveBitmap(String picName, Bitmap bm) {
        File meiziFile = new File(meinvs, picName);
        if (!meiziFile.exists()) {
            meiziFile.mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(meiziFile);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void saveMyBitmap(String bitName, Bitmap mBitmap) throws IOException {
        File f = new File(meinvs + bitName + ".png");
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized static void fileAppend1(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 追加文件：使用FileWriter
     *
     * @param fileName
     * @param content
     */
    public synchronized static boolean fileAppend2(String fileName, String content) {
        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
            writer = new FileWriter(fileName, true);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /*****
     * 读取 assets  文件
     ***/
    public static String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    GlobalApplication.getInstance().getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /*****
     * 读取 assets  文件
     ***/
    public static List<String> getImgsAssets(String fileName) {
        List<String> imgs = new ArrayList<>();
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    GlobalApplication.getInstance().getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                if (!StringUtil.isEmpty(line)) {
                    imgs.add(line);
                }
            }
            return imgs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgs;
    }


    /**
     * 追加文件：使用RandomAccessFile
     *
     * @param fileName 文件名
     * @param content  追加的内容
     */
    public synchronized static void fileAppend3(String fileName, String content) {
        RandomAccessFile randomFile = null;
        try {
            // 打开一个随机访问文件流，按读写方式     
            randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数     
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。     
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //删除文件
    public static void delFile(String filePathAndName) {
        File file = new File(filePathAndName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(String sdPath) {
        File dir = new File(sdPath);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(sdPath); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }


}
