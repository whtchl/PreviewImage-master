package com.whamu2.previewimage.core;

import com.whamu2.previewimage.engine.ImageEngine;
import com.whamu2.previewimage.engine.impl.GlideEngine;
import com.whamu2.previewimage.entity.Image;

import java.util.List;

/**
 * @author whamu2
 * @date 2018/7/13
 */
public final class SelectionSpec {
    public List<Image> models;
    public int markPosition;
    public boolean displayCount;
    public ImageEngine engine;
    public CharSequence title;
    public boolean showDownload;
    public boolean showOriginImage;
    public String downloadLocalPath;
    public float minScale = 1.0f;// 最小缩放倍数
    public float mediumScale = 3.0f;// 中等缩放倍数
    public float maxScale = 5.0f;// 最大缩放倍数
    public int zoomTransitionDuration = 200;// 动画持续时间 单位毫秒 ms

    private SelectionSpec() {
    }

    public static SelectionSpec getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static SelectionSpec getCleanInstance() {
        SelectionSpec selectionSpec = getInstance();
        selectionSpec.reset();
        return selectionSpec;
    }

    private void reset() {
        models = null;
        displayCount = true;
        markPosition = 0;
        engine = null;
        title = null;
        showDownload = false;
        showOriginImage = false;
        downloadLocalPath = null;
        minScale = 1.0f;
        mediumScale = 3.0f;
        maxScale = 5.0f;
        zoomTransitionDuration = 200;
    }

    public boolean displayCountEnabled() {
        return displayCount;
    }

    private static final class InstanceHolder {
        private static final SelectionSpec INSTANCE = new SelectionSpec();
    }
}
