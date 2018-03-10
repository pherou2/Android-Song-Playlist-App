//Phil Heroux
//CS 478 Project 2
//Song Playlist using menus
package com.example.phil.project2_phillip_heroux;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Phil on 2/23/2018.
 */

public class SongObject implements Parcelable{
    private String song;
    private String artist;
    private String wikiSong;
    private String wikiArtist;
    private String url;

    public SongObject(){
        super ();
    }

    public SongObject(String s, String a, String w, String wa, String u){
        song = s;
        artist = a;
        wikiSong = w;
        wikiArtist = wa;
        url = u;
    }

    public SongObject(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        song = data[0];
        artist = data[1];
        wikiSong = data[2];
        wikiArtist = data[3];
        url = data[4];
    }

    public String getSong(){
        return song;
    }
    public String getArtist(){
        return artist;
    }
    public String getWikiSong(){
        return wikiSong;
    }
    public String getWikiArtist(){
        return wikiArtist;
    }
    public String getUrl(){
        return url;
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public SongObject createFromParcel(Parcel in){
            return new SongObject(in);
        }
        public SongObject[] newArray(int size) {
            return new SongObject[size];
        }

    };
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //parcel.writeStringArray(new String[]{song, artist,wikiSong,wikiArtist,url});
    }
}
