package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author jpalg
 * Created at 1/25/2022
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);

        if (!recipeOpt.isPresent()) return null;
        Recipe recipe = recipeOpt.get();

        Optional<IngredientCommand> ingredientOpt = recipe.getIngredients().stream()
                                                          .filter(i -> i.getId().equals(ingredientId))
                                                          .map(ingredientToIngredientCommand::convert)
                                                          .findFirst();

        return ingredientOpt.orElse(null);
    }
}
