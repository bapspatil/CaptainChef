package bapspatil.captainchef.network

import bapspatil.captainchef.model.FoodItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.*

/**
 * Created by bapspatil
 */

interface BakingAPI {

    @get:GET("resources/baking.json") val foodItems: Call<ArrayList<FoodItem>>

    companion object {

        const val BASE_URL = "https://raw.githubusercontent.com/bapspatil/CaptainChef/master/"

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
