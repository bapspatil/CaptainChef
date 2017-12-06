package bapspatil.captainchef.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

import bapspatil.captainchef.R;
import bapspatil.captainchef.adapters.FoodItemsRecyclerViewAdapter;
import bapspatil.captainchef.model.FoodItem;
import bapspatil.captainchef.network.BakingAPI;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodItemsActivity extends AppCompatActivity implements FoodItemsRecyclerViewAdapter.OnFoodItemClickListener {
    private ArrayList<FoodItem> foodItemsList = new ArrayList<>();
    private FoodItemsRecyclerViewAdapter mAdapter;

    @BindView(R.id.food_items_rv) RecyclerView mFoodItemsRecyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Toasty.info(getApplicationContext(), "App developed by Bapusaheb Patil", 5000).show();

        if (isPhone())
            mFoodItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        else
            mFoodItemsRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new FoodItemsRecyclerViewAdapter(getApplicationContext(), foodItemsList, this);
        mFoodItemsRecyclerView.setAdapter(mAdapter);
        mFoodItemsRecyclerView.setHasFixedSize(true);

        fetchFoodItems();
    }

    private void fetchFoodItems() {
        BakingAPI bakingAPI = BakingAPI.retrofit.create(BakingAPI.class);
        Call<ArrayList<FoodItem>> foodItemsCall = bakingAPI.getFoodItems();
        foodItemsCall.enqueue(new Callback<ArrayList<FoodItem>>() {
            @Override
            public void onResponse(Call<ArrayList<FoodItem>> call, Response<ArrayList<FoodItem>> response) {
                foodItemsList.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<FoodItem>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Couldn't load food items!", 5000).show();
            }
        });
    }

    private boolean isPhone() {
        String screenType = getResources().getString(R.string.device);
        return !screenType.equals("tablet");
    }

    @Override
    public void onFoodItemClicked(int position, TextView textView) {
        Toasty.info(getApplicationContext(), "This recipe serves 8 people", 5000).show();
        FoodItem foodItem = foodItemsList.get(position);
        Intent startRecipeActivity = new Intent(this, RecipeActivity.class);
        startRecipeActivity.putExtra("foodItem", foodItem);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(startRecipeActivity, options.toBundle());
    }

}
