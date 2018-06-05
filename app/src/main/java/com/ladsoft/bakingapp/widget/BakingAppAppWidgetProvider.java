package com.ladsoft.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.activity.MainActivity;
import com.ladsoft.bakingapp.activity.RecipeActivity;
import com.ladsoft.bakingapp.application.BakingAppApplication;
import com.ladsoft.bakingapp.manager.SessionManager;
import com.ladsoft.bakingapp.widget.remoteviewsservice.IngredientsListWidgetService;

import javax.inject.Inject;

public class BakingAppAppWidgetProvider extends AppWidgetProvider {
    @Inject SessionManager sessionManager;

    public BakingAppAppWidgetProvider() {
        super();
        BakingAppApplication.getAppComponent().inject(this);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateAppWidgets(context, sessionManager, appWidgetManager, appWidgetIds);
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       Integer appWidgetId, SessionManager sessionManager) {

        RemoteViews views = getListRemoteView(context, sessionManager);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getListRemoteView(Context context, SessionManager sessionManager) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_app_widget);

        String widgetText = context.getString(R.string.app_name);

        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent listAdapterIntent = new Intent(context, IngredientsListWidgetService.class);
        views.setRemoteAdapter(R.id.appwidget_list, listAdapterIntent);

        PendingIntent pendingIntent = buildActivityStartPendingIntent(sessionManager, context);

        views.setOnClickPendingIntent(R.id.widget_root_view, pendingIntent);
        views.setPendingIntentTemplate(R.id.appwidget_list, pendingIntent);

        return views;
    }

    private static PendingIntent buildActivityStartPendingIntent(SessionManager sessionManager, Context context) {
        long lastSelectedRecipeId = sessionManager.getLastSelectedReceiptId();
        Intent activityIntent = lastSelectedRecipeId != 0L
            ? new Intent(context, RecipeActivity.class)
            : new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void updateAppWidgets(Context context, SessionManager sessionManager, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        if (appWidgetIds == null) { return; }

        for (int widgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, widgetId, sessionManager);
        }
    }
}
