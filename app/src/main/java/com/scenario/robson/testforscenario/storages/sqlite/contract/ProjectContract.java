package com.scenario.robson.testforscenario.storages.sqlite.contract;


import android.content.ContentValues;
import android.database.Cursor;

import com.scenario.robson.testforscenario.models.entities.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robson on 23/01/16.
 */
public class ProjectContract {

    public static final String TABLE = "project";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IMAGE = "image";

    public static final String[] COLUNS = {ID, NAME, IMAGE};

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(IMAGE + " BLOB ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Project project) {
        ContentValues content = new ContentValues();
        content.put(ID, project.getId());
        content.put(NAME, project.getName());
        content.put(IMAGE, project.getImage());
        return content;
    }

    public static Project bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Project project = new Project();
            project.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            project.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            project.setImage(cursor.getBlob(cursor.getColumnIndex(IMAGE)));
            return project;
        }
        return null;
    }

    public static List<Project> bindList(Cursor cursor) {
        final List<Project> projects = new ArrayList<Project>();
        while (cursor.moveToNext()) {
            projects.add(bind(cursor));
        }
        return projects;
    }

}
