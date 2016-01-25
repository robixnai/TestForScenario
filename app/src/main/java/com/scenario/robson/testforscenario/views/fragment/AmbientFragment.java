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
import com.scenario.robson.testforscenario.models.entities.Ambient;
import com.scenario.robson.testforscenario.views.activity.AmbientActivity;
import com.scenario.robson.testforscenario.views.adapter.AmbientAdapter;

import java.util.List;

public class AmbientFragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private AmbientAdapter mAmbientAdapter;
    private LinearLayoutManager mLayoutManager;

    public AmbientFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_ambient, container, false);

        AppHelper.insertProject(getActivity());

        setupRecyclerView();
        bindFab();

        return mView;
    }

    private void setupRecyclerView() {
        List<Ambient> ambients = dataSet();

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.projectRecyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAmbientAdapter = new AmbientAdapter(getActivity(), ambients);
        mRecyclerView.setAdapter(mAmbientAdapter);
    }

    private void bindFab() {
        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AmbientActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private List<Ambient> dataSet() {
        final Ambient ambient = new Ambient();
        return ambient.getAll();
    }

}
