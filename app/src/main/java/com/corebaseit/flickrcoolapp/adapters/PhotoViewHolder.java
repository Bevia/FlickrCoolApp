package com.corebaseit.flickrcoolapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.corebaseit.flickrcoolapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vbevia on 15/07/16.
 */
public class PhotoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.textTitle) TextView textTitle;

    public PhotoViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}
