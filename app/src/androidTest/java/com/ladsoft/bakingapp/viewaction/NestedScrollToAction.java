package com.ladsoft.bakingapp.viewaction;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ScrollToAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class NestedScrollToAction implements ViewAction {
    private final ScrollToAction original;

    public NestedScrollToAction(ScrollToAction original) {
        this.original = original;
    }

    @Override
    public Matcher<View> getConstraints() {
        return Matchers.anyOf(
                    Matchers.allOf(
                            ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                            ViewMatchers.isDescendantOfA(ViewMatchers.isAssignableFrom(NestedScrollView.class))
                            )
                    , original.getConstraints()
                );
    }

    @Override
    public String getDescription() {
        return original.getDescription();
    }

    @Override
    public void perform(UiController uiController, View view) {
        this.original.perform(uiController, view);
    }
}