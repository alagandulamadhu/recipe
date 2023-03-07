package nl.abnamro.intake.assesement.recipe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Ingredient {

    @ApiModelProperty(value = "A unique Id to identity ingredient in API. Auto generated value")
    private Integer ingredientId;

    @ApiModelProperty(value = "Name of Ingredient")
    private String name;

    @ApiModelProperty(value = "An attribute which drives update operation either delete or update the ingredient")
    private String action;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
