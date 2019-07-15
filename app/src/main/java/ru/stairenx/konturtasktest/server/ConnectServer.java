package ru.stairenx.konturtasktest.server;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;

public class ConnectServer {

    public static void query(Context context, final ResultResponse resultResponse){
        AsyncHttpClient client = new AsyncHttpClient();
        JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                resultResponse.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                resultResponse.onFail();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        };
        client.setThreadPool(Executors.newSingleThreadExecutor());
        client.get(context,LinksAPI.getResourse1(),responseHandler);
        client.get(context,LinksAPI.getResourse2(),responseHandler);
        client.get(context,LinksAPI.getResourse3(),responseHandler);
    }

    public interface ResultResponse{
        void onSuccess(JSONArray jsonArray);
        void onFail();
    }
}
