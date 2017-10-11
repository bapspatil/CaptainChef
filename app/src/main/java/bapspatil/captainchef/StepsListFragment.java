package bapspatil.captainchef;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bapspatil.captainchef.adapters.IngredientsRecyclerViewAdapter;
import bapspatil.captainchef.adapters.StepsListRecyclerViewAdapter;
import bapspatil.captainchef.data.Ingredient;
import bapspatil.captainchef.data.RecipeStep;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by bapspatil
 */
public class StepsListFragment extends Fragment implements StepsListRecyclerViewAdapter.OnRecipeStepClickedListener{
    //    private OnStepClickListener mListener;
    @BindView(R.id.ingredients_rv) RecyclerView mIngredientsRecyclerView;
    @BindView(R.id.steps_rv) RecyclerView mStepsRecyclerView;
    @BindView(R.id.ingredient_label_tv) TextView mIngredLabelTextView;
    @BindView(R.id.line_view) View lineView;
    @BindView(R.id.steps_label_tv) TextView mStepsLabelTextView;
    private ArrayList<Ingredient> ingredientsList;
    private IngredientsRecyclerViewAdapter mIngredientsAdapter;
    private ArrayList<RecipeStep> recipeStepsList;
    private StepsListRecyclerViewAdapter mStepsListAdapter;
    private Unbinder unbinder;
    OnStepClickListener mStepClickListener;

    public StepsListFragment() {
        // Empty constructor
    }

    public static StepsListFragment newInstance(ArrayList<Ingredient> mIngredientsList, ArrayList<RecipeStep> mRecipeList) {
        StepsListFragment stepsListFragment = new StepsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredientsList", mIngredientsList);
        bundle.putParcelableArrayList("recipeStepsList", mRecipeList);
        stepsListFragment.setArguments(bundle);
        return stepsListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_steps_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        ingredientsList = getArguments().getParcelableArrayList("ingredientsList");
        recipeStepsList = getArguments().getParcelableArrayList("recipeStepsList");

        mIngredientsAdapter = new IngredientsRecyclerViewAdapter(getContext(), ingredientsList);
        mStepsListAdapter = new StepsListRecyclerViewAdapter(getContext(), recipeStepsList, this);
        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mStepsRecyclerView.setAdapter(mStepsListAdapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRecipeStepClicked(RecipeStep recipeStep) {
        mStepClickListener.onStepClicked(recipeStep);
    }

    public interface OnStepClickListener {
        void onStepClicked(RecipeStep mRecipeStep);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mStepClickListener = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }
}
