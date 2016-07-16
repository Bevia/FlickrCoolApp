package com.corebaseit.flickrcoolapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vbevia on 15/07/16.
 */

public class Photos {

    @Expose(serialize = true)
    @SerializedName("total")
    private int total;

    @Expose(serialize = true)
    @SerializedName("photo")
    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> title) {
        this.photos = title;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
