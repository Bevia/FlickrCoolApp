package com.corebaseit.flickrcoolapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by vbevia on 15/07/16.
 */

public class ViewPhotoDetailsActivity extends AppCompatActivity {

    private String TAG_GET_PICTURE_URL;
    private String EXTRA_PHOTO_TRANSFER = "PHOTO_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_details);

        Bundle extras = getIntent().getExtras();
        TAG_GET_PICTURE_URL = extras.getString(EXTRA_PHOTO_TRANSFER);


        Toast.makeText(ViewPhotoDetailsActivity.this, "the photo is: "  +  TAG_GET_PICTURE_URL, Toast.LENGTH_SHORT).show();

        ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
        Picasso.with(this)
                .load(TAG_GET_PICTURE_URL)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photoImage);
    }


}
