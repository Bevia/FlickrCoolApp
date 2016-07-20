package com.corebaseit.flickrcoolapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corebaseit.flickrcoolapp.InternetConnectivityCheker;
import com.corebaseit.flickrcoolapp.R;
import com.corebaseit.flickrcoolapp.ViewPhotoDetailsActivity;
import com.corebaseit.flickrcoolapp.models.Photo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vbevia on 15/07/16.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
    private Context context;
    LayoutInflater inflater;
    private List<Photo> photos;
    final int MAX_WIDTH = 430;
    final int MAX_HEIGHT = 620;
    private String EXTRA_PHOTO_TRANSFER = "PHOTO_URL";
    private String EXTRA_TEXT_TRANSFER = "TITLE_JSON";
    private final InternetConnectivityCheker internetConnectivityCheker =
            new InternetConnectivityCheker();

    public PhotoAdapter(FragmentActivity context, List<Photo> photos) {

        this.photos = photos;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.photo_list_item, parent, false);
        PhotoViewHolder flickerImageViewHolder = new PhotoViewHolder(view);

        return flickerImageViewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {

        final Photo photo = photos.get(position);
        Picasso.with(context)
                .load(photo.getUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerInside()
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (internetConnectivityCheker.isOnline(context)) {
                    Intent intent = new Intent(context, ViewPhotoDetailsActivity.class);
                    intent.putExtra(EXTRA_PHOTO_TRANSFER, photo.getUrl());
                    intent.putExtra(EXTRA_TEXT_TRANSFER, photo.getTitle());
                    context.startActivity(intent);

                    } else {
                        internetConnectivityCheker.showNoInternetConnectionAlertDialog(context);
                    }
                }
            });

        holder.textTitle.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        return (null != photos) ? photos.size() : 0;
    }
}
