package com.example.adiputra.movie_app;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    ImageButton btnMenu;
    public Context mContext;
    public View mAnchor;
    public static TextView tvList;
    public static TextView tvToolbar;
    public int flag;

    //JSON Parse
    //private  static final String ENDPOINT = "https://api.themoviedb.org/3/movie/550?api_key=a6e05a69bd85f4eba7ec8e9db66cd4ef";
    private static final String TOPRATE = "http://api.themoviedb.org/3/discover/movie?certification_country=US&certification=R&sort_by=vote_average.desc?&api_key=a6e05a69bd85f4eba7ec8e9db66cd4ef";
    private static final String POPULAR = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc?&api_key=a6e05a69bd85f4eba7ec8e9db66cd4ef";
    private static final String STARRED = "";
    //private static final String ENDPOINT = "https://kylewbanks.com/rest/posts.json";
    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //JSONParse --open--
        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
//        fetchPosts(1);
        //JSONParse --close--

        tvList = (TextView) findViewById(R.id.tvList);
        tvToolbar = (TextView) findViewById(R.id.tvToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder =new MenuBuilder(ListActivity.this);
                MenuInflater inflater = new MenuInflater(ListActivity.this);
                inflater.inflate(R.menu.main, menuBuilder);
                MenuPopupHelper popup = new MenuPopupHelper(ListActivity.this, menuBuilder, v);
                popup.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.one:
                                flag = 1;
                                fetchPosts(flag);
                                Toast.makeText(ListActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.two:
                                flag = 2;
                                fetchPosts(flag);
                                Toast.makeText(ListActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.three:
                                flag = 3;
                                fetchPosts(flag);
                                Toast.makeText(ListActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_LONG).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {}
                });

                popup.show();
            }
        });
    }

    private void fetchPosts(int flag) {
        if(flag==1){
            tvList.setText("");
            tvToolbar.setText("Top Rate Movie");
            StringRequest request = new StringRequest(Request.Method.GET, TOPRATE, onPostsLoaded, onPostsError);
            requestQueue.add(request);
        }else if(flag==2){
            tvList.setText("");
            tvToolbar.setText("Popular Movie");
            StringRequest request = new StringRequest(Request.Method.GET, POPULAR, onPostsLoaded, onPostsError);
            requestQueue.add(request);
        }else{
            tvList.setText("");
            tvToolbar.setText("Starred Movie");
            StringRequest request = new StringRequest(Request.Method.GET, STARRED, onPostsLoaded, onPostsError);
            requestQueue.add(request);
        }

    }

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("ListActivity", error.toString());
            tvList.append("Please Check Internet Connection\n");
        }
    };

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);
            Post posts = gson.fromJson(response, Post.class);
            Log.i("PostActivity", String.valueOf(posts.page)+" "+posts.results.toString());
            String arr [] = posts.results.toString().split(",");
            int i=1;
            for(String t : arr){
                tvList.append(i+" - "+t+"\n\n");
                i++;
            }
            //String newstr = posts.results.toString().replaceAll("[,]", "");
            //tvList.append(newstr +"\n\n");

                //List<Post> posts = new ArrayList<>(Arrays.asList(mcArray));
                //List<Post> posts = Arrays.asList(gson.fromJson(response, Post[].class));
                //final Post posts = gson.fromJson(response, Post.class);

//                List<Post> posts = null;
//                try {
//                    posts = getList(response);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

        }

    };

}


