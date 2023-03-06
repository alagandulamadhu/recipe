package nl.abnamro.intake.assesement.recipe.data;

import nl.abnamro.intake.assesement.recipe.data.dto.RecipeDto;
import nl.abnamro.intake.assesement.recipe.data.dto.RecipeIngredientDto;
import nl.abnamro.intake.assesement.recipe.data.repository.RecipeRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Objects;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecipeDtoRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void testCrudRecipe() {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Chicken");
        recipeDto.setDescription("Chicken Curry");
        recipeDto.setInstruction("Oven");
        recipeDto.setDishType("Non Veg");
        recipeDto.setServes(2);
        recipeDto.setRecipeIngredients(new ArrayList<>());
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
        recipeIngredientDto.setIngredientName("Chicken");
        recipeDto.getRecipeIngredients().add(recipeIngredientDto);
        recipeRepository.save(recipeDto);
        Assertions.assertTrue(Objects.nonNull(recipeDto.getId()));
        Assertions.assertTrue(Objects.nonNull(recipeRepository.findById(recipeDto.getId()).orElse(null)));
        recipeRepository.deleteById(recipeDto.getId());
        Assertions.assertTrue(Objects.isNull(recipeRepository.findById(recipeDto.getId()).orElse(null)));
    }
}
