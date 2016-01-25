package com.scenario.robson.testforscenario.models.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.scenario.robson.testforscenario.models.entities.Project;
import com.scenario.robson.testforscenario.storages.sqlite.DatabaseHelper;
import com.scenario.robson.testforscenario.storages.sqlite.contract.LightFixtureContract;
import com.scenario.robson.testforscenario.storages.sqlite.contract.ProjectContract;
import com.scenario.robson.testforscenario.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robson on 18/01/16.
 */
public class ProjectsRepository {

    private static class Singleton {
        public static final ProjectsRepository INSTANCE = new ProjectsRepository();
    }

    private ProjectsRepository() {
        super();
    }

    public static ProjectsRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void save(Project project) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(ProjectContract.TABLE, null, ProjectContract.getContentValues(project));
        db.close();
        helper.close();
    }

    public List<Project> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(ProjectContract.TABLE, ProjectContract.COLUNS, null, null, null, null, ProjectContract.NAME);
        List<Project> projects = ProjectContract.bindList(cursor);
        db.close();
        helper.close();
        return projects;
    }

    public Project getProject(Project project) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<String> fields = new ArrayList<String>();
        ArrayList<String> args = new ArrayList<String>();
        if (project.getId() != null) {
            fields.add(ProjectContract.ID + " = ?");
            args.add(project.getId().toString());
        }
        if (project.getName() != null) {
            fields.add(ProjectContract.NAME + " = ?");
            args.add(project.getName());
        }
        String[] selectionFields = new String[fields.size()];
        selectionFields = fields.toArray(selectionFields);
        String selection = AppUtil.implodeArray(selectionFields, " AND ");
        String[] selectionArgs = new String[args.size()];
        selectionArgs = args.toArray(selectionArgs);

        Cursor cursor = db.query(ProjectContract.TABLE, ProjectContract.COLUNS, selection, selectionArgs, null, null, ProjectContract.NAME);
        Project hasProject = ProjectContract.bind(cursor);
        db.close();
        helper.close();
        return hasProject;
    }

}
