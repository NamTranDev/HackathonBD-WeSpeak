package vn.com.wespeak.wespeak.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.wespeak.wespeak.Constant;
import vn.com.wespeak.wespeak.LoadingDialog;
import vn.com.wespeak.wespeak.Logger;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.StatusBarUtil;
import vn.com.wespeak.wespeak.fragment.ProfileFragment;
import vn.com.wespeak.wespeak.fragment.SearchFragment;
import vn.com.wespeak.wespeak.model.User;

public class LoginActivity extends AppCompatActivity implements ValueEventListener {

    @BindView(R.id.bt_learner)
    Button mBtLearner;
    @BindView(R.id.bt_native_speaker)
    Button mBtNativeSpeaker;

    private DatabaseReference mDatabase;
    private LoadingDialog loading;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.black));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        loading = new LoadingDialog(this);
    }

    @OnClick({R.id.bt_learner, R.id.bt_native_speaker})
    public void onViewClicked(View view) {
        if (loading != null)
            loading.showDialog();
        switch (view.getId()) {
            case R.id.bt_learner:
                mDatabase.child("user").child(String.valueOf(1)).addListenerForSingleValueEvent(this);
                break;
            case R.id.bt_native_speaker:
                mDatabase.child("user").child(String.valueOf(2)).addListenerForSingleValueEvent(this);
                break;
        }
    }

    private void goToMain(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constant.EXTRA.USER_LOGIN,user);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        if (user != null){
            if (loading != null && loading.isShowing())
                loading.hideDialog();
            goToMain(user);
        }
        Logger.debug("User : " , user);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Logger.w("loadPost:onCancelled", databaseError.toException());
    }
}
