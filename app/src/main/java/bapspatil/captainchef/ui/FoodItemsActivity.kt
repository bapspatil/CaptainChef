package bapspatil.captainchef.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.TextView
import bapspatil.captainchef.R
import bapspatil.captainchef.adapters.FoodItemsRecyclerViewAdapter
import bapspatil.captainchef.model.FoodItem
import bapspatil.captainchef.network.BakingAPI
import butterknife.BindView
import butterknife.ButterKnife
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FoodItemsActivity : AppCompatActivity(), FoodItemsRecyclerViewAdapter.OnFoodItemClickListener {
    private val foodItemsList = ArrayList<FoodItem>()
    private var mAdapter: FoodItemsRecyclerViewAdapter? = null

    @BindView(R.id.food_items_rv)
    internal var mFoodItemsRecyclerView: RecyclerView? = null
    @BindView(R.id.toolbar)
    internal var toolbar: Toolbar? = null

    private val isPhone: Boolean
        get() {
            val screenType = resources.getString(R.string.device)
            return screenType != "tablet"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_items)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        Toasty.info(applicationContext, "App developed by Bapusaheb Patil", 5000).show()

        if (isPhone)
            mFoodItemsRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        else
            mFoodItemsRecyclerView!!.layoutManager = GridLayoutManager(this, 3)
        mAdapter = FoodItemsRecyclerViewAdapter(applicationContext, foodItemsList, this)
        mFoodItemsRecyclerView!!.adapter = mAdapter
        mFoodItemsRecyclerView!!.setHasFixedSize(true)

        fetchFoodItems()
    }

    private fun fetchFoodItems() {
        val bakingAPI = BakingAPI.retrofit.create(BakingAPI::class.java)
        val foodItemsCall = bakingAPI.foodItems
        foodItemsCall.enqueue(object : Callback<ArrayList<FoodItem>> {
            override fun onResponse(call: Call<ArrayList<FoodItem>>, response: Response<ArrayList<FoodItem>>) {
                if (response.body() != null) {
                    foodItemsList.addAll(response.body()!!)
                    mAdapter!!.notifyDataSetChanged()
                } else {
                    Toasty.error(applicationContext, "Couldn't load food items!", 5000).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<FoodItem>>, t: Throwable) {
                Toasty.error(applicationContext, "Couldn't load food items! " + t.message, 5000).show()
            }
        })
    }

    override fun onFoodItemClicked(position: Int, textView: TextView?) {
        Toasty.info(applicationContext, "This recipe serves 8 people", 5000).show()
        val foodItem = foodItemsList[position]
        val startRecipeActivity = Intent(this, RecipeActivity::class.java)
        startRecipeActivity.putExtra("foodItem", foodItem)
        val options = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out)
        startActivity(startRecipeActivity, options.toBundle())
    }

}
