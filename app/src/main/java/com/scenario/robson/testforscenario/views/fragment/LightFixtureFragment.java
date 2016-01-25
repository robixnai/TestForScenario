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
import com.scenario.robson.testforscenario.models.entities.LightFixture;
import com.scenario.robson.testforscenario.views.activity.LightFixtureActivity;
import com.scenario.robson.testforscenario.views.adapter.LightFixtureAdapter;

import java.util.List;

public class LightFixtureFragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private LightFixtureAdapter mLightFixtureAdapter;
    private LinearLayoutManager mLayoutManager;

    public LightFixtureFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_light_fixture, container, false);

        AppHelper.insertProject(getActivity());

        setupRecyclerView();
        bindFab();

        return mView;
    }

    private void setupRecyclerView() {
        List<LightFixture> lightFixtures = dataSet();

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.projectRecyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLightFixtureAdapter = new LightFixtureAdapter(getActivity(), lightFixtures);
        mRecyclerView.setAdapter(mLightFixtureAdapter);
    }

    private void bindFab() {
        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LightFixtureActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private List<LightFixture> dataSet() {
        final LightFixture lightFixture = new LightFixture();
        return lightFixture.getAll();
    }

}
