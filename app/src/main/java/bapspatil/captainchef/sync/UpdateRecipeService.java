package bapspatil.captainchef.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import bapspatil.captainchef.model.Ingredient;

/**
 * Created by bapspatil
 */

public class UpdateRecipeService extends IntentService {

    public UpdateRecipeService() { super("UpdateRecipeService"); }

    // Helper method to explicitly start the UpdateRecipeService
    public static void startRecipeWidgetService(Context context, ArrayList<Ingredient> ingredientArrayList, String foodItemName) {
        Intent intent = new Intent(context, UpdateRecipeService.class);
        intent.putParcelableArrayListExtra("ingredientsList", ingredientArrayList);
        intent.putExtra("foodItemName", foodItemName);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            String foodItemName = intent.getStringExtra("foodItemName");
            ArrayList<Ingredient> ingredientArrayList = intent.getParcelableArrayListExtra("ingredientsList");
            handleRecipeWidgetUpdate(foodItemName, ingredientArrayList);
        }
    }

    // Handle the recipe update action and send the broadcast to the WidgetProvider which updates the widget
    public void handleRecipeWidgetUpdate(String foodItemName, ArrayList<Ingredient> ingredientArrayList) {
        Intent intentToWidget = new Intent("android.appwidget.action.RECIPE_UPDATE");
        intentToWidget.setAction("android.appwidget.action.RECIPE_UPDATE");
        intentToWidget.putExtra("foodItemName", foodItemName);
        intentToWidget.putParcelableArrayListExtra("ingredientsList", ingredientArrayList);
        sendBroadcast(intentToWidget);
    }
}
