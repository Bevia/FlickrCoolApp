package com.corebaseit.flickrcoolapp.restful;

import com.corebaseit.flickrcoolapp.models.Photos;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vbevia on 15/07/16.
 */

public class SearchJSONResponse {

    @Expose(serialize = true)
    @SerializedName("photos")
    private Photos photos;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }
}