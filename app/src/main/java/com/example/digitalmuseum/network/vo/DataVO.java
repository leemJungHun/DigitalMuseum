package com.example.digitalmuseum.network.vo;

import android.os.Parcel;
import android.os.Parcelable;


public class DataVO implements Parcelable {
    String title;
    String code;
    String productedAt;
    String image;

    public DataVO(String title, String code, String productedAt, String image) {
        this.title = title;
        this.code = code;
        this.productedAt = productedAt;
        this.image = image;
    }

    protected DataVO(Parcel in) {
        title = in.readString();
        code = in.readString();
        productedAt = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(code);
        dest.writeString(productedAt);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataVO> CREATOR = new Creator<DataVO>() {
        @Override
        public DataVO createFromParcel(Parcel in) {
            return new DataVO(in);
        }

        @Override
        public DataVO[] newArray(int size) {
            return new DataVO[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getProductedAt() {
        return productedAt;
    }

    public void setProductedAt(String productedAt) {
        this.productedAt = productedAt;
    }

}
