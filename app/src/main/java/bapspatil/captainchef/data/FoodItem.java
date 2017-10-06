package bapspatil.captainchef.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FoodItem implements Parcelable {
    private int foodId, serves;
    private String foodName, imagePath;
    private ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
    private ArrayList<RecipeStep> recipeStepArrayList = new ArrayList<>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.foodId);
        dest.writeInt(this.serves);
        dest.writeString(this.foodName);
        dest.writeString(this.imagePath);
        dest.writeTypedList(this.ingredientArrayList);
        dest.writeTypedList(this.recipeStepArrayList);
    }

    public FoodItem() {
    }

    protected FoodItem(Parcel in) {
        this.foodId = in.readInt();
        this.serves = in.readInt();
        this.foodName = in.readString();
        this.imagePath = in.readString();
        this.ingredientArrayList = in.createTypedArrayList(Ingredient.CREATOR);
        this.recipeStepArrayList = in.createTypedArrayList(RecipeStep.CREATOR);
    }

    public static final Parcelable.Creator<FoodItem> CREATOR = new Parcelable.Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel source) {
            return new FoodItem(source);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };
}
