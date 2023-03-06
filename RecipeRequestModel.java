package nl.abnamro.intake.assesement.recipe.model;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RecipeRequestModel {

    private String name;
    private Integer serves;
    private String description;

    private String dishType;

    private String instruction;

    private List<Ingredient> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServes() {
        return serves;
    }

    public void setServes(Integer serves) {
        this.serves = serves;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "RecipeRequestModel{" +
                "name='" + name + '\'' +
                ", serves=" + serves +
                ", description='" + description + '\'' +
                ", dishType='" + dishType + '\'' +
                ", instruction='" + instruction + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
