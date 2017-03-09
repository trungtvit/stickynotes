package com.stickynotes.activity;

import android.app.Application;

import com.stickynotes.R;

/**
 * Created by TrungTV on 03/09/2017.
 */

public class CommonApplication extends Application {
    public static final int[] listBackground = new int[]{R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5, R.drawable.bg_6};
    public static final int[] listTextSize = new int[]{R.drawable.font_size_l, R.drawable.font_size_lt, R.drawable.font_size_st, R.drawable.font_size_s, R.drawable.font_size_m};
    public static final int[] listColor = new int[]{R.drawable.color_1, R.drawable.color_2, R.drawable.color_3, R.drawable.color_4, R.drawable.color_5, R.drawable.color_6};
    public static final int[] listAlign = new int[]{R.drawable.font_align_left, R.drawable.font_align_center, R.drawable.font_align_right};
    public static final int[] listPin = new int[]{0, R.drawable.holder_2, R.drawable.holder_3};
    public static final String[] color = new String[]{"#000000", "#0037A8", "#2D7D32", "#FF0032", "#FFCC00", "#FFFFFF"};
    public static final int[] textSize = new int[]{26, 30, 10, 14, 18};
    public static final int[] rotate = new int[]{R.drawable.angle_left, R.drawable.angle_right, R.drawable.angle_center};
    public static final float[] rotateDegrees = new float[]{0, 10, -10};
}
