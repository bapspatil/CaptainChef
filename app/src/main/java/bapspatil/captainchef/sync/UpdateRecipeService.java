package bapspatil.captainchef.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import bapspatil.captainchef.data.Ingredient;

/**
 * Created by bapspatil
 */

public class UpdateRecipeService extends IntentService {

    public UpdateRecipeService() { super("UpdateRecipeService"); }

    public static void startRecipeWidgetService(Context context, ArrayList<Ingredient> ingredientArrayList, String foodItemName) {
        Intent intent = new Intent(context, UpdateRecipeService.class);
        intent.putExtra("ingredientsList", ingredientArrayList);
        intent.putExtra("foodItemName", foodItemName);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            handleRecipeWidgetUpdate(intent);
        }
    }

    private void handleRecipeWidgetUpdate(Intent intent) {
        String foodItemName = intent.getStringExtra("foodItemName");
        ArrayList<Ingredient> ingredientArrayList = intent.getParcelableArrayListExtra("ingredientsList");
        Intent intentToWidget = new Intent("android.appwidget.action.RECIPE_UPDATE");
        intentToWidget.setAction("android.appwidget.action.RECIPE_UPDATE");
        intentToWidget.putExtra("foodItemName", foodItemName);
        intentToWidget.putExtra("ingredientsList", ingredientArrayList);
        sendBroadcast(intentToWidget);
    }
}
