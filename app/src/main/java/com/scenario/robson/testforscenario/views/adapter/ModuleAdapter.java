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
import com.scenario.robson.testforscenario.models.entities.Module;
import com.scenario.robson.testforscenario.views.activity.ModuleAmbientActivity;

import java.util.List;

/**
 * Created by robson on 23/01/16.
 */
public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ViewHolder> {

    private static final String MODULE_ID = "module_id";

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;

    private List<Module> mModules;

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

    public Module getValueAt(int position) {
        return mModules.get(position);
    }

    public ModuleAdapter(Context context, List<Module> modules) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mModules = modules;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambient_list_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Module module = mModules.get(position);

        holder.mBoundString = module.getName();
        holder.mTextView.setText(module.getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ModuleAmbientActivity.class);
                Bundle extras = new Bundle();
                extras.putString(MODULE_ID, module.getName());
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModules.size();
    }

}
