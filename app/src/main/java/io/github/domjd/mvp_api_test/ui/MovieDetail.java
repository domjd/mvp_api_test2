package io.github.domjd.mvp_api_test.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.domjd.mvp_api_test.R;
import io.github.domjd.mvp_api_test.models.Movie;
import io.github.domjd.mvp_api_test.presenters.MoviePresenter;
import io.github.domjd.mvp_api_test.tools.PosterBitmap;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity implements MovieView {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private PlotFragment plotFragment;
    private CastFragment castFragment;
    private AwardsFragment awardsFragment;

    MoviePresenter presenter;
    Movie loadedMovie;
    Realm realm;

    ProgressDialog loadingMovieDialog;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("");

        loadingMovieDialog = ProgressDialog.show(this,"Loading Movie...",null);

        String title = getIntent().getExtras().getString("title");
        presenter = new MoviePresenter(this);
        presenter.loadMovie(title);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nestedView);
        scrollView.setFillViewport (true);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                realm.copyToRealm(loadedMovie);
                realm.commitTransaction();

                Intent i = new Intent(MovieDetail.this, collection.class);
                startActivity(i);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(plotFragment, "PLOT");
        adapter.addFragment(castFragment, "CAST");
        adapter.addFragment(awardsFragment, "ACCOLADES");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onMovieLoadedSuccess(Call movie, Response response) {
        plotFragment = new PlotFragment();
        castFragment = new CastFragment();
        awardsFragment = new AwardsFragment();

        loadedMovie = (Movie) response.body();
        Movie movieCheck = realm.where(Movie.class).equalTo("imdbID", loadedMovie.getImdbID()).findFirst();

        try {

            Bundle bundle = new Bundle();
            bundle.putString("movieplot", loadedMovie.getPlot());
            bundle.putString("releasedate", loadedMovie.getReleased());
            bundle.putString("poster", loadedMovie.getPoster());
            bundle.putString("imdbrating", loadedMovie.getImdbRating());
            bundle.putString("metascore", loadedMovie.getMetascore());
            bundle.putString("certificate", loadedMovie.getRated());
            plotFragment.setArguments(bundle);

            bundle = new Bundle();
            bundle.putString("actors", loadedMovie.getActors());
            bundle.putString("director", loadedMovie.getDirector());
            bundle.putString("writers", loadedMovie.getWriter());
            castFragment.setArguments(bundle);

            bundle = new Bundle();
            bundle.putString("awards", loadedMovie.getAwards());
            awardsFragment.setArguments(bundle);

            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            setTitle(loadedMovie.getTitle());

            //Log.v("loadedmovieID", loadedMovie.getImdbID());
            //Log.v("movieCheckID", movieCheck.getImdbID());
            if(movieCheck != null) {
                if (loadedMovie.getImdbID().equals(movieCheck.getImdbID())) {
                    fab.setImageResource(R.drawable.ic_action_heart_gold);
                    fab.setClickable(false);
                    Log.v("BUTTON CHECK:", "MATCH FOUND");
                }
            }
            Toast.makeText(this, "Loaded movie: " + loadedMovie.getResponse(), Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(this, "Error loading movie: " + e, Toast.LENGTH_LONG).show();
            Log.v("MOVIE ERROR: ", e.toString());
        }

        loadingMovieDialog.dismiss();
    }

    @Override
    public void onMovieLoadedFailure(Call movie, Throwable t) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    private void setupFragments(Movie m){

    }

    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
