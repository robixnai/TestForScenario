package com.scenario.robson.testforscenario.storages.sqlite.contract;

import android.content.ContentValues;
import android.database.Cursor;

import com.scenario.robson.testforscenario.models.entities.LightFixture;
import com.scenario.robson.testforscenario.models.entities.Module;

import java.util.ArrayList;
import java.util.List;

public class LightFixtureContract {

    public static final String TABLE = "light_fixture";
    public static final String ID = "id";
    public static final String ID_LightFixture = "id_LightFixture";
    public static final String NAME = "name";

    public static final String[] COLUNS = {ID, ID_LightFixture, NAME};

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(ID_LightFixture + " INTEGER NULL, ");
        sql.append(NAME + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(LightFixture lightFixture) {
        ContentValues content = new ContentValues();
        content.put(ID, lightFixture.getId());
        content.put(ID_LightFixture, lightFixture.getModule().getId());
        content.put(NAME, lightFixture.getName());
        return content;
    }

    public static LightFixture bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            LightFixture lightFixture = new LightFixture();
            lightFixture.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            Module module = new Module();
            module.setId(cursor.getInt(cursor.getColumnIndex(ID_LightFixture)));
            lightFixture.setModule(module);
            lightFixture.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            return lightFixture;
        }
        return null;
    }

    public static List<LightFixture> bindList(Cursor cursor) {
        final List<LightFixture> lightFixtures = new ArrayList<LightFixture>();
        while (cursor.moveToNext()) {
            lightFixtures.add(bind(cursor));
        }
        return lightFixtures;
    }

}
