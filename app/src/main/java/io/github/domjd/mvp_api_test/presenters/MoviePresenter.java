package io.github.domjd.mvp_api_test.presenters;

import retrofit2.Call;
import retrofit2.Response;
import io.github.domjd.mvp_api_test.models.MovieInteractor;
import io.github.domjd.mvp_api_test.ui.MovieView;

/**
 * Created by Dom on 25/11/2016.
 */
public class MoviePresenter implements iMoviePresenter, MovieInteractorFinshedListener {

    private MovieView view;
    private MovieInteractor interactor;

    public MoviePresenter(MovieView view) {
        this.view = view;
        this.interactor = new MovieInteractor(this);
    }

    @Override
    public void loadMovie(String title) {
        interactor.loadMovie(title);
    }

    @Override
    public void onNetworkSuccess(Call movie, Response response) {
        view.onMovieLoadedSuccess(movie,response);
    }

    @Override
    public void onNetworkFailure(Call movie, Throwable t) {
        view.onMovieLoadedFailure(movie,t);
    }
}