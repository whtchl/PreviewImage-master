package com.whamu2.previewimage;

import android.content.Context;
import android.support.annotation.Nullable;

import com.whamu2.previewimage.core.PreviewBuilder;

import java.lang.ref.WeakReference;

/**
 * image preview tools
 *
 * @author whamu2
 * @date 2018/7/12
 */
public class Preview {

    /**
     * get context to {@link WeakReference}
     */
    private WeakReference<Context> mContext;

    /**
     * initial class
     *
     * @param context {@link Context}
     */
    private Preview(Context context) {
        mContext = new WeakReference<>(context);
    }

    /**
     * {@link Preview} start position
     *
     * @param context {@link Context}
     * @return {@link Preview}
     */
    public static Preview with(Context context) {
        return new Preview(context);
    }

    /**
     * create {@link PreviewBuilder} construction parameter
     *
     * @return {@link PreviewBuilder}
     */
    public PreviewBuilder builder() {
        return new PreviewBuilder(getContext());
    }

    @Nullable
    private Context getContext() {
        return mContext.get();
    }

}
