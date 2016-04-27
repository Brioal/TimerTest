package com.brioal.timertest.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by brioal on 16-4-16.
 */
public class BrioalUtil {
    public static void init(Context mContent) {
        SharedPreferences preferences = mContent.getSharedPreferences("Brioal", Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = preferences.edit();
        // 已做笔记 未做笔记 已上架 等待上架
        editor.putString("Desc", "三种定时器的使用").apply();
        editor.putString("Tag", "定时器").apply();
        editor.putString("State", "已做笔记").apply();
        editor.putString("Url", "http://blog.csdn.net/qq_26971803/article/details/51127531").apply();

    }


}
