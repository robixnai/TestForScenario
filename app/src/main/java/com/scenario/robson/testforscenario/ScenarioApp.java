package com.scenario.robson.testforscenario;

import android.app.Application;

import com.scenario.robson.testforscenario.utils.AppUtil;

/**
 * Created by robson on 18/01/16.
 */
public class ScenarioApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.CONTEXT = getApplicationContext();
    }
}