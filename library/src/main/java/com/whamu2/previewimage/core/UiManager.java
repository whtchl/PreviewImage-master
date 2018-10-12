package com.whamu2.previewimage.core;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.whamu2.previewimage.engine.ImageEngine;
import com.whamu2.previewimage.engine.impl.GlideEngine;

/**
 * General ui update method
 *
 * @author whamu2
 * @date 2018/7/13
 */
interface UiManager<Builder> {

    /**
     * Starting from the first few display
     *
     * @param position range Interval form 0
     * @return Builder
     */
    @CheckResult
    Builder markPosition(int position);

    /**
     * Show bottom text E.g 2/34
     *
     * @param show true/false
     * @return Builder
     */
    @CheckResult
    Builder displayCount(boolean show);

    /**
     * 显示下载按钮
     *
     * @param show bool
     * @return Builder
     */
    @CheckResult
    Builder showDownload(boolean show);

    /**
     * 设置下载路径
     *
     * @param child 所属文件夹
     * @return Builder
     */
    @CheckResult
    Builder downloadLocalPath(@NonNull String child);

    /**
     * 设置显示查看原图
     *
     * @param show bool
     * @return Builder
     */
    @CheckResult
    Builder showOriginImage(boolean show);


}
