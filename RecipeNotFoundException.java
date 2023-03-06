package nl.abnamro.intake.assesement.recipe.exception;

public class RecipeNotFoundException extends RuntimeException {

    private String message;

    public RecipeNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public RecipeNotFoundException() {
    }
}
