package com.corebaseit.flickrcoolapp.restful;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.corebaseit.flickrcoolapp.FlickrAPI.Keys;

import org.json.JSONObject;

/**
 * Created by vbevia on 15/07/16.
 */

public class SearchJSONRequest extends JsonObjectRequest {

    private static String URL = getURL();
    private static final int MY_SOCKET_TIMEOUT_MS = 30000;

    public static String getURL() {
        return buildFlickrUri();
    }

    private static String buildFlickrUri() {

        StringBuffer sb = new StringBuffer("https://api.flickr.com/services/rest/?method=flickr.photos.search");
        sb.append("&api_key=" + Keys.getApiKey());
        sb.append("&format=" + "json&nojsoncallback=1");
        sb.append("&tags=" + "%s");
        sb.append("&extras=" + "url_z");
        sb.append("&per_page=500,tags");

        sb.toString();
        return sb.toString();
    }

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
