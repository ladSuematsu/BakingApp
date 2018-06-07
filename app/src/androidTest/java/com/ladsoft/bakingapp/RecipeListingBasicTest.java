package com.ladsoft.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.ladsoft.bakingapp.activity.MainActivity;
import com.ladsoft.bakingapp.activity.RecipeActivity;
import com.ladsoft.bakingapp.adapter.RecipesAdapter;
import com.ladsoft.bakingapp.mvp.EspressoIdlingResource;
import com.ladsoft.bakingapp.mvp.RecipeMvp;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;

@RunWith(AndroidJUnit4.class)
public class RecipeListingBasicTest {

    @Rule
    public final ActivityTestRule activityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void before() {
        Intents.init();

        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource);
    }

    @Test
    public void checkRecipeListItem() {
        Espresso.onView(ViewMatchers.withId(R.id.recipes))
                .check(
                    ViewAssertions.matches(ViewMatchers.isDisplayed())
                );

        ViewMatchers.assertThat(listItemCount(), greaterThan(0));
    }

    @Test
    public void clickOnItemRecipeOpensRecipePage() {
        Espresso.onView(ViewMatchers.withId(R.id.recipes))
                .check(
                        ViewAssertions.matches(ViewMatchers.isDisplayed())
                );

        TypeSafeMatcher<RecipesAdapter.RecipeViewHolder> matcher =  new TypeSafeMatcher<RecipesAdapter.RecipeViewHolder>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("List item found with content " + TestValues.BROWNIES_RECIPE_TEXT);
            }

            @Override
            public boolean matchesSafely(RecipesAdapter.RecipeViewHolder item) {
                TextView recipeNameTextView = item.itemView.findViewById(R.id.title);

                return recipeNameTextView.getText().toString().equals(TestValues.BROWNIES_RECIPE_TEXT);
            }
        };

        Espresso.onView(ViewMatchers.withId(R.id.recipes))
                .perform(RecyclerViewActions.scrollToHolder(matcher))
                .perform(RecyclerViewActions.actionOnItem(
                        ViewMatchers.hasDescendant(ViewMatchers.withText(TestValues.BROWNIES_RECIPE_TEXT)),
                        ViewActions.click()));

        Intents.intended(IntentMatchers.hasComponent(RecipeActivity.class.getName()));
        Intents.intended(IntentMatchers.hasExtra(RecipeMvp.StateContainer.EXTRA_RECIPE_ID, TestValues.BROWNIES_RECIPE_ID));
    }


    @After
    public void after() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource);
        Intents.release();
    }

    private int listItemCount() {
        return ((RecyclerView)activityTestRule.getActivity()
                .findViewById(R.id.recipes))
                .getAdapter().getItemCount();
    }

    private Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {
        TypeSafeMatcher<View> matcher = new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                ViewParent parent = item.getParent();
                return parent != null
                        && parent instanceof ViewGroup
                        && parentMatcher.matches(parent);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

        };

        return matcher;
    }
}
