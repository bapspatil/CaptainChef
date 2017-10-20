package bapspatil.captainchef;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by bapspatil
 */

@RunWith(AndroidJUnit4.class)
public class FoodItemsActivityTest {

    @Rule public ActivityTestRule<FoodItemsActivity> mActivityTestRule = new ActivityTestRule<>(FoodItemsActivity.class);

    @Test
    public void clickFoodItem_OpensRecipeDetails() {

        // To click on the first item in the RecyclerView of food items
        onView(withRecyclerView(R.id.food_items_rv).atPosition(0)).perform(click());

        // To check if the first ingredient in the first item has the text "Graham Cracker crumbs"
        onView(withRecyclerView(R.id.ingredients_rv).atPosition(0)).check(matches(hasDescendant(withText("Graham Cracker crumbs"))));

        // To check if the first recipe step in the first item has the text "Recipe Introduction"
        onView(withRecyclerView(R.id.steps_rv).atPosition(0)).check(matches(hasDescendant(withText("Recipe Introduction"))));

    }

    // Helper method to get a reference to a RecyclerView
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
