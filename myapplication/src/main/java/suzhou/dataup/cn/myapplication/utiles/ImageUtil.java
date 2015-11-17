package suzhou.dataup.cn.myapplication.utiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageUtil {

    /**
     * 旋转图片
     *
     * @param orientation
     * @param bitmap
     * @return
     */
    public static Bitmap rotateBitmap(int orientation, Bitmap bitmap) {
        if (bitmap == null) return null;
        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.setRotate(0 - orientation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                    matrix, true);

        }
        return bitmap;
    }

    public static int getPhotoOrientation(File file) {
        if (file == null || file.getAbsolutePath().equals(""))
            return 0;

        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(file.getAbsolutePath());
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        int attr = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
        int orientation = 0;
        if (attr == ExifInterface.ORIENTATION_ROTATE_90) {
            orientation = 90;
        } else if (attr == ExifInterface.ORIENTATION_ROTATE_180) {
            orientation = 180;
        } else if (attr == ExifInterface.ORIENTATION_ROTATE_270) {
            orientation = 270;
        }

        return orientation;
    }

    /**
     * 存储bitmap变成jpg 保存图片到SDCARD 属于耗时操作 须放到子线程中执行
     **/
    public static void saveBitmapAsync(final String imagePath, final Bitmap mBitmap, final ExifInterface exifInterface) {
        new Thread() {
            public void run() {
                try {
                    File f = new File(imagePath);
                    File parentFile = f.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    f.createNewFile();
                    FileOutputStream fos = new FileOutputStream(imagePath);
                    if (mBitmap != null && !mBitmap.isRecycled()) {
                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    }
                    PublicUtil.recyleBitmap(mBitmap);

                    ExifInterface exif = new ExifInterface(imagePath);
                    exif = exifInterface;
                    exif.saveAttributes();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }.start();
    }


    /**
     * 删除图片，同时将imageview置为默认图片
     */
    public static void deletePhoto(Context context, ImageView iv, int defaultImage, int defaultBg, String filePath) {
        deletePhoto(context, iv, defaultImage, defaultBg, filePath, true);
    }

    /**
     * 删除图片，同时将imageview置为默认图片
     */
    public static void deletePhoto(Context context, ImageView iv, int defaultImage, int defaultBg, String filePath, boolean isDeleteFile) {
        if (defaultImage > 0) {
            iv.setImageResource(defaultImage);
        }

        if (defaultBg > 0) {
            iv.setBackgroundResource(defaultBg);
        }
        if (isDeleteFile) {
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {

            }
        }
    }
}
