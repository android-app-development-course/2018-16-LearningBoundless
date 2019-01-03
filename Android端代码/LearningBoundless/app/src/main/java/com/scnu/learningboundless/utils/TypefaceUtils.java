package com.scnu.learningboundless.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by WuchangI on 2018/11/19.
 */

public class TypefaceUtils {

    private static TypefaceUtils typefaceUtils;
    private Context mContext;
    private Typeface typeface1;

    public static TypefaceUtils getInstance() {
        return typefaceUtils == null ? typefaceUtils = new TypefaceUtils() : typefaceUtils;
    }

    private TypefaceUtils() {
    }

    public void init(Context context) {

        mContext = context;
    }

    public Typeface getTypeface1() {

        return typeface1 == null ? Typeface.createFromAsset(mContext.getAssets(), "fonts/font1.ttf") : typeface1;
    }


}
