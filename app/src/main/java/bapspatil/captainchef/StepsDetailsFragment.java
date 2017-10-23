package bapspatil.captainchef;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import bapspatil.captainchef.data.RecipeStep;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepsDetailsFragment extends Fragment {
    @BindView(R.id.step_description_tv) TextView mStepDescription;
    @BindView(R.id.video_exoplayer_view) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.next_button) CardView nextButton;
    @BindView(R.id.prev_button) CardView prevButton;
    private SimpleExoPlayer mPlayer;
    private Unbinder unbinder;
    private RecipeStep recipeStep;
    private long position = -1;
    private ArrayList<RecipeStep> recipeStepsList;
    OnButtonClickListener mButtonListener;

    public static final int PREV_BUTTON = 0;
    public static final int NEXT_BUTTON = 1;

    public StepsDetailsFragment() {
        // Required empty public constructor
    }

    public static StepsDetailsFragment newInstance(RecipeStep recipeStep, ArrayList<RecipeStep> recipeStepArrayList) {
        StepsDetailsFragment stepsDetailsFragment = new StepsDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipeStep", recipeStep);
        bundle.putParcelableArrayList("recipeList", recipeStepArrayList);
        stepsDetailsFragment.setArguments(bundle);
        return stepsDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        if (savedInstanceState != null)
            position = savedInstanceState.getLong("SAVED_POSITION");
        recipeStep = getArguments().getParcelable("recipeStep");
        recipeStepsList = getArguments().getParcelableArrayList("recipeList");
        if (recipeStep != null) {
            mStepDescription.setText(recipeStep.getInfo());
            getPlayer();
            if (recipeStep.getStepId() == 0) {
                prevButton.setVisibility(View.INVISIBLE);
            } else if (recipeStep.getStepId() == (recipeStepsList.size() - 1)) {
                nextButton.setVisibility(View.INVISIBLE);
            } else {
                prevButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
            }
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer != null) {
            position = mPlayer.getCurrentPosition();
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPlayer != null) {
            if (position != -1) mPlayer.seekTo(position);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("SAVED_POSITION", position);
    }

    @OnClick(R.id.prev_button)
    void prevStep(View view) {
        mButtonListener.onButtonClicked(PREV_BUTTON, recipeStep, recipeStepsList, view);
    }

    @OnClick(R.id.next_button)
    void nextStep(View view) {
        mButtonListener.onButtonClicked(NEXT_BUTTON, recipeStep, recipeStepsList, view);
    }

    private void getPlayer() {
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        mPlayerView.setUseController(true);
        mPlayerView.requestFocus();
        mPlayerView.setPlayer(mPlayer);
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
// Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "CaptainChef"), defaultBandwidthMeter);
// Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(recipeStep.getVideoUrl()),
                dataSourceFactory, extractorsFactory, null, null);
// Prepare the player with the source.
        if (position != -1)
            mPlayer.seekTo(position);
        mPlayer.prepare(videoSource);
        mPlayer.setPlayWhenReady(true);
    }

    public interface OnButtonClickListener {
        void onButtonClicked(int buttonClicked, RecipeStep recipeStep, ArrayList<RecipeStep> recipeSteps, View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mButtonListener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnButtonClickListener");
        }
    }

}
