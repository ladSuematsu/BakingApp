package com.ladsoft.bakingapp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.activity.MainActivity
import com.ladsoft.bakingapp.widget.remoteviewsservice.IngredientsListWidgetService

/**
 * Implementation of App Widget functionality.
 */
class BakingAppAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {


            val views = getListRemoteView(context)


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun getListRemoteView(context: Context): RemoteViews {
            val widgetText = context.getString(R.string.appwidget_test_text)

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.baking_app_app_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            val listAdapterIntent = Intent(context, IngredientsListWidgetService::class.java)
            views.setRemoteAdapter(R.id.appwidget_list, listAdapterIntent)

            val recipeIntent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, recipeIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.widget_root_view, pendingIntent)

            return views
        }

        fun updateAppWidgets(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            for (widgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, widgetId)
            }
        }
    }
}

