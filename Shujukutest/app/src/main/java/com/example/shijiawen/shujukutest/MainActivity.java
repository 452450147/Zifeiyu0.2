package com.example.shijiawen.shujukutest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.litepal.LitePal;

public class MainActivity extends android.support.multidex.MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}

