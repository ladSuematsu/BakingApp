package com.ladsoft.bakingapp

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ScrollToAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.test.mock.MockContext
import com.ladsoft.bakingapp.activity.RecipeActivity
import com.ladsoft.bakingapp.activity.RecipeStepActivity
import com.ladsoft.bakingapp.adapter.RecipeStepsAdapter
import com.ladsoft.bakingapp.mvp.EspressoIdlingResource
import com.ladsoft.bakingapp.viewaction.NestedScrollToAction
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RecipeStepActivityTest {

    @Rule @JvmField
    val activityTestRule = ActivityTestRule(RecipeActivity::class.java, false, false)

    @Before
    fun before() {
        Intents.init()
        IdlingRegistry.getInstance()
                .register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun stepNavigation() {
        activityTestRule.launchActivity(Intent()
                .putExtra(RecipeActivity.EXTRA_RECIPE_ID, TestValues.BROWNIES_RECIPE_ID))

        Espresso.onView(ViewMatchers.withId(R.id.steps))
                .perform(ViewActions.actionWithAssertions(NestedScrollToAction(ScrollToAction())))
                .check(ViewAssertions.matches(Matchers.allOf(
                        ViewMatchers.hasMinimumChildCount(2),
                        ViewMatchers.hasDescendant(ViewMatchers.withText(TestValues.BROWNIES_STEP_TEXT)))
                ))
                .perform(RecyclerViewActions.scrollTo<RecipeStepsAdapter.StepViewHolder>(ViewMatchers.hasDescendant(ViewMatchers.withText(TestValues.BROWNIES_STEP_TEXT))))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecipeStepsAdapter.StepViewHolder>(1, ViewActions.click()))

        Intents.intended(IntentMatchers.hasComponent(RecipeStepActivity::class.qualifiedName))
        Intents.intended(IntentMatchers.hasExtraWithKey(RecipeStepActivity.EXTRA_STEPS))
        Intents.intended(IntentMatchers.hasExtraWithKey(RecipeStepActivity.EXTRA_STEP_INDEX))

        val stepDescriptionMatcher = object: BaseMatcher<String>() {
            override fun describeTo(description: Description?) {
                description?.appendText("With description starting with: ")
            }

            override fun matches(item: Any?): Boolean {
                val test = item as String

                return test?.startsWith(TestValues.BROWNIES_STEP_DESCRIPTION_PREFIX, true)
            }

        }

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.step_description), ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers
                                .withText(stepDescriptionMatcher)
                        ))

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.previous), ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.step_description), ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(Matchers.not(
                            ViewMatchers.withText(stepDescriptionMatcher)
                        )))

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.next), ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.next), ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.previous), ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.step_description), ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(ViewMatchers.withText(stepDescriptionMatcher)))
    }

    @After
    fun after() {
        IdlingRegistry.getInstance()
                .unregister(EspressoIdlingResource.countingIdlingResource)
        Intents.release()
    }

}