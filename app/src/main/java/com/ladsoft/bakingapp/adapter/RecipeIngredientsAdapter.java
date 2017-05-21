package com.ladsoft.bakingapp.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.entity.Ingredient;
import com.ladsoft.bakingapp.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Ingredient> datasource;

    public RecipeIngredientsAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.datasource = new ArrayList<>();
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientViewHolder(layoutInflater.inflate(R.layout.item_recipe_ingredient, parent, false));
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.setData(datasource.get(position));
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }

    public void setDatasource(List<Ingredient> datasource) {
        this.datasource.clear();
        this.datasource = datasource;
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient) TextView ingredient;
        @BindView(R.id.quantity) TextView quantity;
        @BindView(R.id.measure) TextView measure;

        IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Ingredient ingredient) {
            this.ingredient.setText(ingredient.getDescription());
            this.quantity.setText(String.valueOf(ingredient.getQuantity()));
            this.measure.setText(ingredient.getMeasure());
        }
    }
}
