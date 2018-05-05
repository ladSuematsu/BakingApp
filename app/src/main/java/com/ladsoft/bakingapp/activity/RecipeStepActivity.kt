package com.ladsoft.bakingapp.activity

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.adapter.StepSlideshowAdapter
import com.ladsoft.bakingapp.application.BakingAppApplication
import com.ladsoft.bakingapp.entity.Step
import com.ladsoft.bakingapp.fragment.RecipeStepFragment
import com.ladsoft.bakingapp.mvp.StepsMvp
import com.ladsoft.bakingapp.mvp.presenter.StepPresenter
import javax.inject.Inject


class RecipeStepActivity : AppCompatActivity(), StepsMvp.View, RecipeStepFragment.Callback {

    @JvmField @BindView(R.id.toolbar)
    var toolbar: Toolbar? = null

    @JvmField @BindView(R.id.step_pager)
    var content: ViewPager? = null

    @JvmField @Inject
    var presenter: StepPresenter? = null

    private var slideshowAdapter: StepSlideshowAdapter? = null
    private var activityCreation: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onCreate: " + (savedInstanceState?.toString() ?: "NULL"))

        activityCreation = true

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step)
        ButterKnife.bind(this)

        BakingAppApplication.appComponent.inject(this)

        setupViews()
        setupFragments()
    }

    override fun onStart() {
        Log.d(LOG_TAG, "onStart")

        presenter?.attachView(this)
        if (activityCreation) {

            val intent = intent

            presenter?.setData(intent.getIntExtra(EXTRA_STEP_INDEX, 0),
                    intent.getParcelableArrayListExtra<Parcelable>(EXTRA_STEPS) as List<Step>?,
                    true)

            presenter?.showCurrentStep()

            activityCreation = false
        }

        super.onStart()
    }

    override fun onStop() {
        Log.d(LOG_TAG, "onStop")

        presenter?.detachView()
        super.onStop()
    }

    private fun setupViews() {
        toolbar?.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupFragments() {
        slideshowAdapter = StepSlideshowAdapter(supportFragmentManager, this)
        content?.adapter = slideshowAdapter

        content?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                presenter?.setCurrentStepIndex(position)
            }
        })
    }

    override fun onStepsLoaded(steps: List<Step>) {
        slideshowAdapter!!.setDataSource(steps)
    }

    override fun onNextPress() {
        presenter?.nextStep()
    }

    override fun onPreviousPress() {
        presenter?.previousStep()
    }

    override fun onVisible(): Step? {
        return presenter?.stepData()
    }

    override fun showStep(position: Int) {
        Log.d(LOG_TAG, "Navigating to index $position")
        content?.setCurrentItem(position, true)
    }

    companion object {
        private val LOG_TAG = RecipeStepActivity::class.java.simpleName

        val EXTRA_STEPS = "extra_steps"
        val EXTRA_STEP_INDEX = "extra_step_index"
    }
}
