package bapspatil.captainchef.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {
    @SerializedName("quantity") private float quant;
    @SerializedName("measure") private String measuredWith;
    @SerializedName("ingredient") private String ingredientName;

    public float getQuant() {
        return quant;
    }

    public void setQuant(float quant) {
        this.quant = quant;
    }

    public String getMeasuredWith() {
        return measuredWith;
    }

    public void setMeasuredWith(String measuredWith) {
        this.measuredWith = measuredWith;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Ingredient() {

    }

    public Ingredient(float quant, String measuredWith, String ingredientName) {

        this.quant = quant;
        this.measuredWith = measuredWith;
        this.ingredientName = ingredientName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.quant);
        dest.writeString(this.measuredWith);
        dest.writeString(this.ingredientName);
    }

    protected Ingredient(Parcel in) {
        this.quant = in.readFloat();
        this.measuredWith = in.readString();
        this.ingredientName = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
