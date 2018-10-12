package com.whamu2.previewimage.tools;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.Key;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;


public class OriginalKey implements Key {
    private final String id;
    private final Key signature;

    OriginalKey(String id, Key signature) {
        this.id = id;
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OriginalKey that = (OriginalKey) o;

        return id.equals(that.id) && signature.equals(that.signature);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + signature.hashCode();
        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        try {
            messageDigest.update(id.getBytes(STRING_CHARSET_NAME));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        signature.updateDiskCacheKey(messageDigest);
    }
}