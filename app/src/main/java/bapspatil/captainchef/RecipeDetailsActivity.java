package bapspatil.captainchef;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import bapspatil.captainchef.data.RecipeStep;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsDetailsFragment.OnButtonClickListener {
    private FragmentManager fragmentManager;
    private RecipeStep mRecipeStep;
    private ArrayList<RecipeStep> mRecipeStepsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        mRecipeStep = getIntent().getParcelableExtra("recipeStep");
        mRecipeStepsList = getIntent().getParcelableArrayListExtra("recipeList");
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null) {
            StepsDetailsFragment stepsDetailsFragment = StepsDetailsFragment.newInstance(mRecipeStep, mRecipeStepsList);
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.recipe_details_container, stepsDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isLandscape()) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onButtonClicked(int buttonClicked, RecipeStep recipeStep, ArrayList<RecipeStep> recipeSteps, View view) {
        if (buttonClicked == StepsDetailsFragment.PREV_BUTTON) {
            int id = recipeStep.getStepId();
            id--;
            RecipeStep prevRecipeStep = recipeSteps.get(id);
            StepsDetailsFragment stepsDetailsFragment = StepsDetailsFragment.newInstance(prevRecipeStep, mRecipeStepsList);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.recipe_details_container, stepsDetailsFragment)
                    .commit();
        } else {
            int id = recipeStep.getStepId();
            id++;
            RecipeStep nextRecipeStep = recipeSteps.get(id);
            StepsDetailsFragment stepsDetailsFragment = StepsDetailsFragment.newInstance(nextRecipeStep, mRecipeStepsList);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.recipe_details_container, stepsDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RecipeDetailsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
