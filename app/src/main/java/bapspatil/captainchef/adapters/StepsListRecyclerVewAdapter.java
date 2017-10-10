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

public class StepsListRecyclerVewAdapter extends RecyclerView.Adapter<StepsListRecyclerVewAdapter.StepsListViewHolder> {

    private Context mContext;
    private ArrayList<RecipeStep> mRecipeStepsList;

    public StepsListRecyclerVewAdapter(Context context, ArrayList<RecipeStep> recipeStepArrayList) {
        mContext = context;
        mRecipeStepsList = recipeStepArrayList;
    }

    @Override
    public StepsListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_steps_list, viewGroup, false);
        return new StepsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsListViewHolder stepsListViewHolder, int i) {
        RecipeStep recipeStep = mRecipeStepsList.get(i);
        stepsListViewHolder.mStepNumberTextView.setText((recipeStep.getStepId()+1));
        stepsListViewHolder.mStepTextView.setText(recipeStep.getShortInfo());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class StepsListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_number_tv) TextView mStepNumberTextView;
        @BindView(R.id.step_tv) TextView mStepTextView;

        StepsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
