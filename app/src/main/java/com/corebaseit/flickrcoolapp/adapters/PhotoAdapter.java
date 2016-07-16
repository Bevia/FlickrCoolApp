package com.corebaseit.flickrcoolapp.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corebaseit.flickrcoolapp.models.Photo;
import com.corebaseit.flickrcoolapp.R;
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

        holder.textTitle.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        return (null != photos) ? photos.size() : 0;
    }

    public Photo getPhoto(int position){
        return (photos != null ? photos.get(position) : null );
    }
}
