package com.scenario.robson.testforscenario.storages.sqlite.contract;

import android.content.ContentValues;
import android.database.Cursor;

import com.scenario.robson.testforscenario.models.entities.Ambient;
import com.scenario.robson.testforscenario.models.entities.Module;

import java.util.ArrayList;
import java.util.List;

public class ModuleContract {

    public static final String TABLE = "module";
    public static final String ID = "id";
    public static final String ID_AMBIENT = "id_ambient";
    public static final String NAME = "name";

    public static final String[] COLUNS = {ID, ID_AMBIENT, NAME};

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(ID_AMBIENT + " INTEGER NULL, ");
        sql.append(NAME + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Module module) {
        ContentValues content = new ContentValues();
        content.put(ID, module.getId());
        content.put(ID_AMBIENT, module.getAmbient().getId());
        content.put(NAME, module.getName());
        return content;
    }

    public static Module bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Module module = new Module();
            module.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            Ambient ambient = new Ambient();
            ambient.setId(cursor.getInt(cursor.getColumnIndex(ID_AMBIENT)));
            module.setAmbient(ambient);
            module.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            return module;
        }
        return null;
    }

    public static List<Module> bindList(Cursor cursor) {
        final List<Module> modules = new ArrayList<Module>();
        while (cursor.moveToNext()) {
            modules.add(bind(cursor));
        }
        return modules;
    }

}
