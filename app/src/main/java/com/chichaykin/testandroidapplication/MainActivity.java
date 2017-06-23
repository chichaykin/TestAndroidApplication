package com.chichaykin.testandroidapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import rx.Observable;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private Fragment2 fragment2;
    private MatrixJob matrixJob;

    final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            if (binder instanceof MatrixJob) {
                matrixJob = ((MatrixJob) binder);
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
        mIsBound = true;
    }

    private boolean mIsBound;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(connection);
            mIsBound = false;
        }
    }

    @Override
    public void onClick() {
        Observable<Result> o = matrixJob.calculateMatrix();
    }


}
