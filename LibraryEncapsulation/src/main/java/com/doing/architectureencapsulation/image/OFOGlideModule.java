package com.doing.architectureencapsulation.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.doing.architectureencapsulation.constants.C;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-13.
 */

public class OFOGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        long availableSize = DiskUtils.getSDAvialableSize();
        long diskCacheSize = availableSize >  500 * C.M ?
                250 * C.M : availableSize / 2;
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, (int) diskCacheSize))
            .setMemoryCache(ImageUtils.getMemroyCache(context))
            .setDecodeFormat(DecodeFormat.DEFAULT);

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
         //配置数据类型的解析器，我们这里并不需要
    }
}
