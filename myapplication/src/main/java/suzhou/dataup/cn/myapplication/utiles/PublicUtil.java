/**
 * Copyright (c) 2002-2014 AutoNavi, Inc. All rights reserved.
 * <p/>
 * This software is the confidential and proprietary information of AutoNavi,
 * Inc. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with AutoNavi.
 */
package suzhou.dataup.cn.myapplication.utiles;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 公共帮助类
 *
 * @author wang.yuchao
 * @since 2014-10-31
 */
public class PublicUtil {
    private static final String TAG = "[PublicUtil]";


    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static String getNowTime() {
        Date date = new Date();
        long iNowTime = date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(iNowTime);
    }


    public static void clearBitmapsAndmFiles(ArrayList<Bitmap> bitmaps,
                                             ArrayList<String> mfiles) {
        if (bitmaps != null) {
            for (Bitmap bitmap : bitmaps) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                }
            }
            bitmaps.clear();
            mfiles.clear();
        }
    }

    /**
     * 根据文件名称得到文件的时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String getPhotoTime(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            String strTime = exifInterface
                    .getAttribute(ExifInterface.TAG_DATETIME);
            if (!TextUtils.isEmpty(strTime)) {
                Date date = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss")
                        .parse(strTime);
                strTime = sdf.format(date);
                return strTime;// 如果图片有时间
            }
        } catch (Exception e) {
            e.printStackTrace();
            return sdf.format(new Date());// 如果图片没有时间
        }
        return sdf.format(new Date());// 如果图片没有时间
    }


    /**
     * 删除第四张图片
     */
    public static void delelteFileAndBitmap(Bitmap bitmap3, String fileName3) {
        if (bitmap3 != null && !bitmap3.isRecycled()) {
            bitmap3.recycle();
            bitmap3 = null;
        }
        if (!TextUtils.isEmpty(fileName3)) {
            File file = new File(fileName3);
            if (file != null && file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 删除并清空图片以及文件路径
     */
    public static void deleteAndClearStation(ArrayList<Bitmap> bitmaps,
                                             ArrayList<String> mfiles, Bitmap bitmap3, String fileName3,
                                             boolean isdel) {
        if (isdel) {
            for (String filename : mfiles) {
                if (!TextUtils.isEmpty(filename))
                    new File(filename).delete();
            }
            if (!TextUtils.isEmpty(fileName3)) {
                new File(fileName3).delete();
            }
        } else {

        }
        if (bitmaps != null) {
            if (bitmaps.size() > 0) {
                for (Bitmap bitmap : bitmaps) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                }
                bitmaps.clear();
                mfiles.clear();
            }
        }
        if (bitmap3 != null && !bitmap3.isRecycled()) {
            bitmap3.recycle();
            bitmap3 = null;
        }
        fileName3 = null;
    }

    private static long lastClickTime;

    /**
     * 判断是否为连击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void recyleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    public static void recyleBitmaps(ArrayList<Bitmap> bitmaps) {
        if (bitmaps != null) {
            for (Bitmap bitmap : bitmaps) {
                recyleBitmap(bitmap);
            }
            bitmaps.clear();
        }
    }

    public static void deleteFile(String filename) {
        if (!TextUtils.isEmpty(filename)) {
            File file = new File(filename);
            if (file != null && file.exists()) {
                file.delete();
            }
        }
    }

    public static void deleteFiles(ArrayList<String> mfiles) {
        if (mfiles != null) {
            for (int i = 0; i < mfiles.size(); i++) {
                if (!TextUtils.isEmpty(mfiles.get(i))) {
                    File file = new File(mfiles.get(i));
                    if (file != null && file.exists()) {
                        file.delete();
                    }
                }
            }
        }
    }

    public static void onTaskEixt(ArrayList<Bitmap> smallBitmaps,
                                  Bitmap smallBitmap3, ArrayList<String> mfiles, String fileName3) {
        PublicUtil.recyleBitmaps(smallBitmaps);
        PublicUtil.recyleBitmap(smallBitmap3);
        PublicUtil.deleteFiles(mfiles);
        PublicUtil.deleteFile(fileName3);
        if (mfiles != null) {
            mfiles.clear();
        }
        if (fileName3 != null) {
            fileName3 = null;
        }
    }

    public static void onTaskAdvance(ArrayList<Bitmap> smallBitmaps,
                                     Bitmap smallBitmap3, ArrayList<String> mfiles, String fileName3) {
        PublicUtil.recyleBitmaps(smallBitmaps);
        PublicUtil.recyleBitmap(smallBitmap3);
        if (mfiles != null) {
            mfiles.clear();
        }
        if (fileName3 != null) {
            fileName3 = null;
        }
    }


}
