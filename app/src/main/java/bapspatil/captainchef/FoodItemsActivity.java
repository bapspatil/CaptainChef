package bapspatil.captainchef;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import bapspatil.captainchef.adapters.FoodItemsRecyclerViewAdapter;
import bapspatil.captainchef.data.FoodItem;
import bapspatil.captainchef.data.Ingredient;
import bapspatil.captainchef.data.RecipeStep;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodItemsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private ArrayList<FoodItem> foodItemsList = new ArrayList<>();
    private static final int FOOD_ITEMS_LOADER_ID = 13;
    @BindView(R.id.food_items_rv) RecyclerView mFoodItemsRecyclerView;
    private FoodItemsRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);
        ButterKnife.bind(this);
        mFoodItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new FoodItemsRecyclerViewAdapter(getApplicationContext(), foodItemsList);
        mFoodItemsRecyclerView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(FOOD_ITEMS_LOADER_ID, null, this);
    }

    public String loadMainJson() {
        String json;
        try {
            InputStream is = getResources().getAssets().open("baking.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public Loader<String> onCreateLoader(int i, final Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {
            String mFoodItems;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (mFoodItems != null)
                    deliverResult(mFoodItems);
                else
                    forceLoad();
            }

            @Override
            public String loadInBackground() {
                return loadMainJson();
            }

            @Override
            public void deliverResult(String fetchedJsonData) {
                mFoodItems = fetchedJsonData;
                super.deliverResult(fetchedJsonData);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String fetchedJsonData) {
        foodItemsList.clear();
        try {
            JSONArray jsonFoodItemsArray = new JSONArray(fetchedJsonData);
            for (int i = 0; i < jsonFoodItemsArray.length(); i++) {
                JSONObject jsonFoodItem = jsonFoodItemsArray.getJSONObject(i);
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodId(jsonFoodItem.getInt("id"));
                foodItem.setFoodName(jsonFoodItem.getString("name"));
                JSONArray jsonIngredientArray = jsonFoodItem.getJSONArray("ingredients");
                ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
                for (int j = 0; j < jsonIngredientArray.length(); j++) {
                    JSONObject jsonIngredient = jsonIngredientArray.getJSONObject(j);
                    Ingredient ingredient = new Ingredient();
                    ingredient.setQuant(jsonIngredient.getInt("quantity"));
                    ingredient.setIngredientName(jsonIngredient.getString("ingredient"));
                    ingredient.setMeasuredWith(jsonIngredient.getString("measure"));
                    ingredientArrayList.add(ingredient);
                }
                JSONArray jsonRecipeStepArray = jsonFoodItem.getJSONArray("steps");
                ArrayList<RecipeStep> recipeStepArrayList = new ArrayList<>();
                for (int j = 0; j < jsonRecipeStepArray.length(); j++) {
                    JSONObject jsonRecipeStep = jsonRecipeStepArray.getJSONObject(j);
                    RecipeStep recipeStep = new RecipeStep();
                    recipeStep.setStepId(jsonRecipeStep.getInt("id"));
                    recipeStep.setShortInfo(jsonRecipeStep.getString("shortDescription"));
                    recipeStep.setInfo(jsonRecipeStep.getString("description"));
                    recipeStep.setVideoUrl(jsonRecipeStep.getString("videoURL"));
                    recipeStep.setThumbnailUrl(jsonRecipeStep.getString("thumbnailURL"));
                    recipeStepArrayList.add(recipeStep);
                }
                foodItem.setIngredientArrayList(ingredientArrayList);
                foodItem.setRecipeStepArrayList(recipeStepArrayList);
                foodItemsList.add(foodItem);
                mAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
