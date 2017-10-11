package bapspatil.captainchef;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import bapspatil.captainchef.data.FoodItem;
import bapspatil.captainchef.data.Ingredient;
import bapspatil.captainchef.data.RecipeStep;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements StepsListFragment.OnStepClickListener {

    private ArrayList<Ingredient> ingredientsList = new ArrayList<>();
    private ArrayList<RecipeStep> recipeStepsList = new ArrayList<>();
    StepsDetailsFragment stepsDetailsFragment;
    FragmentManager fragmentManager;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        FoodItem foodItem = getIntent().getParcelableExtra("foodItem");
        ingredientsList = foodItem.getIngredientArrayList();
        recipeStepsList = foodItem.getRecipeStepArrayList();
        if (isPhone()) {
            mTwoPane = false;
            StepsListFragment stepsListFragment = StepsListFragment.newInstance(ingredientsList, recipeStepsList);
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_container, stepsListFragment)
                    .commit();

        } else {

        }
    }

    private boolean isPhone() {
        String screenType = getResources().getString(R.string.device);
        return !screenType.equals("tablet");
    }

    @Override
    public void onStepClicked(RecipeStep mRecipeStep) {
        stepsDetailsFragment = StepsDetailsFragment.newInstance(mRecipeStep);
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_container, stepsDetailsFragment)
                .commit();
    }
}
