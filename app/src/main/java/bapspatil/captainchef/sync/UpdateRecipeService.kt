package bapspatil.captainchef.sync

import android.app.IntentService
import android.content.Context
import android.content.Intent
import bapspatil.captainchef.model.Ingredient
import java.util.*

/**
 * Created by bapspatil
 */

class UpdateRecipeService : IntentService("UpdateRecipeService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val foodItemName = intent.getStringExtra("foodItemName")
            val ingredientArrayList = intent.getParcelableArrayListExtra<Ingredient>("ingredientsList")
            handleRecipeWidgetUpdate(foodItemName, ingredientArrayList)
        }
    }

    // Handle the recipe update action and send the broadcast to the WidgetProvider which updates the widget
    fun handleRecipeWidgetUpdate(foodItemName: String, ingredientArrayList: ArrayList<Ingredient>) {
        val intentToWidget = Intent("android.appwidget.action.RECIPE_UPDATE")
        intentToWidget.action = "android.appwidget.action.RECIPE_UPDATE"
        intentToWidget.putExtra("foodItemName", foodItemName)
        intentToWidget.putParcelableArrayListExtra("ingredientsList", ingredientArrayList)
        sendBroadcast(intentToWidget)
    }

    companion object {

        // Helper method to explicitly start the UpdateRecipeService
        fun startRecipeWidgetService(context: Context, ingredientArrayList: ArrayList<Ingredient>, foodItemName: String) {
            val intent = Intent(context, UpdateRecipeService::class.java)
            intent.putParcelableArrayListExtra("ingredientsList", ingredientArrayList)
            intent.putExtra("foodItemName", foodItemName)
            context.startService(intent)
        }
    }
}
