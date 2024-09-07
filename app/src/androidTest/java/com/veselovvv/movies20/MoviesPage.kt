package com.veselovvv.movies20

class MoviesPage : AbstractPage(R.id.movies_root_layout) {
    private val searchViewUi = SearchViewUi()
    private val recyclerViewUi = RecyclerViewUi(
        R.id.movies_swipe_to_refresh,
        R.id.movies_recycler_view
    )

    private val swipeToRefreshUi = SwipeToRefreshUi(
        R.id.movies_root_layout,
        R.id.movies_swipe_to_refresh
    )

    fun checkMoviesListState(movies: List<Pair<String, String>>) =
        recyclerViewUi.checkMoviesListState(movies = movies)

    fun swipeToRefresh() = swipeToRefreshUi.swipeToRefresh()
    fun clickSearchButton() = searchViewUi.clickSearchButton(searchMenuItemId = R.id.action_search_movies)
    fun checkSearchViewState() = searchViewUi.checkSearchViewState()
    fun clickBackSearchButton() = searchViewUi.clickBackSearchButton()
    fun typeInSearchView(text: String) = searchViewUi.typeInSearchView(text = text)
}
