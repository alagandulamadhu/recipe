package nl.abnamro.intake.assesement.recipe.data.repository;

import nl.abnamro.intake.assesement.recipe.data.dto.RecipeIngredientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientDto, Integer> {
}
