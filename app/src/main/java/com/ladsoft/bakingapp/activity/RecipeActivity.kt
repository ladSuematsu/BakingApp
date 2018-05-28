package com.ladsoft.bakingapp.activity


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.adapter.RecipeStepsAdapter
import com.ladsoft.bakingapp.adapter.StepSlideshowAdapter
import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.entity.Step
import com.ladsoft.bakingapp.fragment.RecipeFragment
import com.ladsoft.bakingapp.fragment.RecipeStepFragment
import com.ladsoft.bakingapp.manager.SessionManager
import com.ladsoft.bakingapp.mvp.RecipeMvp
import com.ladsoft.bakingapp.mvp.StepsMvp
import com.ladsoft.bakingapp.mvp.presenter.RecipePresenter
import com.ladsoft.bakingapp.mvp.presenter.StepPresenter
import com.ladsoft.bakingapp.mvp.presenter.state.RecipeState
import com.ladsoft.bakingapp.service.IngredientUpdateService
import com.ladsoft.bakingapp.util.UiUtils
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.*
import javax.inject.Inject

class RecipeActivity : AppCompatActivity(), HasSupportFragmentInjector, RecipeMvp.View, RecipeStepFragment.Callback, StepsMvp.View {
    @JvmField @Inject var dispatchingAndroidFragmentInjector: DispatchingAndroidInjector<Fragment>? = null

    @JvmField @Inject var presenter: RecipePresenter? = null
    @JvmField @Inject var sessionManager: SessionManager? = null
    @JvmField @BindView(R.id.toolbar)
    var toolbar: Toolbar? = null

    @JvmField @BindView(R.id.recipe_name)
    var recipeName: TextView? = null

    @JvmField @BindView(R.id.servings)
    var servings: TextView? = null

    @JvmField @BindView(R.id.ingredient_count)
    var ingredientCount: TextView? = null

    @JvmField @BindView(R.id.step_count)
    var stepCount: TextView? = null

    @JvmField @BindView(R.id.content)
    var content: FrameLayout? = null

    @JvmField @BindView(R.id.detail)
    var detail: ViewPager? = null

    @JvmField @BindString(R.string.recipe_error_generic)
    var genericErrorMessage: String? = null

    private var slideshowAdapter: StepSlideshowAdapter? = null

    private var recipeFragment: RecipeFragment? = null

    private val DATA_STATE = "data_state"
    private var stepPresenter: StepPresenter? = null

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidFragmentInjector!!

    private val stepAdapterListener = object : RecipeStepsAdapter.Listener {
        override fun onItemClickListener(itemIndex: Int, steps: List<Step>?) {
            if (detail != null) {
                detail?.visibility = View.VISIBLE

                stepPresenter?.setCurrentStepIndex(itemIndex)
                stepPresenter?.showCurrentStep()
            } else {
                val intent = Intent(this@RecipeActivity, RecipeStepActivity::class.java)
                intent.putParcelableArrayListExtra(RecipeStepActivity.EXTRA_STEPS, steps as ArrayList<Step>?)
                intent.putExtra(RecipeStepActivity.EXTRA_STEP_INDEX, itemIndex)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        ButterKnife.bind(this)

        setupViews()
        setupFragments(savedInstanceState != null)

        stepPresenter = StepPresenter()

        val state: RecipeState

        state = if (savedInstanceState == null) RecipeState(intent.extras)
                else RecipeState(savedInstanceState.getSerializable(DATA_STATE))

        startService(Intent(this, IngredientUpdateService::class.java))
        presenter?.setData(state)
    }

    override fun onStart() {
        super.onStart()
        presenter?.attachView(this)
        stepPresenter?.attachView(this@RecipeActivity)
        presenter?.loadData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(DATA_STATE, presenter?.state?.getStateMap())
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        presenter?.detachView()
        stepPresenter?.detachView()
        super.onStop()
    }

    override fun showRefresh(show: Boolean) {}

    override fun onRecipeLoaded(recipe: Recipe) {
        recipeName?.text = recipe.name
        toolbar?.title = recipe.name

        servings?.text = String.format(getString(R.string.recipe_serving_count_format),
                recipe.servings)

        ingredientCount?.text = String.format(getString(R.string.recipe_ingredient_count_format),
                recipe.getIngredientCount())

        stepCount?.text = String.format(getString(R.string.recipe_step_count_format),
                recipe.getStepCount())

        recipeFragment?.setDatasource(recipe)
        stepPresenter?.setData(0, recipe.steps, false)
    }

    override fun onRecipeLoadError() {
        UiUtils.showSnackbar(content!!, genericErrorMessage!!, null, Snackbar.LENGTH_LONG, null)
    }

    private fun setupViews() {
        toolbar?.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupFragments(isRestore: Boolean) {
        val fragmentManager = supportFragmentManager

        recipeFragment = fragmentManager.findFragmentById(content!!.id) as RecipeFragment?
        if (recipeFragment  == null) {
            recipeFragment = RecipeFragment.newInstance()
            fragmentManager.beginTransaction()
                    .replace(content!!.id, recipeFragment)
                    .commitNow()
        }

        if (detail != null && !isRestore) {
            detail?.visibility = View.INVISIBLE
        }

        recipeFragment?.setListener(stepAdapterListener)

        if (detail != null) {
            slideshowAdapter = StepSlideshowAdapter(supportFragmentManager, this)
            detail?.adapter = slideshowAdapter

            detail?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    stepPresenter?.setCurrentStepIndex(position)
                }
            })
        }
    }

    override fun onNextPress() {
        stepPresenter?.nextStep()
    }

    override fun onPreviousPress() {
        stepPresenter?.previousStep()
    }

    override fun onVisible(): Step? {
        return stepPresenter?.stepData()
    }

    override fun showStep(position: Int) {
        Log.d("RECIPE_STEP", "Navigating to index $position")
        detail?.setCurrentItem(position, true)
    }

    override fun onStepsLoaded(steps: List<Step>) {
        if (detail != null) {
            slideshowAdapter?.setDataSource(steps)
        }
    }

    companion object {
        val EXTRA_RECIPE_ID = "extra_recipe_id"
    }

}
