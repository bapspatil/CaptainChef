package bapspatil.captainchef;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import java.util.ArrayList;

import bapspatil.captainchef.data.FoodItem;
import bapspatil.captainchef.data.Ingredient;
import bapspatil.captainchef.data.RecipeStep;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeActivity extends AppCompatActivity {

    @BindView(R.id.prev_button) CardView mPrevButton;
    @BindView(R.id.next_button) CardView mNextButton;
    private ArrayList<Ingredient> ingredientsList = new ArrayList<>();
    private ArrayList<RecipeStep> recipeStepsList = new ArrayList<>();
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
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

    @OnClick(R.id.prev_button)
    public void prevStep(View view) {

    }

    @OnClick(R.id.next_button)
    public void nextStep(View view) {

    }

    private boolean isPhone() {
        String screenType = getResources().getString(R.string.device);
        return !screenType.equals("tablet");
    }
}
