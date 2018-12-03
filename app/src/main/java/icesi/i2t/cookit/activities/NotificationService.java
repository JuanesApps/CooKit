package icesi.i2t.cookit.activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import icesi.i2t.cookit.R;

public class NotificationService extends Service {

    NotificationManager manager;
    FirebaseDatabase db;
    int idNotificacion = 1;
    public final static String CHANNEL_ID = "conFire";
    public final static String CHANNEL_NAME = "ConFire";
    public final static int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
    public String message;


    @Override
    public void onCreate() {
//        message =
        Log.e("5218162917917181", "Notifica3x2");
        crearNotificacion();
    }

    private void crearNotificacion() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Log.e("5218162917917181", "Notifica3x2");

        //Manejar notificaciones en OREO
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Se tiene que definir un Notificacion Channel
            //Crear tres constantes: CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE
            NotificationChannel canal = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,CHANNEL_IMPORTANCE);
            manager.createNotificationChannel(canal);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Actualizacion")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("El estado de sus ordenes ha cambiado")
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        manager.notify(idNotificacion,builder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
