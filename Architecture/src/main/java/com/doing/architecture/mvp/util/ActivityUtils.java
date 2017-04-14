package com.doing.architecture.mvp.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-14.
 */

public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager manager,
                                             @NonNull Fragment fragment, int frameId) {
        Preconditions.checkNotNull(manager);
        Preconditions.checkNotNull(fragment);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
