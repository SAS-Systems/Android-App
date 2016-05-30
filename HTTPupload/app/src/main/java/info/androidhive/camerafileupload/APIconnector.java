package info.androidhive.camerafileupload;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.Header;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by D062403 on 18.05.2016.
 */
public class APIconnector {

    private Context context = null;
    private APIclient client;

    public APIconnector(Context context) {

        this.context = context;
        this.client = new APIclient(context);
    }

    @SuppressWarnings("deprecation")
    public boolean login() {

        boolean result = false;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String username = prefs.getString("acc_username", "");
        String password = prefs.getString("acc_password", "");

        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        client.post("user/login", params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {

                loginFailed();
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {

                try {
                    JSONObject obj = new JSONObject(responseString);

                    if(statusCode == 200 && obj.getInt("error") == 0) {

                        loginSuccessful();
                        loadUserdata();

                        Log.v("login", String.valueOf(obj.getInt("error")));

                    }
                    else {

                        loginFailed();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    loginFailed();
                }
            }
        });

        return false;
    }

    public boolean loadUserdata() {

        client.get("user/me", null, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {

                //failded
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {

                try {
                    JSONObject obj = new JSONObject(responseString);

                    JSONObject userdata = obj.getJSONObject("userData");
                    Config.USER_ID = userdata.getInt("id");

                    Log.v("userid", String.valueOf(Config.USER_ID));


                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                    //failded
                }
            }
        });

        return true;
    }

    public void loginSuccessful() {

        Toast.makeText(context, context.getString(R.string.pref_toast_login_success), Toast.LENGTH_SHORT).show();
    }

    public void loginFailed() {

        Toast.makeText(context, context.getString(R.string.pref_toast_login_failed), Toast.LENGTH_SHORT).show();
    }
}
