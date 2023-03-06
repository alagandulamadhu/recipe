package nl.abnamro.intake.assesement.recipe.data.specification;

import nl.abnamro.intake.assesement.recipe.data.dto.RecipeDto;
import nl.abnamro.intake.assesement.recipe.data.dto.RecipeIngredientDto;
import nl.abnamro.intake.assesement.recipe.data.RecipeSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeSearchSpecification {

    public static Specification<RecipeDto> recipeSearchSpecification(RecipeSearchCriteria recipeSearchCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (Objects.nonNull(recipeSearchCriteria.getDishType())) {
                predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("dishType"), recipeSearchCriteria.getDishType())));
            }
            if (Objects.nonNull(recipeSearchCriteria.getServes())) {
                predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("serves"), recipeSearchCriteria.getServes())));
            }
            if (Objects.nonNull(recipeSearchCriteria.getInstruction())) {
                predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.get("instruction"), "% " + recipeSearchCriteria.getInstruction() + "%")));
            }
            if (Objects.nonNull(recipeSearchCriteria.getInclusiveIngredients()) || Objects.nonNull(recipeSearchCriteria.getExclusiveIngredients())) {
                ListJoin<RecipeDto, RecipeIngredientDto> recipeIngredientListJoin = root.joinList("recipeIngredients", JoinType.LEFT);
                if (Objects.nonNull(recipeSearchCriteria.getInclusiveIngredients()))
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.and(recipeIngredientListJoin.get("ingredientName").in(recipeSearchCriteria.getInclusiveIngredients()))));
                if (Objects.nonNull(recipeSearchCriteria.getExclusiveIngredients()))
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.not(recipeIngredientListJoin.get("ingredientName").in(recipeSearchCriteria.getExclusiveIngredients()))));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
    }
}
