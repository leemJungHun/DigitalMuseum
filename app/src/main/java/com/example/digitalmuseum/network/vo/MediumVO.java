package com.example.digitalmuseum.network.vo;

import android.os.Parcel;
import android.os.Parcelable;

public class MediumVO implements Parcelable {
    private String title;
    private String code;
    private String image;

    public MediumVO(String title, String code, String image) {
        this.title = title;
        this.code = code;
        this.image = image;
    }

    protected MediumVO(Parcel in) {
        title = in.readString();
        code = in.readString();
        image = in.readString();
    }

    public static final Creator<MediumVO> CREATOR = new Creator<MediumVO>() {
        @Override
        public MediumVO createFromParcel(Parcel in) {
            return new MediumVO(in);
        }

        @Override
        public MediumVO[] newArray(int size) {
            return new MediumVO[size];
        }
    };

    public MediumVO() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(code);
    }
}
