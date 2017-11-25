package vn.com.wespeak.wespeak;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnSearchListener {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.contain_bottom)
    LinearLayout mContainbottom;
    @BindView(R.id.tab)
    TabLayout mTab;

    private int[] tabIcons = {
            R.drawable.ic_tab_search,
            R.drawable.ic_tab_history,
            R.drawable.ic_tab_profile
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_main);
        ButterKnife.bind(this);
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.black));
        setupViewPager(mViewpager);
        mTab.setupWithViewPager(mViewpager);
        setupTabIcons();

        mViewpager.post(new Runnable() {
            @Override
            public void run() {
                mViewpager.setCurrentItem(0);
            }
        });
    }

    private void setTextTitle(String text) {
        if (mTvTitle != null) {
            mTvTitle.setText(text);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        SearchFragment searchFragment = SearchFragment.getInstance();
        searchFragment.setmOnSearchListener(this);
        adapter.addFragment(searchFragment);
        adapter.addFragment(new HistoryFragment());
        adapter.addFragment(new ProfileFragment());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setTextTitle("");
                        break;
                    case 1:
                        setTextTitle("Conversations");
                        break;
                    case 2:
                        setTextTitle("Profile");
                        break;
                    }
                }

                @Override
                public void onPageScrollStateChanged ( int state){

                }
            });
        }

    private void setupTabIcons() {
        TabLayout.Tab tab1 = mTab.getTabAt(0);
        if (tab1 != null)
            tab1.setIcon(tabIcons[0]);

        TabLayout.Tab tab2 = mTab.getTabAt(1);
        if (tab2 != null)
            tab2.setIcon(tabIcons[1]);

        TabLayout.Tab tab3 = mTab.getTabAt(2);
        if (tab3 != null)
            tab3.setIcon(tabIcons[2]);
    }

    @Override
    public void onShowDialog() {
        mContainbottom.animate().translationY(mContainbottom.getHeight()).setDuration(500).start();
    }

    @Override
    public void onDismissDialog() {
        mContainbottom.animate().translationY(0).setDuration(500).start();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
