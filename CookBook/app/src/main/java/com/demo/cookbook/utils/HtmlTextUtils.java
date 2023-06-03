package com.demo.cookbook.utils;

import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class HtmlTextUtils {
    /**
     * 仅显示文本内容
     *
     * @param textView 目标控件
     * @param source   目标源
     */
    public static void showJustText(TextView textView, String source) {
        if (textView == null) {
            return;
        }
        // 溢出滚动
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        Spanned spanned;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(source);
        }
        textView.setText(spanned);
    }
}
