package com.whamu2.previewimage.engine.impl;

import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.whamu2.previewimage.R;
import com.whamu2.previewimage.engine.ImageEngine;

import java.io.File;

/**
 * {@link ImageEngine} implementation using Glide.
 */

public class GlideEngine implements ImageEngine {

    @Override
    public void loadImage(Fragment fragment, String model, BaseTarget<File> target) {
        Glide.with(fragment)
                .asFile()
                .load(model)
                .apply(RequestOptions.errorOf(R.drawable.ic_empty_light))
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .into(target);
    }

    @Override
    public void loadImage(Fragment fragment, File model, BaseTarget<File> target) {
        Glide.with(fragment)
                .asFile()
                .load(model)
                .apply(RequestOptions.errorOf(R.drawable.ic_empty_light))
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .into(target);
    }
}
