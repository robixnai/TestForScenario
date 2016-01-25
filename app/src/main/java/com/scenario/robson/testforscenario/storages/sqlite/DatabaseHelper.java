package com.scenario.robson.testforscenario.storages.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.scenario.robson.testforscenario.storages.sqlite.contract.AmbientAmbientContract;
import com.scenario.robson.testforscenario.storages.sqlite.contract.AmbientContract;
import com.scenario.robson.testforscenario.storages.sqlite.contract.LightFixtureContract;
import com.scenario.robson.testforscenario.storages.sqlite.contract.ModuleContract;
import com.scenario.robson.testforscenario.storages.sqlite.contract.ProjectContract;

/**
 * Created by robson on 23/01/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "SCENARIO_DB";
    private static int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelper.BANCO_DADOS, null, DatabaseHelper.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProjectContract.createTable());
        db.execSQL(AmbientContract.createTable());
        db.execSQL(AmbientAmbientContract.createTable());
        db.execSQL(ModuleContract.createTable());
        db.execSQL(LightFixtureContract.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

}
