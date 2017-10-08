package bapspatil.captainchef.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bapspatil
 */

public class FoodItemsRecyclerViewAdapter extends RecyclerView.Adapter<FoodItemsRecyclerViewAdapter.FoodItemsViewHolder> {

    private Context mContext;

    public FoodItemsRecyclerViewAdapter() {
        super();
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
        return 0;
    }

    public class FoodItemsViewHolder extends RecyclerView.ViewHolder {

        public FoodItemsViewHolder(View itemView) {
            super(itemView);
        }
    }

}
