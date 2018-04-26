package com.ladsoft.bakingapp.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.entity.Step
import java.util.*

class RecipeStepsAdapter(private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecipeStepsAdapter.StepViewHolder>() {
    private var datasource: MutableList<Step> = ArrayList()
    private var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        return StepViewHolder(layoutInflater.inflate(R.layout.item_recipe_step, parent, false))
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.setData(datasource[position])
    }

    override fun getItemCount(): Int {
        return datasource.size
    }

    fun setDatasource(datasource: MutableList<Step>) {
        this.datasource.clear()
        this.datasource = datasource
        notifyDataSetChanged()
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    inner class StepViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @JvmField @BindView(R.id.step)
        internal var step: TextView? = null

        init {
            itemView.setOnClickListener(this)
            ButterKnife.bind(this, itemView)
        }

        fun setData(step: Step) {
            this.step?.text = step.shortDescription
        }

        override fun onClick(v: View) {
            listener?.onItemClickListener(adapterPosition, datasource)
        }
    }

    interface Listener {
        fun onItemClickListener(itemIndex: Int, steps: List<Step>?)
    }
}
