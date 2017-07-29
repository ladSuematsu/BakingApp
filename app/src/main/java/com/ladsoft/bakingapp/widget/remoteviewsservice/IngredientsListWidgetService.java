package com.ladsoft.bakingapp.widget.remoteviewsservice;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ladsoft.bakingapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IngredientsListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this);
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private List<String> ingredients;

    public ListRemoteViewsFactory(Context context) {
        this.context = context.getApplicationContext();
        this.ingredients = new ArrayList<>();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        // TODO: update the internal list with the selected recipe ingredients list
        ingredients = Arrays.asList("Eggs", "Milk", "Sugar", "Salt", "Baking powder");
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

        String ingredientName = ingredients.get(position);
        views.setTextViewText(R.id.ingredient, ingredientName);

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
