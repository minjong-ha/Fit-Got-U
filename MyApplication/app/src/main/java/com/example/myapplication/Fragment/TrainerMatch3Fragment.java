package com.example.myapplication.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class TrainerMatch3Fragment extends Fragment {
    private String youtubevideourl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_match3, container, false);
        if (getArguments() != null) {
            youtubevideourl = getArguments().getString("youtubevideourl");
        }
        YouTubePlayerSupportFragment player = (YouTubePlayerSupportFragment)getChildFragmentManager().findFragmentById(R.id.tm3_youtube_fragment);
        player.initialize(getString(R.string.google_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (youTubePlayer != null && !b) {
                    youTubePlayer.cueVideo(youtubevideourl);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getActivity().getApplicationContext(), "실패", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
