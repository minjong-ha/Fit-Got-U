package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

public class HomeTrainingFragment2 extends Fragment {
    private int a;
    //public static Fragment newInstance(DataObject data) {
    public static HomeTrainingFragment2 newInstance(int data) {
        HomeTrainingFragment2 f = new HomeTrainingFragment2();
        Bundle b = new Bundle();
        //b.putParcelable(DataObject.class.getName(), data);
        b.putInt("data", data);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //str = getArguments().getParcelable(DataObject.class.getName());
            a = getArguments().getInt("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_home_training, container, false);
        return view;
    }
}
