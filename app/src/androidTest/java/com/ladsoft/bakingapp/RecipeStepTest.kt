package com.ladsoft.bakingapp

import android.content.Intent
import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.ladsoft.bakingapp.activity.RecipeStepActivity
import com.ladsoft.bakingapp.entity.Step
import com.ladsoft.bakingapp.mvp.EspressoIdlingResource
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class RecipeStepTest {

    val stepIndex = 1
    var steps = listOf(Step (
            0,
            2,
            "Recipe Introduction",
            "Recipe Introduction",
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc33_-intro-brownies/-intro-brownies.mp4",
            ""),
            Step(1,
                    2,
                    "Starting prep",
                    "1. Preheat the oven to 350�F. Butter the bottom and sides of a 9'x13' pan.",
                    "",
                    ""),
            Step(2,
            2,
            "Melt butter and bittersweet chocolate.",
            "2. Melt the butter and bittersweet chocolate together in a microwave or a double boiler. If microwaving, heat for 30 seconds at a time, removing bowl and stirring ingredients in between.",
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc43_1-melt-choclate-chips-and-butter-brownies/1-melt-choclate-chips-and-butter-brownies.mp4",
            "")
        )

    @Rule @JvmField
    val activityTestRule = object : ActivityTestRule<RecipeStepActivity>(RecipeStepActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext

            return Intent(targetContext, RecipeStepActivity::class.java)
                    .putExtra(RecipeStepActivity.EXTRA_STEP_INDEX, stepIndex)
                    .putParcelableArrayListExtra(RecipeStepActivity.EXTRA_STEPS,  ArrayList<Step>(steps))
        }
    }

    @Before
    fun before() {
        Intents.init()
        IdlingRegistry.getInstance()
                .register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun stepClickTest() {
        checkViewPagerPageContent(steps[stepIndex])

        clickPrevious()

        checkViewPagerPageContent(steps[stepIndex - 1])

        clickNext()
        clickNext()

        checkViewPagerPageContent(steps[stepIndex + 1])

    }

    @Test
    fun stepSwipeTest() {
        checkViewPagerPageContent(steps[stepIndex])

        previousPage()

        checkViewPagerPageContent(steps[stepIndex - 1])

        nextPage()
        nextPage()


        checkViewPagerPageContent(steps[stepIndex + 1])
    }

    private fun previousPage() {
        Espresso.onView(ViewMatchers.withId(R.id.step_pager))
                .perform(ViewActions.swipeRight())

        SystemClock.sleep(1000)
    }

    private fun nextPage() {
        Espresso.onView(ViewMatchers.withId(R.id.step_pager))
                .perform(ViewActions.swipeLeft())

        SystemClock.sleep(1000)
    }


    private fun clickPrevious() {
        Espresso.onView(Matchers.allOf(
                ViewMatchers.withId(R.id.previous),
                ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
    }

    private fun clickNext() {
        Espresso.onView(Matchers.allOf(
                ViewMatchers.withId(R.id.next),
                ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
    }

    private fun checkViewPagerPageContent(step: Step) {
        Espresso.onView(Matchers.allOf(
                ViewMatchers.withId(R.id.step_description),
                ViewMatchers.isCompletelyDisplayed()))
                .check(ViewAssertions.matches(
                        ViewMatchers.withText(step.description)))
    }

    @After
    fun after() {
        IdlingRegistry.getInstance()
                .unregister(EspressoIdlingResource.countingIdlingResource)
        Intents.release()
    }

}