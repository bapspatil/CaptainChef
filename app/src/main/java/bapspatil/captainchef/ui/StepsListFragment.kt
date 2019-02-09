package bapspatil.captainchef.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bapspatil.captainchef.R
import bapspatil.captainchef.adapters.IngredientsRecyclerViewAdapter
import bapspatil.captainchef.adapters.StepsListRecyclerViewAdapter
import bapspatil.captainchef.model.Ingredient
import bapspatil.captainchef.model.RecipeStep
import bapspatil.captainchef.sync.UpdateRecipeService
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_steps_list.*
import java.util.*

/**
 * Created by bapspatil
 */
class StepsListFragment : Fragment(), StepsListRecyclerViewAdapter.OnRecipeStepClickedListener {

    private var ingredientsList: ArrayList<Ingredient>? = null
    private var mIngredientsAdapter: IngredientsRecyclerViewAdapter? = null
    private var recipeStepsList: ArrayList<RecipeStep>? = null
    private var mStepsListAdapter: StepsListRecyclerViewAdapter? = null
    private var foodItemName: String? = null
    private var unbinder: Unbinder? = null
    lateinit var mStepClickListener: OnStepClickListener

    override fun onRecipeStepClicked(recipeStep: RecipeStep) {
        mStepClickListener.onStepClicked(recipeStep)
    }

    interface OnStepClickListener {
        fun onStepClicked(mRecipeStep: RecipeStep)
    }

    @OnClick(R.id.add_to_widget_button)
    internal fun addToWidget(view: View) {
        // Start the UpdateRecipeService to update the ingredients list widget in the homescreen
        UpdateRecipeService.startRecipeWidgetService(context!!, ingredientsList!!, foodItemName!!)

        Toasty.info(context!!, "Recipe ingredients have been added to homescreen widget!", 5000).show()
    }

    override fun onStart() {
        super.onStart()
        ingredientsList = arguments!!.getParcelableArrayList("ingredientsList")
        recipeStepsList = arguments!!.getParcelableArrayList("recipeStepsList")
        foodItemName = arguments!!.getString("foodItemName")

        mIngredientsAdapter = IngredientsRecyclerViewAdapter(context!!, ingredientsList!!)
        mStepsListAdapter = StepsListRecyclerViewAdapter(context!!, recipeStepsList!!, this)

        ingredients_rv!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        steps_rv!!.layoutManager = linearLayoutManager

        ingredients_rv!!.adapter = mIngredientsAdapter!!
        steps_rv!!.adapter = mStepsListAdapter!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_steps_list, container, false)
        unbinder = ButterKnife.bind(this, rootView)

        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mStepClickListener = context as OnStepClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + " must implement OnStepClickListener")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

    companion object {

        fun newInstance(mIngredientsList: ArrayList<Ingredient>, mRecipeList: ArrayList<RecipeStep>, mFoodItemName: String): StepsListFragment {
            val stepsListFragment = StepsListFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("ingredientsList", mIngredientsList)
            bundle.putParcelableArrayList("recipeStepsList", mRecipeList)
            bundle.putString("foodItemName", mFoodItemName)
            stepsListFragment.arguments = bundle
            return stepsListFragment
        }
    }
} // Empty constructor
