package movienight.javi.com.movienight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Javi on 10/24/2016.
 */

public class VoteCountFilterableItem implements FilterableItem<Integer>, Parcelable{

    private Integer mVoteCount;

    public VoteCountFilterableItem(Integer voteCount) {

        mVoteCount = voteCount;
    }

    protected VoteCountFilterableItem(Parcel in) {

        mVoteCount = in.readInt();
    }

    public static final Creator<VoteCountFilterableItem> CREATOR = new Creator<VoteCountFilterableItem>() {
        @Override
        public VoteCountFilterableItem createFromParcel(Parcel in) {
            return new VoteCountFilterableItem(in);
        }

        @Override
        public VoteCountFilterableItem[] newArray(int size) {
            return new VoteCountFilterableItem[size];
        }
    };

    @Override
    public Integer getObject() {

        return mVoteCount;
    }

    @Override
    public String getValue() {

        return mVoteCount + " votes.";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(mVoteCount);
    }
}
