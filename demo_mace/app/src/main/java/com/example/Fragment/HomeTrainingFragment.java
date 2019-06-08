package com.example.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.Activity.MainActivity;
import com.example.R;

public class HomeTrainingFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hometraining, container, false);

        view.findViewById(R.id.ht_f1).setOnClickListener(this);
        view.findViewById(R.id.ht_f2).setOnClickListener(this);
        view.findViewById(R.id.ht_f3).setOnClickListener(this);
        view.findViewById(R.id.ht_f4).setOnClickListener(this);
        view.findViewById(R.id.ht_f5).setOnClickListener(this);
        view.findViewById(R.id.ht_f6).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        ((MainActivity)getActivity()).ChangeFragmentMain(view.getId());
    }
}
