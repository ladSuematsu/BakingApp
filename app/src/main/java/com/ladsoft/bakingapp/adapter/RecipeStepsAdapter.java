package com.ladsoft.bakingapp.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.entity.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Step> datasource;
    private Listener listener;

    public RecipeStepsAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.datasource = new ArrayList<>();
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StepViewHolder(layoutInflater.inflate(R.layout.item_recipe_step, parent, false));
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.setData(datasource.get(position));
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }

    public void setDatasource(List<Step> datasource) {
        this.datasource.clear();
        this.datasource = datasource;
        notifyDataSetChanged();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step) TextView step;

        StepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Step step) {
            this.step.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View v) {
            if(listener != null) {
                listener.onItemClickListener(getAdapterPosition(), datasource);
            }
        }
    }

    public interface Listener {
        void onItemClickListener(int itemIndex, List<Step> steps);
    }
}
