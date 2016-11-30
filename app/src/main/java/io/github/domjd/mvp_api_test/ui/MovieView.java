package io.github.domjd.mvp_api_test.ui;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Dom on 25/11/2016.
 */
public interface MovieView {
    void onMovieLoadedSuccess(Call movie, Response response);
    void onMovieLoadedFailure(Call movie, Throwable t);
    Context getContext();
}
