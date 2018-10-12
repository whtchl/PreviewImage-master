package com.whamu2.previewimage.engine.target;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.whamu2.previewimage.progress.OnProgressListener;
import com.whamu2.previewimage.progress.ProgressManager;

/**
 * @author whamu2
 * @date 2018/7/21
 */
public class GlideImageViewTarget extends DrawableImageViewTarget {

    private String imageUrl;

    public GlideImageViewTarget(ImageView view, String imageUrl, OnProgressListener listener) {
        super(view);
        this.imageUrl = imageUrl;
        ProgressManager.addListener(imageUrl, listener);
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        super.onLoadStarted(placeholder);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        OnProgressListener onProgressListener = ProgressManager.getProgressListener(imageUrl);
        if (onProgressListener != null) {
            onProgressListener.onProgress(true, 100, 0, 0);
            ProgressManager.removeListener(imageUrl);
        }
        super.onLoadFailed(errorDrawable);
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        OnProgressListener onProgressListener = ProgressManager.getProgressListener(imageUrl);
        if (onProgressListener != null) {
            onProgressListener.onProgress(true, 100, 0, 0);
            ProgressManager.removeListener(imageUrl);
        }
        super.onResourceReady(resource, transition);
    }
}
