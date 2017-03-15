package com.doing.architectureencapsulation.share;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;

import static com.xiaomi.push.thrift.a.U;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-15.
 */

public class SystemShareApi extends AbstractSharedApi {

    public SystemShareApi(Activity context) {
        super(context);
    }

    @Override
    public void share(@Nullable String content, @Nullable String imagePath) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        if (!TextUtils.isEmpty(content)) {
            intent.putExtra(Intent.EXTRA_TEXT, content);
            intent.setType("text/plain");
        }

        if (!TextUtils.isEmpty(imagePath)) {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
            intent.setType("image/*");
        }

        mContext.startActivity(intent);
    }
}
