package com.tam.instagramclient;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {
    private final static String TAG = "PhotosActivity";
    private final static String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> photos = null;
    private InstagramPhotosAdapter photosAdapter;
    private SwipeRefreshLayout swipeContainer = null;

    private void fetchPopularPhotos() {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray photosJson = response.getJSONArray("data");
                    JSONObject photoJson = null;
                    InstagramPhoto photo = null;
                    photosAdapter.clear();
                    for (int i = 0; i < photosJson.length(); i++) {
                        photoJson = photosJson.getJSONObject(i);
                        photo = new InstagramPhoto();
                        photo.setUserName(photoJson.getJSONObject("user").getString("username"));
                        photo.setProfilePicture(photoJson.getJSONObject("user").getString("profile_picture"));
                        if (photoJson.optJSONObject("caption") != null) {
                            photo.setCaption(photoJson.getJSONObject("caption").optString("text"));
                        }
                        photo.setImageUrl(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setImageHeight(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        photo.setLikesContent(photoJson.getJSONObject("likes").getInt("count"));
                        photo.setCreatedAt(photoJson.getLong("created_time"));
                        photo.setLikesCount(photoJson.getJSONObject("likes").getInt("count"));
                        photos.add(photo);
                    }
                    photosAdapter.notifyDataSetChanged();
                } catch (JSONException ex) {
                    Log.e(TAG, ex.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, responseString);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        final ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        photos = new ArrayList<InstagramPhoto>();
        photosAdapter = new InstagramPhotosAdapter(this, photos);
        lvPhotos.setAdapter(photosAdapter);
        fetchPopularPhotos();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

}
