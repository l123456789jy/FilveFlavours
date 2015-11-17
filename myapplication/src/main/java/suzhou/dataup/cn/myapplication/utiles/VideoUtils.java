package suzhou.dataup.cn.myapplication.utiles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

public class VideoUtils {

    public static final String VIDEO_FOLDER_NAME = "formal";
    public static final String VIDEOTEMP_FOLDER_NAME = "temp";
    public static final String THUMBNAIL_FOLDER_NAME = "thumbnail";
    public static final int THUMBNAIL_WIDTH = 96;
    public static final int THUMBNAIL_HEIGHT = 96;


    /**
     * 获取视频文件缓存file
     *
     * @param recordFilePath
     * @return
     * @throws IOException
     */
    public static File getTempRecordVideoFile(String recordFilePath) throws IOException {
        return File.createTempFile("Vedio", ".3gp", new File(recordFilePath));
    }


    /**
     * 用以计时操作的相关方法
     *
     * @param num
     * @return
     */
    public static String format(int num) {

        String s = num + "";
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;
    }


    /**
     * 播放视频
     *
     * @param context
     * @param videoPath
     */
    public static void playVideo(Context context, String videoPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String strend = "";
        if (videoPath.toLowerCase().endsWith(".mp4")) {
            strend = "mp4";
        } else if (videoPath.toLowerCase().endsWith(".3gp")) {
            strend = "3gp";
        } else if (videoPath.toLowerCase().endsWith(".mov")) {
            strend = "mov";
        } else if (videoPath.toLowerCase().endsWith(".wmv")) {
            strend = "wmv";
        }

        intent.setDataAndType(Uri.parse(videoPath), "video/" + strend);
        context.startActivity(intent);
    }

    /**
     * 生成视频缩略图文件并返回路?
     *
     * @param videoPath
     */
    public static Bitmap getVideoThubnailFilePath(String videoPath) {
        Bitmap bitmap = ComBitmapUtils.getVideoThumbnail(videoPath, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, MediaStore.Images.Thumbnails.MICRO_KIND);
        return bitmap;
    }

}
