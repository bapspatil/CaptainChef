package bapspatil.captainchef.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bapspatil.captainchef.data.FoodItem;

/**
 * Created by bapspatil
 */

public class FoodItemsRecyclerViewAdapter extends RecyclerView.Adapter<FoodItemsRecyclerViewAdapter.FoodItemsViewHolder> {

    private Context mContext;
    private ArrayList<FoodItem> mFoodItemsList;

    public FoodItemsRecyclerViewAdapter(Context context, ArrayList<FoodItem> foodItemsList) {
        this.mContext = context;
        this.mFoodItemsList = foodItemsList;
    }

    @Override
    public FoodItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FoodItemsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mFoodItemsList.size();
    }

    public class FoodItemsViewHolder extends RecyclerView.ViewHolder {

        public FoodItemsViewHolder(View itemView) {
            super(itemView);
        }
    }

}
