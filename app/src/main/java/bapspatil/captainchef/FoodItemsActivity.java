package bapspatil.captainchef;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import bapspatil.captainchef.data.FoodItem;

public class FoodItemsActivity extends AppCompatActivity {
    private ArrayList<FoodItem> foodItemsList;
    private static final int FOOD_ITEMS_LOADER_ID = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);
    }

}
