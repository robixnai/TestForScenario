package com.scenario.robson.testforscenario.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.models.entities.LightFixture;
import com.scenario.robson.testforscenario.views.activity.LigtFixtureModuleActivity;

import java.util.List;

/**
 * Created by robson on 23/01/16.
 */
public class LightFixtureAdapter extends RecyclerView.Adapter<LightFixtureAdapter.ViewHolder> {

    public static final String LIGHT_FICTURE_ID = "light_fixture_id";

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;

    private List<LightFixture> mLightFixtures;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString;

        public final View mView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(android.R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + mTextView.getText();
        }
    }

    public LightFixture getValueAt(int position) {
        return mLightFixtures.get(position);
    }

    public LightFixtureAdapter(Context context, List<LightFixture> lightFixtures) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mLightFixtures = lightFixtures;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambient_list_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final LightFixture lightFixture = mLightFixtures.get(position);

        holder.mBoundString = lightFixture.getName();
        holder.mTextView.setText(lightFixture.getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, LigtFixtureModuleActivity.class);
                Bundle extras = new Bundle();
                extras.putString(LIGHT_FICTURE_ID, lightFixture.getName());
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLightFixtures.size();
    }

}
