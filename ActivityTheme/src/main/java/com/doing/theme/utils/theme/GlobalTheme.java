package com.doing.theme.utils.theme;


import com.doing.theme.R;

public class GlobalTheme {

    private static ColorTheme mCurrentTheme = ColorTheme.NORMAL;

    public enum ColorTheme{
        BLUE,GRAY,PURPLE,COLOR_PRIMARY,NORMAL
    }

    public static void setCurrentTheme(ColorTheme colorTheme) {
        mCurrentTheme = colorTheme;
    }

    public static int getCurrentTheme() {
        switch (mCurrentTheme) {
            case BLUE:
                return R.style.Global_Blue;
            case GRAY:
                return R.style.Global_Gray;
            case PURPLE:
                return R.style.Global_Purple;
            case COLOR_PRIMARY:
                return R.style.Global_ColorPrimary;
            default:
                return 0;
        }
    }
}
