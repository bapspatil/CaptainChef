package bapspatil.captainchef.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FoodItem implements Parcelable {
    private int foodId;
    private String foodName, imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
    private ArrayList<RecipeStep> recipeStepArrayList = new ArrayList<>();

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

    public ArrayList<Ingredient> getIngredientArrayList() {
        return ingredientArrayList;
    }

    public void setIngredientArrayList(ArrayList<Ingredient> ingredientArrayList) {
        this.ingredientArrayList = ingredientArrayList;
    }

    public ArrayList<RecipeStep> getRecipeStepArrayList() {
        return recipeStepArrayList;
    }

    public void setRecipeStepArrayList(ArrayList<RecipeStep> recipeStepArrayList) {
        this.recipeStepArrayList = recipeStepArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.foodId);
        dest.writeString(this.foodName);
        dest.writeTypedList(this.ingredientArrayList);
        dest.writeTypedList(this.recipeStepArrayList);
    }

    public FoodItem() {
    }

    protected FoodItem(Parcel in) {
        this.foodId = in.readInt();
        this.foodName = in.readString();
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
