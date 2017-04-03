package com.stickynotes.application;

import android.app.Application;

import com.stickynotes.R;

/**
 * Created by TrungTV on 03/09/2017.
 */

public class CommonApplication extends Application {
    public static final int[] listBackground = new int[]{R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5, R.drawable.bg_6, R.drawable.bg_7, R.drawable.bg_8,
            R.drawable.bg_9, R.drawable.bg_10, R.drawable.bg_11, R.drawable.bg_12, R.drawable.bg_13, R.drawable.bg_14, R.drawable.bg_15, R.drawable.bg_16, R.drawable.bg_17, R.drawable.bg_18};
    public static final int[] listTextSize = new int[]{R.drawable.font_size_l, R.drawable.font_size_lt, R.drawable.font_size_st, R.drawable.font_size_s, R.drawable.font_size_m};
    public static final int[] listColor = new int[]{R.drawable.color_1, R.drawable.color_2, R.drawable.color_3, R.drawable.color_4, R.drawable.color_5, R.drawable.color_6};
    public static final int[] listAlign = new int[]{R.drawable.font_align_left, R.drawable.font_align_center, R.drawable.font_align_right};
    public static final int[] listPin = new int[]{R.drawable.holder_0, R.drawable.holder_1, R.drawable.holder_2, R.drawable.holder_3, R.drawable.holder_4, R.drawable.holder_5, R.drawable.holder_6, R.drawable.holder_7, R.drawable.holder_8, R.drawable.holder_9,
            R.drawable.holder_10, R.drawable.holder_11, R.drawable.holder_12, R.drawable.holder_13, R.drawable.holder_14, R.drawable.holder_15, R.drawable.holder_16, R.drawable.holder_17, R.drawable.holder_18};
    public static final String[] color = new String[]{"#000000", "#0037A8", "#2D7D32", "#FF0032", "#FFCC00", "#FFFFFF"};
    public static final int[] textSize = new int[]{26, 30, 10, 14, 18};
    public static final int[] align = new int[]{0, 1, 2};
    public static final int[] rotate = new int[]{R.drawable.angle_left, R.drawable.angle_right, R.drawable.angle_center};
    public static final float[] rotateDegrees = new float[]{0, 10, -10};
}
