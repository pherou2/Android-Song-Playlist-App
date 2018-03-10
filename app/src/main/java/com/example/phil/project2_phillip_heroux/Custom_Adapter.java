//Phil Heroux
//CS 478 Project 2
//Song Playlist using menus
package com.example.phil.project2_phillip_heroux;

/**
 * Created by Phil on 2/23/2018.
 */
import android.app.VoiceInteractor;
import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.app.ActionBar;

import java.util.ArrayList;

class Custom_Adapter extends ArrayAdapter<SongObject>{
    String song;
    TextView songText;
    String artist;
    TextView artistText;
    ImageView image;

    Custom_Adapter(Context context, ArrayList<SongObject> list){
        super(context, R.layout.custom_row, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get inflater and inflate with custom row layout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        String song = getItem(position).getSong();
        TextView songText = (TextView)customView.findViewById(R.id.songText);
        String artist = getItem(position).getArtist();
        TextView artistText = (TextView)customView.findViewById(R.id.artistText);
        ImageView image = (ImageView) customView.findViewById(R.id.imageView);
        //assign variables to custom layout items
        songText.setText(song);
        artistText.setText(artist);
        image.setImageResource(R.drawable.musicnote);

        return customView;
    }
    //add song to list, update to show in views
    public void add(SongObject song) {
        super.add(song);
        notifyDataSetChanged();
    }
    //remove song from list, update to show in views
    public void remove(SongObject song){
        super.remove(song);
        notifyDataSetChanged();
    }


}
