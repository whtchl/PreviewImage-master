package com.whamu2.previewimage.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.whamu2.previewimage.R;
import com.whamu2.previewimage.core.SelectionSpec;
import com.whamu2.previewimage.engine.ImageEngine;
import com.whamu2.previewimage.engine.impl.GlideEngine;
import com.whamu2.previewimage.engine.target.ProgressTarget;
import com.whamu2.previewimage.entity.Image;
import com.whamu2.previewimage.tools.DownloadUtils;
import com.whamu2.previewimage.tools.ImageLoader;
import com.whamu2.previewimage.tools.Utils;
import com.whamu2.previewimage.ui.internal.RingProgressBar;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * @author whamu2
 * @date 2018/7/23
 */
public class ViewFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = ViewFragment.class.getSimpleName();
    private static final String KEY_POSITION = "POSITION";
    private static final String KEY_URL = "URL";
    private static final String KEY_COMPLETE = "COMPLETE";
    private static final int DEF_POSITION = -1;
    private static final int PERMISSION_CODE = 104;
    private static final int MSG_ORIGIN = 895;
    private static final int MSG_IS_COMPLETE = 638;

    private static final String PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private SubsamplingScaleImageView mScaleImageView;
    private RingProgressBar mRingProgressBar;
    private TextView mOriginView;
    private ImageView mDLView;

    private int position = DEF_POSITION;
    private SelectionSpec mSpec;
    private MessageHandler mHandler;

    private ImageEngine mEngine;
    private boolean isShowOrigin = false;

    public static ViewFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        ViewFragment fragment = new ViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mSpec == null) {
            mSpec = SelectionSpec.getInstance();
        }
        if (getArguments() != null) {
            position = getArguments().getInt(KEY_POSITION);
        }
        mHandler = new MessageHandler(this);
        mEngine = new GlideEngine();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewpager_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScaleImageView = view.findViewById(R.id.subsamplingscaleimageview);
        mRingProgressBar = view.findViewById(R.id.progress);
        mOriginView = view.findViewById(R.id.tv_origin);
        mDLView = view.findViewById(R.id.iv_download);
        mScaleImageView.setOnClickListener(this);
        mOriginView.setOnClickListener(this);
        mDLView.setOnClickListener(this);

        mScaleImageView.setDoubleTapZoomDuration(mSpec.zoomTransitionDuration);
        mScaleImageView.setMinScale(mSpec.minScale);
        mScaleImageView.setMaxScale(mSpec.maxScale);
        mScaleImageView.setDoubleTapZoomScale(mSpec.mediumScale);

        Image image = mSpec.models.get(position);
        mOriginView.setVisibility(mSpec.showOriginImage && !TextUtils.isEmpty(image.getOriginUrl()) ? View.VISIBLE : View.GONE);
        mDLView.setVisibility(mSpec.showDownload && !TextUtils.isEmpty(mSpec.downloadLocalPath) ? View.VISIBLE : View.GONE);

        showImage();
    }

    private void showImage() {
        isShowOrigin = false;
        Image image = mSpec.models.get(position);
        if (image != null) {
            final String thumbnailUrl = image.getThumbnailUrl();
            Log.d(TAG,"tchl  "+thumbnailUrl );

            File cacheFile;
            if(thumbnailUrl.substring(0,3).equals("http")){
                cacheFile = ImageLoader.getGlideCacheFile(getContext(),thumbnailUrl);
            }else{
                cacheFile = new File(thumbnailUrl);
            }
            //cacheFile = new File(thumbnailUrl);
            if (cacheFile != null && cacheFile.exists()) {

                mEngine.loadImage(this, cacheFile, new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        Log.d(TAG, resource.getPath()+"onResourceReady 1 /" + resource.getAbsolutePath());
                        mScaleImageView.setImage(ImageSource.uri(Uri.fromFile(new File(resource.getAbsolutePath()))));
                        //mScaleImageView.setImage(ImageSource.uri(Uri.fromFile(new File("/sdcard/jpg.jpg"))));
                    }
                });
            } else {

                mEngine.loadImage(this, thumbnailUrl, new ProgressTarget<File>(thumbnailUrl) {
                    @Override
                    public void onComplete(@NonNull File resource) {
                        Log.d(TAG, "onComplete 1 /" + resource.getAbsolutePath());
                        mScaleImageView.setImage(ImageSource.uri(Uri.fromFile(new File(resource.getAbsolutePath()))));
                    }

                    @Override
                    public void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                        if (isComplete) {
                            mRingProgressBar.setVisibility(View.GONE);
                        } else {
                            mRingProgressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

        }
    }

    public void showOrigin() {
        Image image = mSpec.models.get(position);
        final String originUrl = image.getOriginUrl();

        if (!TextUtils.isEmpty(originUrl)) {

            mEngine.loadImage(this, originUrl, new ProgressTarget<File>(originUrl) {
                @Override
                public void onComplete(@NonNull File resource) {
                    Log.d(TAG, "onComplete 2 /" + resource.getAbsolutePath());
                    mScaleImageView.setImage(ImageSource.uri(Uri.fromFile(new File(resource.getAbsolutePath()))));
                    isShowOrigin = true;
                }

                @Override
                public void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                    if (isComplete) {
                        mRingProgressBar.setVisibility(View.GONE);
                    } else {
                        mRingProgressBar.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.subsamplingscaleimageview) {
            Utils.requireNonNull(getActivity()).finish();

        } else if (id == R.id.tv_origin) {
            Message message = mHandler.obtainMessage();
            message.what = MSG_ORIGIN;
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL, mSpec.models.get(position).getOriginUrl());
            bundle.putInt(KEY_POSITION, position);
            message.obj = bundle;
            mHandler.sendMessage(message);

        } else if (id == R.id.iv_download) {
            if (ContextCompat.checkSelfPermission(Utils.requireNonNull(getContext()), PERMISSION)
                    != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{PERMISSION}, PERMISSION_CODE);
            } else {
                download();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    download();
                } else {
                    Toast.makeText(getContext(), "未获得存储权限，下载失败！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void download() {
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String path = directory.getAbsolutePath()
                + File.separator
                + mSpec.downloadLocalPath
                + File.separator;
        Image image = mSpec.models.get(position);
        String imageUrl = isShowOrigin ? image.getOriginUrl() : image.getThumbnailUrl();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String alias = dateFormat.format(new Date(System.currentTimeMillis()));
        if (!TextUtils.isEmpty(imageUrl)) {
            String name = MessageFormat.format("{0}.jpeg", alias);
            DownloadUtils.downloadPicture(getContext(), imageUrl, path, name);
        }
    }

    private static class MessageHandler extends Handler {

        private WeakReference<ViewFragment> reference;

        MessageHandler(ViewFragment fragment) {
            reference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final ViewFragment fragment = reference.get();
            int what = msg.what;
            if (what == MSG_ORIGIN) {
                Bundle bundle = (Bundle) msg.obj;
                final String originUrl = bundle.getString(KEY_URL);
                final int position = bundle.getInt(KEY_POSITION);
                Glide.with(fragment)
                        .asFile()
                        .load(originUrl)
                        .into(new ProgressTarget<File>(Utils.requireNonNull(originUrl)) {
                            @Override
                            public void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                                if (isComplete) {
                                    fragment.mOriginView.setText("查看原图");
                                    fragment.mOriginView.setVisibility(View.GONE);
                                } else {
                                    fragment.mOriginView.setText(MessageFormat.format("已完成{0}%", percentage));
                                    fragment.mOriginView.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onComplete(@NonNull File resource) {
                                Log.d(TAG, "onComplete 3 /" + resource.getAbsolutePath());
                                fragment.showOrigin();

                                Message message = fragment.mHandler.obtainMessage();
                                Bundle bundle = new Bundle();
                                bundle.putInt(KEY_POSITION, position);
                                bundle.putBoolean(KEY_COMPLETE, true);
                                message.what = MSG_IS_COMPLETE;
                                message.obj = bundle;
                                fragment.mHandler.sendMessage(message);
                            }
                        });


            } else if (what == MSG_IS_COMPLETE) {
                fragment.mOriginView.setText("查看原图");
                fragment.mOriginView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScaleImageView.recycle();
        Glide.with(this).clear(mScaleImageView);
        mEngine = null;
    }
}
