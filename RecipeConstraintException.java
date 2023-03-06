package nl.abnamro.intake.assesement.recipe.exception;

public class RecipeConstraintException extends RuntimeException {

    private String message;

    public RecipeConstraintException(String message) {
        super(message);
        this.message = message;
    }

    public RecipeConstraintException() {
        super();
    }
}
