package bapspatil.captainchef;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

import bapspatil.captainchef.data.RecipeStep;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepsDetailsFragment extends Fragment implements OnPreparedListener{
    @BindView(R.id.step_description_tv) TextView mStepDescription;
    @BindView(R.id.video_view) VideoView mVideoView;
    private RecipeStep recipeStep;

    public StepsDetailsFragment() {
        // Required empty public constructor
    }

    public static StepsDetailsFragment newInstance(RecipeStep recipeStep) {
        StepsDetailsFragment stepsDetailsFragment = new StepsDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipeStep", recipeStep);
        stepsDetailsFragment.setArguments(bundle);
        return stepsDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_details, container, false);
        ButterKnife.bind(this, rootView);

        recipeStep = getArguments().getParcelable("recipeStep");
        if (recipeStep != null) {
            mStepDescription.setText(recipeStep.getInfo());
            mVideoView.setOnPreparedListener(this);
            mVideoView.setVideoURI(Uri.parse(recipeStep.getVideoUrl()));
        }
        return rootView;
    }

    @Override
    public void onPrepared() {
        mVideoView.start();
    }

}
