package nl.abnamro.intake.assesement.recipe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nl.abnamro.intake.assesement.recipe.aop.annotation.ExecutionTime;
import nl.abnamro.intake.assesement.recipe.data.dto.RecipeDto;
import nl.abnamro.intake.assesement.recipe.model.RecipeRequestModel;
import nl.abnamro.intake.assesement.recipe.data.RecipeSearchCriteria;
import nl.abnamro.intake.assesement.recipe.model.RecipeResponseModel;
import nl.abnamro.intake.assesement.recipe.service.RecipeService;
import nl.abnamro.intake.assesement.recipe.util.RecipeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Api(value = "CRUD Rest APIs for Recipe resource")
@RestController
@RequestMapping("/api")
public class RecipeController {
    private static Logger LOGGER = LogManager.getLogger(RecipeController.class);

    @Autowired
    private RecipeService recipeService;

    @ExecutionTime
    @ApiOperation(value = "Create Recipe REST API")
    @PostMapping(value = "/v1/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeResponseModel> createRecipe(@Valid @RequestBody RecipeRequestModel recipeRequestModel) {
        RecipeResponseModel recipeResponseModel = RecipeUtil.buildRecipeResponseModel(recipeService.saveRecipe(recipeRequestModel));
        recipeResponseModel.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(recipeResponseModel, HttpStatus.CREATED);
    }

    @ExecutionTime
    @ApiOperation(value = "Delete Recipe by recipeId REST API")
    @DeleteMapping(value = "/v1/recipes/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeResponseModel> deleteRecipe(@PathVariable Integer recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.ok(new RecipeResponseModel(HttpStatus.OK.value(), null, new Date()));
    }

    @ExecutionTime
    @ApiOperation(value = "Update Recipe By recipeId REST API")
    @PutMapping(value = "/v1/recipes/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeResponseModel> updateRecipe(@PathVariable Integer recipeId, @RequestBody RecipeRequestModel recipeRequestModel) {
        RecipeResponseModel recipeResponseModel = RecipeUtil.buildRecipeResponseModel(recipeService.updateRecipe(recipeRequestModel, recipeId));
        recipeResponseModel.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(recipeResponseModel);
    }

    @ExecutionTime
    @ApiOperation(value = "Get Recipe by recipeId REST API")
    @GetMapping(value = "/v1/recipes/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeResponseModel> getRecipe(@PathVariable Integer recipeId) {
        RecipeResponseModel recipeResponseModel = RecipeUtil.buildRecipeResponseModel(recipeService.findRecipe(recipeId));
        recipeResponseModel.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(recipeResponseModel);
    }

    @ExecutionTime
    @ApiOperation(value = "Get All Recipes REST API")
    @GetMapping(value = "/v1/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeResponseModel> getAllRecipe() {
        List<RecipeDto> recipeDtoList = recipeService.findAllRecipes();
        if (CollectionUtils.isEmpty(recipeDtoList)) {
            return new ResponseEntity<>(new RecipeResponseModel(HttpStatus.NO_CONTENT.value(), null, new Date()), HttpStatus.NO_CONTENT);
        }
        RecipeResponseModel recipeResponseModel = RecipeUtil.buildRecipeResponseModel(recipeService.findAllRecipes());
        recipeResponseModel.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(recipeResponseModel);
    }

    @ExecutionTime
    @ApiOperation(value = "Get all recipes by lookup REST API")
    @GetMapping(value = "/v1/recipes/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeResponseModel> searchRecipes(@RequestParam(required = false, name = "dishType") String dishType, @RequestParam(required = false, name = "serves") Integer serves,
                                                             @RequestParam(required = false, name = "instruction") String instruction, @RequestParam(required = false, name = "includeIngredients") List<String> includeIngredients,
                                                             @RequestParam(required = false, name = "excludeIngredients") List<String> excludeIngredients) {
        List<RecipeDto> recipeDtoList = recipeService.searchRecipes(buildRecipeSearchCriteria(dishType, serves, instruction, includeIngredients, excludeIngredients));
        if (CollectionUtils.isEmpty(recipeDtoList)) {
            return new ResponseEntity<>(new RecipeResponseModel(HttpStatus.NO_CONTENT.value(), null, new Date()), HttpStatus.NO_CONTENT);
        }
        RecipeResponseModel recipeResponseModel = RecipeUtil.buildRecipeResponseModel(recipeService.findAllRecipes());
        recipeResponseModel.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(recipeResponseModel);
    }

    private RecipeSearchCriteria buildRecipeSearchCriteria(String dishType, Integer serves, String instruction, List<String> includeIngredients, List<String> excludeIngredients) {
        RecipeSearchCriteria recipeSearchCriteria = new RecipeSearchCriteria();
        recipeSearchCriteria.setDishType(dishType);
        recipeSearchCriteria.setServes(serves);
        recipeSearchCriteria.setInstruction(instruction);
        recipeSearchCriteria.setInclusiveIngredients(includeIngredients);
        recipeSearchCriteria.setExclusiveIngredients(excludeIngredients);
        return recipeSearchCriteria;
    }

}
