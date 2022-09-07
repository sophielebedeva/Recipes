package recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
public class Recipe {
    public Recipe(String name, String description, List<String> ingredients, List<String> directions, String category, LocalDateTime date) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.category = category;
        this.date = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @NonNull
    private String category;

    @NonNull
    private LocalDateTime date;

    @NotBlank
    @NonNull
    private String description;


    @NonNull
    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients;

    @NonNull
    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> directions;

    @NotBlank
    @NonNull
    private String name;

    @JsonIgnore
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public String getCategory() {return category; }

    public LocalDateTime getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public void setName(String name) {
        this.name = name;
    }
}
