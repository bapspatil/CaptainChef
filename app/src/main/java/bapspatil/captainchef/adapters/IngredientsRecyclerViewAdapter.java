package bapspatil.captainchef.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bapspatil.captainchef.R;
import bapspatil.captainchef.data.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bapspatil
 */

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientsViewHolder> {
    private Context mContext;
    private ArrayList<Ingredient> mIngredientsList;

    public IngredientsRecyclerViewAdapter(Context mContext, ArrayList<Ingredient> mIngredientsList) {
        this.mContext = mContext;
        this.mIngredientsList = mIngredientsList;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_ingredients, viewGroup, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder ingredientsViewHolder, int i) {
        Ingredient ingredient = mIngredientsList.get(i);
        ingredientsViewHolder.mIngredientTextView.setText(ingredient.getIngredientName());
        ingredientsViewHolder.mQuantityTextView.setText(String.valueOf(ingredient.getQuant()));
        ingredientsViewHolder.mMeasureTextView.setText(ingredient.getMeasuredWith());
    }

    @Override
    public int getItemCount() {
        return mIngredientsList.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_tv) TextView mIngredientTextView;
        @BindView(R.id.quantity_tv) TextView mQuantityTextView;
        @BindView(R.id.measure_tv) TextView mMeasureTextView;

        IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
