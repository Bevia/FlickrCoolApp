package com.corebaseit.flickrcoolapp.restful;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by vbevia on 15/07/16.
 */

public class SearchJSONRequest extends JsonObjectRequest {
    private static final String URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=370fcc38fea136afa89f2bfcf4ca52d5&format=json&nojsoncallback=1&tags=%s&extras=url_z&per_page=500,tags";

    private static final int MY_SOCKET_TIMEOUT_MS = 30000;

    public SearchJSONRequest(String tags, Listener<JSONObject> listener, ErrorListener errorListener) {
        super(Method.GET, String.format(URL, tags), listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        setTag(SearchJSONRequest.class.getName());

        Log.e(SearchJSONRequest.class.getName(), "url : " + getUrl());
    }

    @Override
    public int getMethod() {
        return Method.GET;
    }
}
