package com.veselovvv.movies20

import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.veselovvv.movies20.core.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MoviesTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        hiltRule.inject()
    }

    /**
     * Check movies list state
     * 1. Swipe to refresh
     * Check movies list state
     * 2. Recreate activity
     * Check movies list state
     */
    @Test
    fun loadMovies() = with(MoviesPage()) {
        checkMoviesListState(
            movies = listOf(
                Pair("Back to the Future", "1985"),
                Pair("Eight Crazy Nights", "2002"),
                Pair("Kill Bill: Vol. 1", "2003")
            )
        )

        swipeToRefresh()
        checkMoviesListState(
            movies = listOf(
                Pair("Back to the Future", "1985"),
                Pair("Eight Crazy Nights", "2002"),
                Pair("Kill Bill: Vol. 1", "2003")
            )
        )

        activityScenarioRule.scenario.recreate()
        checkMoviesListState(
            movies = listOf(
                Pair("Back to the Future", "1985"),
                Pair("Eight Crazy Nights", "2002"),
                Pair("Kill Bill: Vol. 1", "2003")
            )
        )
    }

    /**
     * Check movies list state
     * 1. Click search button
     * Check search view state
     * 2. CLick back search button
     * Check movies list state
     * 3. Click search button
     * Check search view state
     * 4. Type "Eight" in search view
     * Check movies list state with found movie
     * 5. CLick back search button
     * Check movies list state
     */
    @Test
    fun searchMovies() = with(MoviesPage()) {
        checkMoviesListState(
            movies = listOf(
                Pair("Back to the Future", "1985"),
                Pair("Eight Crazy Nights", "2002"),
                Pair("Kill Bill: Vol. 1", "2003")
            )
        )

        clickSearchButton()
        checkSearchViewState()

        clickBackSearchButton()
        checkMoviesListState(
            movies = listOf(
                Pair("Back to the Future", "1985"),
                Pair("Eight Crazy Nights", "2002"),
                Pair("Kill Bill: Vol. 1", "2003")
            )
        )

        clickSearchButton()
        checkSearchViewState()

        typeInSearchView(text = "Eight")
        checkMoviesListState(
            movies = listOf(
                Pair("Eight Crazy Nights", "2002")
            )
        )

        clickBackSearchButton()
        checkMoviesListState(
            movies = listOf(
                Pair("Back to the Future", "1985"),
                Pair("Eight Crazy Nights", "2002"),
                Pair("Kill Bill: Vol. 1", "2003")
            )
        )
    }

    /**
     * Check Movies Page is visible
     * Check movies list state
     * 1. Click on first item in list (index = 0)
     * Check Movie Info Page is visible
     * Check movie info state
     * 2. Recreate activity
     * Check error state with text "No connection. Please try again!"
     * 3. Click "Retry" button
     * Check Movie Info Page is visible
     * Check movie info state
     * 4. Scroll up
     * 5. Swipe to refresh
     * Check error state with text "No connection. Please try again!"
     * 6. Click "Retry" button
     * Check Movie Info Page is visible
     * Check movie info state
     * 7. Press back button
     * Check Movie Info Page is not visible
     * Check Movies Page is visible
     * Check movies list state
     */
    @Test
    fun loadMovieInfoAndGoBack() {
        val moviesPage = MoviesPage()

        with(moviesPage) {
            checkIsVisible()
            checkMoviesListState(
                movies = listOf(
                    Pair("Back to the Future", "1985"),
                    Pair("Eight Crazy Nights", "2002"),
                    Pair("Kill Bill: Vol. 1", "2003")
                )
            )
            clickOnItemInList(index = 0)
        }

        val movieInfoPage = MovieInfoPage()

        with(movieInfoPage) {
            checkIsVisible()
            checkMovieInfoState(
                title = "Back to the Future",
                date = "03.07.1985",
                rating = "8.318",
                runtime = "116",
                budget = "$19.000.000",
                revenue = "$381.109.762",
                overview = "Eighties teenager Marty McFly is accidentally sent back in time to 1955..."
            )

            activityScenarioRule.scenario.recreate()
            checkErrorState(message = "No connection. Please try again!")

            clickRetryButton()
            checkIsVisible()
            checkMovieInfoState(
                title = "Back to the Future",
                date = "03.07.1985",
                rating = "8.318",
                runtime = "116",
                budget = "$19.000.000",
                revenue = "$381.109.762",
                overview = "Eighties teenager Marty McFly is accidentally sent back in time to 1955..."
            )

            scrollUp()
            swipeToRefresh()
            checkErrorState(message = "No connection. Please try again!")

            clickRetryButton()
            checkIsVisible()
            checkMovieInfoState(
                title = "Back to the Future",
                date = "03.07.1985",
                rating = "8.318",
                runtime = "116",
                budget = "$19.000.000",
                revenue = "$381.109.762",
                overview = "Eighties teenager Marty McFly is accidentally sent back in time to 1955..."
            )
        }

        pressBack()
        movieInfoPage.checkIsNotVisible()

        with(moviesPage) {
            checkIsVisible()
            checkMoviesListState(
                movies = listOf(
                    Pair("Back to the Future", "1985"),
                    Pair("Eight Crazy Nights", "2002"),
                    Pair("Kill Bill: Vol. 1", "2003")
                )
            )
        }
    }
}