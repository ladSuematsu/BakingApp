package com.ladsoft.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ScrollToAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ladsoft.bakingapp.activity.RecipeActivity;
import com.ladsoft.bakingapp.activity.RecipeStepActivity;
import com.ladsoft.bakingapp.mvp.EspressoIdlingResource;
import com.ladsoft.bakingapp.mvp.RecipeMvp;
import com.ladsoft.bakingapp.viewaction.NestedScrollToAction;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class RecipeDetailsFragmentTest {

    @Rule
    public final ActivityTestRule<RecipeActivity> activityTestRule = new ActivityTestRule<RecipeActivity>(RecipeActivity.class) {
        @Override
        public Intent getActivityIntent() {
            Context targetContext =
                    InstrumentationRegistry.getInstrumentation().getTargetContext();

            return new Intent(targetContext, RecipeActivity.class)
                            .putExtra(RecipeMvp.StateContainer.EXTRA_RECIPE_ID, TestValues.BROWNIES_RECIPE_ID);
        }
    };

    @Before
    public void before() {
        Intents.init();
        IdlingRegistry.getInstance()
                .register(EspressoIdlingResource.countingIdlingResource);
    }

    @Test
    public void loadBrowniesRecipe() {
        Espresso.onView(ViewMatchers.withId(R.id.ingredients))
                .check(ViewAssertions.matches(
                            ViewMatchers.hasMinimumChildCount(1))
                    );

        Espresso.onView(ViewMatchers.withId(R.id.steps))
                .check(ViewAssertions.matches(
                        ViewMatchers.hasMinimumChildCount(1))
                );
    }

    @Test
    public void loadRecipeStep() {
        Espresso.onView(ViewMatchers.withId(R.id.steps))
                .perform(ViewActions.actionWithAssertions(new NestedScrollToAction(new ScrollToAction())))
                .check(ViewAssertions.matches(Matchers.allOf(
                                        ViewMatchers.hasMinimumChildCount(2),
                                        ViewMatchers.hasDescendant(ViewMatchers.withText(TestValues.BROWNIES_STEP_TEXT)))
                                    ))
                .perform(RecyclerViewActions.scrollTo(ViewMatchers.hasDescendant(ViewMatchers.withText(TestValues.BROWNIES_STEP_TEXT))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));

        Intents.intended(IntentMatchers.hasComponent(RecipeStepActivity.class.getName()));
        Intents.intended(IntentMatchers.hasExtraWithKey(RecipeStepActivity.EXTRA_STEPS));
        Intents.intended(IntentMatchers.hasExtraWithKey(RecipeStepActivity.EXTRA_STEP_INDEX));
    }

    @After
    public void after() {
        IdlingRegistry.getInstance()
                .unregister(EspressoIdlingResource.countingIdlingResource);
        Intents.release();
    }

}