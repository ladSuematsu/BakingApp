package com.ladsoft.bakingapp.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.manager.SessionManager;
import com.ladsoft.bakingapp.widget.BakingAppAppWidgetProvider;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class IngredientUpdateService extends IntentService {
    @Inject SessionManager sessionManager;

    public IngredientUpdateService() {
        super("IngredientUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        AndroidInjection.inject(this);

        updateIngredientListWidget();
    }

    private void updateIngredientListWidget() {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(
                new ComponentName(this, BakingAppAppWidgetProvider.class));

        widgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list);

        BakingAppAppWidgetProvider.updateAppWidgets(this, sessionManager, widgetManager, appWidgetIds);
    }
}
