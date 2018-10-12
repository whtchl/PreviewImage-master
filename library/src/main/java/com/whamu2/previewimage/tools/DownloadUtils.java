package com.whamu2.previewimage.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class DownloadUtils {

    public static void downloadPicture(final Context context, final String url, final String path, final String name) {

        Glide.with(context).asFile().load(url).into(new SimpleTarget<File>() {
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                boolean result = copyFile(resource, path, name);
                if (result) {
                    Toast.makeText(context, "成功保存到 ".concat(path).concat(name), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    /**
     * 根据文件路径拷贝文件
     *
     * @param resourceFile 源文件
     * @param targetPath   目标路径（包含文件名和文件格式）
     * @return boolean 成功true、失败false
     */
    public static boolean copyFile(File resourceFile, String targetPath, String fileName) {
        boolean result = false;
        if (resourceFile == null || TextUtils.isEmpty(targetPath)) {
            return result;
        }
        File target = new File(targetPath);
        if (target.exists()) {
            target.delete(); // 已存在的话先删除
        } else {
            try {
                target.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File targetFile = new File(targetPath.concat(fileName));
        if (targetFile.exists()) {
            targetFile.delete();
        } else {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileChannel resourceChannel;
        FileChannel targetChannel;
        try {
            resourceChannel = new FileInputStream(resourceFile).getChannel();
            targetChannel = new FileOutputStream(targetFile).getChannel();
            resourceChannel.transferTo(0, resourceChannel.size(), targetChannel);
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        try {
            resourceChannel.close();
            targetChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}