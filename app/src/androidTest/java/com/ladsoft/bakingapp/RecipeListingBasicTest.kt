package com.ladsoft.bakingapp

import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.test.suitebuilder.annotation.LargeTest
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ladsoft.bakingapp.activity.MainActivity
import com.ladsoft.bakingapp.activity.RecipeActivity
import com.ladsoft.bakingapp.adapter.RecipesAdapter
import com.ladsoft.bakingapp.mvp.EspressoIdlingResource
import com.ladsoft.bakingapp.mvp.RecipeMvp
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RecipeListingBasicTest {

    @Rule @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun before() {
        Intents.init()

        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun checkRecipeListItem() {
        Espresso.onView(ViewMatchers.withId(R.id.recipes))
                .check(
                    ViewAssertions.matches(ViewMatchers.isDisplayed())
                )

        ViewMatchers.assertThat(listItemCount(), greaterThan(0))
    }

    @Test
    fun clickOnItemRecipeOpensRecipePage() {
        Espresso.onView(ViewMatchers.withId(R.id.recipes))
                .check(
                        ViewAssertions.matches(ViewMatchers.isDisplayed())
                )

        val matcher = object: TypeSafeMatcher<RecipesAdapter.RecipeViewHolder>() {
            override fun describeTo(description: Description?) {
                description?.appendText("List item found with content " + Utils.BROWNIES_RECIPE_TEXT)
            }

            override fun matchesSafely(item: RecipesAdapter.RecipeViewHolder?): Boolean {
                val recipeNameTextView = item?.itemView?.findViewById<TextView>(R.id.title)

                return recipeNameTextView?.text?.toString().equals(Utils.BROWNIES_RECIPE_TEXT)
            }
        }

        Espresso.onView(ViewMatchers.withId(R.id.recipes))
                .perform(RecyclerViewActions.scrollToHolder(matcher))
                .perform(RecyclerViewActions.actionOnItem<RecipesAdapter.RecipeViewHolder>(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(Utils.BROWNIES_RECIPE_TEXT)),
                        ViewActions.click()))

        Intents.intended(IntentMatchers.hasComponent(RecipeActivity::class.qualifiedName))
        Intents.intended(IntentMatchers.hasExtra(RecipeMvp.StateContainer.EXTRA_RECIPE_ID, Utils.BROWNIES_RECIPE_ID))
    }


    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        Intents.release()
    }

    private fun listItemCount() = activityTestRule.activity
            .findViewById<RecyclerView>(R.id.recipes)
            .adapter.itemCount

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View>? {
        val matcher = object: TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View?): Boolean {
                val parent = item?.parent
                return parent != null
                        && parent is ViewGroup
                        && parentMatcher.matches(parent)
            }

            override fun describeTo(description: Description?) {
                description?.appendText("Child at position " + position + " in parent ")
                return parentMatcher.describeTo(description)
            }

        }

        return matcher
    }
}
