package bapspatil.captainchef.sync

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import bapspatil.captainchef.R
import bapspatil.captainchef.model.Ingredient
import java.util.*

/**
 * Created by bapspatil
 */
class RecipeWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        /*for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        // Get the AppWidgetManager & appWidgetIds from the context
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, RecipeWidgetProvider::class.java))

        // Get the action present in the Intent passed by the UpdateRecipeService
        val action = intent.action
        if (action == "android.appwidget.action.RECIPE_UPDATE") {

            // Get the extras from the Intent obtained from the UpdateRecipeService
            ingredientArrayList = intent.getParcelableArrayListExtra("ingredientsList")
            foodItemName = intent.getStringExtra("foodItemName")

            // Notify the AppWidgetManager that the data in the widget has changed
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients_list_view)

            // Update the data displayed in the widget
            updateRecipeWidgets(context, appWidgetManager, appWidgetIds)
            super.onReceive(context, intent)
        }
    }

    companion object {

        var ingredientArrayList = ArrayList<Ingredient>()
        lateinit var foodItemName: String

        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.recipe_widget_provider)

            // Setting the Food Item name
            views.setTextViewText(R.id.appwidget_food_item_title, foodItemName)

            // Setting the RemoteAdapter for the list view of Ingredients
            val intentForListView = Intent(context, RecipeWidgetRemoteViewsService::class.java)
            views.setRemoteAdapter(R.id.widget_ingredients_list_view, intentForListView)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun updateRecipeWidgets(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }
}

