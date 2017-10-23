package bapspatil.captainchef.sync;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import bapspatil.captainchef.R;
import bapspatil.captainchef.data.Ingredient;

import static bapspatil.captainchef.RecipeWidgetProvider.ingredientArrayList;

;

/**
 * Created by bapspatil
 */

public class RecipeWidgetRemoteViewsService extends RemoteViewsService {

    ArrayList<Ingredient> remoteIngredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    public class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;

        public RecipeWidgetRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            remoteIngredientsList = ingredientArrayList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (remoteIngredientsList == null) return 0;
            else return remoteIngredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_list_item_view);
            views.setTextViewText(R.id.widget_ingredients_text_view, remoteIngredientsList.get(i).getIngredientName() + "\n\t\t\tQuantity: " + remoteIngredientsList.get(i).getQuant() + "\n\t\t\tMeasure: " + remoteIngredientsList.get(i).getMeasuredWith());
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
