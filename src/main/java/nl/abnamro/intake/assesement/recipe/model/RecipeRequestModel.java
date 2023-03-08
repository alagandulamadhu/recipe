package nl.abnamro.intake.assesement.recipe.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@ApiModel("RecipeRequestModel information")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RecipeRequestModel {

    @ApiModelProperty(value = "Name of the recipe")
    @NotBlank(message = "Recipe Name is mandatory")
    private String name;
    @ApiModelProperty(value = "Number of serving people")
    @NotNull(message = "Number of serves is mandatory")
    @Positive(message = "Valid Number required")
    private Integer serves;

    @ApiModelProperty(value = "Recipe Description")
    private String description;

    @ApiModelProperty(value = "Recipe is Veg or Non Veg")
    @NotBlank(message = "Dish Type is mandatory")
    private String dishType;

    @ApiModelProperty(value = "Step by step instruction to prepare recipe")
    @NotBlank(message = "Instructions are mandatory")
    private String instruction;

    @ApiModelProperty(value = "Recipe Ingredients")
    @NotEmpty(message = "Ingredients list can not be empty")
    @Valid
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

}
