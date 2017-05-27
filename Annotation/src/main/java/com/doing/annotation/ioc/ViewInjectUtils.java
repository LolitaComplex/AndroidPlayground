package com.doing.annotation.ioc;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-05-25.
 */

public class ViewInjectUtils {

    private static final String SET_CONTENT_VIEW = "setContentView";
    private static final String FIND_VIEW_BY_ID = "findViewById";

    public static void inject(Activity activity) {
        injectContentView(activity);
        injectViews(activity);
        injectEvent(activity);
    }

    private static void injectEvent(final Activity activity) {
        Class<? extends Activity> injectClass = activity.getClass();
        Method[] methods = injectClass.getMethods();
        for (final Method method : methods) {
            OnClick eventField = method.getAnnotation(OnClick.class);
            if (eventField != null) {
//                EventBase annotation = eventField.annotationType().getAnnotation(EventBase.class);
                int[] clickViewsId = eventField.value();
                for (int viewId : clickViewsId) {
                    View view = activity.findViewById(viewId);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(activity, v);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

    private static void injectViews(Activity activity) {
        Class<? extends Activity> injectClass = activity.getClass();
        Field[] fields = injectClass.getDeclaredFields();
        for (Field field : fields) {
            ViewInject viewField = field.getAnnotation(ViewInject.class);
            if (viewField != null) {
                int viewId = viewField.value();
                if (viewId != -1) {
                    try {
//                        Method method = injectClass.getMethod(FIND_VIEW_BY_ID, int.class);
//                        Object viewObj = method.invoke(activity, viewId);
//                        field.setAccessible(true);
                        View viewObj = activity.findViewById(viewId);
                        field.set(activity, viewObj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectContentView(Activity activity) {
        Class<? extends Activity> injectClass = activity.getClass();
        ContentView contentView = injectClass.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutId = contentView.value();
            activity.setContentView(layoutId);
//            try {
//                Method method = injectClass.getMethod(SET_CONTENT_VIEW, int.class);
//                method.invoke(activity, layoutId);

//            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
        }
    }
}
