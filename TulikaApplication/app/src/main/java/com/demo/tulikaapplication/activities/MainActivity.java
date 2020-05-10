package com.demo.tulikaapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;

import android.view.Menu;

import android.view.MenuItem;
import android.view.View;

import com.demo.tulikaapplication.R;
import com.demo.tulikaapplication.adapters.ContentAdapter;
import com.demo.tulikaapplication.models.ContentItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private RecyclerView content_lv;
private ArrayList<ContentItems> contentList=new ArrayList<>();

    GridLayoutManager MyLayoutManager;
    ContentAdapter content_ada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


        }
    }

    public void init(){
       content_lv=findViewById(R.id.content_list);
        for(int i=1;i<4;i++){
            if(i==1){
                fetchData("pageOne.json");
            }else
            if(i==2){
                fetchData("pageTwo.json");

            }else  if(i==3){
                fetchData("pageThree.json");

            }

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                content_ada.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                content_ada.getFilter().filter(newText);
                return true;
            }
        });
    }


    private void fetchData(String json_str){
        try{
            final JSONObject job = new JSONObject(loadJSONFromAsset(json_str));
            JSONObject page_obj=job.getJSONObject("page");
            getSupportActionBar().setTitle(page_obj.getString("title"));
            JSONObject content_obj=page_obj.getJSONObject("content-items");
            JSONArray content_arr=content_obj.getJSONArray("content");
            for(int i=0;i<content_arr.length();i++){
                JSONObject data=content_arr.getJSONObject(i);
                contentList.add(new ContentItems(data.getString("name"),data.getString("poster-image")));
                content_lv.setHasFixedSize(true);
                int orientation = this.getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    MyLayoutManager = new GridLayoutManager(MainActivity.this,3);
                } else {
                    MyLayoutManager = new GridLayoutManager(MainActivity.this,4);
                }
                MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                if (contentList.size() > 0 & content_lv != null) {
                    content_ada=new ContentAdapter(contentList,MainActivity.this);
                    content_lv.setAdapter(content_ada);
                }
                content_lv.setLayoutManager(MyLayoutManager);

            }

        }catch (Exception e){

        }
    }



    public String loadJSONFromAsset(String page_n) {
        String json = null;
        try {
            InputStream is = getAssets().open(page_n);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
       content_lv.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));

        } else {
            content_lv.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        }
    }

}
