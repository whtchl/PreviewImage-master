package com.whamu2.previewimage.engine.target;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import com.whamu2.previewimage.progress.OnProgressListener;
import com.whamu2.previewimage.progress.ProgressManager;

/**
 * 带进度的 {@link com.bumptech.glide.request.target.Target}
 * {@link com.bumptech.glide.request.target.BaseTarget}
 *
 * @author whamu2
 * @date 2018/7/23
 */

public abstract class ProgressTarget<Z> extends BaseTarget<Z> implements OnProgressListener {
    private final int width;
    private final int height;
    private final String imageUrl;

    public ProgressTarget(@NonNull String imageUrl) {
        this(SIZE_ORIGINAL, SIZE_ORIGINAL, imageUrl);
    }

    public ProgressTarget(int width, int height, @NonNull String imageUrl) {
        this.width = width;
        this.height = height;
        this.imageUrl = imageUrl;
        ProgressManager.addListener(imageUrl, this);
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
    public void onResourceReady(@NonNull Z resource, @Nullable Transition<? super Z> transition) {
        OnProgressListener onProgressListener = ProgressManager.getProgressListener(imageUrl);
        onComplete(resource);
        if (onProgressListener != null) {
            onProgressListener.onProgress(true, 100, 0, 0);
            ProgressManager.removeListener(imageUrl);
        }
    }

    @Override
    public void getSize(@NonNull SizeReadyCallback cb) {
        if (!Util.isValidDimensions(width, height)) {
            throw new IllegalArgumentException(
                    "Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given" + " width: "
                            + width + " and height: " + height + ", either provide dimensions in the constructor"
                            + " or call override()");
        }
        cb.onSizeReady(width, height);
    }

    @Override
    public void removeCallback(@NonNull SizeReadyCallback cb) {
        // Do nothing, we never retain a reference to the callback.
    }

    public abstract void onComplete(@NonNull Z resource);
}
