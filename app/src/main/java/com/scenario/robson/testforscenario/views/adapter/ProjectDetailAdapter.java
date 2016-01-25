package com.scenario.robson.testforscenario.views.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scenario.robson.testforscenario.R;
import com.scenario.robson.testforscenario.helpers.AppHelper;
import com.scenario.robson.testforscenario.models.entities.Project;
import com.scenario.robson.testforscenario.models.entities.Projects;
import com.scenario.robson.testforscenario.utils.AppUtil;

import java.util.List;

/**
 * Created by robson on 24/01/16.
 */
public class ProjectDetailAdapter extends RecyclerView.Adapter<ProjectDetailAdapter.ViewHolder> {

    private List<Projects> mItens;

    public ProjectDetailAdapter(List<Projects> itens) {
        mItens = itens;
    }

    public void setItens(List<Projects> itens) {
        this.mItens = itens;
    }

    public List<Projects> getItens() {
        return mItens;
    }

    @Override
    public ProjectDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.projects_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Projects projects = mItens.get(position);
        final Project project = projects.getAmbient().getProject();
        final byte[] image = project.getImage();

        Bitmap imageBitmap = AppHelper.getByteArrayAsBitmap(image);
        holder.mImgProject.setImageBitmap(imageBitmap);
        holder.mTxtAmbient.setText(projects.getAmbient().getName());

        int moduleTotal = projects.getModules().size();
        holder.mTxtModules.setText(moduleTotal + " Modules");
    }

    @Override
    public int getItemCount() {
        return mItens.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImgProject;
        public TextView mTxtAmbient;
        public TextView mTxtModules;

        public ViewHolder(View view) {
            super(view);
            mImgProject = AppUtil.get(view.findViewById(R.id.imageViewImageProject));
            mTxtAmbient = AppUtil.get(view.findViewById(R.id.textViewAmbient));
            mTxtModules = AppUtil.get(view.findViewById(R.id.textViewModule));
        }

    }

}
