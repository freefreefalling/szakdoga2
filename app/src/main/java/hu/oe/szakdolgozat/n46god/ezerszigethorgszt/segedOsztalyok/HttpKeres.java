package hu.oe.szakdolgozat.n46god.ezerszigethorgszt.segedOsztalyok;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpKeres{
    private final Handler handler;
    public IKeszVan keszInterface;

    public static final String PHP_URL = "http://szakdolgozat.ezerszigetto.hu/i/";
    public static final String JAVA_URL = "http://szakdolgozat.ezerszigetto.hu/";

    public HttpKeres() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public String adatKuldese(String url, final HashMap<String, String> adatok) {
        String valasz = "";
        
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                FormBody.Builder formBody = new FormBody.Builder();

                for (HashMap.Entry<String, String> adat : adatok.entrySet()) {
                    formBody.add(adat.getKey(), adat.getValue());
                }

                RequestBody body = new FormBody.Builder().build();

                Request request = new Request.Builder()
                        .url(Constant.TOKEN_REG_URL)
                        .post(body)
                        .build();

                try {
                    Response valasz = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        return valasz;
    }

    public void adatKerese(final String url, final HashMap<String, String> adatok) {
        Thread t = new Thread() {
            @Override
            public void run() {
               super.run();
                try {
                    URL myURL = new URL(url);
                    String string = "";
                    HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(30000);
                    conn.setReadTimeout(30000);
                    conn.connect();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream is = conn.getInputStream();
                        BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                        StringBuilder sBuilder = new StringBuilder();

                        String line = null;
                        while ((line = bReader.readLine()) != null) {
                            sBuilder.append(line + "\n");
                        }

                        is.close();
                        string = sBuilder.toString();

                        kesz(string);
                    }
                    else {
                        //postError("STATUS ERROR: " + conn.getResponseMessage());
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } ;
        t.start();
    }

    public void adatKerese(String url) {
        adatKerese(url, null);
    }

    private void kesz(final String string) {
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                if (keszInterface != null) {
                    keszInterface.keszVan(string);
                }
            }
        });
    }

    public void interfaceHozzaAd(IKeszVan i){
        keszInterface = i;
    }
}
