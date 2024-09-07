package com.veselovvv.movies20

import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.allOf

class SearchViewUi {
    private val searchEditTextInteraction: ViewInteraction = onView(
        allOf(
            isDescendantOfA(isAssignableFrom(SearchView::class.java)),
            isAssignableFrom(EditText::class.java)
        )
    )

    fun clickSearchButton(searchMenuItemId: Int) {
        onView(withId(searchMenuItemId)).perform(click())
    }

    fun checkSearchViewState() {
        searchEditTextInteraction.check(matches(isDisplayed()))
    }

    fun clickBackSearchButton() {
        onView(
            allOf(
                isAssignableFrom(ImageView::class.java),
                withContentDescription("Collapse")
            )
        ).perform(click())
    }

    fun typeInSearchView(text: String) {
        searchEditTextInteraction.perform(clearText()).perform(typeText(text))
    }
}
