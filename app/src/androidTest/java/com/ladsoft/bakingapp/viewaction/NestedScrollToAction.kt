package com.ladsoft.bakingapp.viewaction

import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ScrollToAction
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v4.widget.NestedScrollView
import android.view.View
import org.hamcrest.Matcher
import org.hamcrest.Matchers

internal class NestedScrollToAction (private val original: ScrollToAction): ViewAction by original {
    override fun getConstraints(): Matcher<View> = Matchers.anyOf(
            Matchers.allOf(
                    ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                    ViewMatchers.isDescendantOfA(ViewMatchers.isAssignableFrom(NestedScrollView::class.java))
            ),
            original.constraints
    )
}