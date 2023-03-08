package nl.abnamro.intake.assesement.recipe.service.impl;

import nl.abnamro.intake.assesement.recipe.data.dto.RecipeDto;
import nl.abnamro.intake.assesement.recipe.data.repository.RecipeRepository;
import nl.abnamro.intake.assesement.recipe.data.specification.RecipeSearchSpecification;
import nl.abnamro.intake.assesement.recipe.exception.RecipeConstraintException;
import nl.abnamro.intake.assesement.recipe.exception.RecipeNotFoundException;
import nl.abnamro.intake.assesement.recipe.model.RecipeRequestModel;
import nl.abnamro.intake.assesement.recipe.data.RecipeSearchCriteria;
import nl.abnamro.intake.assesement.recipe.service.RecipeService;
import nl.abnamro.intake.assesement.recipe.util.RecipeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class RecipeServiceImpl implements RecipeService {

    public static final String RECIPE_DTO_ALREADY_EXISTS = "RecipeDto Already exists : ";
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public RecipeDto saveRecipe(RecipeRequestModel recipeRequestModel) {
        List<RecipeDto> recipeDtos = recipeRepository.findRecipeByName(recipeRequestModel.getName());
        if (Objects.nonNull(recipeDtos) && recipeDtos.size() > 0) {
            throw new RecipeConstraintException(RECIPE_DTO_ALREADY_EXISTS + recipeRequestModel.getName());
        }
        return recipeRepository.save(RecipeUtil.buildRecipe(recipeRequestModel));
    }

    @Override
    public void deleteRecipe(Integer recipeId) {
        recipeRepository.delete(findRecipe(recipeId));
    }

    @Override
    public RecipeDto updateRecipe(RecipeRequestModel recipeRequestModel, Integer recipeId) {
        RecipeDto recipeDto = findRecipe(recipeId);
        if (Objects.nonNull(recipeRequestModel.getName())) {
            List<RecipeDto> recipeDtos = recipeRepository.findRecipeByName(recipeRequestModel.getName());
            if (!CollectionUtils.isEmpty(recipeDtos) && !recipeDtos.get(0).getId().equals(recipeDto.getId())) {
                throw new RecipeConstraintException(RECIPE_DTO_ALREADY_EXISTS + recipeRequestModel.getName());
            }
        }
        RecipeUtil.updateRecipe(recipeRequestModel, recipeDto);
        return recipeRepository.save(recipeDto);
    }

    @Override
    public RecipeDto findRecipe(Integer recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException("Recipe Not Found : " + recipeId));
    }

    @Override
    public List<RecipeDto> findAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public List<RecipeDto> searchRecipes(RecipeSearchCriteria recipeSearchCriteria) {
        List<RecipeDto> recipeDtos = recipeRepository.findAll(RecipeSearchSpecification.recipeSearchSpecification(recipeSearchCriteria));
        return recipeDtos;
    }
}
