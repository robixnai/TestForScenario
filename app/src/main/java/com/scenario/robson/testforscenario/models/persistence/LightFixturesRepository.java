package com.scenario.robson.testforscenario.models.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.scenario.robson.testforscenario.models.entities.LightFixture;
import com.scenario.robson.testforscenario.storages.sqlite.DatabaseHelper;
import com.scenario.robson.testforscenario.storages.sqlite.contract.LightFixtureContract;
import com.scenario.robson.testforscenario.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robson on 18/01/16.
 */
public class LightFixturesRepository {

    private static class Singleton {
        public static final LightFixturesRepository INSTANCE = new LightFixturesRepository();
    }

    private LightFixturesRepository() {
        super();
    }

    public static LightFixturesRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void save(LightFixture LightFixture) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(LightFixtureContract.TABLE, null, LightFixtureContract.getContentValues(LightFixture));
        db.close();
        helper.close();
    }

    public void update(LightFixture lightFixture) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        String where = LightFixtureContract.ID + " = ?";
        String[] args = {lightFixture.getId().toString()};
        db.update(LightFixtureContract.TABLE, LightFixtureContract.getContentValues(lightFixture), where, args);
        db.close();
        helper.close();
    }

    public List<LightFixture> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(LightFixtureContract.TABLE, LightFixtureContract.COLUNS, null, null, null, null, LightFixtureContract.NAME);
        List<LightFixture> lightFixtures = LightFixtureContract.bindList(cursor);
        db.close();
        helper.close();
        return lightFixtures;
    }

    public LightFixture getLightFixture(LightFixture lightFixture) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<String> fields = new ArrayList<String>();
        ArrayList<String> args = new ArrayList<String>();
        if (lightFixture.getId() != null) {
            fields.add(LightFixtureContract.ID + " = ?");
            args.add(lightFixture.getId().toString());
        }
        if (lightFixture.getName() != null) {
            fields.add(LightFixtureContract.NAME + " = ?");
            args.add(lightFixture.getName());
        }
        String[] selectionFields = new String[fields.size()];
        selectionFields = fields.toArray(selectionFields);
        String selection = AppUtil.implodeArray(selectionFields, " AND ");
        String[] selectionArgs = new String[args.size()];
        selectionArgs = args.toArray(selectionArgs);

        Cursor cursor = db.query(LightFixtureContract.TABLE, LightFixtureContract.COLUNS, selection, selectionArgs, null, null, LightFixtureContract.NAME);
        LightFixture hasLightFixture = LightFixtureContract.bind(cursor);
        db.close();
        helper.close();
        return hasLightFixture;
    }

}
