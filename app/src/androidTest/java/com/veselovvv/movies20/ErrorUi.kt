package com.veselovvv.movies20

import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import org.hamcrest.Matchers.allOf

class ErrorUi(@IdRes private val failLayoutId: Int) {
    private val parentIdMatcher = withId(failLayoutId)
    private val parentClassMatcher = isAssignableFrom(LinearLayout::class.java)

    fun checkErrorState(message: String) {
        onView(
            allOf(parentIdMatcher, parentClassMatcher)
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withParent(parentIdMatcher),
                withParent(parentClassMatcher),
                withId(R.id.fail_message_text_view),
                isAssignableFrom(MaterialTextView::class.java)
            )
        ).check(matches(withText(message)))
    }

    fun clickRetryButton() {
        onView(
            allOf(
                withParent(parentIdMatcher),
                withParent(parentClassMatcher),
                withId(R.id.fail_try_again_button),
                isAssignableFrom(MaterialButton::class.java)
            )
        ).perform(click())
    }
}
