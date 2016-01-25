package com.scenario.robson.testforscenario.storages.sqlite.contract;

import android.content.ContentValues;
import android.database.Cursor;

import com.scenario.robson.testforscenario.models.entities.Ambient;
import com.scenario.robson.testforscenario.models.entities.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robson on 23/01/16.
 */
public class AmbientAmbientContract {

    public static final String TABLE = "ambient_ambient";
    public static final String ID = "id";
    public static final String ID_AMBIENT = "id_ambient";

    public static final String[] COLUNS = {ID, ID_AMBIENT};

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER, ");
        sql.append(ID_AMBIENT + " INTEGER ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Integer ambientId, Ambient ambient) {
        ContentValues content = new ContentValues();
        content.put(ID, ambientId);
        content.put(ID_AMBIENT, ambient.getId());
        return content;
    }

    public static Ambient bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Ambient ambient = new Ambient();
            ambient.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            ambient.setId((cursor.getInt(cursor.getColumnIndex(ID_AMBIENT))));
            return ambient;
        }
        return null;
    }

    public static List<Ambient> bindList(Cursor cursor) {
        final List<Ambient> ambients = new ArrayList<Ambient>();
        while (cursor.moveToNext()) {
            ambients.add(bind(cursor));
        }
        return ambients;
    }

}
