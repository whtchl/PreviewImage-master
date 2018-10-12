package com.whamu2.previewimage.tools;

/**
 * @author whamu2
 * @date 2018/7/16
 */
public final class Utils {
    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }
}
