package com.knight.jone.mySuperDemo.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class VolleyTest {

    private RequestQueue requestQueue;

    private static class VolleyTestHolder {
        static VolleyTest INSTANCE = new VolleyTest();
    }

    public void init(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
    }

    public void stop() {
        if (requestQueue == null) {
            return;
        }
        requestQueue.stop();
    }

    public VolleyTest getIntent() {
        return VolleyTestHolder.INSTANCE;
    }

    public void stringRequest(Context context, int method, String url,
                              Response.Listener<String> listener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(method, url, listener, errorListener);
        requestQueue.add(stringRequest);
    }

    public void jsonRequest(Context context,
                            int method, String url, JSONObject jsonRequest,
                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url,
                jsonRequest, listener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void imageRequest(String url, Response.Listener<Bitmap> listener, int maxWidth, int maxHeight,
                             Bitmap.Config decodeConfig, Response.ErrorListener errorListener) {
        ImageRequest imageRequest = new ImageRequest(url, listener, maxWidth,
                maxHeight, decodeConfig, errorListener);
        requestQueue.add(imageRequest);
    }
}
