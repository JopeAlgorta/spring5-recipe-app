package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author jpalg
 * Created at 1/25/2022
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);

        if (!recipeOpt.isPresent()) return null;
        Recipe recipe = recipeOpt.get();

        Optional<IngredientCommand> ingredientOpt = recipe.getIngredients().stream().filter(
                i -> i.getId().equals(ingredientId)).map(ingredientToIngredientCommand::convert).findFirst();

        return ingredientOpt.orElse(null);
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) return null;
        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(
                i -> i.getId().equals(ingredientCommand.getId())).findFirst();

        if (!ingredientOptional.isPresent()) {
            Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }
        else {
            Ingredient ingredient = ingredientOptional.get();

            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setDescription(ingredientCommand.getDescription());
            ingredient.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId()).orElse(null));
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream().filter(
                recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId())).findFirst();
        //check by description
        if (!savedIngredientOptional.isPresent()) {
            //not totally safe... But best guess
            savedIngredientOptional = savedRecipe.getIngredients().stream().filter(
                    i -> i.getDescription().equals(ingredientCommand.getDescription())).filter(
                    i -> i.getAmount().equals(ingredientCommand.getAmount())).filter(
                    i -> i.getUom().getId().equals(ingredientCommand.getUom().getId())).findFirst();
        }

        //to do check for fail
        return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }

    @Override
    public void deleteIngredient(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) return;
        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(
                i -> i.getId().equals(ingredientId)).findFirst();

        if (!ingredientOptional.isPresent()) return;
        else recipe.removeIngredient(ingredientOptional.get());

        recipeRepository.save(recipe);
    }
}
