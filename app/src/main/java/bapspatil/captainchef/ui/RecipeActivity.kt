package bapspatil.captainchef.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import bapspatil.captainchef.R
import bapspatil.captainchef.model.FoodItem
import bapspatil.captainchef.model.Ingredient
import bapspatil.captainchef.model.RecipeStep
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

class RecipeActivity : AppCompatActivity(), StepsListFragment.OnStepClickListener, StepsDetailsFragment.OnButtonClickListener {

    private var ingredientsList: ArrayList<Ingredient>? = ArrayList()
    private var recipeStepsList: ArrayList<RecipeStep>? = ArrayList()
    private lateinit var fragmentManager: FragmentManager
    private var mTwoPane: Boolean = false
    @BindView(R.id.toolbar)
    var toolbar: Toolbar? = null
    @BindView(R.id.recipe_toolbar_tv)
    var recipeToolbarTextView: TextView? = null

    private val isPhone: Boolean
        get() {
            val screenType = resources.getString(R.string.device)
            return screenType != "tablet"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        ButterKnife.bind(this)
        toolbar!!.title = ""
        setSupportActionBar(toolbar)

        fragmentManager = supportFragmentManager

        val (_, foodItemName, _, ingredients, steps) = intent.getParcelableExtra<FoodItem>("foodItem")

        recipeToolbarTextView!!.text = foodItemName

        ingredientsList = ingredients
        recipeStepsList = steps
        if (isPhone) {
            mTwoPane = false
            if (savedInstanceState == null) {
                val stepsListFragment = StepsListFragment.newInstance(ingredientsList!!, recipeStepsList!!, foodItemName!!)
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.recipe_container, stepsListFragment)
                        .commit()
            }

        } else {
            mTwoPane = true
            if (savedInstanceState == null) {
                val stepsListFragment = StepsListFragment.newInstance(ingredientsList!!, recipeStepsList!!, foodItemName!!)
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.recipe_container, stepsListFragment)
                        .commit()
            }
        }
    }

    override fun onStepClicked(mRecipeStep: RecipeStep) {
        if (!mTwoPane) {
            val startRecipeDetailsActivity = Intent(this, RecipeDetailsActivity::class.java)
            startRecipeDetailsActivity.putExtra("recipeStep", mRecipeStep)
            startRecipeDetailsActivity.putParcelableArrayListExtra("recipeList", recipeStepsList)
            val options = ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(startRecipeDetailsActivity, options.toBundle())
        } else {
            val stepsDetailsFragment = StepsDetailsFragment.newInstance(mRecipeStep, recipeStepsList!!)
            fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.recipe_details_container, stepsDetailsFragment)
                    .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this@RecipeActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onButtonClicked(buttonClicked: Int, recipeStep: RecipeStep?, recipeSteps: ArrayList<RecipeStep>?, view: View) {

    }
}
