package vn.com.wespeak.wespeak.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.wespeak.wespeak.Constant;
import vn.com.wespeak.wespeak.Logger;
import vn.com.wespeak.wespeak.dialog.DialogFind;
import vn.com.wespeak.wespeak.dialog.DialogStartCalling;
import vn.com.wespeak.wespeak.fragment.HistoryFragment;
import vn.com.wespeak.wespeak.fragment.ProfileFragment;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.fragment.SearchFragment;
import vn.com.wespeak.wespeak.StatusBarUtil;
import vn.com.wespeak.wespeak.model.Converstation;
import vn.com.wespeak.wespeak.model.User;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnSearchListener, DialogStartCalling.onDialogStartCallingCallBack {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.contain_bottom)
    LinearLayout mContainbottom;
    @BindView(R.id.tab)
    TabLayout mTab;

    private List<Fragment> listFragment = new ArrayList<>();
    private List<User> listUser = new ArrayList<>();
    private List<Converstation> listConversation = new ArrayList<>();

    private DatabaseReference mDatabase;
    private User user;
    private boolean isPress;

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
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("conversation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Logger.debug("conversation");
                listConversation.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Converstation converstation = child.getValue(Converstation.class);
                    if (converstation != null)
                        listConversation.add(converstation);
                }

                updateConverStation();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Logger.w("loadPost:onCancelled", databaseError.toException());
            }
        });

        if (getIntent().getExtras() != null) {
            user = getIntent().getExtras().getParcelable(Constant.EXTRA.USER_LOGIN);
            setupViewPager(mViewpager, user);
            mTab.setupWithViewPager(mViewpager);
            setupTabIcons();
            mViewpager.post(() -> {
                mTvTitle.setVisibility(View.INVISIBLE);
                mViewpager.setCurrentItem(0);
            });
        }

        mDatabase.child("user").child(String.valueOf(user != null ? user.id : -1)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    Fragment profileFragment = listFragment.get(Constant.POSITION.PROFILE);
                    if (profileFragment != null && profileFragment instanceof ProfileFragment) {
                        ((ProfileFragment) profileFragment).initData(user);
                    }

                    if (!TextUtils.isEmpty(user.conversationId) && !isPress && listUser.size() > 0) {
                        User receiverUser = null;
                        switch (user.id) {
                            case Constant.TypeLogin.LEANER:
                                receiverUser = getUser(Constant.TypeLogin.TEACHER);
                                break;
                            case Constant.TypeLogin.TEACHER:
                                receiverUser = getUser(Constant.TypeLogin.LEANER);
                                break;
                        }
                        if (receiverUser != null) {
                            try {
                                receiverUser.conversationId = user.conversationId;
                                DialogStartCalling dialogStartCalling = DialogStartCalling.getInstance(receiverUser);
                                dialogStartCalling.setOnDialogStartCallingCallBack(MainActivity.this);
                                dialogStartCalling.show(getSupportFragmentManager(), "Calling Dialog Start");
                            } catch (IllegalStateException e) {
                                Logger.debug(e);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listUser.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    if (user != null)
                        listUser.add(user);
                }

                updateConverStation();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Logger.w("loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private User getUser(int id) {
        for (User user : listUser) {
            if (user.id == id)
                return user;
        }
        return null;
    }

    private void updateConverStation() {
        if (listConversation.size() > 0 && listUser.size() > 0 && user != null) {
            switch (user.id) {
                case Constant.TypeLogin.LEANER:
                    List<Integer> listLeaner = new ArrayList<>();
                    for (Converstation converstation : listConversation) {
                        if (converstation.learnerId == 1) {
                            listLeaner.add(converstation.teacherId);
                        }
                    }

                    List<User> userTeacher = new ArrayList<>();
                    for (User user : listUser) {
                        if (listLeaner.contains(user.id)) {
                            userTeacher.add(user);
                        }
                    }

                    if (userTeacher.size() > 0) {
                        Fragment historyFragment = listFragment.get(Constant.POSITION.CONVERSTATION);
                        if (historyFragment != null && historyFragment instanceof HistoryFragment)
                            ((HistoryFragment) historyFragment).initData(userTeacher);

                        Fragment fragmentProfile = listFragment.get(Constant.POSITION.PROFILE);
                        if (fragmentProfile != null && fragmentProfile instanceof ProfileFragment){
                            ((ProfileFragment)fragmentProfile).updateConversation(userTeacher.size());
                        }
                    }
                    break;
                case Constant.TypeLogin.TEACHER:
                    List<Integer> listTeacher = new ArrayList<>();
                    for (Converstation converstation : listConversation) {
                        if (converstation.teacherId == 2) {
                            listTeacher.add(converstation.learnerId);
                        }
                    }

                    List<User> userLeaner = new ArrayList<>();
                    for (User user : listUser) {
                        if (listTeacher.contains(user.id)) {
                            userLeaner.add(user);
                        }
                    }

                    if (userLeaner.size() > 0) {
                        Fragment historyFragment = listFragment.get(Constant.POSITION.CONVERSTATION);
                        if (historyFragment != null && historyFragment instanceof HistoryFragment)
                            ((HistoryFragment) historyFragment).initData(userLeaner);

                        Fragment fragmentProfile = listFragment.get(Constant.POSITION.PROFILE);
                        if (fragmentProfile != null && fragmentProfile instanceof ProfileFragment){
                            ((ProfileFragment)fragmentProfile).updateConversation(userLeaner.size());
                        }
                    }
                    break;
            }
        }
    }

    private void setTextTitle(String text) {
        if (mTvTitle != null) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(text);
        }
    }

    private void setupViewPager(ViewPager viewPager, User user) {
        SearchFragment searchFragment = SearchFragment.getInstance(user.id);
        searchFragment.setmOnSearchListener(this);
        listFragment.add(Constant.POSITION.SEARCH, searchFragment);
        listFragment.add(Constant.POSITION.CONVERSTATION, HistoryFragment.getInstance());
        listFragment.add(Constant.POSITION.PROFILE, ProfileFragment.getInstance(user));
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), listFragment);
        viewPager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTvTitle.setVisibility(View.INVISIBLE);
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
            public void onPageScrollStateChanged(int state) {

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
        mContainbottom.animate().translationY(mContainbottom.getHeight()).setDuration(1000).start();
    }

    @Override
    public void onDismissDialog() {
        mContainbottom.animate().translationY(0).setDuration(1000).start();
    }

    @Override
    public void onFind() {
        int random = new Random().nextInt();
        String conversationId = String.valueOf(random);
        if (user != null) {
            User userReceiver = null;
            isPress = true;
            switch (user.id) {
                case Constant.TypeLogin.LEANER:
                    mDatabase.child("user").child(String.valueOf(Constant.TypeLogin.TEACHER)).child("conversationId").setValue(conversationId);
                    userReceiver = getUser(Constant.TypeLogin.TEACHER);
                    break;
                case Constant.TypeLogin.TEACHER:
                    mDatabase.child("user").child(String.valueOf(Constant.TypeLogin.LEANER)).child("conversationId").setValue(conversationId);
                    userReceiver = getUser(Constant.TypeLogin.LEANER);
                    break;
            }

            if (userReceiver != null && this.user != null) {
                getToCalling(userReceiver, user, Constant.CallType.CALLING, conversationId);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }
    }

    @Override
    public void onDialogStartListener(DialogStartCalling dialog, User userReceiver) {
        if (dialog != null)
            dialog.dismiss();

        if (user != null && userReceiver != null) {
            getToCalling(userReceiver, user, Constant.CallType.RECEIVER, userReceiver.conversationId);
        }
    }

    private void getToCalling(User userReceiver, User main, int type, String conversationId) {
        Intent intent = new Intent(this, CallingActivity.class);
        intent.putExtra(Constant.EXTRA.USER_ANOTHER, userReceiver);
        intent.putExtra(Constant.EXTRA.USER_MAIN, main.token);
        intent.putExtra(Constant.EXTRA.CALL_TYPE, type);
        intent.putExtra(Constant.EXTRA.CONVERSATION_ID, conversationId);
        startActivity(intent);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;

        ViewPagerAdapter(FragmentManager manager, List<Fragment> mFragmentList) {
            super(manager);
            this.mFragmentList = mFragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
