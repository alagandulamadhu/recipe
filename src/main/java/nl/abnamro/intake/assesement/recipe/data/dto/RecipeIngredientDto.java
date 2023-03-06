package nl.abnamro.intake.assesement.recipe.data.dto;

import javax.persistence.*;

@Entity
@Table(name = "RECIPE_INGREDIENT")
public class RecipeIngredientDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "INGREDIENT_NAME")
    private String ingredientName;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private RecipeDto recipe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public RecipeDto getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeDto recipe) {
        this.recipe = recipe;
    }

}
