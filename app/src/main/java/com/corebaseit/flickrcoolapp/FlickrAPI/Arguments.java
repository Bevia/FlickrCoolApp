package com.corebaseit.flickrcoolapp.FlickrAPI;

/**
 * Created by vbevia on 15/07/16.
 */
public class Arguments {

    //per_page (Optional)
    private static final int PER_PAGE = 500;
    private static final String EXTRAS = "url_z";

    //To return an API response in JSON format, send a parameter "format" in the request with a value of "json".
    //If you just want the raw JSON, with no function wrapper, add the parameter nojsoncallback with a value of 1 to your request.
    private static final String FORMAT = "json&nojsoncallback=1";

    public static int getPerPage() {
        return PER_PAGE;
    }

    public static String getEXTRAS() {
        return EXTRAS;
    }

    public static String getFORMAT() {
        return FORMAT;
    }
}
