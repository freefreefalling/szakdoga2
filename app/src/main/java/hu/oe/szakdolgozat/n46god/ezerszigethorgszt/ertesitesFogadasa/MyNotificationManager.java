package hu.oe.szakdolgozat.n46god.ezerszigethorgszt.ertesitesFogadasa;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import hu.oe.szakdolgozat.n46god.ezerszigethorgszt.MainActivity;
import hu.oe.szakdolgozat.n46god.ezerszigethorgszt.R;
import hu.oe.szakdolgozat.n46god.ezerszigethorgszt.segedOsztalyok.Constant;

public class MyNotificationManager {
    private Context mCtx;
    private static MyNotificationManager mInstance;

 /*   private MyNotificationManager(Context context){
        mCtx = context;
    }

    public static synchronized MyNotificationManager getmInstance(Context context){
        if(mInstance == null){
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }*/
    public MyNotificationManager(Context context){
        mCtx = context;
    }
    public void displayNotification(String title, String body){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx, Constant.CHANNEL_ID)
                .setSmallIcon(R.drawable.sullo)
                .setSound(Uri.parse("android.resource://hu.oe.szakdolgozat.n46god.ezerszigethorgszt/" + R.raw.ertesites_hang))
                .setLights(Color.RED,1000, 1000)
                .setVibrate(new long[]{0, 500, 1000})
                .setContentTitle(title)
                .setContentText(body);
        Intent intent = new Intent(mCtx, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);

        if(mNotificationManager != null){
            mNotificationManager.notify(1, mBuilder.build());
        }
    }
}
