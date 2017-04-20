package com.edu.uestc.zhongbao_android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhu on 17/4/12.
 */

public class PictureModel implements Parcelable{
    public String picture;

    protected PictureModel(Parcel in) {
        picture = in.readString();
    }

    public static final Creator<PictureModel> CREATOR = new Creator<PictureModel>() {
        @Override
        public PictureModel createFromParcel(Parcel in) {
            return new PictureModel(in);
        }

        @Override
        public PictureModel[] newArray(int size) {
            return new PictureModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(picture);
    }
}
