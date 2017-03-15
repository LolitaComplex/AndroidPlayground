package com.doing.architectureencapsulation.image;

import java.util.Locale;

/**
 * Class description here
 *
 * 缩略分为内缩略、外缩略、裁剪缩略。
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-13.
 */

public class UrlGenerator {

    public static final String sStringFormat = "%s?imageView&thumbnail=%dx%d";

    public static String getImageUrl(String url, int width, int height) {
        return String.format(Locale.getDefault(), sStringFormat, url, width, height);
    }
}
