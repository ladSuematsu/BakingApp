package com.ladsoft.bakingapp.service

import android.app.IntentService
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.application.BakingAppApplication
import com.ladsoft.bakingapp.manager.SessionManager
import com.ladsoft.bakingapp.widget.BakingAppAppWidgetProvider
import javax.inject.Inject

class IngredientUpdateService: IntentService("IngredientUpdateService") {
    @Inject lateinit var sessionManager: SessionManager

    init {
        BakingAppApplication.appComponent.inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        updateIngredientListWidget()
    }

    private fun updateIngredientListWidget() {
        val widgetManager = AppWidgetManager.getInstance(this)
        val appWidgetIds = widgetManager.getAppWidgetIds(ComponentName(this, BakingAppAppWidgetProvider::class.java))

        widgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list)

        BakingAppAppWidgetProvider.updateAppWidgets(this, sessionManager, widgetManager, appWidgetIds)
    }


}
