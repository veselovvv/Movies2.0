package com.veselovvv.movies20

class MovieInfoPage : AbstractPage(R.id.movie_info_root_layout) {
    private val swipeToRefreshUi = SwipeToRefreshUi(
        R.id.movie_info_root_layout,
        R.id.movie_info_swipe_to_refresh
    )
    private val errorUi = ErrorUi(R.id.movie_info_fail_layout)
    private val textViewUi = TextViewUi()

    fun swipeToRefresh() = swipeToRefreshUi.swipeToRefresh()
    fun checkErrorState(message: String) = errorUi.checkErrorState(message)
    fun clickRetryButton() = errorUi.clickRetryButton()
    fun scrollUp() = textViewUi.scrollTo(R.id.movie_info_title)

    fun checkMovieInfoState(
        title: String,
        date: String,
        rating: String,
        runtime: String,
        budget: String,
        revenue: String,
        overview: String
    ) {
        with(textViewUi) {
            checkText(R.id.movie_info_title, title)
            checkText(R.id.movie_info_release_date, date)
            checkText(R.id.movie_info_rating, rating)
            checkText(R.id.movie_info_runtime, runtime)
            checkText(R.id.movie_info_budget, budget)
            checkText(R.id.movie_info_revenue, revenue)
            checkText(R.id.movie_info_overview, overview)
        }
    }
}
