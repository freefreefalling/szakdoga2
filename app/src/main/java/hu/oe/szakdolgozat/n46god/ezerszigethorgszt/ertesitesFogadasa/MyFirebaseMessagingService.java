package hu.oe.szakdolgozat.n46god.ezerszigethorgszt.ertesitesFogadasa;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String body = remoteMessage.getNotification().getBody();
        String title = remoteMessage.getNotification().getTitle();

        new MyNotificationManager(getApplicationContext())
                .displayNotification(title, body);

    }
}
