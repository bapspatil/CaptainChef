package bapspatil.captainchef.ui

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import bapspatil.captainchef.R
import bapspatil.captainchef.model.RecipeStep
import butterknife.ButterKnife
import java.util.*

class RecipeDetailsActivity : AppCompatActivity(), StepsDetailsFragment.OnButtonClickListener {
    private var fragmentManager: FragmentManager? = null
    private var mRecipeStep: RecipeStep? = null
    private var mRecipeStepsList: ArrayList<RecipeStep>? = null

    private val isLandscape: Boolean
        get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)
        ButterKnife.bind(this)

        mRecipeStep = intent.getParcelableExtra("recipeStep")
        mRecipeStepsList = intent.getParcelableArrayListExtra("recipeList")
        fragmentManager = supportFragmentManager
        if (savedInstanceState == null) {
            val stepsDetailsFragment = StepsDetailsFragment.newInstance(mRecipeStep, mRecipeStepsList)
            fragmentManager!!.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.recipe_details_container, stepsDetailsFragment)
                    .commit()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && isLandscape) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun onButtonClicked(buttonClicked: Int, recipeStep: RecipeStep, recipeSteps: ArrayList<RecipeStep>, view: View) {
        if (buttonClicked == StepsDetailsFragment.PREV_BUTTON) {
            var id = recipeStep.stepId
            id--
            val prevRecipeStep = recipeSteps[id]
            val stepsDetailsFragment = StepsDetailsFragment.newInstance(prevRecipeStep, mRecipeStepsList)
            fragmentManager = supportFragmentManager
            fragmentManager!!.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.recipe_details_container, stepsDetailsFragment)
                    .commit()
        } else {
            var id = recipeStep.stepId
            id++
            val nextRecipeStep = recipeSteps[id]
            val stepsDetailsFragment = StepsDetailsFragment.newInstance(nextRecipeStep, mRecipeStepsList)
            fragmentManager = supportFragmentManager
            fragmentManager!!.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.recipe_details_container, stepsDetailsFragment)
                    .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this@RecipeDetailsActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

}
