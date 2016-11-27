package uk.domdudley.mvp_api_test.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;
import uk.domdudley.mvp_api_test.ItemClickListener;
import uk.domdudley.mvp_api_test.R;
import uk.domdudley.mvp_api_test.models.Movie;
import uk.domdudley.mvp_api_test.presenters.MoviePresenter;

public class MainActivity extends AppCompatActivity implements MovieView {

    MoviePresenter presenter;

    TextView tvMovieTitle;
    TextView tvMovieReleaseDate;
    TextView tvPlot;
    ImageView ivPoster;
    RatingBar rbIMDBRating;
    RatingBar rbMetacritic;

    Button btnAddCollection;
    Realm realm;

    Movie loadedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MoviePresenter(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        tvMovieTitle = (TextView) findViewById(R.id.movieTitleTxt);
        tvMovieReleaseDate = (TextView)findViewById(R.id.releaseDateTxt);
        tvPlot = (TextView)findViewById(R.id.plotTxt);
        ivPoster = (ImageView)findViewById(R.id.posterImg);
        rbIMDBRating = (RatingBar)findViewById(R.id.imdbRating);
        rbMetacritic = (RatingBar)findViewById(R.id.metaRating);

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
            rbIMDBRating.setIsIndicator(true);
            rbIMDBRating.setRating(Float.valueOf(loadedMovie.getImdbRating()));
            rbMetacritic.isIndicator();

            if(android.text.TextUtils.isDigitsOnly(loadedMovie.getMetascore()))
                rbMetacritic.setRating(Float.valueOf(loadedMovie.getMetascore()) / 10f);
            else
                rbMetacritic.setRating(0);

            new PosterBitmap().execute(loadedMovie.getPoster());

            Toast.makeText(this, "Loaded movie: " + loadedMovie.getResponse(), Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(this, "Error loading movie: " + loadedMovie.getResponse(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onMovieLoadedFailure(Call movie, Throwable t) {
        tvMovieTitle.setText("FAIL");
    }

    public class PosterBitmap extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {
           try {
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(params[0]).getContent());
               return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivPoster.setImageBitmap(bitmap);
        }
    }
}
