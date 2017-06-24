package com.chichaykin.testandroidapplication.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.chichaykin.testandroidapplication.CalculationProvider;
import com.chichaykin.testandroidapplication.NetworkModule;
import com.chichaykin.testandroidapplication.R;
import com.chichaykin.testandroidapplication.api.Data;
import com.chichaykin.testandroidapplication.api.NetworkApi;
import com.chichaykin.testandroidapplication.model.Result;
import com.chichaykin.testandroidapplication.ui.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rx.Observable;

import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;
import static rx.Emitter.BackpressureMode.LATEST;

/**
 * Do calculation job and store calculated result in JobService.result field
 */
public class JobService extends Service implements MatrixJob {

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder localBinder = new LocalBinder();
    private NotificationManager notificationManager;
    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private final int NOTIFICATION = R.string.local_service_started;
    private Result result;
    private NetworkApi networkApi;
    private CalculationProvider calcProvider;

    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();

        //TODO: move instantiating to DI
        networkApi = new NetworkModule().provides();
        calcProvider = new CalculationProvider();
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        notificationManager.cancel(NOTIFICATION);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        return START_NOT_STICKY;
    }

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.local_service_started);

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.star_on)  // the status icon
                .setTicker(text)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(getText(R.string.local_service_label))  // the label of the entry
                .setContentText(text)  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        // Send the notification.
        notificationManager.notify(NOTIFICATION, notification);
    }

    @Override
    public Result getData() {
        return result;
    }

    @Override
    public Observable<Result> calculateMatrix() {
        Observable<Result> observable;
        if (true) {
            // Note! Load data from asset to avoid parsing html
            // to download file from dropbox.
            observable = readDataFromAssetsAndCalculate();
        } else {
            observable = networkApi.getData()
                    .flatMap(this::getResult);
        }
        return observable
                .doOnNext(result -> this.result = result); //cache result
    }

    private Observable<Result> readDataFromAssetsAndCalculate() {
        return Observable.create(resultEmitter -> {
            Log.d(TAG, "start downloading file...");
            InputStreamReader reader = null;
            try  {
                reader = new InputStreamReader(getAssets().open("mapdata.json"));
                Gson gson = new GsonBuilder().create();
                Data data = gson.fromJson(reader, Data.class);

                resultEmitter.onNext(calcProvider.calculate(data));
                resultEmitter.onCompleted();
            } catch (Exception e) {
                e.printStackTrace();
                resultEmitter.onError(e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, LATEST);
    }

    private Observable<Result> getResult(Data data) {
        return Observable.fromCallable(() -> calcProvider.calculate(data));
    }


    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        public MatrixJob getService() {
            return JobService.this;
        }
    }
}
