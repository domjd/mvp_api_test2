package io.github.domjd.mvp_api_test.ui;


import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.domjd.mvp_api_test.R;
import io.github.domjd.mvp_api_test.tools.PosterBitmap;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlotFragment extends Fragment {

    public PlotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("PLOT FRAGMENT: ", "fragment created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plot, container, false);

        TextView tvPlot = (TextView) v.findViewById(R.id.moviePlotFragmentTxt);
        tvPlot.setText(getArguments().getString("movieplot"));

        TextView tvReleaseDate = (TextView) v.findViewById(R.id.movieRelease);
        tvReleaseDate.setText(getArguments().getString("releasedate"));

        TextView tvCertificate = (TextView) v.findViewById(R.id.movieCertificate);
        tvCertificate.setText(getArguments().getString("certificate"));

        TextView tvIMDBRating = (TextView) v.findViewById(R.id.imdbRating);
        tvIMDBRating.setText(getArguments().getString("imdbrating"));

        TextView tvMetaScore = (TextView)v.findViewById(R.id.metaRating);
        tvMetaScore .setText(getArguments().getString("metascore"));

        return v;
    }

}
