package com.veselovvv.movies20

import android.widget.LinearLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.Matchers.allOf

class SwipeToRefreshUi(rootLayout: Int, swipeToRefreshLayout: Int) {
    private val interaction: ViewInteraction = onView(
        allOf(
            withParent(withId(rootLayout)),
            withParent(isAssignableFrom(LinearLayout::class.java)),
            withId(swipeToRefreshLayout),
            isAssignableFrom(SwipeRefreshLayout::class.java)
        )
    )

    fun swipeToRefresh() {
        interaction.perform(swipeDown())
    }
}