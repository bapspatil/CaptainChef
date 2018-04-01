package bapspatil.captainchef.adapters

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import bapspatil.captainchef.R
import bapspatil.captainchef.model.Ingredient
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

/**
 * Created by bapspatil
 */

class IngredientsRecyclerViewAdapter(private val mContext: Context, private val mIngredientsList: ArrayList<Ingredient>) : RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientsViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_ingredients, viewGroup, false)
        return IngredientsViewHolder(view)
    }

    override fun onBindViewHolder(ingredientsViewHolder: IngredientsViewHolder, i: Int) {
        val (quant, measuredWith, ingredientName) = mIngredientsList[i]
        val typeface = ResourcesCompat.getFont(mContext, R.font.autour_one)
        ingredientsViewHolder.mIngredientTextView!!.typeface = typeface
        ingredientsViewHolder.mMeasureTextView!!.typeface = typeface
        ingredientsViewHolder.mQuantityTextView!!.typeface = typeface
        ingredientsViewHolder.mIngredientTextView!!.text = ingredientName
        ingredientsViewHolder.mQuantityTextView!!.text = quant.toString()
        ingredientsViewHolder.mMeasureTextView!!.text = measuredWith
    }

    override fun getItemCount(): Int {
        return mIngredientsList.size
    }

    inner class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.ingredient_tv)
        var mIngredientTextView: TextView? = null
        @BindView(R.id.quantity_tv)
        var mQuantityTextView: TextView? = null
        @BindView(R.id.measure_tv)
        var mMeasureTextView: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
