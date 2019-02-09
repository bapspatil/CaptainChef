package bapspatil.captainchef.sync

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import bapspatil.captainchef.R
import bapspatil.captainchef.model.Ingredient
import java.util.*

/**
 * Created by bapspatil
 */

class RecipeWidgetRemoteViewsService : RemoteViewsService() {

    internal var remoteIngredientsList: ArrayList<Ingredient>? = null

    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return RecipeWidgetRemoteViewsFactory(this.applicationContext, intent)
    }

    inner class RecipeWidgetRemoteViewsFactory(internal var mContext: Context, intent: Intent) : RemoteViewsService.RemoteViewsFactory {

        override fun onCreate() {
        }

        override fun onDataSetChanged() {
            remoteIngredientsList = RecipeWidgetProvider.Companion.ingredientArrayList
        }

        override fun onDestroy() {
        }

        override fun getCount(): Int {
            return if (remoteIngredientsList == null)
                0
            else
                remoteIngredientsList!!.size
        }

        override fun getViewAt(i: Int): RemoteViews {
            // Construct the RemoteViews for the individual ingredient list item
            val views = RemoteViews(mContext.packageName, R.layout.recipe_widget_list_item_view)

            // Set the TextView in the layout of those individual ingredient list items
            views.setTextViewText(R.id.widget_ingredients_text_view, remoteIngredientsList!![i].ingredientName + "\n\t\t\tQuantity: " + remoteIngredientsList!![i].quant + " " + remoteIngredientsList!![i].measuredWith)

            // Return the RemoteViews
            return views
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun hasStableIds(): Boolean {
            return true
        }
    }
}
