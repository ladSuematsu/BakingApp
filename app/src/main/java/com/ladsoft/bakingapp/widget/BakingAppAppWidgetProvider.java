package com.ladsoft.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.activity.MainActivity;
import com.ladsoft.bakingapp.widget.remoteviewsservice.IngredientsListWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppAppWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = getListRemoteView(context);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews getListRemoteView(Context context) {
        CharSequence widgetText = context.getString(R.string.appwidget_test_text);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent listAdapterIntent = new Intent(context, IngredientsListWidgetService.class);
        views.setRemoteAdapter(R.id.appwidget_list, listAdapterIntent);

        Intent recipeIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, recipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_root_view, pendingIntent);

        return views;
    }

    public static void updateAppWidgets (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, widgetId);
        }
    }
}

