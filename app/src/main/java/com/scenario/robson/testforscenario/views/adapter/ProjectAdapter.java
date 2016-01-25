package com.scenario.robson.testforscenario.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.helpers.AppHelper;
import com.scenario.robson.testforscenario.models.entities.Project;
import com.scenario.robson.testforscenario.views.activity.ProjectDetailActivity;

import java.util.List;

/**
 * Created by robson on 23/01/16.
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    public static final String PROJECT_ID = "project_id";

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;

    private List<Project> mProjects;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString;

        public final View mView;
        public final ImageView mImageView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.imageProject);
            mTextView = (TextView) view.findViewById(android.R.id.text1);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    public Project getValueAt(int position) {
        return mProjects.get(position);
    }

    public ProjectAdapter(Context context, List<Project> projects) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mProjects = projects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Project project = mProjects.get(position);

        holder.mBoundString = project.getName();
        holder.mTextView.setText(project.getName());
        Bitmap imageBitmap = AppHelper.getByteArrayAsBitmap(project.getImage());
        holder.mImageView.setImageBitmap(imageBitmap);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ProjectDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putInt(PROJECT_ID, project.getId());
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }

}
