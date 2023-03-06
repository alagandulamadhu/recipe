package nl.abnamro.intake.assesement.recipe.service;

import nl.abnamro.intake.assesement.recipe.data.dto.RecipeDto;
import nl.abnamro.intake.assesement.recipe.model.RecipeRequestModel;
import nl.abnamro.intake.assesement.recipe.data.RecipeSearchCriteria;

import java.util.List;

public interface RecipeService {

    public RecipeDto saveRecipe(RecipeRequestModel recipeRequestModel);

    public void deleteRecipe(Integer recipeId);

    public RecipeDto updateRecipe(RecipeRequestModel recipeRequestModel, Integer recipeId);

    public RecipeDto findRecipe(Integer recipeId);

    public List<RecipeDto> findAllRecipes();

    public List<RecipeDto> searchRecipes(RecipeSearchCriteria recipeSearchCriteria);
}
