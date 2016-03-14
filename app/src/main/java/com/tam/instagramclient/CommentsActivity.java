package com.tam.instagramclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentsActivity extends AppCompatActivity {

    @Bind(R.id.lvAllComments)
    ListView lvAllComments;

    public final static String PHOTO_POSITION = "PHOTO_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        int position = getIntent().getIntExtra(PHOTO_POSITION, 0);
        InstagramPhoto photo = PhotosActivity.photos.get(position);
        InstagramCommentsAdapter adapter = new InstagramCommentsAdapter(this, photo.getComments());
        lvAllComments.setAdapter(adapter);
    }
}
