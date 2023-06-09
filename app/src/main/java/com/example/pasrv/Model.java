package com.example.pasrv;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Model implements Parcelable {

    private String Judul;
    private String Sinopsis;
    private String Popularity;
    private String ReleaseDate;
    private String VoteAverage;
    private String Vote;
    private String Poster;


    protected Model(Parcel in) {
        Judul = in.readString();
        Sinopsis = in.readString();
        Popularity = in.readString();
        ReleaseDate = in.readString();
        VoteAverage = in.readString();
        Vote = in.readString();
        Poster = in.readString();
    }

    Model(){

    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getSinopsis() {
        return Sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        Sinopsis = sinopsis;
    }

    public String getPopularity() {
        return Popularity;
    }

    public void setPopularity(String popularity) {
        Popularity = popularity;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return VoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        VoteAverage = voteAverage;
    }

    public String getVote() {
        return Vote;
    }

    public void setVote(String vote) {
        Vote = vote;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Judul);
        parcel.writeString(Sinopsis);
        parcel.writeString(Popularity);
        parcel.writeString(ReleaseDate);
        parcel.writeString(VoteAverage);
        parcel.writeString(Vote);
        parcel.writeString(Poster);
    }
}
