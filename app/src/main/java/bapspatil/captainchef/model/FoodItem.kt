package bapspatil.captainchef.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class FoodItem(@SerializedName("id") var foodId: Int,
                    @SerializedName("name") var foodName: String?,
                    @SerializedName("image") var imageUrl: String?,
                    @SerializedName("ingredients") var ingredients: ArrayList<Ingredient>?,
                    @SerializedName("steps") var steps: ArrayList<RecipeStep>?) : Parcelable