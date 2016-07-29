package com.corebaseit.flickrcoolapp;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.Target;
import com.corebaseit.flickrcoolapp.utils.InternetConnectivityCheker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vbevia on 15/07/16.
 */

public class ViewPhotoDetailsActivity extends AppCompatActivity {

    private final InternetConnectivityCheker internetConnectivityCheker =
            new InternetConnectivityCheker();

    private String TAG_GET_PICTURE_URL;
    private String TAG_GET_TITLE;
    private String EXTRA_TEXT_TRANSFER = "TITLE_JSON";
    private String EXTRA_PHOTO_TRANSFER = "PHOTO_URL";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.zoomOutImage)
    ImageView zoomOutImage;

    @BindView(R.id.imageFullSizeImage)
    ImageView photoImageFullSize;

    @BindView(R.id.photo_image)
    ImageView photoImage;

    @BindView(R.id.zoomImage)
    ImageView zoomImage;

    @BindView(R.id.titleText)
    TextView titleTextView;

    @BindView(R.id.fullSizeImage)
    FrameLayout fullSizeImage;

    @BindView(R.id.mainScrollView)
    ScrollView mainScrollView;

    @BindView(R.id.favoriteImageText)
    RelativeLayout favoriteImageText;

    private Context context;
    private View decorView;
    private String TAG = "permissions";
    private OrientationEventListener myOrientationEventListener;
    private static boolean PORTRAIT_MODE;
    private static boolean PORTRAIT_REVERSE_MODE;
    private static boolean LANDSCAPE_MODE;
    private static boolean LANDSCAPE_REVERSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_details);
        ButterKnife.bind(this);

        //add permission for Marshmallow
        isStoragePermissionGranted();

        Bundle extras = getIntent().getExtras();
        TAG_GET_PICTURE_URL = extras.getString(EXTRA_PHOTO_TRANSFER);
        TAG_GET_TITLE = extras.getString(EXTRA_TEXT_TRANSFER);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        final int MAX_WIDTH = 1463;
        final int MAX_HEIGHT = 2048;

        ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                view.setAlpha(0f);
                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(1400);
                fadeAnim.start();
            }
        };

        Glide.with(this)
                .load(TAG_GET_PICTURE_URL)
                .placeholder(R.drawable.placeholder)
                .animate(animationObject)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable>
                            target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        placeZoomImage();
                        titleTextView.setText(TAG_GET_TITLE);
                        return false;
                    }
                })
                .into(photoImage);

        Picasso.with(this)
                .load(TAG_GET_PICTURE_URL)
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerInside()
                .into(photoImageFullSize, new Callback() {
                    @Override
                    public void onSuccess() {
                        zoomOutImage.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                    }
                });

        myOrientationEventListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
            }
        };
        if (myOrientationEventListener.canDetectOrientation() == true) {
            Log.w("ORIENTATION", "myOrientationEventListener is disable");
            myOrientationEventListener.disable();
        }

        photoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fullSizeImage.setVisibility(View.VISIBLE);
                mainScrollView.setVisibility(View.GONE);
                /**
                 *   Moving to full SIZE Image, lets hide the toolbar and the navigation bar!
                 */
                toolbar.setVisibility(View.GONE);
                hideNavigationBar();

                if (android.provider.Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
                  /*  Toast.makeText(getApplicationContext(), "Auto Rotate is ON", Toast.LENGTH_SHORT).show();*/
                    Log.w("ORIENTATION", "Auto Rotate is ON");
                    rotateFullSizeImage();
                } else {
                    Toast.makeText(getApplicationContext(), "Auto Rotate is OFF", Toast.LENGTH_SHORT).show();
                    if (myOrientationEventListener.canDetectOrientation() == true) {
                        myOrientationEventListener.disable();
                        Log.w("ORIENTATION", "myOrientationEventListener is disable");
                    }
                }
            }
        });

        photoImageFullSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fullSizeImage.setVisibility(View.GONE);
                mainScrollView.setVisibility(View.VISIBLE);

                /**
                 *   Recover the toolbar and the navigation bar!
                 */
                toolbar.setVisibility(View.VISIBLE);
                decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(uiOptions);

                ViewPhotoDetailsActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                myOrientationEventListener.disable();

            }
        });

        favoriteImageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toastNoInternetConnection();
            }
        });
    }
    public void toastNoInternetConnection() {

        Toast toast = Toast.makeText(ViewPhotoDetailsActivity.this,"\nThis feature \nWill be available in next version ;)\n", Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        toast.setGravity(Gravity.CENTER, 0, 0);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();

    }

    /**
     * ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
     * ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
     * ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
     * ActivityInfo.SCREEN_ORIENTATION_USER
     * ActivityInfo.SCREEN_ORIENTATION_BEHIND
     * ActivityInfo.SCREEN_ORIENTATION_SENSOR
     * ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
     * ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
     * ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
     * ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
     * ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
     * ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
     */

    private void rotateFullSizeImage() {

        myOrientationEventListener = new OrientationEventListener(ViewPhotoDetailsActivity.this,
                SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == -1) {
                    //The device is flat: do nothing!
                } else {

                    if ((orientation < 45) || (orientation >= 315)) {
                        PORTRAIT_MODE = true;
                    } else {
                        PORTRAIT_MODE = false;
                    }
                    if ((orientation < 225) && (orientation >= 135)) {
                        PORTRAIT_REVERSE_MODE = true;
                    } else {
                        PORTRAIT_REVERSE_MODE = false;
                    }
                    if ((orientation < 315) && (orientation >= 225)) {
                        LANDSCAPE_MODE = true;
                    } else {
                        LANDSCAPE_MODE = false;
                    }
                    if ((orientation < 135) && (orientation > 45)) {
                        LANDSCAPE_REVERSE = true;
                    } else {
                        LANDSCAPE_REVERSE = false;
                    }

                   /* Log.w("ORIENTATION", orientation + " PORTRAIT_MODE = " + PORTRAIT_MODE +
                            "  " + PORTRAIT_REVERSE_MODE +
                            "  " + LANDSCAPE_MODE +
                            "  " + LANDSCAPE_REVERSE);*/

                    if (PORTRAIT_MODE == true) {
                        ViewPhotoDetailsActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                       /* Log.w("ORIENTATION", orientation + " PORTRAIT_MODE = " + "portrait");*/
                    }
                    if (PORTRAIT_REVERSE_MODE == true) {
                        ViewPhotoDetailsActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                       /* Log.w("ORIENTATION", orientation + " PORTRAIT_MODE = " + "portrait reverse");*/
                    }
                    if (LANDSCAPE_REVERSE == true) {
                        ViewPhotoDetailsActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                      /*  Log.w("ORIENTATION", orientation + " PORTRAIT_MODE = " + "landscape");*/
                    }
                    if (LANDSCAPE_MODE == true) {
                        ViewPhotoDetailsActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        /*Log.w("ORIENTATION", orientation + " PORTRAIT_MODE = " + "landscape reverse");*/
                    }
                }
            }
        };
        Log.w("Listener", " can detect orientation: " + myOrientationEventListener.canDetectOrientation() + " ");
        myOrientationEventListener.enable();
        Log.w("ORIENTATION", "myOrientationEventListener is enable");
    }

    private void placeZoomImage() {
        zoomImage.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem settingsMenuItem = menu.findItem(R.id.sharing);
        SpannableString s = new SpannableString(settingsMenuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.blue_stone)), 0, s.length(), 0);
        settingsMenuItem.setTitle(s);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sharing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sharing) {

            if (internetConnectivityCheker.isOnline(ViewPhotoDetailsActivity.this)) {
                shareImage();
            } else {
                internetConnectivityCheker.showNoInternetConnectionAlertDialog(ViewPhotoDetailsActivity.this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Share text and image over Social Networks...
     */

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    private Uri getBitmapFromAsset() {

        Drawable mDrawable = photoImageFullSize.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                mBitmap, "Image Description", null);

        Uri uri = Uri.parse(path);
        return uri;
    }

    private void shareImage() {

        String name = TAG_GET_TITLE;
        String text = "Tell me what you think about this great pic: " + name + " ,Cheers! ;)";

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "title");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = getBitmapFromAsset();

        /**
         * Share image and txt over any social sharing network
         */

        share.putExtra(Intent.EXTRA_TEXT, text);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image"));
    }

    public void hideNavigationBar() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions =
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
