package com.veselovvv.movies20

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.textview.MaterialTextView
import org.hamcrest.CoreMatchers.allOf

class TextViewUi {
    fun interaction(@IdRes textViewId: Int) = onView(
        allOf(
            withId(textViewId),
            isAssignableFrom(MaterialTextView::class.java)
        )
    )

    fun checkText(@IdRes textViewId: Int, text: String) {
        interaction(textViewId).perform(scrollTo()).check(matches(withText(text)))
    }

    fun scrollTo(@IdRes textViewId: Int) {
        interaction(textViewId).perform(scrollTo())
    }
}