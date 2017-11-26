package vn.com.wespeak.wespeak.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.ShapedImageView;
import vn.com.wespeak.wespeak.model.User;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private List<User> mData;
    private OnItemClick onItemClick;

    public HistoryAdapter(List<User> mData) {
        this.mData = mData;
    }

    public void updateData(List<User> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history, parent, false);
        return new HistoryHolder(view,onItemClick);
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
        @BindView(R.id.rating)
        RatingBar mRating;

        private OnItemClick onItemClick;

        HistoryHolder(View itemView,OnItemClick onItemClick) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClick = onItemClick;
        }

        Context getContext() {
            return itemView.getContext();
        }

        void bind(User user) {
            mTvUser.setText(user.name);
            mRating.setRating(user.rating);
            if (!TextUtils.isEmpty(user.url)){
                Picasso.with(getContext()).load(user.url).placeholder(R.drawable.image_holder).error(R.drawable.image_holder).into(mIvUser);
            }
            switch (user.country) {
                case "VN":
                    mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.vietnam_national_flag));
                    break;
                case "Germany":
                    mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.germany_national_flag));
                    break;
                default:
                    mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.usa_national_flag));
                    break;
            }

            itemView.setOnClickListener(view -> {
                if (onItemClick != null)
                    onItemClick.onItemClick(user);
            });
        }
    }

    public interface OnItemClick {
        void onItemClick(User user);
    }
}
