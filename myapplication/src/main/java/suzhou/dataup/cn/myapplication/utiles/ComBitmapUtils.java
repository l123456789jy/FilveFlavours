package suzhou.dataup.cn.myapplication.utiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ComBitmapUtils {

    // 压缩图片尺寸
    public static Bitmap compressBySize(String pathName, int targetWidth, int targetHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
        // 得到图片的宽度、高度；
        float imgWidth = opts.outWidth;
        float imgHeight = opts.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
        opts.inSampleSize = 1;
        String dateTime = null;
        ExifInterface exifInterface = null;
        try {
            // android读取图片EXIF信息
            exifInterface = new ExifInterface(pathName);
            dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            if (TextUtils.isEmpty(dateTime)) {
                dateTime = PublicUtil.getNowTime();
            } else {
                int index = dateTime.indexOf(" ");
                dateTime = dateTime.substring(0, index + 1).replaceAll(":", "-")
                        + dateTime.substring(index + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (widthRatio > 1 || widthRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }
        // 设置好缩放比例后，加载图片进内容；
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(pathName, opts);
        //图片旋转问题
        // 若图片旋转,则转回适应角度
        bitmap = ImageUtil.rotateBitmap(360 - ImageUtil.getPhotoOrientation(new File(pathName)), bitmap);
        return bitmap;
    }

    // 存储进SD卡

    public static void saveFile(Bitmap bm, String fileName, boolean IsJpg)
            throws Exception {
        File dirFile = new File(fileName);
        // 检测图片是否存在
        if (dirFile.exists()) {
            dirFile.delete(); // 删除原图片
        }
        File myCaptureFile = new File(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(myCaptureFile));
        if (IsJpg) {
            // 100表示不进行压缩，70表示压缩率为30%
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bos);
        } else {
            // 100表示不进行压缩，70表示压缩率为30%
            bm.compress(Bitmap.CompressFormat.PNG, 90, bos);
        }
        bos.flush();
        bos.close();
    }

    /**
     * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

}
