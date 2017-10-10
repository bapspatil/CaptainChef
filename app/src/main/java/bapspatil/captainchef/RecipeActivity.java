package bapspatil.captainchef;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import bapspatil.captainchef.data.FoodItem;
import bapspatil.captainchef.data.Ingredient;
import bapspatil.captainchef.data.RecipeStep;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private ArrayList<Ingredient> ingredientsList = new ArrayList<>();
    private ArrayList<RecipeStep> recipeStepsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FoodItem foodItem = getIntent().getParcelableExtra("foodItem");
        if(savedInstanceState == null) {
            ingredientsList = foodItem.getIngredientArrayList();
            recipeStepsList = foodItem.getRecipeStepArrayList();
        } else {
            ingredientsList = savedInstanceState.getParcelableArrayList("ingredientsList");
            recipeStepsList = savedInstanceState.getParcelableArrayList("recipeStepsList");
        }
        if(isPhone()) {
            mTwoPane = false;
            StepsListFragment stepsListFragment = StepsListFragment.newInstance(ingredientsList, recipeStepsList);
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_container, stepsListFragment)
                    .commit();
        } else {

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("ingredientsList", ingredientsList);
        outState.putParcelableArrayList("recipeStepsList", recipeStepsList);
        super.onSaveInstanceState(outState);
    }

    private boolean isPhone() {
        String screenType = getResources().getString(R.string.device);
        return !screenType.equals("tablet");
    }
}
