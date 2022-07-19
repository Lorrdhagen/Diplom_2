package site.nomoreparties.stellarburgers.api.model;

import java.util.List;

public class OrderIngredients {
    private List<String> ingredients;

    public OrderIngredients(List<String> ingridients) {
        this.ingredients = ingridients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "site.nomoreparties.stellarburgers.api.model.OrderIngredients{" +
                "ingredients=" + ingredients +
                '}';
    }
}
