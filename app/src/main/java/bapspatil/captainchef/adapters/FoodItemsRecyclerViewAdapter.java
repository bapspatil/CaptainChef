package bapspatil.captainchef.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private OnFoodItemClickListener mClickListener;

    public FoodItemsRecyclerViewAdapter(Context context, ArrayList<FoodItem> foodItemsList, OnFoodItemClickListener clickListener) {
        this.mContext = context;
        this.mFoodItemsList = foodItemsList;
        this.mClickListener = clickListener;
    }

    @Override
    public FoodItemsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_food_item, viewGroup, false);
        return new FoodItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodItemsViewHolder holder, int position) {
        FoodItem foodItem = mFoodItemsList.get(position);
        holder.mFoodItemTextView.setText(foodItem.getFoodName());
        String foodName = foodItem.getFoodName();
        switch (foodName) {
            case "Nutella Pie": holder.mFoodItemImageView.setImageResource(R.drawable.nutella_pie); break;
            case "Brownies": holder.mFoodItemImageView.setImageResource(R.drawable.brownie); break;
            case "Yellow Cake": holder.mFoodItemImageView.setImageResource(R.drawable.yellow_cake); break;
            case "Cheesecake": holder.mFoodItemImageView.setImageResource(R.drawable.cheesecake); break;
        }
    }

    @Override
    public int getItemCount() {
        return mFoodItemsList.size();
    }

    class FoodItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.food_item_tv) TextView mFoodItemTextView;
        @BindView(R.id.food_item_iv) ImageView mFoodItemImageView;

        FoodItemsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener != null)
                mClickListener.onFoodItemClicked(getAdapterPosition());
        }
    }

    public interface OnFoodItemClickListener {
        void onFoodItemClicked(int position);
    }

}
