package com.example.myapplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Etc.onFragmentListener;
import com.example.myapplication.R;

public class HomeTrainingFragment extends Fragment implements View.OnClickListener {
    private onFragmentListener mOnHTListener;

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
        if (mOnHTListener != null) {
            mOnHTListener.onReceivedData(view.getId());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof onFragmentListener) {
            mOnHTListener = (onFragmentListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnHTListener = null;
    }
}
