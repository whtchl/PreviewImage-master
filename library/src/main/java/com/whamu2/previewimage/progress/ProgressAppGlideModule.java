package com.whamu2.previewimage.progress;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;
import java.io.InputStream;

/**
 * @author by sunfusheng on 2017/6/14.
 */
@GlideModule
public class ProgressAppGlideModule extends AppGlideModule {

    private static DiskCache cache;

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        // 设置缓存目录
        File cacheDir = new File(context.getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR);
        cache = DiskLruCacheWrapper.create(cacheDir, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);// 250 MB
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                return cache;
            }
        });
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }
}