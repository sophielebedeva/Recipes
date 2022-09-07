package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.entity.Recipe;
import recipes.repo.RecipeRepo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class RecipeService {
    RecipeRepo recipeRepo;


    @Autowired
    RecipeService(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public void saveRecipe(Recipe recipe) {
        recipeRepo.save(recipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepo.deleteById(id);
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepo.findById(id);
    }

    public void updateRecipe(long id, Recipe newRecipe){
        Optional<Recipe> recipe = findById(id);
        recipe.get().setName(newRecipe.getName());
        recipe.get().setCategory(newRecipe.getCategory());
        recipe.get().setDescription(newRecipe.getDescription());
        recipe.get().setDirections(newRecipe.getDirections());
        recipe.get().setIngredients(newRecipe.getIngredients());
        recipe.get().setDate(LocalDateTime.now());
        recipeRepo.save(recipe.get());
    }

    public List<Recipe> findByCategory (String category){
        return recipeRepo.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findByName(String name) {
        return recipeRepo.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}


