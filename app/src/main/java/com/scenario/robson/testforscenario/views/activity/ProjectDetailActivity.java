package com.scenario.robson.testforscenario.views.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.models.entities.Ambient;
import com.scenario.robson.testforscenario.models.entities.Projects;
import com.scenario.robson.testforscenario.utils.AppUtil;
import com.scenario.robson.testforscenario.views.adapter.ProjectDetailAdapter;

import java.util.List;

public class ProjectDetailActivity extends AppCompatActivity {

    public static final String PROJECT_ID = "project_id";

    private RecyclerView mRecycleViewProjects;
    private ProjectDetailAdapter mProjectsDetailAdapter;
    private Integer mProjectId;
    private Projects mProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        setTitle(R.string.Projects);

        bindObjects();
        bindElements();
        setupAdapter();
    }

    private void bindObjects() {
        mProjects = new Projects();
    }

    private void bindElements() {
        mRecycleViewProjects = AppUtil.get(findViewById(R.id.recyclerViewProjects));
        mRecycleViewProjects.setHasFixedSize(true);
        mRecycleViewProjects.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupAdapter() {
        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        mProjectId = extras.getInt(PROJECT_ID);

        final List<Projects> projects = mProjects.getProjects(mProjectId);
        mProjectsDetailAdapter = new ProjectDetailAdapter(projects);
        mRecycleViewProjects.setAdapter(mProjectsDetailAdapter);
    }

}
