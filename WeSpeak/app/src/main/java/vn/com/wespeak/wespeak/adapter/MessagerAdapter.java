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
import vn.com.wespeak.wespeak.Constant;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.model.Message;

public class MessagerAdapter extends RecyclerView.Adapter<MessagerAdapter.MessageHolder> {

    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    private List<Message> messages;

    public MessagerAdapter(List<Message> messages) {
        this.messages = messages;
    }

    public void updateData(Message message){
        messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == LEFT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_conversation_left, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_conversation_right, parent, false);
        }
        return new MessageHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).type == Constant.MESSAGE.RECEIVER)
            return LEFT;
        return RIGHT;
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        if (messages.get(position) != null){
            holder.bind(messages.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_user)
        AppCompatImageView mIvUser;
        @BindView(R.id.tv_chat)
        TextView mTvChat;

        MessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        Context getContext(){
            return itemView.getContext();
        }

        void bind(Message message){
            mTvChat.setText(message.message);
            if (!TextUtils.isEmpty(message.url)){
                Picasso.with(getContext()).load(message.url).placeholder(R.drawable.image_holder).error(R.drawable.image_holder).into(mIvUser);
            }
        }
    }
}
