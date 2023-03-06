package nl.abnamro.intake.assesement.recipe.data;

import java.util.List;

public class RecipeSearchCriteria {

    private String dishType;
    private Integer serves;
    private String instruction;
    private List<String> inclusiveIngredients;
    private List<String> exclusiveIngredients;

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public Integer getServes() {
        return serves;
    }

    public void setServes(Integer serves) {
        this.serves = serves;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public List<String> getInclusiveIngredients() {
        return inclusiveIngredients;
    }

    public void setInclusiveIngredients(List<String> inclusiveIngredients) {
        this.inclusiveIngredients = inclusiveIngredients;
    }

    public List<String> getExclusiveIngredients() {
        return exclusiveIngredients;
    }

    public void setExclusiveIngredients(List<String> exclusiveIngredients) {
        this.exclusiveIngredients = exclusiveIngredients;
    }
}
