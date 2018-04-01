package bapspatil.captainchef.adapters

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import bapspatil.captainchef.R
import bapspatil.captainchef.model.RecipeStep
import bapspatil.captainchef.utils.GlideApp
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

/**
 * Created by bapspatil
 */

class StepsListRecyclerViewAdapter(private val mContext: Context, private val mRecipeStepsList: ArrayList<RecipeStep>, private val mClickListener: OnRecipeStepClickedListener?) : RecyclerView.Adapter<StepsListRecyclerViewAdapter.StepsListViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): StepsListViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_steps_list, viewGroup, false)
        return StepsListViewHolder(view)
    }

    override fun onBindViewHolder(stepsListViewHolder: StepsListViewHolder, i: Int) {
        val (stepId, shortInfo, _, _, thumbnailUrl) = mRecipeStepsList[i]
        val recipeStepString = stepId.toString() + ". " + shortInfo
        val typeface = ResourcesCompat.getFont(mContext, R.font.autour_one)
        stepsListViewHolder.mStepTextView!!.typeface = typeface
        stepsListViewHolder.mStepTextView!!.text = recipeStepString
        GlideApp.with(mContext)
                .load(thumbnailUrl)
                .centerCrop()
                .fallback(R.drawable.fallback_recipe_thumbnail)
                .error(R.drawable.fallback_recipe_thumbnail)
                .placeholder(R.drawable.fallback_recipe_thumbnail)
                .into(stepsListViewHolder.mStepImageView!!)
    }

    override fun getItemCount(): Int {
        return mRecipeStepsList.size
    }

    inner class StepsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @BindView(R.id.step_item_tv)
        var mStepTextView: TextView? = null
        @BindView(R.id.step_item_iv)
        var mStepImageView: ImageView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (mClickListener != null) {
                val recipeStep = mRecipeStepsList[adapterPosition]
                mClickListener.onRecipeStepClicked(recipeStep)
            }
        }
    }

    interface OnRecipeStepClickedListener {
        fun onRecipeStepClicked(recipeStep: RecipeStep)
    }

}
