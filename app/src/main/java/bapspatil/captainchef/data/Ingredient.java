package bapspatil.captainchef.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private int quant;
    private String measuredWith, ingredientName;

    @Override
    public int describeContents() {
        return 0;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
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

    @Override

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.quant);
        dest.writeString(this.measuredWith);
        dest.writeString(this.ingredientName);
    }

    public Ingredient() {
    }

    protected Ingredient(Parcel in) {
        this.quant = in.readInt();
        this.measuredWith = in.readString();
        this.ingredientName = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
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
