package com.corebaseit.flickrcoolapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.corebaseit.flickrcoolapp.R;

/**
 * Created by vbevia on 15/07/16.
 */
public class PhotoViewHolder extends RecyclerView.ViewHolder {
    protected ImageView imageView;
    protected TextView textTitle;

    public PhotoViewHolder(View view) {
        super(view);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        textTitle = (TextView) view.findViewById(R.id.textTitle);
    }
}
