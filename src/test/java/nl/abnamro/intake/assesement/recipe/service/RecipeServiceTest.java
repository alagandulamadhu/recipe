package nl.abnamro.intake.assesement.recipe.service;

import nl.abnamro.intake.assesement.recipe.data.RecipeSearchCriteria;
import nl.abnamro.intake.assesement.recipe.data.dto.RecipeDto;
import nl.abnamro.intake.assesement.recipe.data.repository.RecipeRepository;
import nl.abnamro.intake.assesement.recipe.exception.RecipeConstraintException;
import nl.abnamro.intake.assesement.recipe.exception.RecipeNotFoundException;
import nl.abnamro.intake.assesement.recipe.model.Ingredient;
import nl.abnamro.intake.assesement.recipe.model.RecipeRequestModel;
import nl.abnamro.intake.assesement.recipe.service.impl.RecipeServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import java.util.*;


@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTest {


    @InjectMocks
    private RecipeServiceImpl recipeService;
    @Mock
    private RecipeRepository recipeRepository;

    @Test
    public void saveRecipe_When_Recipe_Not_Exists() {
        RecipeRequestModel recipeRequestModel = buildRecipeRequestModel();
        RecipeDto recipeDto = Mockito.spy(RecipeDto.class);
        recipeDto.setName("Chicken");
        recipeDto.setId(1);
        Mockito.when(recipeRepository.save(ArgumentMatchers.any(RecipeDto.class))).thenReturn(recipeDto);
        Mockito.when(recipeRepository.findRecipeByName(ArgumentMatchers.anyString())).thenReturn(null);
        RecipeDto createdRecipe = recipeService.saveRecipe(recipeRequestModel);
        Assertions.assertEquals(recipeDto, createdRecipe);
    }

    @Test(expected = RecipeConstraintException.class)
    public void saveRecipe_When_Recipe_Exists() {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        RecipeRequestModel recipeRequestModel = buildRecipeRequestModel();
        RecipeDto recipeDto = Mockito.spy(RecipeDto.class);
        recipeDto.setName("Chicken");
        recipeDto.setId(1);
        recipeDtoList.add(recipeDto);
        Mockito.when(recipeRepository.findRecipeByName(ArgumentMatchers.anyString())).thenReturn(recipeDtoList);
        recipeService.saveRecipe(recipeRequestModel);

    }

    @Test
    public void deleteRecipe_When_Recipe_found() {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Chicken");
        recipeDto.setId(1);
        Mockito.when(recipeRepository.findById(recipeDto.getId())).thenReturn(Optional.of(recipeDto));
        Mockito.doNothing().when(recipeRepository).delete(recipeDto);
        recipeService.deleteRecipe(recipeDto.getId());
    }

    @Test(expected = RecipeNotFoundException.class)
    public void deleteRecipe_When_Recipe_Not_found() {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Chicken");
        recipeDto.setId(1);
        Mockito.when(recipeRepository.findById(recipeDto.getId())).thenReturn(Optional.ofNullable(null));
        //Mockito.doNothing().when(recipeRepository).delete(recipeDto);
        recipeService.deleteRecipe(recipeDto.getId());
    }

    @Test
    public void updateRecipe_for_intended_recipe() {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        Integer recipeId = 1;
        RecipeRequestModel recipeRequestModel = new RecipeRequestModel();
        recipeRequestModel.setName("Chicken");
        recipeRequestModel.setServes(4);
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Chicken");
        recipeDto.setServes(1);
        recipeDto.setId(1);
        RecipeDto updateRecipe = new RecipeDto();
        BeanUtils.copyProperties(recipeDto, updateRecipe);
        updateRecipe.setServes(recipeRequestModel.getServes());
        recipeDtoList.add(recipeDto);
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeDto));
        Mockito.when(recipeRepository.findRecipeByName(recipeRequestModel.getName())).thenReturn(recipeDtoList);
        Mockito.when(recipeRepository.save(ArgumentMatchers.any(RecipeDto.class))).thenReturn(updateRecipe);
        RecipeDto updatedRecipe = recipeService.updateRecipe(recipeRequestModel, recipeId);
        Assertions.assertEquals(updatedRecipe.getId(), updatedRecipe.getId());
    }

    @Test(expected = RecipeConstraintException.class)
    public void updateRecipe_for_not_intended_recipe() {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        Integer recipeId = 1;
        RecipeRequestModel recipeRequestModel = new RecipeRequestModel();
        recipeRequestModel.setName("Chicken");
        recipeRequestModel.setServes(4);
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Chicken");
        recipeDto.setServes(1);
        recipeDto.setId(2);
        RecipeDto updateRecipe = new RecipeDto();
        BeanUtils.copyProperties(recipeDto, updateRecipe);
        updateRecipe.setServes(recipeRequestModel.getServes());
        recipeDtoList.add(recipeDto);
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(new RecipeDto()));
        Mockito.when(recipeRepository.findRecipeByName(recipeRequestModel.getName())).thenReturn(recipeDtoList);
        //Mockito.when(recipeRepository.save(ArgumentMatchers.any(RecipeDto.class))).thenReturn(updateRecipe);
        RecipeDto updatedRecipe = recipeService.updateRecipe(recipeRequestModel, recipeId);
        Assertions.assertEquals(updatedRecipe.getId(), updatedRecipe.getId());
    }

    @Test(expected = RecipeNotFoundException.class)
    public void updateRecipe_when_no_recipe_exists() {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        Integer recipeId = 1;
        RecipeRequestModel recipeRequestModel = new RecipeRequestModel();
        recipeRequestModel.setName("Chicken");
        recipeRequestModel.setServes(4);
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Chicken");
        recipeDto.setServes(1);
        recipeDto.setId(1);
        RecipeDto updateRecipe = new RecipeDto();
        BeanUtils.copyProperties(recipeDto, updateRecipe);
        updateRecipe.setServes(recipeRequestModel.getServes());
        recipeDtoList.add(recipeDto);
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.ofNullable(null));
       /* Mockito.when(recipeRepository.findRecipeByName(recipeRequestModel.getName())).thenReturn(recipeDtoList);
        Mockito.when(recipeRepository.save(ArgumentMatchers.any(RecipeDto.class))).thenReturn(updateRecipe);*/
        RecipeDto updatedRecipe = recipeService.updateRecipe(recipeRequestModel, recipeId);
        Assertions.assertEquals(updatedRecipe.getId(), updatedRecipe.getId());
    }

    @Test
    public void findRecipe_when_recipe_exists() {
        Integer recipeId = 1;
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Chicken");
        recipeDto.setId(recipeId);
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeDto));
        RecipeDto foundRecipe = recipeService.findRecipe(recipeId);
        Assertions.assertEquals(recipeDto, foundRecipe);
    }

    @Test(expected = RecipeNotFoundException.class)
    public void findRecipe_when_recipe_not_exists() {
        Integer recipeId = 1;
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.ofNullable(null));
        RecipeDto foundRecipe = recipeService.findRecipe(recipeId);
        Assertions.assertTrue(Objects.isNull(foundRecipe));
    }

    @Test
    public void findAllRecipes_when_recipes_exists() {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Chicken");
        recipeDto.setId(1);
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        recipeDtoList.add(recipeDto);
        Mockito.when(recipeRepository.findAll()).thenReturn(recipeDtoList);
        List<RecipeDto> foundAllRecipes = recipeService.findAllRecipes();
        Assertions.assertEquals(recipeDtoList.size(), foundAllRecipes.size());
    }

    @Test
    public void findAllRecipes_when_no_recipes_exists() {
        Mockito.when(recipeRepository.findAll()).thenReturn(new ArrayList<>());
        List<RecipeDto> foundAllRecipes = recipeService.findAllRecipes();
        Assertions.assertTrue(foundAllRecipes.isEmpty());
    }

    @Test
    public void searchRecipes_when_recipes_exists() {
        RecipeSearchCriteria recipeSearchCriteria = new RecipeSearchCriteria();
        recipeSearchCriteria.setDishType("Non Veg");
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1);
        recipeDto.setName("Chicken");
        recipeDto.setDishType("Non Veg");
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        recipeDtoList.add(recipeDto);
        Mockito.when(recipeRepository.findAll(ArgumentMatchers.any(Specification.class))).thenReturn(recipeDtoList);
        List<RecipeDto> foundRecipes = recipeService.searchRecipes(recipeSearchCriteria);
        Assertions.assertEquals(recipeDtoList.size(), foundRecipes.size());
    }

    @Test
    public void searchRecipes_when_recipes_not_exists() {
        RecipeSearchCriteria recipeSearchCriteria = new RecipeSearchCriteria();
        recipeSearchCriteria.setDishType("Non Veg");
        Mockito.when(recipeRepository.findAll(ArgumentMatchers.any(Specification.class))).thenReturn(new ArrayList());
        List<RecipeDto> foundRecipes = recipeService.searchRecipes(recipeSearchCriteria);
        Assertions.assertTrue(foundRecipes.isEmpty());
    }

    private RecipeRequestModel buildRecipeRequestModel() {
        RecipeRequestModel recipeRequestModel = new RecipeRequestModel();
        recipeRequestModel.setDescription("Chicken 65 ");
        recipeRequestModel.setName("Chicken");
        recipeRequestModel.setDishType("Non-Veg");
        recipeRequestModel.setServes(2);
        recipeRequestModel.setInstruction("Instructions");
        ArrayList<Ingredient> ingredients = Mockito.mock(ArrayList.class);
        Ingredient ingredient = Mockito.mock(Ingredient.class);
        ingredient.setIngredientId(1);
        ingredient.setName("Chicken");
        ingredient.setAction("some action");
        ingredients.add(ingredient);
        recipeRequestModel.setIngredients(ingredients);
        return recipeRequestModel;
    }
}
