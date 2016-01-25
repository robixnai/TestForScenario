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
import com.scenario.robson.testforscenario.models.entities.Module;
import com.scenario.robson.testforscenario.views.activity.ModuleActivity;
import com.scenario.robson.testforscenario.views.adapter.ModuleAdapter;

import java.util.List;

public class ModuleFragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private ModuleAdapter mModuleAdapter;
    private LinearLayoutManager mLayoutManager;

    public ModuleFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_module, container, false);

        AppHelper.insertProject(getActivity());

        setupRecyclerView();
        bindFab();

        return mView;
    }

    private void setupRecyclerView() {
        List<Module> modules = dataSet();

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.projectRecyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mModuleAdapter = new ModuleAdapter(getActivity(), modules);
        mRecyclerView.setAdapter(mModuleAdapter);
    }

    private void bindFab() {
        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ModuleActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private List<Module> dataSet() {
        final Module module = new Module();
        return module.getAll();
    }

}
