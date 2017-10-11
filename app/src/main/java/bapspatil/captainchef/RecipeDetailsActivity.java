package bapspatil.captainchef;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import bapspatil.captainchef.data.RecipeStep;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private RecipeStep mRecipeStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        mRecipeStep = getIntent().getParcelableExtra("recipeStep");
        StepsDetailsFragment stepsDetailsFragment = StepsDetailsFragment.newInstance(mRecipeStep);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_details_container, stepsDetailsFragment)
                .commit();
    }
}
