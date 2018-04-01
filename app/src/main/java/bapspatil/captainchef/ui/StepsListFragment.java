package bapspatil.captainchef.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bapspatil.captainchef.R;
import bapspatil.captainchef.adapters.IngredientsRecyclerViewAdapter;
import bapspatil.captainchef.adapters.MyRecyclerView;
import bapspatil.captainchef.adapters.StepsListRecyclerViewAdapter;
import bapspatil.captainchef.model.Ingredient;
import bapspatil.captainchef.model.RecipeStep;
import bapspatil.captainchef.sync.UpdateRecipeService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;


/**
 * Created by bapspatil
 */
public class StepsListFragment extends Fragment implements StepsListRecyclerViewAdapter.OnRecipeStepClickedListener {
    //    private OnStepClickListener mListener;
    @BindView(R.id.ingredients_rv) MyRecyclerView mIngredientsRecyclerView;
    @BindView(R.id.steps_rv) MyRecyclerView mStepsRecyclerView;
    @BindView(R.id.add_to_widget_button) CardView addToWidgetButton;
    private ArrayList<Ingredient> ingredientsList;
    private IngredientsRecyclerViewAdapter mIngredientsAdapter;
    private ArrayList<RecipeStep> recipeStepsList;
    private StepsListRecyclerViewAdapter mStepsListAdapter;
    private String foodItemName;
    private Unbinder unbinder;
    OnStepClickListener mStepClickListener;

    public StepsListFragment() {
        // Empty constructor
    }

    public static StepsListFragment newInstance(ArrayList<Ingredient> mIngredientsList, ArrayList<RecipeStep> mRecipeList, String mFoodItemName) {
        StepsListFragment stepsListFragment = new StepsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredientsList", mIngredientsList);
        bundle.putParcelableArrayList("recipeStepsList", mRecipeList);
        bundle.putString("foodItemName", mFoodItemName);
        stepsListFragment.setArguments(bundle);
        return stepsListFragment;
    }

    @Override
    public void onRecipeStepClicked(RecipeStep recipeStep) {
        mStepClickListener.onStepClicked(recipeStep);
    }

    public interface OnStepClickListener {
        void onStepClicked(RecipeStep mRecipeStep);
    }

    @OnClick(R.id.add_to_widget_button)
    void addToWidget(View view) {
        // Start the UpdateRecipeService to update the ingredients list widget in the homescreen
        UpdateRecipeService.Companion.startRecipeWidgetService(getContext(), ingredientsList, foodItemName);

        Toasty.info(getContext(), "Recipe ingredients have been added to homescreen widget!", 5000).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        ingredientsList = getArguments().getParcelableArrayList("ingredientsList");
        recipeStepsList = getArguments().getParcelableArrayList("recipeStepsList");
        foodItemName = getArguments().getString("foodItemName");

        mIngredientsAdapter = new IngredientsRecyclerViewAdapter(getContext(), ingredientsList);
        mStepsListAdapter = new StepsListRecyclerViewAdapter(getContext(), recipeStepsList, this);

        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mStepsRecyclerView.setLayoutManager(linearLayoutManager);

        mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
        mStepsRecyclerView.setAdapter(mStepsListAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_steps_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
