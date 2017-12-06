package bapspatil.captainchef.network;

import java.util.List;

import bapspatil.captainchef.model.FoodItem;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by bapspatil
 */

public interface BakingAPI {

    @GET("resources/baking.json")
    Call<List<FoodItem>> getFoodItems();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/bapspatil/CaptainChef/master/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
