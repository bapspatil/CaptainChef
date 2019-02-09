package bapspatil.captainchef.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeStep(
    @SerializedName("id") var stepId: Int,
    @SerializedName("shortDescription") var shortInfo: String?,
    @SerializedName("description") var info: String?,
    @SerializedName("videoURL") var videoUrl: String?,
    @SerializedName("thumbnailURL") var thumbnailUrl: String?
) : Parcelable