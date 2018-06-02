package com.ladsoft.bakingapp;

import android.content.Intent;
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
import com.ladsoft.bakingapp.viewaction.NestedScrollToAction;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeStepActivityTest {

    @Rule
    private final ActivityTestRule activityTestRule = new ActivityTestRule<>(RecipeActivity.class, false, false);

    @Before
    private void before() {
        Intents.init();
        IdlingRegistry.getInstance()
                .register(EspressoIdlingResource.countingIdlingResource);
    }

    @Test
    private void stepNavigation() {
        activityTestRule.launchActivity(new Intent()
                .putExtra(RecipeActivity.EXTRA_RECIPE_ID, TestValues.BROWNIES_RECIPE_ID));

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

        BaseMatcher<String> stepDescriptionMatcher = new BaseMatcher<String>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("With description starting with: ");
            }

            @Override
            public boolean matches(Object item) {
                String test = (String) item;

                return test.startsWith(TestValues.BROWNIES_STEP_DESCRIPTION_PREFIX);
            }

        };

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.step_description), ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers
                                .withText(stepDescriptionMatcher)
                        ));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.previous), ViewMatchers.isDisplayed()))
            .perform(ViewActions.click());
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.step_description), ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(Matchers.not(
                            ViewMatchers.withText(stepDescriptionMatcher)
                        )));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.next), ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.next), ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.previous), ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.step_description), ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(ViewMatchers.withText(stepDescriptionMatcher)));
    }

    @After
    private void after() {
        IdlingRegistry.getInstance()
                .unregister(EspressoIdlingResource.countingIdlingResource);
        Intents.release();
    }

}