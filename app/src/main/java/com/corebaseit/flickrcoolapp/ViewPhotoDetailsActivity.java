package com.corebaseit.flickrcoolapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by vbevia on 15/07/16.
 */

public class ViewPhotoDetailsActivity extends AppCompatActivity {

    private String TAG_GET_PICTURE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_details);

        Bundle extras = getIntent().getExtras();
        TAG_GET_PICTURE_URL = extras.getString("MYPHOTP");


        ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
        Picasso.with(this)
                .load(TAG_GET_PICTURE_URL)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photoImage);
    }


}
