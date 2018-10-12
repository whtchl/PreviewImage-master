
package com.whamu2.previewimage.engine;

import android.support.v4.app.Fragment;

import com.bumptech.glide.request.target.BaseTarget;

import java.io.File;

/**
 * load image engine glide or picasso
 */
public interface ImageEngine {
    /**
     * Load a static image url.
     *
     * @param fragment Fragment
     * @param model    the loaded image
     */
    void loadImage(Fragment fragment, String model, BaseTarget<File> target);

    /**
     * Load a static image file local.
     *
     * @param fragment Fragment
     * @param model    the loaded image
     */
    void loadImage(Fragment fragment, File model, BaseTarget<File> target);
}
