/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tuanche.api.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tuanche.api.bitmap.callback.BitmapSetter;
import com.tuanche.api.bitmap.core.BitmapSize;
import com.tuanche.api.utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;

public class BitmapCommonUtils {

    /**
     * @param context
     * @param dirName Only the folder name, not contain full path.
     * @return app_cache_path/dirName
     */
    public static String getDiskCacheDir(Context context, String dirName) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ?
                context.getExternalCacheDir().getPath() : context.getCacheDir().getPath();

        return cachePath + File.separator + dirName;
    }

    public static long getAvailableSpace(File dir) {
        try {
            final StatFs stats = new StatFs(dir.getPath());
            return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
        } catch (Throwable e) {
            LogUtils.e(e.getMessage(), e);
            return -1;
        }

    }

    private static BitmapSize screenSize = null;

    /**
     * 	获取手机屏幕参数（像素）
     * @param context
     * @return
     */
    public static BitmapSize getScreenSize(Context context) {
        if (screenSize == null) {
            screenSize = new BitmapSize();
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            screenSize.setWidth(displayMetrics.widthPixels);
            screenSize.setHeight(displayMetrics.heightPixels);
        }
        return screenSize;
    }

    /**
     * @param view				图形类控件
     * @param maxImageWidth		图片宽
     * @param maxImageHeight	图片高
     * @return	如果maxImageWidth和maxImageWidth大于0，此BitmapSize已配置，直接返回。
     * 			否则，根据所传控件设置BitmapSize，并返回该对象
     * 
     */
    public static BitmapSize optimizeMaxSizeByView(View view, int maxImageWidth, int maxImageHeight) {
        int width = maxImageWidth;
        int height = maxImageHeight;

        if (width > 0 && height > 0) {
            return new BitmapSize(width, height);
        }

        final ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            if (params.width > 0) {
                width = params.width;
            } else if (params.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
                width = view.getWidth();
            }

            if (params.height > 0) {
                height = params.height;
            } else if (params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                height = view.getHeight();
            }
        }

        if (width <= 0) width = getFieldValue(view, "mMaxWidth");
        if (height <= 0) height = getFieldValue(view, "mMaxHeight");

        BitmapSize screenSize = getScreenSize(view.getContext());
        if (width <= 0) width = screenSize.getWidth();
        if (height <= 0) height = screenSize.getHeight();

        return new BitmapSize(width, height);
    }

    private static int getFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Throwable e) {
        }
        return value;
    }

    public static final ImageViewSetter sDefaultImageViewSetter = new ImageViewSetter();

    public static class ImageViewSetter implements BitmapSetter<ImageView> {

        @Override
        public void setBitmap(ImageView container, Bitmap bitmap) {
            container.setImageBitmap(bitmap);
        }

        @Override
        public void setDrawable(ImageView container, Drawable drawable) {
            container.setImageDrawable(drawable);
        }

        @Override
        public Drawable getDrawable(ImageView container) {
            return container.getDrawable();
        }
    }
    /**
     * 将bitmap对象转换成byte数组
     * @param bmp
     * @param needRecycle
     * @return
     */
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
