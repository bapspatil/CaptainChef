package bapspatil.captainchef.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bapspatil.captainchef.R;
import bapspatil.captainchef.data.FoodItem;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_food_item, parent, false);
        return new FoodItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodItemsViewHolder holder, int position) {
        FoodItem foodItem = mFoodItemsList.get(position);
        holder.mFoodItemTextView.setText(foodItem.getFoodName());
    }

    @Override
    public int getItemCount() {
        return mFoodItemsList.size();
    }

    public class FoodItemsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.food_item_tv) TextView mFoodItemTextView;

        FoodItemsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
