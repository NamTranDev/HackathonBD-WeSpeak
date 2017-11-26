package vn.com.wespeak.wespeak.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.model.ConversationMockup;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationHolder> {

    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    private List<ConversationMockup> list;

    public ConversationAdapter(List<ConversationMockup> list) {
        this.list = list;
    }

    @Override
    public ConversationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == LEFT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_conversation_left, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_conversation_right, parent, false);
        }
        return new ConversationHolder(view);
    }

    @Override
    public void onBindViewHolder(ConversationHolder holder, int position) {
        if (list.get(position) != null){
            holder.bind(list.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).type == LEFT)
            return LEFT;
        return RIGHT;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ConversationHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_user)
        AppCompatImageView mIvUser;
        @BindView(R.id.tv_chat)
        TextView mTvChat;

        ConversationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        Context getContext(){
            return itemView.getContext();
        }

        void bind(ConversationMockup data){
            mTvChat.setText(data.data);
            if (!TextUtils.isEmpty(data.url)){
                Picasso.with(getContext()).load(data.url).placeholder(R.drawable.image_holder).error(R.drawable.image_holder).into(mIvUser);
            }
        }
    }
}
