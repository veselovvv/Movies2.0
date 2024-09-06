package com.veselovvv.movies20

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.Matchers.allOf

class RecyclerViewUi(swipeToRefreshId: Int, recyclerViewId: Int) {
    private val interaction: ViewInteraction = onView(
        allOf(
            withParent(withId(swipeToRefreshId)),
            withParent(isAssignableFrom(SwipeRefreshLayout::class.java)),
            withId(recyclerViewId),
            isAssignableFrom(RecyclerView::class.java)
        )
    )

    fun checkMoviesListState(movies: List<Pair<String, String>>) {
        movies.forEachIndexed { index, (title, year) ->
            interaction.perform(scrollToPosition<RecyclerView.ViewHolder>(index))
                .check(matches(isDisplayed()))
                .check(matches(withRecyclerViewItemText(R.id.title_text_view, title)))
                .check(matches(withRecyclerViewItemText(R.id.release_year_text_view, year)))
        }
    }
}
