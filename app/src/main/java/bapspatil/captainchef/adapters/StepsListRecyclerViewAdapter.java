package bapspatil.captainchef.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bapspatil.captainchef.R;
import bapspatil.captainchef.data.RecipeStep;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bapspatil
 */

public class StepsListRecyclerViewAdapter extends RecyclerView.Adapter<StepsListRecyclerViewAdapter.StepsListViewHolder> {
    private Context mContext;
    private ArrayList<RecipeStep> mRecipeStepsList;
    private OnRecipeStepClickedListener mClickListener;

    public StepsListRecyclerViewAdapter(Context context, ArrayList<RecipeStep> recipeStepArrayList, OnRecipeStepClickedListener clickedListener) {
        mContext = context;
        mRecipeStepsList = recipeStepArrayList;
        mClickListener = clickedListener;
    }

    @Override
    public StepsListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_steps_list, viewGroup, false);
        return new StepsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsListViewHolder stepsListViewHolder, int i) {
        RecipeStep recipeStep = mRecipeStepsList.get(i);
        stepsListViewHolder.mStepNumberTextView.setText(String.valueOf((recipeStep.getStepId() + 1)));
        stepsListViewHolder.mStepTextView.setText(recipeStep.getShortInfo());
    }

    @Override
    public int getItemCount() {
        return mRecipeStepsList.size();
    }

    class StepsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.step_number_tv) TextView mStepNumberTextView;
        @BindView(R.id.step_tv) TextView mStepTextView;

        StepsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener != null) {
                RecipeStep recipeStep = mRecipeStepsList.get(getAdapterPosition());
                mClickListener.onRecipeStepClicked(recipeStep);
            }
        }
    }

    public interface OnRecipeStepClickedListener {
        void onRecipeStepClicked(RecipeStep recipeStep);
    }
}
