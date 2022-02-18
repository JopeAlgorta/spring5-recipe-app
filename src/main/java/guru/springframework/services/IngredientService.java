package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

/**
 * @author jpalg
 * Created at 1/25/2022
 */
public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteIngredient(Long recipeId, Long ingredientId);
}
