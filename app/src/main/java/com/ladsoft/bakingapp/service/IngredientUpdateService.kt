package com.ladsoft.bakingapp.service

import android.app.IntentService
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.widget.BakingAppAppWidgetProvider

class IngredientUpdateService: IntentService("IngredientUpdateService") {
    override fun onHandleIntent(intent: Intent?) {
        updateIngredientListWidget()
    }

    private fun updateIngredientListWidget() {
        val widgetManager = AppWidgetManager.getInstance(this)
        val appWidgetIds = widgetManager.getAppWidgetIds(ComponentName(this, BakingAppAppWidgetProvider::class.java))

        widgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list);

        BakingAppAppWidgetProvider.updateAppWidgets(this, widgetManager, appWidgetIds)
    }


}
