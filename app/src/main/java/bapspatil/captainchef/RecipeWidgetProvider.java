package bapspatil.captainchef;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;

import bapspatil.captainchef.data.Ingredient;
import bapspatil.captainchef.sync.RecipeWidgetRemoteViewsService;

/**
 * Created by bapspatil
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
    public static String foodItemName;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        // Setting the Food Item name
        views.setTextViewText(R.id.appwidget_food_item_title, foodItemName);

        // Setting the RemoteAdapter for the list view of Ingredients
        Intent intentForListView = new Intent(context, RecipeWidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.widget_ingredients_list_view, intentForListView);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        /*for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the AppWidgetManager & appWidgetIds from the context
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));

        // Get the action present in the Intent passed by the UpdateRecipeService
        final String action = intent.getAction();
        if (action.equals("android.appwidget.action.RECIPE_UPDATE")) {

            // Get the extras from the Intent obtained from the UpdateRecipeService
            ingredientArrayList = intent.getParcelableArrayListExtra("ingredientsList");
            foodItemName = intent.getStringExtra("foodItemName");

            // Notify the AppWidgetManager that the data in the widget has changed
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients_list_view);

            // Update the data displayed in the widget
            updateRecipeWidgets(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);
        }
    }
}

