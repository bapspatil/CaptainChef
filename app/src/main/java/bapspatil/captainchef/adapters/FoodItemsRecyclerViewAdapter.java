package bapspatil.captainchef.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
            case "Nutella Pie": Glide.with(mContext)
                    .load(foodItem.getImageUrl())
                    .centerCrop()
                    .fallback(R.drawable.nutella_pie)
                    .error(R.drawable.nutella_pie)
                    .placeholder(R.drawable.nutella_pie)
                    .into(holder.mFoodItemImageView); break;
            case "Brownies": Glide.with(mContext)
                    .load(foodItem.getImageUrl())
                    .centerCrop()
                    .fallback(R.drawable.brownie)
                    .error(R.drawable.brownie)
                    .placeholder(R.drawable.brownie)
                    .into(holder.mFoodItemImageView); break;
            case "Yellow Cake": Glide.with(mContext)
                    .load(foodItem.getImageUrl())
                    .centerCrop()
                    .fallback(R.drawable.yellow_cake)
                    .error(R.drawable.yellow_cake)
                    .placeholder(R.drawable.yellow_cake)
                    .into(holder.mFoodItemImageView); break;
            case "Cheesecake": Glide.with(mContext)
                    .load(foodItem.getImageUrl())
                    .centerCrop()
                    .fallback(R.drawable.cheesecake)
                    .error(R.drawable.cheesecake)
                    .placeholder(R.drawable.cheesecake)
                    .into(holder.mFoodItemImageView); break;
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
