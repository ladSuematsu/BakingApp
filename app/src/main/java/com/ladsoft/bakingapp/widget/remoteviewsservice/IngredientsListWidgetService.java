package com.ladsoft.bakingapp.widget.remoteviewsservice;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.application.BakingAppApplication;
import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository;
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository;
import com.ladsoft.bakingapp.entity.Ingredient;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.manager.SessionManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class IngredientsListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this);
    }

    protected class ListRemoteViewsFactory implements RemoteViewsFactory {
        @Inject SessionManager sessionManager;
        @Inject DatabaseRecipeRepository recipeRepository;
        @Inject DatabaseIngredientRepository repository;

        private final Context context;
        private List<Ingredient> ingredients = new ArrayList();
        private Recipe recipe = null;

        public ListRemoteViewsFactory(Context context) {
            BakingAppApplication.appComponent.inject(this);
            this.context = context.getApplicationContext();
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            long lastSelectedReceiptId = sessionManager.getLastSelectedReceiptId();

            recipe = recipeRepository.loadRecipe(lastSelectedReceiptId);

            ingredients.clear();
            if (recipe != null) {
                ingredients = repository.loadForRecipeId(lastSelectedReceiptId);

                if (ingredients.size() < 1) {
                    recipe = null;
                }
            }
        }

        @Override
        public void onDestroy() { }

        @Override
        public int getCount() {
            return ingredients.size() + (recipe != null ? 1 : 0);
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews;
            if (position == 0) {
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item_recipe_name);

                remoteViews.setTextViewText(R.id.recipe_name, recipe.getName());
                remoteViews.setOnClickFillInIntent(R.id.widget_recipe_list_root, new Intent());
            } else {
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item_recipe_ingredient);

                remoteViews.setViewVisibility(R.id.quantity, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.measure, View.VISIBLE);

                Ingredient ingredient = ingredients.get(position -1);
                remoteViews.setTextViewText(R.id.ingredient, ingredient.getDescription());
                remoteViews.setTextViewText(R.id.quantity, String.valueOf(ingredient.getQuantity()));
                remoteViews.setTextViewText(R.id.measure, ingredient.getMeasure());

                remoteViews.setOnClickFillInIntent(R.id.widget_recipe_list_root, new Intent());
            }

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public long getItemId(int position) {
            return position == 0 ? recipe.getId() : ingredients.get(position - 1).getId();
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
