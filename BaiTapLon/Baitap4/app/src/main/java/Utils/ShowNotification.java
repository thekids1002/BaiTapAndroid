package Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.example.baitap4.MainActivity;

public class ShowNotification extends Service {
    // Class này không dùng
    // Class này không dùng
    // Class này không dùng
    private final static String TAG = "ShowNotification";

    @Override
    public void onCreate() {
        showNoti();
        super.onCreate();
    }
    public void onDestroy () {
        Log. e ( TAG , "onDestroy" ) ;
        super .onDestroy() ;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    public void showNoti(){
        Intent mainIntent = new Intent(this, MainActivity.class);

        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification noti = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle("HELLO " + System.currentTimeMillis())
                .setContentText("PLEASE CHECK WE HAVE UPDATED NEWS")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setTicker("ticker message")
                .setWhen(System.currentTimeMillis())
                .build();

        notificationManager.notify(0, noti);

        Log.i(TAG, "Notification created");
    }
}