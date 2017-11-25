package vn.com.wespeak.wespeak.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {

    public String conversationId;
    public String feedback;
    public int partner;
    public float rating;

    public Review() {
    }

    public Review(String conversationId, String feedback, int partner, float rating) {
        this.conversationId = conversationId;
        this.feedback = feedback;
        this.partner = partner;
        this.rating = rating;
    }

    protected Review(Parcel in) {
        conversationId = in.readString();
        feedback = in.readString();
        partner = in.readInt();
        rating = in.readFloat();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(conversationId);
        parcel.writeString(feedback);
        parcel.writeInt(partner);
        parcel.writeFloat(rating);
    }

    @Override
    public String toString() {
        return "Review{" +
                "conversationId=" + conversationId +
                ", feedback='" + feedback + '\'' +
                ", partner='" + partner + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
