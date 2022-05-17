package Utils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.baitapnhom.baitap4.MainActivity;
import com.baitapnhom.baitap4.R;

public class ReminderBroadCast extends BroadcastReceiver {
    public static final String CHANNEL_ID = "notifyAppSelfy";
    public static final String CONTENT_TITLE = "Selfie a photo";
    public static final String CONTENT_TEXT = "Hey guy, please selfie a photo";
    public static final int NOTIFICATION_ID = 200;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentNoti = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentNoti, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(CONTENT_TITLE)
                .setContentText(CONTENT_TEXT)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}
