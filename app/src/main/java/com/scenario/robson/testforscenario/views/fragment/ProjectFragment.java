package com.scenario.robson.testforscenario.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.helpers.AppHelper;
import com.scenario.robson.testforscenario.models.entities.Project;
import com.scenario.robson.testforscenario.views.activity.ProjectActivity;
import com.scenario.robson.testforscenario.views.adapter.ProjectAdapter;

import java.util.List;

public class ProjectFragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private ProjectAdapter mProjectAdapter;
    private LinearLayoutManager mLayoutManager;

    public ProjectFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_project, container, false);

        AppHelper.insertProject(getActivity());

        setupRecyclerView();
        bindFab();

        return mView;
    }

    private void setupRecyclerView() {
        List<Project> projects = dataSet();

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.projectRecyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mProjectAdapter = new ProjectAdapter(getActivity(), projects);
        mRecyclerView.setAdapter(mProjectAdapter);
    }

    private void bindFab() {
        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab);

        if (dataSet().size() >= 10) {
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProjectActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private List<Project> dataSet() {
        final Project project = new Project();
        return project.getAll();
    }

}
