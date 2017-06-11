package com.ladsoft.bakingapp.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Recipe> datasource;
    private Listener callback;

    public RecipesAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.datasource = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(layoutInflater.inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.setData(datasource.get(position));
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }

    public void setDatasource(List<Recipe> datasource) {
        this.datasource.clear();
        this.datasource = datasource;
        notifyDataSetChanged();
    }

    public void setListener(Listener listener) {
        this.callback = listener;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.title) TextView title;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setData(Recipe recipe) {
            title.setText(recipe.getName());
        }

        @Override
        public void onClick(View v) {
            if(callback != null) {
                callback.onItemClickListener(datasource.get(getAdapterPosition()));
            }
        }
    }

    public interface Listener {
        void onItemClickListener(Recipe recipe);
    }
}
