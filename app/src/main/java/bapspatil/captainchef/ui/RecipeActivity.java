package bapspatil.captainchef.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import bapspatil.captainchef.R;
import bapspatil.captainchef.model.FoodItem;
import bapspatil.captainchef.model.Ingredient;
import bapspatil.captainchef.model.RecipeStep;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements StepsListFragment.OnStepClickListener, StepsDetailsFragment.OnButtonClickListener {

    private ArrayList<Ingredient> ingredientsList = new ArrayList<>();
    private ArrayList<RecipeStep> recipeStepsList = new ArrayList<>();
    FragmentManager fragmentManager;
    private boolean mTwoPane;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recipe_toolbar_tv) TextView recipeToolbarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        FoodItem foodItem = getIntent().getParcelableExtra("foodItem");
        String foodItemName = foodItem.getFoodName();

        recipeToolbarTextView.setText(foodItemName);

        ingredientsList = foodItem.getIngredients();
        recipeStepsList = foodItem.getSteps();
        if (isPhone()) {
            mTwoPane = false;
            if (savedInstanceState == null) {
                StepsListFragment stepsListFragment = StepsListFragment.newInstance(ingredientsList, recipeStepsList, foodItemName);
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.recipe_container, stepsListFragment)
                        .commit();
            }

        } else {
            mTwoPane = true;
            if (savedInstanceState == null) {
                StepsListFragment stepsListFragment = StepsListFragment.newInstance(ingredientsList, recipeStepsList, foodItemName);
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.recipe_container, stepsListFragment)
                        .commit();
            }
        }
    }

    private boolean isPhone() {
        String screenType = getResources().getString(R.string.device);
        return !screenType.equals("tablet");
    }

    @Override
    public void onStepClicked(RecipeStep mRecipeStep) {
        if (!mTwoPane) {
            Intent startRecipeDetailsActivity = new Intent(this, RecipeDetailsActivity.class);
            startRecipeDetailsActivity.putExtra("recipeStep", mRecipeStep);
            startRecipeDetailsActivity.putParcelableArrayListExtra("recipeList", recipeStepsList);
            ActivityOptions options =
                    ActivityOptions.makeCustomAnimation(getApplicationContext(), android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(startRecipeDetailsActivity, options.toBundle());
        } else {
            StepsDetailsFragment stepsDetailsFragment = StepsDetailsFragment.newInstance(mRecipeStep, recipeStepsList);
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
        RecipeActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onButtonClicked(int buttonClicked, RecipeStep recipeStep, ArrayList<RecipeStep> recipeSteps, View view) {

    }

}
