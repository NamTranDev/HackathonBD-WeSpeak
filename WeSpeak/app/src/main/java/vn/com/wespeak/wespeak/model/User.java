package vn.com.wespeak.wespeak.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{

    public String conversationId;
    public String country;
    public int id;
    public String name;
    public float rating;
    public String token;
    public String url;

    public User() {
    }

    protected User(Parcel in) {
        conversationId = in.readString();
        country = in.readString();
        id = in.readInt();
        name = in.readString();
        rating = in.readFloat();
        token = in.readString();
        url = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(conversationId);
        parcel.writeString(country);
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeFloat(rating);
        parcel.writeString(token);
        parcel.writeString(url);
    }

    @Override
    public String toString() {
        return "User{" +
                "conversationId='" + conversationId + '\'' +
                ", country='" + country + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", token='" + token + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
