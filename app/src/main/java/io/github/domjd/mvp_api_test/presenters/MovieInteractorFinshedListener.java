package io.github.domjd.mvp_api_test.presenters;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Dom on 25/11/2016.
 */
public interface MovieInteractorFinshedListener {
    void onNetworkSuccess(Call movie, Response response);
    void onNetworkFailure(Call movie, Throwable t);
}
