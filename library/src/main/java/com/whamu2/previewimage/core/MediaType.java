package com.whamu2.previewimage.core;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.whamu2.previewimage.entity.Image;

import java.util.List;

/**
 * load image type to arrays
 *
 * @author whamu2
 * @date 2018/7/13
 */
interface MediaType<Builder> {

    @NonNull
    @CheckResult
    Builder load(@Nullable List<Image> images);
}
