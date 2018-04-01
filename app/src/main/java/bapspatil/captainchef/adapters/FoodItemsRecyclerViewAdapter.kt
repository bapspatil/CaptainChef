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
import bapspatil.captainchef.model.FoodItem
import bapspatil.captainchef.utils.GlideApp
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.util.*

/**
 * Created by bapspatil
 */

class FoodItemsRecyclerViewAdapter(private val mContext: Context, private val mFoodItemsList: ArrayList<FoodItem>, private val mClickListener: OnFoodItemClickListener?) : RecyclerView.Adapter<FoodItemsRecyclerViewAdapter.FoodItemsViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FoodItemsViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_food_item, viewGroup, false)
        return FoodItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodItemsViewHolder, position: Int) {
        val typeface = ResourcesCompat.getFont(mContext, R.font.autour_one)
        holder.mFoodItemTextView!!.typeface = typeface
        holder.mFoodItemTextView!!.text = mFoodItemsList[position].foodName
        GlideApp.with(mContext)
                .load(mFoodItemsList[position].imageUrl)
                .centerCrop()
                .error(R.drawable.fallback_recipe_thumbnail)
                .fallback(R.drawable.fallback_recipe_thumbnail)
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.mFoodItemImageView!!)
    }

    override fun getItemCount(): Int {
        return mFoodItemsList.size
    }

    inner class FoodItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @BindView(R.id.food_item_tv)
        var mFoodItemTextView: TextView? = null
        @BindView(R.id.food_item_iv)
        var mFoodItemImageView: ImageView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            mClickListener?.onFoodItemClicked(adapterPosition, mFoodItemTextView)
        }
    }

    interface OnFoodItemClickListener {
        fun onFoodItemClicked(position: Int, textView: TextView?)
    }

}
