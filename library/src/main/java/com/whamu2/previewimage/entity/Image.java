package com.whamu2.previewimage.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 构造实体
 *
 * @author whamu2
 * @date 2018/7/21
 */
public class Image implements Parcelable {

    private String thumbnailUrl;    // 缩略图，质量很差
    private String originUrl;       // 原图或者高清图

    public Image() {
    }

    protected Image(Parcel in) {
        thumbnailUrl = in.readString();
        originUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnailUrl);
        dest.writeString(originUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    @Override
    public String toString() {
        return "Image{" +
                "thumbnailUrl='" + thumbnailUrl + '\'' +
                ", originUrl='" + originUrl + '\'' +
                '}';
    }
}
