package com.scenario.robson.testforscenario.models.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.scenario.robson.testforscenario.models.entities.Ambient;
import com.scenario.robson.testforscenario.models.entities.Module;
import com.scenario.robson.testforscenario.storages.sqlite.DatabaseHelper;
import com.scenario.robson.testforscenario.storages.sqlite.contract.ModuleContract;
import com.scenario.robson.testforscenario.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robson on 18/01/16.
 */
public class ModulesRepository {

    private static class Singleton {
        public static final ModulesRepository INSTANCE = new ModulesRepository();
    }

    private ModulesRepository() {
        super();
    }

    public static ModulesRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void save(Module module) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(ModuleContract.TABLE, null, ModuleContract.getContentValues(module));
        db.close();
        helper.close();
    }

    public void update(Module module) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        String where = ModuleContract.ID + " = ?";
        String[] args = {module.getId().toString()};
        db.update(ModuleContract.TABLE, ModuleContract.getContentValues(module), where, args);
        db.close();
        helper.close();
    }

    public List<Module> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(ModuleContract.TABLE, ModuleContract.COLUNS, null, null, null, null, ModuleContract.NAME);
        List<Module> modules = ModuleContract.bindList(cursor);
        db.close();
        helper.close();
        return modules;
    }

    public Module getModule(Module module) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<String> fields = new ArrayList<String>();
        ArrayList<String> args = new ArrayList<String>();
        if (module.getId() != null) {
            fields.add(ModuleContract.ID + " = ?");
            args.add(module.getId().toString());
        }
        if (module.getName() != null) {
            fields.add(ModuleContract.NAME + " = ?");
            args.add(module.getName());
        }
        String[] selectionFields = new String[fields.size()];
        selectionFields = fields.toArray(selectionFields);
        String selection = AppUtil.implodeArray(selectionFields, " AND ");
        String[] selectionArgs = new String[args.size()];
        selectionArgs = args.toArray(selectionArgs);

        Cursor cursor = db.query(ModuleContract.TABLE, ModuleContract.COLUNS, selection, selectionArgs, null, null, ModuleContract.NAME);
        Module hasModule = ModuleContract.bind(cursor);
        db.close();
        helper.close();
        return hasModule;
    }

}
