package io.github.domjd.mvp_api_test.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.domjd.mvp_api_test.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CastFragment extends Fragment {


    public CastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast, container, false);

        TextView tvDirector = (TextView) view.findViewById(R.id.directorTxt);
        tvDirector.setText(getArguments().getString("director"));

        TextView tvWriters = (TextView) view.findViewById(R.id.writersTxt);
        tvWriters.setText(getArguments().getString("writers"));

        TextView tvActors = (TextView) view.findViewById(R.id.actorsTxt);
        tvActors.setText(getArguments().getString("actors"));


        // Inflate the layout for this fragment
        return view;
    }

}
