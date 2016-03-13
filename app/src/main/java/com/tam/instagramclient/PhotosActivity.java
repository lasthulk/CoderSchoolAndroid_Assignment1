package com.tam.instagramclient;

import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        photos = new ArrayList<InstagramPhoto>();
        photosAdapter = new InstagramPhotosAdapter(this, photos);
        final ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(photosAdapter);
        fetchPopularPhotos();
    }

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

                    for (int i = 0; i < photosJson.length(); i++) {
                        photoJson = photosJson.getJSONObject(i);
                        photo = new InstagramPhoto();
                        photo.setUserName(photoJson.getJSONObject("user").getString("username"));
                        if (photoJson.optJSONObject("caption") != null) {
                            photo.setCaption(photoJson.getJSONObject("caption").getString("text"));
                        }
                        photo.setImageUrl(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setImageHeight(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        photo.setLikesContent(photoJson.getJSONObject("likes").getInt("count"));
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
}
