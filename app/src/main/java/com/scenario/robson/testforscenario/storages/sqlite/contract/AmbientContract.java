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
public class AmbientContract {

    public static final String TABLE = "ambient";
    public static final String ID = "id";
    public static final String ID_PROJECT = "id_project";
    public static final String NAME = "name";

    public static final String[] COLUNS = {ID, NAME, ID_PROJECT};

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(ID_PROJECT + " INTEGER, ");
        sql.append(NAME + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Ambient ambient) {
        ContentValues content = new ContentValues();
        content.put(ID, ambient.getId());
        content.put(ID_PROJECT, ambient.getProject().getId());
        content.put(NAME, ambient.getName());
        return content;
    }

    public static Ambient bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Ambient ambient = new Ambient();
            ambient.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            Project project = new Project();
            project.setId(cursor.getInt(cursor.getColumnIndex(ID_PROJECT)));
            ambient.setProject(project);
            ambient.setName(cursor.getString(cursor.getColumnIndex(NAME)));
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
