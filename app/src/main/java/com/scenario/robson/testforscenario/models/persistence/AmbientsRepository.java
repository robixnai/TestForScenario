package com.scenario.robson.testforscenario.models.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.scenario.robson.testforscenario.models.entities.Ambient;
import com.scenario.robson.testforscenario.models.entities.Module;
import com.scenario.robson.testforscenario.storages.sqlite.DatabaseHelper;
import com.scenario.robson.testforscenario.storages.sqlite.contract.AmbientAmbientContract;
import com.scenario.robson.testforscenario.storages.sqlite.contract.AmbientContract;
import com.scenario.robson.testforscenario.storages.sqlite.contract.ModuleContract;
import com.scenario.robson.testforscenario.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robson on 18/01/16.
 */
public class AmbientsRepository {

    private static class Singleton {
        public static final AmbientsRepository INSTANCE = new AmbientsRepository();
    }

    private AmbientsRepository() {
        super();
    }

    public static AmbientsRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void save(Ambient ambient) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(AmbientContract.TABLE, null, AmbientContract.getContentValues(ambient));
        db.close();
        helper.close();
    }

    public void saveAmbients(Ambient ambient) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        delete(ambient);

        List<Ambient> ambients = ambient.getAmbients();
        for (Ambient a: ambients) {
            db.insert(AmbientAmbientContract.TABLE, null, AmbientAmbientContract.getContentValues(ambient.getId(), ambient));
        }

        db.close();
        helper.close();
    }

    public void update(Ambient ambient) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        String where = AmbientContract.ID + " = ?";
        String[] args = {ambient.getId().toString()};
        db.update(AmbientContract.TABLE, AmbientContract.getContentValues(ambient), where, args);
        db.close();
        helper.close();
    }

    public void delete(Ambient ambient) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        String where = AmbientAmbientContract.ID + " = ?";
        String[] args = {ambient.getId().toString()};
        db.delete(AmbientAmbientContract.TABLE, where, args);
        db.close();
        helper.close();
    }

    public List<Ambient> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(AmbientContract.TABLE, AmbientContract.COLUNS, null, null, null, null, AmbientContract.NAME);
        List<Ambient> ambients = AmbientContract.bindList(cursor);
        db.close();
        helper.close();
        return ambients;
    }

    public List<Ambient> getAmbientsProject(Integer projectId) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = AmbientContract.ID_PROJECT + " = ?";
        String[] selectionArgs = {projectId.toString()};
        Cursor cursor = db.query(AmbientContract.TABLE, AmbientContract.COLUNS, selection, selectionArgs, null, null, AmbientContract.NAME);
        List<Ambient> ambients = AmbientContract.bindList(cursor);
        db.close();
        helper.close();
        return ambients;
    }

    public Ambient getAmbient(Ambient ambient) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<String> fields = new ArrayList<String>();
        ArrayList<String> args = new ArrayList<String>();
        if (ambient.getId() != null) {
            fields.add(AmbientContract.ID + " = ?");
            args.add(ambient.getId().toString());
        }
        if (ambient.getName() != null) {
            fields.add(AmbientContract.NAME + " = ?");
            args.add(ambient.getName());
        }
        String[] selectionFields = new String[fields.size()];
        selectionFields = fields.toArray(selectionFields);
        String selection = AppUtil.implodeArray(selectionFields, " AND ");
        String[] selectionArgs = new String[args.size()];
        selectionArgs = args.toArray(selectionArgs);

        Cursor cursor = db.query(AmbientContract.TABLE, AmbientContract.COLUNS, selection, selectionArgs, null, null, AmbientContract.NAME);
        Ambient hasAmbient = AmbientContract.bind(cursor);
        db.close();
        helper.close();
        return hasAmbient;
    }

    public List<Module> getAmbientModule(Ambient ambient) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        String selection = ModuleContract.ID_AMBIENT + " = ?";
        String[] selectionArgs = {ambient.getId().toString()};

        Cursor cursor = db.query(ModuleContract.TABLE, ModuleContract.COLUNS, selection, selectionArgs, null, null, ModuleContract.NAME);
        List<Module> hasModule = ModuleContract.bindList(cursor);
        db.close();
        helper.close();
        return hasModule;
    }

    public List<Ambient> getAmbients(Ambient ambient) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        String selection = AmbientAmbientContract.ID + " = ?";
        String[] selectionArgs = {ambient.getId().toString()};

        Cursor cursor = db.query(AmbientAmbientContract.TABLE, AmbientAmbientContract.COLUNS, selection, selectionArgs, null, null, null);
        List<Ambient> hasAmbient = AmbientAmbientContract.bindList(cursor);
        db.close();
        helper.close();
        return hasAmbient;
    }

}
