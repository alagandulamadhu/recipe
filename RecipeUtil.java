package nl.abnamro.intake.assesement.recipe.util;

import nl.abnamro.intake.assesement.recipe.data.dto.RecipeDto;
import nl.abnamro.intake.assesement.recipe.data.dto.RecipeIngredientDto;
import nl.abnamro.intake.assesement.recipe.model.Ingredient;
import nl.abnamro.intake.assesement.recipe.model.RecipeRequestModel;
import nl.abnamro.intake.assesement.recipe.model.Recipe;
import nl.abnamro.intake.assesement.recipe.model.RecipeResponseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecipeUtil {

    public static RecipeDto buildRecipe(RecipeRequestModel recipeRequestModel) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName(recipeRequestModel.getName());
        recipeDto.setDescription(recipeRequestModel.getDescription());
        recipeDto.setServes(recipeRequestModel.getServes());
        recipeDto.setInstruction(recipeRequestModel.getInstruction());
        recipeDto.setDishType(recipeRequestModel.getDishType());
        recipeDto.setRecipeIngredients(new ArrayList<>());
        recipeRequestModel.getIngredients().forEach(ingredient -> {
            RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
            recipeIngredientDto.setRecipe(recipeDto);
            recipeIngredientDto.setIngredientName(ingredient.getName());
            recipeDto.getRecipeIngredients().add(recipeIngredientDto);
        });
        return recipeDto;
    }

    public static void updateRecipe(RecipeRequestModel recipeRequestModel, RecipeDto recipeDto) {

        if (Objects.nonNull(recipeRequestModel.getName())) recipeDto.setName(recipeRequestModel.getName());
        if (Objects.nonNull(recipeRequestModel.getDescription())) recipeDto.setName(recipeRequestModel.getDescription());
        if (Objects.nonNull(recipeRequestModel.getServes())) recipeDto.setServes(recipeRequestModel.getServes());
        if (Objects.nonNull(recipeRequestModel.getInstruction())) recipeDto.setInstruction(recipeRequestModel.getInstruction());
        if (Objects.nonNull(recipeRequestModel.getDishType())) recipeDto.setDishType(recipeRequestModel.getDishType());

        if (Objects.nonNull(recipeDto.getRecipeIngredients())) {
            Map<Integer, RecipeIngredientDto> recipeMap = recipeDto.getRecipeIngredients().stream().collect(Collectors.toMap(RecipeIngredientDto::getId, Function.identity()));
            recipeRequestModel.getIngredients().forEach(ingredient -> {
                if (Objects.isNull(ingredient.getIngredientId()) || Objects.isNull(recipeMap.get(ingredient.getIngredientId()))) {
                    RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
                    recipeIngredientDto.setRecipe(recipeDto);
                    recipeIngredientDto.setIngredientName(ingredient.getName());
                    recipeDto.getRecipeIngredients().add(recipeIngredientDto);
                } else {
                    if (Objects.nonNull(ingredient.getName())) {
                        if (Objects.equals(ingredient.getAction(), "update"))
                            recipeMap.get(ingredient.getIngredientId()).setIngredientName(ingredient.getName());
                        else {
                            recipeDto.getRecipeIngredients().remove(recipeMap.get(ingredient.getIngredientId()));
                        }
                        ;
                    }
                }
            });
        }
    }

    public static RecipeResponseModel buildRecipeResponseModel(RecipeDto recipeDto) {
        RecipeResponseModel recipeResponseModel = new RecipeResponseModel();
        recipeResponseModel.setTimestamp(new Date());
        recipeResponseModel.setData(new ArrayList<>());
        recipeResponseModel.getData().add(buildRecipe(recipeDto));
        return recipeResponseModel;
    }

    public static RecipeResponseModel buildRecipeResponseModel(List<RecipeDto> recipeDto) {
        RecipeResponseModel recipeResponseModel = new RecipeResponseModel();
        recipeResponseModel.setTimestamp(new Date());
        recipeResponseModel.setData(recipeDto.stream().map(RecipeUtil::buildRecipe).collect(Collectors.toList()));
        return recipeResponseModel;
    }

    private static Recipe buildRecipe(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        BeanUtils.copyProperties(recipeDto, recipe);
        recipe.setRecipeId(recipeDto.getId());
        if (!CollectionUtils.isEmpty(recipeDto.getRecipeIngredients())) {
            recipe.setIngredients(recipeDto.getRecipeIngredients().stream().map(RecipeUtil::buildIngredient).collect(Collectors.toList()));
        }
        return recipe;
    }

    private static Ingredient buildIngredient(RecipeIngredientDto recipeIngredientDto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(recipeIngredientDto.getIngredientName());
        ingredient.setIngredientId(recipeIngredientDto.getId());
        return ingredient;
    }

}
