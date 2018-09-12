package com.dicoding.clouds.kamusbahasa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class WordModel implements Parcelable {
    private int id;
    private String kata;
    private String arti;

    public WordModel(String kata, String arti) {
        this.kata = kata;
        this.arti = arti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kata);
        dest.writeString(this.arti);
    }

    public WordModel() {
    }

    protected WordModel(Parcel in) {
        this.kata = in.readString();
        this.arti = in.readString();
    }

    public static final Creator<WordModel> CREATOR = new Creator<WordModel>() {
        @Override
        public WordModel createFromParcel(Parcel source) {
            return new WordModel(source);
        }

        @Override
        public WordModel[] newArray(int size) {
            return new WordModel[size];
        }
    };
}
