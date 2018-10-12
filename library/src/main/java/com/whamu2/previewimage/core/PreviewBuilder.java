package com.whamu2.previewimage.core;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.whamu2.previewimage.engine.ImageEngine;
import com.whamu2.previewimage.entity.Image;
import com.whamu2.previewimage.ui.PreviewFragmentActivity;

import java.util.List;

/**
 * construction parameter
 *
 * @author whamu2
 * @date 2018/7/13
 */
public class PreviewBuilder implements Cloneable, MediaType<PreviewBuilder>
        , UiManager<PreviewBuilder> {

    private Context mContext;
    private SelectionSpec mSpec;

    public PreviewBuilder(Context context) {
        this.mContext = context;
        mSpec = SelectionSpec.getCleanInstance();
    }

    @NonNull
    @Override
    public PreviewBuilder load(@Nullable List<Image> images) {
        mSpec.models = images;
        return this;
    }

    @Override
    public PreviewBuilder markPosition(int position) {
        mSpec.markPosition = position;
        return this;
    }

    @Override
    public PreviewBuilder displayCount(boolean show) {
        mSpec.displayCount = show;
        return this;
    }

    @Override
    public PreviewBuilder showDownload(boolean show) {
        mSpec.showDownload = show;
        return this;
    }

    @Override
    public PreviewBuilder downloadLocalPath(@NonNull String child) {
        mSpec.downloadLocalPath = child;
        return this;
    }

    @Override
    public PreviewBuilder showOriginImage(boolean show) {
        mSpec.showOriginImage = show;
        return this;
    }

    /**
     * Last call method
     */
    public void show() {
        if (mContext == null) {
            throw new NullPointerException("Context can't null");
        }
        if (mSpec.models.size() < 1) {
            return;
        }
        Intent intent = new Intent(mContext, PreviewFragmentActivity.class);
        mContext.startActivity(intent);
    }
}
