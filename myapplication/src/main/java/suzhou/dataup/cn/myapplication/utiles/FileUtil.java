
package suzhou.dataup.cn.myapplication.utiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

/**
 * 文件操作工具类,包括保存文件等.
 *
 * @version 0.1 20100702
 */
public class FileUtil {

    /**
     * 日志TAG, 便于查找.
     */
    private static final String TAG = "[FileUtil]";
    /**
     * URI类型：file
     */
    public static final String URI_TYPE_FILE = "file";

    /**
     * 保存多媒体数据为文件.
     *
     * @param data     多媒体数据
     * @param fileName 保存文件名
     * @return 保存成功或失败
     */
    public static boolean save2File(byte[] data, String fileName) {
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            // 文件或目录不存在时,创建目录和文件.
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            // 写入数据
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();

            return true;
        } catch (IOException ex) {

            return false;
        }
    }

    /**
     * 保存多媒体数据为文件.
     *
     * @param data     多媒体数据
     * @param fileName 保存文件名
     * @return 保存成功或失败
     */
    public static boolean save2File(InputStream data, String fileName) {
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            // 文件或目录不存在时,创建目录和文件.
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            // 写入数据
            fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len;
            while ((len = data.read(b)) > -1) {
                fos.write(b, 0, len);
            }
            fos.close();

            return true;
        } catch (IOException ex) {

            return false;
        }
    }

    /**
     * 读取文件的字节数组.
     *
     * @param file 文件
     * @return 字节数组
     */
    public static byte[] readFile4Bytes(File file) {
        // 如果文件不存在,返回空
        if (!file.exists()) {
            return null;
        }

        FileInputStream fis = null;
        try {
            // 读取文件内容.
            fis = new FileInputStream(file);
            byte[] arrData = new byte[(int) file.length()];
            fis.read(arrData);

            // 返回
            return arrData;
        } catch (IOException e) {
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 读取文本文件内容，以行的形式读取
     *
     * @param String filePathAndName 带有完整绝对路径的文件名
     * @return String 返回文本文件的内容
     */
    public static String readFileContent(String filePathAndName)
            throws IOException {
        return readFileContent(filePathAndName, null, null, 1024);
    }

    /**
     * 读取文本文件内容，以行的形式读取
     *
     * @param String filePathAndName 带有完整绝对路径的文件名
     * @param String encoding 文本文件打开的编码方式 例如 GBK,UTF-8
     * @return String 返回文本文件的内容
     */
    public static String readFileContent(String filePathAndName, String encoding)
            throws IOException {
        return readFileContent(filePathAndName, encoding, null, 1024);
    }

    /**
     * 读取文本文件内容，以行的形式读取
     *
     * @param String filePathAndName 带有完整绝对路径的文件名
     * @param int    bufLen 设置缓冲区大小
     * @return String 返回文本文件的内容
     */
    public static String readFileContent(String filePathAndName, int bufLen)
            throws IOException {
        return readFileContent(filePathAndName, null, null, bufLen);
    }

    /**
     * 读取文本文件内容，以行的形式读取
     *
     * @param String filePathAndName 带有完整绝对路径的文件名
     * @param String encoding 文本文件打开的编码方式 例如 GBK,UTF-8
     * @param String sep 分隔符 例如：#，默认为空格
     * @return String 返回文本文件的内容
     */
    public static String readFileContent(String filePathAndName,
                                         String encoding, String sep) throws IOException {
        return readFileContent(filePathAndName, encoding, sep, 1024);
    }

    /**
     * 读取文本文件内容，以行的形式读取
     * <p/>
     * bufLen 设置缓冲区大小
     *
     * @return String 返回文本文件的内容
     */
    public static String readFileContent(String filePathAndName,
                                         String encoding, String sep, int bufLen) throws IOException {
        if (filePathAndName == null || filePathAndName.equals("")) {
            return "";
        }
        if (sep == null || sep.equals("")) {
            sep = "\n";
        }
        if (!new File(filePathAndName).exists()) {
            return "";
        }
        StringBuffer str = new StringBuffer("");
        FileInputStream fs = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fs = new FileInputStream(filePathAndName);
            if (encoding == null || encoding.trim().equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding.trim());
            }
            br = new BufferedReader(isr, bufLen);

            String data = "";
            while ((data = br.readLine()) != null) {
                str.append(data).append(sep);
            }

        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (br != null)
                    br.close();
                if (isr != null)
                    isr.close();
                if (fs != null)
                    fs.close();
            } catch (IOException e) {
                throw e;
            }
        }
        return str.toString();
    }

    /**
     * 把Assets里的文件拷贝到sd卡上
     *
     * @param assetManager    AssetManager
     * @param fileName        Asset文件名
     * @param destinationPath 完整目标路径
     * @return 拷贝成功
     */
    public static boolean copyAssetToSDCard(AssetManager assetManager, String fileName, String destinationPath) {
        try {
            InputStream is = assetManager.open(fileName);
            FileOutputStream os = new FileOutputStream(destinationPath);

            if (is != null && os != null) {
                byte[] data = new byte[1024];
                int len;
                while ((len = is.read(data)) > 0) {
                    os.write(data, 0, len);
                }

                os.close();
                is.close();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    /**
     * 调用系统方式打开文件.
     *
     * @param file 文件
     */
    public static void openFile(Context context, File file) {
        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    MimeTypeMap.getFileExtensionFromUrl(file.getPath())));
            context.startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(context, "打开失败.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据文件路径，检查文件是否不大于指定大小
     *
     * @param filepath 文件路径
     * @return
     */
    public static boolean checkFileSize(String filepath, int maxSize) {
        File file = new File(filepath);
        if (!file.exists() || file.isDirectory()) {
            return false;
        }
        return file.length() <= maxSize * 1024;
    }

    public static void openMedia(Context context, File file) {
        if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")) {
            viewPhoto(context, file);
        } else {
            openFile(context, file);
        }
    }

    /**
     * 打开多媒体文件.
     *
     * @param file 多媒体文件
     */
    public static void viewPhoto(Context context, String file) {
        viewPhoto(context, new File(file));
    }

    /**
     * 打开照片
     *
     * @param context
     * @param file
     */
    public static void viewPhoto(Context context, File file) {
        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            context.startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(context, "打开失败.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 将字符串以UTF-8编码保存到文件中
     *
     * @param str
     * @param fileName
     * @return
     */
    public static boolean saveStrToFile(String str, String fileName) {
        return saveStrToFile(str, fileName, "UTF-8");
    }

    /**
     * 将字符串以charsetName编码保存到文件中
     *
     * @param str
     * @param fileName
     * @return
     */
    public static boolean saveStrToFile(String str, String fileName, String charsetName) {
        if (str == null || "".equals(str)) {
            return false;
        }

        FileOutputStream stream = null;
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            byte[] b = null;
            if (charsetName != null && !"".equals(charsetName)) {
                b = str.getBytes(charsetName);
            } else {
                b = str.getBytes();
            }

            stream = new FileOutputStream(file);
            stream.write(b, 0, b.length);
            stream.flush();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                    stream = null;
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 将content://形式的uri转为实际文件路径
     *
     * @param uri
     * @return
     */
    public static String uriToPath(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            if (uri.getScheme().equalsIgnoreCase(URI_TYPE_FILE)) {
                return uri.getPath();
            }
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)); //图片文件路径
            }
        } catch (Exception e) {
            if (null != cursor) {
                cursor.close();
                cursor = null;
            }
            return null;
        }
        return null;
    }

    /**
     * 打开多媒体文件.
     *
     * @param file 多媒体文件
     */
    public static void playSound(Context context, String file) {
        playSound(context, new File(file));
    }

    /**
     * 打开多媒体文件.
     *
     * @param file 多媒体文件
     */
    public static void playSound(Context context, File file) {
        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			intent.setClassName("com.android.music", "com.android.music.MediaPlaybackActivity");
            intent.setDataAndType(Uri.fromFile(file), "audio/*");
            context.startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(context, "打开失败.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开视频文件.
     *
     * @param file 视频文件
     */
    public static void playVideo(Context context, String file) {
        playVideo(context, new File(file));
    }

    /**
     * 打开视频文件.
     *
     * @param file 视频文件
     */
    public static void playVideo(Context context, File file) {
        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "video/*");
            context.startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(context, "打开失败.", Toast.LENGTH_SHORT).show();
        }
    }
}