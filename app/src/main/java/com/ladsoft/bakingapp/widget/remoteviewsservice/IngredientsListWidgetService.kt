package com.ladsoft.bakingapp.widget.remoteviewsservice


import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.application.BakingAppApplication
import com.ladsoft.bakingapp.data.database.repository.DatabaseIngredientRepository
import com.ladsoft.bakingapp.data.database.repository.DatabaseRecipeRepository
import com.ladsoft.bakingapp.entity.Ingredient
import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.manager.SessionManager
import javax.inject.Inject

class IngredientsListWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return ListRemoteViewsFactory(this)
    }

    class ListRemoteViewsFactory(context: Context) : RemoteViewsService.RemoteViewsFactory {
        @JvmField @Inject var sessionManager: SessionManager? = null
        @JvmField @Inject var recipeRepository: DatabaseRecipeRepository? = null
        @JvmField @Inject var repository: DatabaseIngredientRepository? = null

        private val context = context.applicationContext
        private var ingredients: MutableList<Ingredient> = ArrayList()
        private var recipe: Recipe? = null

        init {
            BakingAppApplication.appComponent.inject(this)
        }

        override fun onCreate() {}

        override fun onDataSetChanged() {
            val lastSelectedReceiptId = sessionManager!!.lastSelectedReceiptId.invoke()

            recipe = recipeRepository?.loadRecipe(lastSelectedReceiptId) ?: null
            ingredients.clear()
            if (recipe != null) {
                ingredients = repository
                        ?.loadForRecipeId(lastSelectedReceiptId)
                        ?.toMutableList() ?: ArrayList()
                if (ingredients.size < 0) {
                    recipe = null
                }
            }
        }

        override fun onDestroy() {

        }

        override fun getCount(): Int {
            return ingredients.size + if (recipe != null) 1 else 0
        }

        override fun getViewAt(position: Int): RemoteViews {
            val views = RemoteViews(context.packageName, R.layout.widget_item_recipe_ingredient)

            if (position == 0) {
                views.setViewVisibility(R.id.quantity, View.GONE)
                views.setViewVisibility(R.id.measure, View.GONE)

                views.setTextViewText(R.id.ingredient, recipe!!.name)
                views.setTextViewText(R.id.quantity, null)
                views.setTextViewText(R.id.measure, null)
            } else {
                views.setViewVisibility(R.id.quantity, View.VISIBLE)
                views.setViewVisibility(R.id.measure, View.VISIBLE)

                val ingredient = ingredients[position - 1]
                views.setTextViewText(R.id.ingredient, ingredient.description)
                views.setTextViewText(R.id.quantity, ingredient.quantity.toString())
                views.setTextViewText(R.id.measure, ingredient.measure)
            }

            return views
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getViewTypeCount(): Int {
            return 1
        }
        override fun getItemId(position: Int): Long {
            return if (position == 0) recipe!!.id else ingredients[position - 1].id
        }

        override fun hasStableIds(): Boolean {
            return false
        }

    }

}