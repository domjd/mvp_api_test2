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
public class AwardsFragment extends Fragment {


    public AwardsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_awards, container, false);

        TextView tvAwards = (TextView) view.findViewById(R.id.awardsTxt);
        tvAwards.setText(getArguments().getString("awards"));

        return view;
    }

}
