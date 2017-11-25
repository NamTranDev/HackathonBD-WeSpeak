package vn.com.wespeak.wespeak.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.wespeak.wespeak.Constant;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.ShapedImageView;
import vn.com.wespeak.wespeak.StatusBarUtil;
import vn.com.wespeak.wespeak.model.Review;

public class RatingActivity extends AppCompatActivity {

    @BindView(R.id.iv_partner)
    ShapedImageView mIvPartner;
    @BindView(R.id.rating)
    AppCompatRatingBar mRating;
    @BindView(R.id.edt_feedback)
    EditText mEdtFeedback;

    private int mType;
    private float rating;
    private String mConversationId;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_rating);
        ButterKnife.bind(this);

        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.black));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (getIntent().getExtras() != null){
            mConversationId = getIntent().getExtras().getString(Constant.EXTRA.CONVERSATION_ID_RATING);
            mType = getIntent().getExtras().getInt(Constant.EXTRA.USER_RATING_TYPE);
            rating = getIntent().getExtras().getFloat(Constant.EXTRA.USER_RATING);
        }

    }

    @OnClick(R.id.bt_)
    public void onViewClicked() {
        float rating = (this.rating + mRating.getRating()) /2;
        DatabaseReference newRef = mDatabase.child("review").push();
        switch (mType){
            case Constant.TypeLogin.LEANER:
                newRef.setValue(new Review(mConversationId,mEdtFeedback.getText().toString(),Constant.TypeLogin.TEACHER,mRating.getRating()));
                mDatabase.child("user").child(String.valueOf(Constant.TypeLogin.TEACHER)).child("conversationId").setValue("");
                mDatabase.child("user").child(String.valueOf(Constant.TypeLogin.TEACHER)).child("rating").setValue(rating);
                break;
            case Constant.TypeLogin.TEACHER:
                newRef.setValue(new Review(mConversationId,mEdtFeedback.getText().toString(),Constant.TypeLogin.LEANER,mRating.getRating()));
                mDatabase.child("user").child(String.valueOf(Constant.TypeLogin.LEANER)).child("conversationId").setValue("");
                mDatabase.child("user").child(String.valueOf(Constant.TypeLogin.LEANER)).child("rating").setValue(rating);
                break;
        }
        finish();
    }
}
