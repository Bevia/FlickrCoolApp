package com.corebaseit.flickrcoolapp.models;

/**
 * Created by vbevia on 31/12/15.
 */
import android.graphics.Bitmap;

public class FavoritesDbModel {

    private long id;
    private String name;
    private String image;
    private Bitmap bmp;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public Bitmap getBmp() {
        return bmp;
    }
    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }
}
