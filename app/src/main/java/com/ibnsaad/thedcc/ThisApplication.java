package com.ibnsaad.thedcc;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;


public class ThisApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);

    }

}
