package bapspatil.captainchef;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
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

    public String loadJSONFromAsset() {
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

}
