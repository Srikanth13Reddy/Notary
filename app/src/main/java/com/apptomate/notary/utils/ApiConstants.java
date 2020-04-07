package com.apptomate.notary.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.format.DateUtils;
import android.util.Pair;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ApiConstants
{
    public static final String BaseUrl="https://notaryapi.herokuapp.com/";

   public static ProgressDialog showProgressDialog(Context context,String message)
    {
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage(message);
        pd.setCancelable(false);
        return pd;
    }

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static String time(String s)
    {
        String time_="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            long time = sdf.parse(s).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            time_=ago.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time_;
    }

    public static void parseVolleyError(Context context,VolleyError error) {
        if ( error instanceof NoConnectionError) {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            //This indicates that the reuest has either time out or there is no connection
        } else if (error instanceof TimeoutError ) {
            //Error indicating that there was an Authentication Failure while performing the request
            Toast.makeText(context, "Poor internet", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            //Indicates that the server responded with a error response
            Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NetworkError) {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            //Indicates that there was network error while performing the request
        } else if (error instanceof ParseError) {
            Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show();
            // Indicates that the server response could not be parsed
        }
    }

}
