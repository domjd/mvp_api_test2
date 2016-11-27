package uk.domdudley.mvp_api_test.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import io.realm.Realm;
import io.realm.RealmResults;
import uk.domdudley.mvp_api_test.ItemClickListener;
import uk.domdudley.mvp_api_test.R;
import uk.domdudley.mvp_api_test.adapters.CollectionAdapter;
import uk.domdudley.mvp_api_test.models.Movie;

public class collection extends AppCompatActivity implements ItemClickListener {

    private RecyclerView collectionRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter collectionAdapter;

    RealmResults<Movie> collection;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        collection = realm.where(Movie.class).findAll();

/*        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();*/

        collectionRecycler = (RecyclerView)findViewById(R.id.collectionRecycler);
        collectionRecycler.hasFixedSize();

        layoutManager = new LinearLayoutManager(this);
        collectionRecycler.setLayoutManager(layoutManager);

        collectionAdapter = new CollectionAdapter(collection, this);
        collectionRecycler.setAdapter(collectionAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lauchAddMovieDialog();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        final Movie movie = collection.get(position);
        Intent i = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("title", movie.getTitle());
        i.putExtras(b);
        startActivity(i);
    }

    private void lauchAddMovieDialog(){

        final EditText searchText = new EditText(this);
        searchText.setHint("film name");
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Search for a movie")
                .setView(searchText)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(collection.this, MainActivity.class);
                        Bundle b = new Bundle();
                        b.putString("title", searchText.getText().toString());
                        i.putExtras(b);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}