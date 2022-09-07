package recipes.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.entity.Recipe;
import recipes.service.RecipeService;
import recipes.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@Validated
public class RecipesController {

    private final RecipeService recipeService;
    private final UserService userService;

    @Autowired
    public RecipesController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }


    @PostMapping("/recipe/new")
    public ResponseEntity<?> addRecipe(HttpServletResponse response, @Valid @RequestBody Recipe recipe) {
        Recipe newRecipe = new Recipe(recipe.getName(), recipe.getDescription(), recipe.getIngredients(), recipe.getDirections(), recipe.getCategory(), recipe.getDate());
        newRecipe.setEmail(userService.getLoggedInUser());
        recipeService.saveRecipe(newRecipe);
        long id = newRecipe.getId();
        JsonObject postResponse = new JsonObject();
        postResponse.addProperty("id", id);
        return new ResponseEntity<>(postResponse.toString(), HttpStatus.OK);
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable long id) {
        Optional<?> getResponse = recipeService.findById(id);
        if (getResponse.isPresent()) {
            return new ResponseEntity<>(getResponse.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/recipe/search", params = "category")
    public List<Recipe> getRecipeByCategory(@RequestParam("category") String category) {
        return recipeService.findByCategory(category);
    }

    @GetMapping(value = "/recipe/search", params = "name")
    public List<Recipe> getRecipeByName(@RequestParam("name") String name) {
        return recipeService.findByName(name);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id) {
        Optional<Recipe> recipe = recipeService.findById(id);
        if (recipe.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");
        if (!recipe.get().getEmail().equals(userService.getLoggedInUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own recipes.");
        }
        recipeService.deleteRecipe(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/recipe/{id}")
    public void updateRecipe(@PathVariable long id, @Valid @RequestBody Recipe newRecipe) {
        Optional<Recipe> recipe = recipeService.findById(id);
        if (recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");
        }
        if (!recipe.get().getEmail().equals(userService.getLoggedInUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your own recipes.");
        }
        recipeService.updateRecipe(id, newRecipe);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }
}

