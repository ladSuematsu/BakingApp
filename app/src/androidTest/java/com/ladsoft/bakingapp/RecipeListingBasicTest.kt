package com.ladsoft.bakingapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.registerIdlingResources
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.base.IdlingResourceRegistry
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.test.suitebuilder.annotation.LargeTest
import android.view.View
import android.view.ViewGroup
import com.ladsoft.bakingapp.activity.MainActivity
import com.ladsoft.bakingapp.data.network.api.BakingAppApi
import com.ladsoft.bakingapp.data.network.api.BakingAppApiModule
import com.ladsoft.bakingapp.fragment.RecipesFragment
import com.ladsoft.bakingapp.mvp.EspressoIdlingResource
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

//    private lateinit var idlingResource: CountingIdlingResource
//    private lateinit var networkIdlingResource: IdlingResource

    @Before
    fun before() {
        Intents.init()

//        val fragment = activityTestRule.activity
//                .supportFragmentManager
//                .findFragmentByTag(RecipesFragment.TAG) as RecipesFragment

//        idlingResource = fragment.countingIdlingResource
//        networkIdlingResource = BakingAppApiModule.idlingResource

//        IdlingRegistry.getInstance().register(idlingResource, networkIdlingResource)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun checkRecipeListItem() {
        onView(withId(R.id.recipes))
                .check(
                    matches(isDisplayed())
                )

        assertThat(listItemCount(), greaterThan(0))
    }

    @After
    fun after() {
//        IdlingRegistry.getInstance().unregister(idlingResource, networkIdlingResource)
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
