package com.ladsoft.bakingapp.fragment


import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.adapter.RecipesAdapter
import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.mvp.RecipesMvp
import com.ladsoft.bakingapp.mvp.presenter.RecipesPresenter
import com.ladsoft.bakingapp.util.UiUtils
import com.ladsoft.bakingapp.view.layoutmanager.decoration.SimplePaddingDecoration
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class RecipesFragment : Fragment(), RecipesMvp.View, SwipeRefreshLayout.OnRefreshListener {

    @JvmField @BindView(R.id.recipes) var recipes: RecyclerView? = null

    @JvmField @BindView(R.id.refresh) var refresh: SwipeRefreshLayout? = null

    @JvmField @Inject var presenter: RecipesPresenter? = null

    private lateinit var adapter: RecipesAdapter
    private var recipeAdapterListener: RecipesAdapter.Listener? = null
    private lateinit var genericErrorMessage: String

    companion object {
        val TAG = RecipesFragment::class.java.simpleName

        fun newInstance(): RecipesFragment {
            return RecipesFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        genericErrorMessage = getString(R.string.recipes_error)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipes!!.layoutManager = LinearLayoutManager(context)
        recipes!!.addItemDecoration(SimplePaddingDecoration(resources.getDimensionPixelSize(R.dimen.list_item_content_margin)))
        adapter = RecipesAdapter(LayoutInflater.from(context))
        adapter.setListener(recipeAdapterListener!!)
        recipes!!.adapter = adapter
        refresh!!.setOnRefreshListener(this)
    }

    override fun onStart() {
        super.onStart()
        presenter!!.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter!!.loadData()
    }

    override fun onStop() {
        presenter!!.detachView()
        super.onStop()
    }

    override fun onRefresh() {
        presenter!!.loadData()
    }

    fun setRecipeAdapterListener(recipeAdapterListener: RecipesAdapter.Listener) {
        this.recipeAdapterListener = recipeAdapterListener
    }

    override fun onRecipesLoaded(recipes: MutableList<Recipe>?) {
        adapter.setDatasource(recipes)
    }

    override fun onRecipeLoadError() {
        UiUtils.showSnackbar(view!!, genericErrorMessage, null, Snackbar.LENGTH_LONG, null)
    }

    override fun showRefresh(show: Boolean) {
        refresh!!.isRefreshing = show
    }
}
