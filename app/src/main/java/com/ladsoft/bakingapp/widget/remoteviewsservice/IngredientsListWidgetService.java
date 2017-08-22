package com.ladsoft.bakingapp.widget.remoteviewsservice;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.data.repository.RecipeRepository;
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
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    @Inject SessionManager sessionManager;

    private final Context context;
    private List<Ingredient> ingredients;

    ListRemoteViewsFactory(Context context) {
        this.context = context.getApplicationContext();
        this.ingredients = new ArrayList<>();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        long lastSelectedReceiptId = sessionManager.getLastSelectedReceiptId().invoke();

        List<Recipe> recipes= RecipeRepository.INSTANCE.getRecipes();
        for (Recipe recipe : recipes) {
            if (recipe.getId() == lastSelectedReceiptId) {
                ingredients = recipe.getIngredients();
                break;
            }
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item_recipe_ingredient);

        Ingredient ingredient = ingredients.get(position);
        views.setTextViewText(R.id.ingredient, ingredient.getDescription());
        views.setTextViewText(R.id.quantity, String.valueOf(ingredient.getQuantity()));
        views.setTextViewText(R.id.measure, ingredient.getMeasure());

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
        //return ingredients.get(position).getId();
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
