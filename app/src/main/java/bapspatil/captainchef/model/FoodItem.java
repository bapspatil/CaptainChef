package bapspatil.captainchef.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodItem implements Parcelable {
    @SerializedName("id") private int foodId;
    @SerializedName("name") private String foodName;
    @SerializedName("image") private String imageUrl;
    @SerializedName("ingredients") private ArrayList<Ingredient> ingredients;
    @SerializedName("steps") private ArrayList<RecipeStep> steps;

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<RecipeStep> steps) {
        this.steps = steps;
    }

    public FoodItem(int foodId, String foodName, String imageUrl, ArrayList<Ingredient> ingredients, ArrayList<RecipeStep> steps) {

        this.foodId = foodId;
        this.foodName = foodName;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public FoodItem() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.foodId);
        dest.writeString(this.foodName);
        dest.writeString(this.imageUrl);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
    }

    protected FoodItem(Parcel in) {
        this.foodId = in.readInt();
        this.foodName = in.readString();
        this.imageUrl = in.readString();
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.steps = in.createTypedArrayList(RecipeStep.CREATOR);
    }

    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
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
