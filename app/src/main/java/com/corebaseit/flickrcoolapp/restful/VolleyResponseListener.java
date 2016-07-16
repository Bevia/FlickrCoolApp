package com.corebaseit.flickrcoolapp.restful;

import android.util.Log;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.ParameterizedType;

/**
 * Created by vbevia on 15/07/16.
 */

public abstract class VolleyResponseListener<T, E> implements Listener<E>
{
    private Gson prettyGson;
    private Gson gson;

    @SuppressWarnings("unchecked")
    @Override
    public void onResponse(E response)
    {
        Log.d("VolleyResponseListener", "response : " + response);

        onResult(getGson().fromJson(response.toString(),
                (Class<T>)((ParameterizedType)getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0]));
    }

    public abstract void onResult(T response);

    private Gson getGson() {return getGson(true);}
    private Gson getGson(final boolean pretty)
    {
         GsonBuilder builder = new GsonBuilder();

        if(pretty)
        {
            if( prettyGson == null )
            {
                {
                    builder.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
                    prettyGson = builder.create();
                }
            }
            return prettyGson;
        } else
        {
            if( gson == null )
            {
                {
                    gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                }
            }
            return gson;
        }
    }
}