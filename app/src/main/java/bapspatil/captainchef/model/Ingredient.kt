package bapspatil.captainchef.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(@SerializedName("quantity") var quant: Float,
                      @SerializedName("measure") var measuredWith: String?,
                      @SerializedName("ingredient") var ingredientName: String?) : Parcelable
