package com.ibnsaad.thedcc.utils;

import android.app.Activity;
import android.net.NetworkInfo;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.ibnsaad.thedcc.listeners.ConnectivityListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Connectivity {

    ConnectivityListener connectivityListener;
    private boolean hasInternet;
    private Disposable networkDisposable;
    private Disposable internetDisposable;
    private String connectionType;
    private Activity activity;
    private Connectivity connectivity;

    public Connectivity(Activity activity, ConnectivityListener connectivityListener) {
        this.connectivityListener = connectivityListener;
        this.activity = activity;
        setConnectionListener();
    }

    private void setConnectionListener() {

        networkDisposable = ReactiveNetwork.observeNetworkConnectivity(activity.getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    Log.d("ssssssssssssss", connectivity.toString());
                    final NetworkInfo.State state = connectivity.state();
                    connectionType = connectivity.typeName();   // WIFI OR MOBILE
                    //  tvConnectivityStatus.setText(String.format("state: %s, typeName: %s", state, name));
                    Log.d("ssssssssssssss", "onCreate: " + String.format("state: %s, typeName: %s", state, connectionType));
                });

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> {
                    hasInternet = isConnected;
                    //    tvInternetStatus.setText(isConnected.toString());
                    if (!isConnected) {

                        connectivityListener.isConnected(false);
                        hasInternet = false;
                    }else {
                        connectivityListener.isConnected(true);
                        hasInternet = true;
                    }
                    Log.d("ssssssssssssss", "onCreate: " + isConnected.toString());
                });
    }

    public boolean isHasInternet() {
        return hasInternet;
    }

    public String getConnectionType() {
        return connectionType;
    }
}
