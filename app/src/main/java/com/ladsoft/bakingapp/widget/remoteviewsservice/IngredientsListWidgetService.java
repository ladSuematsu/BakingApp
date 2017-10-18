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
    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this);
    }

    public static class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        @Inject SessionManager sessionManager;
        @Inject DatabaseRecipeRepository recipeRepository;
        @Inject DatabaseIngredientRepository repository;

        private final Context context;
        private Recipe recipe;
        private List<Ingredient> ingredients;

        ListRemoteViewsFactory(Context context) {
            this.context = context.getApplicationContext();
            this.ingredients = new ArrayList<>();
            BakingAppApplication.appComponent.inject(this);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            long lastSelectedReceiptId = sessionManager.getLastSelectedReceiptId().invoke();

            recipe = recipeRepository.loadRecipe(lastSelectedReceiptId);
            ingredients.clear();
            if (recipe != null) {
                ingredients = repository.loadForRecipeId(lastSelectedReceiptId);
                if (ingredients == null) {
                    recipe = null;
                    ingredients = new ArrayList<>();
                }
            }
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredients.size() + (recipe != null ? 1 : 0);
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item_recipe_ingredient);

            if (position == 0) {
                views.setViewVisibility(R.id.quantity, View.GONE);
                views.setViewVisibility(R.id.measure, View.GONE);

                views.setTextViewText(R.id.ingredient, recipe.getName());
                views.setTextViewText(R.id.quantity, null);
                views.setTextViewText(R.id.measure, null);
            } else {
                views.setViewVisibility(R.id.quantity, View.VISIBLE);
                views.setViewVisibility(R.id.measure, View.VISIBLE);

                Ingredient ingredient = ingredients.get(position - 1);
                views.setTextViewText(R.id.ingredient, ingredient.getDescription());
                views.setTextViewText(R.id.quantity, String.valueOf(ingredient.getQuantity()));
                views.setTextViewText(R.id.measure, ingredient.getMeasure());
            }

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
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