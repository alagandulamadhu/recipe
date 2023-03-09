package nl.abnamro.intake.assesement.recipe.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import nl.abnamro.intake.assesement.recipe.data.dto.RecipeDto;
import nl.abnamro.intake.assesement.recipe.data.repository.RecipeRepository;
import nl.abnamro.intake.assesement.recipe.model.Ingredient;
import nl.abnamro.intake.assesement.recipe.model.Recipe;
import nl.abnamro.intake.assesement.recipe.model.RecipeRequestModel;
import nl.abnamro.intake.assesement.recipe.model.RecipeResponseModel;
import nl.abnamro.intake.assesement.recipe.service.RecipeService;
import nl.abnamro.intake.assesement.recipe.util.RecipeUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.CREATED;


@RunWith(MockitoJUnitRunner.class)
class RecipeControllerTest {

    @InjectMocks
    RecipeController recipeControllerTest;
    @Mock
    RecipeService recipeService;
    @Mock
    RecipeRepository recipeRepository;

    @Mock
    Recipe reciepe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createRecipe() {
        RecipeRequestModel recipeRequestModel = Mockito.spy(RecipeRequestModel.class);
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

        RecipeDto recipeDto = Mockito.spy(RecipeDto.class);
        recipeDto.setName("Chicken");

        Mockito.when(recipeService.saveRecipe(recipeRequestModel)).thenReturn(recipeDto);

        ResponseEntity<RecipeResponseModel> response = recipeControllerTest.createRecipe(recipeRequestModel);
        assertEquals(response.getStatusCode(), CREATED);
        assertEquals(response.getBody().getData().get(0).getName(), recipeRequestModel.getName());
    }

    @Test
    void deleteRecipe() {
        RecipeDto recipeDto = Mockito.spy(RecipeDto.class);
        recipeDto.setName("Chicken");
        recipeDto.setId(1);
        Mockito.when(recipeService.findRecipe(1)).thenReturn(recipeDto);
        ResponseEntity<RecipeResponseModel> response = recipeControllerTest.deleteRecipe(1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void updateRecipe() {
        RecipeRequestModel recipeRequestModel = Mockito.spy(RecipeRequestModel.class);
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

        RecipeDto recipeDto = Mockito.spy(RecipeDto.class);
        recipeDto.setName("Chicken");
        recipeDto.setId(1);
        Mockito.when(recipeService.updateRecipe(recipeRequestModel, 1)).thenReturn(recipeDto);
        ResponseEntity<RecipeResponseModel> response = recipeControllerTest.updateRecipe(1, recipeRequestModel);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getRecipe() {
        RecipeDto recipeDto = Mockito.spy(RecipeDto.class);
        recipeDto.setName("Chicken");
        recipeDto.setId(1);
        Mockito.when(recipeService.findRecipe(1)).thenReturn(recipeDto);
        ResponseEntity<RecipeResponseModel> response = recipeControllerTest.getRecipe(1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getData().get(0).getRecipeId(), recipeDto.getId());
    }


}