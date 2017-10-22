package bapspatil.captainchef.sync;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
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
        String foodItemName = intent.getExtras().getString("foodItemName");
        ArrayList<Ingredient> ingredientArrayList = intent.getExtras().getParcelableArrayList("ingredientsList");
        Intent intentToWidget = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intentToWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intentToWidget.putExtra("foodItemName", foodItemName);
        intentToWidget.putExtra("ingredientsList", ingredientArrayList);
        sendBroadcast(intentToWidget);
    }
}
