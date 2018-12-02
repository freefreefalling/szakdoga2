package hu.oe.szakdolgozat.n46god.ezerszigethorgszt.ertesitesFogadasa;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;
import java.util.HashMap;

import hu.oe.szakdolgozat.n46god.ezerszigethorgszt.segedOsztalyok.Constant;
import hu.oe.szakdolgozat.n46god.ezerszigethorgszt.segedOsztalyok.HttpKeres;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyFirebaseInstantService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MyfirebaseToken", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(final String token) {
        HashMap h = new HashMap<String, String>();
        h.put("token", token);
        new HttpKeres().adatKuldese(HttpKeres.PHP_URL + "i/telefonRegisztraalasa", h);
    }
}
