package site.nomoreparties.stellarburgers.order;

import java.util.ArrayList;
import java.util.List;

public class OrderIngredients {
    private final List<String> ingredients = new ArrayList<>();

    public List<String> getIngredients() {
        return ingredients;
    }

    public void add(String id) {
        ingredients.add(id);
    }

    static OrderIngredients correctIngredients() {
        OrderIngredients ingredients = new OrderIngredients();
        ingredients.add("61c0c5a71d1f82001bdaaa6f");
        ingredients.add("61c0c5a71d1f82001bdaaa71");
        return ingredients;
    }

    static OrderIngredients wrongHashIngredients() {
        OrderIngredients ingredients = new OrderIngredients();
        ingredients.add("61c0c5a71d1122001bdaaa6f");
        ingredients.add("61c0c5a71d1f820011bdaaa71");
        return ingredients;
    }

    static OrderIngredients emptyIngredients() {
        OrderIngredients ingredients = new OrderIngredients();
        return ingredients;
    }

}
