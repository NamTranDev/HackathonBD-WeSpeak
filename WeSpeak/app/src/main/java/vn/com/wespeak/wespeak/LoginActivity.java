package vn.com.wespeak.wespeak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.bt_learner)
    Button mBtLearner;
    @BindView(R.id.bt_native_speaker)
    Button mBtNativeSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.black));
    }

    @OnClick({R.id.bt_learner, R.id.bt_native_speaker})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_learner:
            case R.id.bt_native_speaker:
                goToMain();
                break;
        }
    }

    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
