package com.chichaykin.testandroidapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";
    private Fragment2 fragment2;
    private MatrixJob matrixJob;
    private Subscription subscription = Subscriptions.unsubscribed();
    final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            if (binder instanceof JobService.LocalBinder) {
                matrixJob = ((JobService.LocalBinder) binder).getService();
                Result result = matrixJob.getData();
                fragment2.setData(result);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            matrixJob = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment2 = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.result_fragment);
        bindService(new Intent(this, JobService.class), connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    @Override
    public void onClick() {
        subscription = matrixJob.calculateMatrix()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (!fragment2.isDetached()) {
                        fragment2.setData(result);
                    }
                }, error -> Log.e(TAG, error.toString()));
    }


}
