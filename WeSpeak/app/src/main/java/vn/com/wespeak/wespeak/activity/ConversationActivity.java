package vn.com.wespeak.wespeak.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.wespeak.wespeak.Constant;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.StatusBarUtil;
import vn.com.wespeak.wespeak.adapter.ConversationAdapter;
import vn.com.wespeak.wespeak.model.ConversationMockup;
import vn.com.wespeak.wespeak.model.User;

public class ConversationActivity extends AppCompatActivity {

    @BindView(R.id.rv_conversation)
    RecyclerView mRvConversation;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.progress)
    SeekBar mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.black));

        if (getIntent().getExtras() != null){
            User user = getIntent().getExtras().getParcelable(Constant.EXTRA.USER_CONVERSATION);
            if (user != null){
                mTvTitle.setText(user.name);
                List<ConversationMockup> data = new ArrayList<>();
                data.add(new ConversationMockup(user.url,1,"Hello,I'm Henry"));
                data.add(new ConversationMockup(user.url,2,"Hi.I'm Join. Nice to talk to you"));
                data.add(new ConversationMockup(user.url,1,"Sibling. It's mean brother or sister. Make sense?"));
                data.add(new ConversationMockup(user.url,2,"Oh I see"));
                data.add(new ConversationMockup(user.url,2,"Thanks!"));
                ConversationAdapter adapter = new ConversationAdapter(data);
                mRvConversation.setLayoutManager(new LinearLayoutManager(this));
                mRvConversation.setAdapter(adapter);
            }
        }

        mProgress.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.parseColor("#04d9d9"), PorterDuff.Mode.MULTIPLY));
        mProgress.getThumb().setColorFilter(Color.parseColor("#04d9d9"), PorterDuff.Mode.SRC_IN);
    }
}
