package com.whamu2.previewimage.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.whamu2.previewimage.R;
import com.whamu2.previewimage.core.SelectionSpec;
import com.whamu2.previewimage.tools.ImageLoader;
import com.whamu2.previewimage.ui.internal.EventViewPager;

import java.text.MessageFormat;

/**
 * full-screen activity that shows and hides the system UI
 * for image preview
 *
 * @author whamu2
 */
public class PreviewFragmentActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = PreviewFragmentActivity.class.getSimpleName();
    /**
     * it use update delay thread mills
     */
    private static final int UI_COMMON_DELAY = 100;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 200;

    private final Handler mUIHandler = new Handler();

    private final Runnable mViewPagerRunnable = new Runnable() {
        @Override
        public void run() {
            cycle();
        }
    };

    private final Runnable mBottomTextRunnable = new Runnable() {
        @Override
        public void run() {
            showCountText();
        }
    };

    private EventViewPager mViewPager;
    private TextView mCountTextView;

    private SelectionSpec mSpec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_preview);
        mSpec = SelectionSpec.getInstance();

        mViewPager = findViewById(R.id.viewpager);
        mCountTextView = findViewById(R.id.tv_count);

        FragmentAdapter mAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.addOnPageChangeListener(this);
        for (int pos = 0; pos < mSpec.models.size(); pos++) {
            mAdapter.add(ViewFragment.newInstance(pos));
        }
//        mViewPager.setOffscreenPageLimit(mSpec.models.size() - 1);
        mViewPager.setAdapter(mAdapter);

        mUIHandler.postDelayed(mViewPagerRunnable, UI_ANIMATION_DELAY);
    }

    /**
     * set images for fragment
     */
    private void cycle() {
        if (mSpec.markPosition > mSpec.models.size() - 1) {
            mViewPager.setCurrentItem(0);
        } else
            mViewPager.setCurrentItem(mSpec.markPosition);
        showCountText();
    }

    /**
     * display count text ui
     */
    private void showCountText() {
        int currentPosition = mViewPager.getCurrentItem() + 1;
        int size = mSpec.models.size();
        if (mSpec.displayCountEnabled() && mSpec.models.size() > 0) {
            mCountTextView.setVisibility(View.VISIBLE);
            mCountTextView.setText(MessageFormat.format("{0}/{1}", currentPosition, size));
        } else {
            mCountTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(final int position) {
        mUIHandler.postDelayed(mBottomTextRunnable, UI_COMMON_DELAY);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoader.cleanDiskCache(this);
    }
}
