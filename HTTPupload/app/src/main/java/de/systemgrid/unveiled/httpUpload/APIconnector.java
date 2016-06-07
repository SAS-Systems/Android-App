package de.systemgrid.unveiled.httpUpload;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

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

        client.post("app/user/login", params, new TextHttpResponseHandler() {

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

                        Config.USER_ID = obj.getInt("userId");
                        Config.UPLOAD_TOKEN = obj.getString("uploadToken");

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

    public void loginSuccessful() {

        Toast.makeText(context, context.getString(R.string.pref_toast_login_success), Toast.LENGTH_SHORT).show();
    }

    public void loginFailed() {

        Toast.makeText(context, context.getString(R.string.pref_toast_login_failed), Toast.LENGTH_SHORT).show();
    }
}
