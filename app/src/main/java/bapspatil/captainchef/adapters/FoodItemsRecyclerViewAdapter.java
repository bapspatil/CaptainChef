package bapspatil.captainchef.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;

import bapspatil.captainchef.R;
import bapspatil.captainchef.model.FoodItem;
import bapspatil.captainchef.utils.GlideApp;
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
        holder.mFoodItemTextView.setText(mFoodItemsList.get(position).getFoodName());
        GlideApp.with(mContext)
                .load(mFoodItemsList.get(position).getImageUrl())
                .centerCrop()
                .error(R.drawable.fallback_recipe_thumbnail)
                .fallback(R.drawable.fallback_recipe_thumbnail)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(holder.mFoodItemImageView);
    }

    @Override
    public int getItemCount() {
        return mFoodItemsList.size();
    }

    class FoodItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.food_item_tv) TextView mFoodItemTextView;
        @BindView(R.id.food_item_iv) ImageView mFoodItemImageView;

        FoodItemsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null)
                mClickListener.onFoodItemClicked(getAdapterPosition(), mFoodItemTextView);
        }
    }

    public interface OnFoodItemClickListener {
        void onFoodItemClicked(int position, TextView textView);
    }

}
