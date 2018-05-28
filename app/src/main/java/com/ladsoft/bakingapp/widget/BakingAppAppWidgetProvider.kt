package com.ladsoft.bakingapp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.activity.MainActivity
import com.ladsoft.bakingapp.activity.RecipeActivity
import com.ladsoft.bakingapp.application.BakingAppApplication
import com.ladsoft.bakingapp.manager.SessionManager
import com.ladsoft.bakingapp.widget.remoteviewsservice.IngredientsListWidgetService
import javax.inject.Inject

/**
 * Implementation of App Widget functionality.
 */
class BakingAppAppWidgetProvider : AppWidgetProvider() {

    @Inject lateinit var sessionManager: SessionManager

    init {
        BakingAppApplication.appComponent.inject(this)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, sessionManager)
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
                                     appWidgetId: Int, sessionManager: SessionManager) {


            val views = getListRemoteView(context, sessionManager);


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun getListRemoteView(context: Context, sessionManager: SessionManager): RemoteViews {
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.baking_app_app_widget).apply {
                val widgetText = context.getString(R.string.app_name)

                setTextViewText(R.id.appwidget_text, widgetText)

                val listAdapterIntent = Intent(context, IngredientsListWidgetService::class.java)
                setRemoteAdapter(R.id.appwidget_list, listAdapterIntent)

                val pendingIntent = buildActivityStartPendingIntent(sessionManager, context)

                setOnClickPendingIntent(R.id.widget_root_view, pendingIntent)
                setPendingIntentTemplate(R.id.appwidget_list, pendingIntent)
            }

            return views
        }

        private fun buildActivityStartPendingIntent(sessionManager: SessionManager, context: Context): PendingIntent? {
            val lastSelectedRecipeId = sessionManager.lastSelectedReceiptId()
            val activityIntent = if (lastSelectedRecipeId.equals(0L).not())
                Intent(context, RecipeActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(RecipeActivity.EXTRA_RECIPE_ID, lastSelectedRecipeId)
            else
                Intent(context, MainActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

            return PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        }

        fun updateAppWidgets(context: Context, sessionManager: SessionManager, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            for (widgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, widgetId, sessionManager)
            }
        }
    }
}

