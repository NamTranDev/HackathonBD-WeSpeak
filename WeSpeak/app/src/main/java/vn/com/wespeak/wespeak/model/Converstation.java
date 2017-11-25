package vn.com.wespeak.wespeak.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Converstation implements Parcelable{

    public int learnerId;
    public int teacherId;

    public Converstation() {
    }

    protected Converstation(Parcel in) {
        learnerId = in.readInt();
        teacherId = in.readInt();
    }

    public static final Creator<Converstation> CREATOR = new Creator<Converstation>() {
        @Override
        public Converstation createFromParcel(Parcel in) {
            return new Converstation(in);
        }

        @Override
        public Converstation[] newArray(int size) {
            return new Converstation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(learnerId);
        parcel.writeInt(teacherId);
    }

    @Override
    public String toString() {
        return "Converstation{" +
                "leaner=" + learnerId +
                ", teacher=" + teacherId +
                '}';
    }
}
