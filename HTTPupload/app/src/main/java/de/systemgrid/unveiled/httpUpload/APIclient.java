package de.systemgrid.unveiled.httpUpload;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

/**
 * Created by D062403 on 19.05.2016.
 */
public class APIclient {

    private AsyncHttpClient client;
    private PersistentCookieStore cookieStore;
    private Context context;

    public APIclient(Context context) {

        this.context = context;

        client = new AsyncHttpClient();

        cookieStore = new PersistentCookieStore(context);
        client.setCookieStore(cookieStore);
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return Config.API_URL + relativeUrl;
    }
}
