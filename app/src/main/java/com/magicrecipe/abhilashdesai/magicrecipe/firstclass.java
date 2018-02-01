package com.magicrecipe.abhilashdesai.magicrecipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import org.cryse.widget.persistentsearch.PersistentSearchView;
import org.cryse.widget.persistentsearch.SearchItem;

/**
 * Created by Abhilash on 31-01-2018.
 */

public class firstclass extends Activity {
String str;
    Button b1;
    PersistentSearchView e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        e1=(PersistentSearchView)findViewById(R.id.sbar);
       e1.setSearchListener(new PersistentSearchView.SearchListener() {
           @Override
           public boolean onSuggestion(SearchItem searchItem) {
               return false;
           }

           @Override
           public void onSearchCleared() {

           }

           @Override
           public void onSearchTermChanged(String term) {

           }

           @Override
           public void onSearch(String query) {
               str=e1.getSearchText().toString();
               Intent i = new Intent(getApplicationContext(),MainActivity.class);

               i.putExtra("EXTRA_SESSION_ID", ""+str);
               startActivity(i);
           }

           @Override
           public void onSearchEditOpened() {

           }

           @Override
           public void onSearchEditClosed() {

           }

           @Override
           public boolean onSearchEditBackPressed() {
               return false;
           }

           @Override
           public void onSearchExit() {

           }
       });



    }
}