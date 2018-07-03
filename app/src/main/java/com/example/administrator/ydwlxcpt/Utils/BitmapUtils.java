package com.example.administrator.ydwlxcpt.Utils;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Administrator on 2017/9/21.
 */

public class BitmapUtils {

    /**
     *把View转换成Bitmap类型
     *@paramaddViewContent要转换的View
     *@return
     */
    public static Bitmap getViewBitmap(View addViewContent) {
        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0,0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());


        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        return bitmap;
    }
}
