package uk.domdudley.mvp_api_test.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Response;
import uk.domdudley.mvp_api_test.tools.PosterBitmap;
import uk.domdudley.mvp_api_test.R;
import uk.domdudley.mvp_api_test.models.Movie;
import uk.domdudley.mvp_api_test.presenters.MoviePresenter;

public class MainActivity extends AppCompatActivity implements MovieView {

    MoviePresenter presenter;

    TextView tvMovieTitle;
    TextView tvMovieReleaseDate;
    TextView tvPlot;
    ImageView ivPoster;
    TextView tvIMDBRating;
    TextView tvMetacritic;
    TextView tvActors;
    TextView tvWriters;
    TextView tvGenre;
    TextView tvDirector;
    TextView tvAwards;
    TextView tvCountry;

    Button btnAddCollection;
    Realm realm;

    Movie loadedMovie;

    ProgressDialog loadingMovieDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MoviePresenter(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        loadingMovieDialog = ProgressDialog.show(this,"Loading Movie...",null);

        tvMovieTitle = (TextView) findViewById(R.id.movieTitleTxt);
        tvMovieReleaseDate = (TextView)findViewById(R.id.releaseDateTxt);
        tvPlot = (TextView)findViewById(R.id.plotTxt);
        ivPoster = (ImageView)findViewById(R.id.posterImg);
        tvIMDBRating = (TextView) findViewById(R.id.imdbRating);
        tvMetacritic = (TextView) findViewById(R.id.metaRating);
        tvActors = (TextView) findViewById(R.id.actorsTxt);
        tvWriters = (TextView) findViewById(R.id.writersTxt);
        tvGenre = (TextView) findViewById(R.id.genreTxt);
        tvDirector = (TextView) findViewById(R.id.directorTxt);
        tvAwards = (TextView) findViewById(R.id.awardsTxt);
        tvCountry = (TextView) findViewById(R.id.countryTxt);

        btnAddCollection = (Button)findViewById(R.id.addtocolllectionBtn);
        btnAddCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                realm.copyToRealm(loadedMovie);
                realm.commitTransaction();

                Intent i = new Intent(MainActivity.this, collection.class);
                startActivity(i);
            }
        });

        String title = getIntent().getExtras().getString("title");
        presenter.loadMovie(title);

    }

    @Override
    public void onMovieLoadedSuccess(Call movie, Response response) {
        loadedMovie = (Movie)response.body();

        try {
            tvMovieTitle.setText(loadedMovie.getTitle());
            tvMovieReleaseDate.setText(loadedMovie.getReleased());
            tvPlot.setText(loadedMovie.getPlot());
            tvIMDBRating.setText(tvIMDBRating.getText().toString() + ": " + loadedMovie.getImdbRating());
            tvMetacritic.setText(tvMetacritic.getText().toString() + ": " + loadedMovie.getMetascore());
            tvActors.setText(tvActors.getText().toString() + loadedMovie.getActors());
            tvWriters.setText(tvWriters.getText().toString() + loadedMovie.getWriter());
            tvDirector.setText(tvDirector.getText().toString() + loadedMovie.getDirector());
            tvAwards.setText(tvAwards.getText().toString() + loadedMovie.getAwards());
            tvCountry.setText(loadedMovie.getCountry());
            tvGenre.setText(loadedMovie.getGenre());

            new PosterBitmap(ivPoster).execute(loadedMovie.getPoster());

            Toast.makeText(this, "Loaded movie: " + loadedMovie.getResponse(), Toast.LENGTH_LONG).show();
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.movieContentLayout);
            rl.setVisibility(View.VISIBLE);
            loadingMovieDialog.dismiss();

        }catch (Exception e){
            Toast.makeText(this, "Error loading movie: " + loadedMovie.getResponse(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onMovieLoadedFailure(Call movie, Throwable t) {
        tvMovieTitle.setText("FAIL");
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
