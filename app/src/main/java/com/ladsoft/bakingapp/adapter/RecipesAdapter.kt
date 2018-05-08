package com.ladsoft.bakingapp.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.entity.Recipe
import java.util.*

class RecipesAdapter(private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {
    private var datasource: MutableList<Recipe> = ArrayList()
    private var callback: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(layoutInflater.inflate(R.layout.item_recipe, parent, false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.setData(datasource[position])
    }

    override fun getItemCount(): Int {
        return datasource.size
    }

    fun setDatasource(datasource: MutableList<Recipe>?) {
        this.datasource = datasource ?: ArrayList()
        notifyDataSetChanged()
    }

    fun setListener(listener: Listener) {
        this.callback = listener
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @JvmField @BindView(R.id.title) var title: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        fun setData(recipe: Recipe) {
            title!!.text = recipe.name
        }

        override fun onClick(v: View) {
            callback?.onItemClickListener(datasource[adapterPosition])
        }
    }

    interface Listener {
        fun onItemClickListener(recipe: Recipe)
    }
}
