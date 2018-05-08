package com.ladsoft.bakingapp.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.entity.Ingredient
import java.util.*

class RecipeIngredientsAdapter(private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientViewHolder>() {
    private var datasource: MutableList<Ingredient> = ArrayList() 

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(layoutInflater.inflate(R.layout.item_recipe_ingredient, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.setData(datasource[position])
    }

    override fun getItemCount(): Int {
        return datasource.size
    }

    fun setDatasource(datasource: MutableList<Ingredient>?) {
        this.datasource.clear()

        if (datasource != null) {
            this.datasource = datasource
        }

        notifyDataSetChanged()
    }

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @JvmField @BindView(R.id.ingredient)
        var ingredient: TextView? = null

        @JvmField @BindView(R.id.quantity)
        var quantity: TextView? = null

        @JvmField @BindView(R.id.measure)
        var measure: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }

        fun setData(ingredient: Ingredient) {
            this.ingredient?.text = ingredient.description
            this.quantity?.text = ingredient.quantity.toString()
            this.measure?.text = ingredient.measure
        }
    }
}
