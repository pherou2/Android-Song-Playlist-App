//Phil Heroux
//CS 478 Project 2
//Song Playlist using menus
package com.example.phil.project2_phillip_heroux;

import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.app.ActionBar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //initialize
    ArrayList<SongObject> list;
    Custom_Adapter adapter;
    ListView SongList;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //define list, populate with default songs using prepopulate function
        list = new ArrayList<SongObject>();
        prepopulateSongs(list);


        setContentView(R.layout.activity_main);
        //store optionmenu view in variable
        android.support.v7.widget.Toolbar OptionMenu = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(OptionMenu);


        //set adapter to new custom adapter, use list of songobjects to populate item adapter layout
        //Set listview item with adapter items
        adapter = new Custom_Adapter(this, list );
        SongList = (ListView)findViewById(R.id.listview);
        SongList.setAdapter(adapter);

        SongList.setOnItemClickListener(listShortClick);
        registerForContextMenu(SongList);


    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        //search for which click item, if none of default items, must be a song in the remove submenu
        switch (item.getItemId()){
            case R.id.opt_menu_add_song:
                addSongProtocol();
                return true;
            case R.id.opt_menu_remove_song:
                removeSongProtocol(item);
                return true;
            case R.id.opt_menu_exit:
                System.exit(0);
            default:
                //check if list only has one item, if not remove item selected from listview
                if(list.size() == 1){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_LONG;
                    Toast toastMessage = Toast.makeText(context, "You must leave one song in your playlist" , duration);
                    toastMessage.show();
                    return true;
                }
                else{
                    SongObject removeSong = list.get(item.getItemId()-1);
                    adapter.remove(removeSong);
                    return true;
                }


        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        //((AdapterView.AdapterContextMenuInfo)menuInfo).id();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        SongObject itemSelected = list.get(pos);

        //switch to find which action to user would like from the context menu, send proper string to intent
        switch (item.getItemId()){
            case R.id.con_menu_play_vid:
                sendIntent(itemSelected.getUrl());
                return true;
            case R.id.con_menu_song_wiki:
                sendIntent(itemSelected.getWikiSong());
                return true;
            case R.id.con_menu_artist_wiki:
                sendIntent(itemSelected.getWikiArtist());
                return true;
        }

        return true;

    }

    public void addSongProtocol(){
        //build dialog box for add song inputs; assign variables to each input
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View add_song_view = getLayoutInflater().inflate(R.layout.add_song_dialog, null);
        final EditText songInput = (EditText) add_song_view.findViewById(R.id.add_song_input);
        final EditText artistInput = (EditText) add_song_view.findViewById(R.id.add_band_input);
        final EditText wikiSongInput = (EditText) add_song_view.findViewById(R.id.add_wikiSong_input);
        final EditText wikiArtistInput = (EditText) add_song_view.findViewById(R.id.add_wikiArtist_input);
        final EditText urlInput = (EditText) add_song_view.findViewById(R.id.add_url_input);
        Button enterSongButton = (Button) add_song_view.findViewById(R.id.enter_song_button);
        //build dialog and show
        builder.setView(add_song_view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        enterSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //build new song object to add
                SongObject newSong = new SongObject(songInput.getText().toString(), artistInput.getText().toString(),
                        wikiSongInput.getText().toString(), wikiArtistInput.getText().toString(), urlInput.getText().toString());

                //add song to adapter which will update list, kill the dialog box
                adapter.add(newSong);
                dialog.dismiss();
            }
        });
    }

    public void removeSongProtocol(MenuItem item){
        SubMenu submenu = item.getSubMenu();
        submenu.clear();
        int itemCount = 1;
        //populate submenu with songs to choose
        for(SongObject i: list){
            submenu.add(0,itemCount,submenu.NONE,i.getSong());
            itemCount++;
        }



    }



    public ListView.OnItemClickListener listShortClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            //send media url to intent
            SongObject itemSelected = list.get(pos);
            sendIntent(itemSelected.getUrl());
        }
    };
    //send intent to new activity
    public void sendIntent(String link){
        intent = new Intent(MainActivity.this, VideoPlayActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);

    }

    //prepopulate songs
    public void prepopulateSongs(ArrayList<SongObject> list){
        SongObject obj1 = new SongObject("Mr. Brownstone", "Guns n'Roses", "https://en.wikipedia.org/wiki/Mr._Brownstone",
                "https://en.wikipedia.org/wiki/Guns_N%27_Roses", "https://www.youtube.com/watch?v=KVYDnQwi3OQ");
        SongObject obj2 = new SongObject("Elderly woman behind a counter in a small town", "Pearl Jam",
                "https://en.wikipedia.org/wiki/Elderly_Woman_Behind_the_Counter_in_a_Small_Town",
                "https://en.wikipedia.org/wiki/Pearl_Jam", "https://www.youtube.com/watch?v=2z0cKtSlHOU");
        SongObject obj3 = new SongObject("Ooh la la", "The Faces", "https://en.wikipedia.org/wiki/Ooh_La_La_(Faces_song)",
                "https://en.wikipedia.org/wiki/Faces_(band)", "https://www.youtube.com/watch?v=1_xwnb3cymc");
        SongObject obj4 = new SongObject("San Tropez", "Pink Floyd", "https://en.wikipedia.org/wiki/San_Tropez_(song)",
                "https://en.wikipedia.org/wiki/Pink_Floyd", "https://www.youtube.com/watch?v=Cv5uuhkS4j8");
        SongObject obj5 = new SongObject("Intervention", "Arcade Fire", "https://en.wikipedia.org/wiki/Intervention_(song)",
                "https://en.wikipedia.org/wiki/Arcade_Fire","https://www.youtube.com/watch?v=ZO7ZWfvCjBE");

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);

    }
/*
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        outState.putParcelableArrayList("savedList", list);
        super.onSaveInstanceState(outState, outPersistentState);
    }*/
}
