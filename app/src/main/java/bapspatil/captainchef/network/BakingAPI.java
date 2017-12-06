package bapspatil.captainchef.network;

import java.util.ArrayList;

import bapspatil.captainchef.model.FoodItem;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by bapspatil
 */

public interface BakingAPI {

    String BASE_URL = "https://raw.githubusercontent.com/bapspatil/CaptainChef/master/";

    @GET("resources/baking.json")
    Call<ArrayList<FoodItem>> getFoodItems();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
