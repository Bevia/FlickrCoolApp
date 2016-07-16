package com.corebaseit.flickrcoolapp.restful;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.corebaseit.flickrcoolapp.models.Photos;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbevia on 15/07/16.
 */
public class SearchJSONObjects extends VolleyResponseListener<SearchJSONResponse,JSONObject>
        implements Response.ErrorListener{

    public interface OnPhotosReceivedListener{
        public void OnPhotosReceived(Photos photos);
        public void OnError(VolleyError error);
    }

    private OnPhotosReceivedListener onPhotosReceivedListener;
    private RequestQueue queue;

    private String lastQuery;

    @Override
    public void onResult(SearchJSONResponse response) {
        saveLastRequest();
        if(onPhotosReceivedListener != null){
            onPhotosReceivedListener.OnPhotosReceived(response.getPhotos());
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        saveLastRequest();
    }

    private void saveLastRequest(){
        if(!TextUtils.isEmpty(lastQuery) && !FlickrCoolApp.historyList.contains(lastQuery)){
            FlickrCoolApp.historyList.add(lastQuery);
        }
    }

    public SearchJSONObjects(Context context, OnPhotosReceivedListener onPhotosReceivedListener){
        this.queue = Volley.newRequestQueue(context);
        this.onPhotosReceivedListener = onPhotosReceivedListener;

    }

    public void search(String tagsStr){
        stop();
        lastQuery = tagsStr.trim();
        queue.add(new SearchJSONRequest(TagsUtils.convertToTags(tagsStr), this, this));
    }

    public void stop(){
        queue.cancelAll(SearchJSONRequest.class.getName());
    }

    /**
     * Generate the necessary static nested classes:
     */
    public static class FlickrCoolApp extends Application {
        //    Temp search history store
        public static List<String> historyList = new ArrayList<>();
    }

    public static class TagsUtils {
        public static String convertToTags(String query){
            String[] tags = query.replaceAll(",", " ").split(" ");
            StringBuilder result = new StringBuilder();
            for(int i=0; i<tags.length; i++){
                result.append(tags[i].trim());
                if(i<tags.length-2){
                    result.append(",");
                }
            }
            return result.toString();
        }
    }
}
