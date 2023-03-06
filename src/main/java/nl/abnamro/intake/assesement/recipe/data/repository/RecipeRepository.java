package nl.abnamro.intake.assesement.recipe.data.repository;

import nl.abnamro.intake.assesement.recipe.data.dto.RecipeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeDto, Integer>, JpaSpecificationExecutor {
    List<RecipeDto> findRecipeByName(String name);
}
