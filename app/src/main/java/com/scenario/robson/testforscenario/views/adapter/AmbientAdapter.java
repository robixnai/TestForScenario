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
import com.scenario.robson.testforscenario.models.entities.Ambient;
import com.scenario.robson.testforscenario.views.activity.AmbientActivity;
import com.scenario.robson.testforscenario.views.activity.AmbientEditActivity;

import java.util.List;

/**
 * Created by robson on 23/01/16.
 */
public class AmbientAdapter extends RecyclerView.Adapter<AmbientAdapter.ViewHolder> {

    public static final String AMBIENT_ID = "ambient_id";

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;

    private List<Ambient> mAmbients;

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

    public Ambient getValueAt(int position) {
        return mAmbients.get(position);
    }

    public AmbientAdapter(Context context, List<Ambient> ambients) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mAmbients = ambients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambient_list_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Ambient ambient = mAmbients.get(position);

        holder.mBoundString = ambient.getName();
        holder.mTextView.setText(ambient.getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AmbientEditActivity.class);
                Bundle extras = new Bundle();
                extras.putString(AMBIENT_ID, ambient.getName());
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAmbients.size();
    }

}
