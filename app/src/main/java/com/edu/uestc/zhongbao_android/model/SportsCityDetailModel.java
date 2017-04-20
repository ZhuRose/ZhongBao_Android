package com.edu.uestc.zhongbao_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhu on 17/4/20.
 */

public class SportsCityDetailModel implements Parcelable {
    public String uuid;
    public String nickname;
    public String headpic;
    public String create_time;
    public String content;
    public String number;
    public String site;
    public String latitude;
    public String longtitude;

    public List<PictureModel> pictures;

    protected SportsCityDetailModel(Parcel in) {
        pictures = new ArrayList<PictureModel>();
        uuid = in.readString();
        nickname = in.readString();
        headpic = in.readString();
        create_time = in.readString();
        content = in.readString();
        number = in.readString();
        site = in.readString();
        latitude = in.readString();
        longtitude = in.readString();
        in.readTypedList(pictures, PictureModel.CREATOR);
    }

    public static final Creator<SportsCityDetailModel> CREATOR = new Creator<SportsCityDetailModel>() {
        @Override
        public SportsCityDetailModel createFromParcel(Parcel in) {
            return new SportsCityDetailModel(in);
        }

        @Override
        public SportsCityDetailModel[] newArray(int size) {
            return new SportsCityDetailModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(nickname);
        dest.writeString(headpic);
        dest.writeString(create_time);
        dest.writeString(content);
        dest.writeString(number);
        dest.writeString(site);
        dest.writeString(latitude);
        dest.writeString(longtitude);
        dest.writeTypedList(pictures);
    }
}
