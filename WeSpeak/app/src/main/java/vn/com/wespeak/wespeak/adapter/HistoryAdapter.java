package vn.com.wespeak.wespeak.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.ShapedImageView;
import vn.com.wespeak.wespeak.model.User;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private List<User> mData;

    public HistoryAdapter(List<User> mData) {
        this.mData = mData;
    }

    public void updateData(List<User> mData){
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class HistoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_user)
        ShapedImageView mIvUser;
        @BindView(R.id.iv_national_flag)
        AppCompatImageView mIvNationalFlag;
        @BindView(R.id.tv_user)
        TextView mTvUser;

        HistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        Context getContext(){
            return itemView.getContext();
        }

        void bind(User user) {
            mTvUser.setText(user.name);
            if (user.country.equals("VN")){
                mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.vietnam_national_flag));
            }else if (user.country.equals("Germany")){
                mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.germany_national_flag));
            }else {
                mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.usa_national_flag));
            }
        }
    }
}
