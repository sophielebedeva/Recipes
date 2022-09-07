package recipes.entity;

import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        recipes = new HashSet<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getRecipes() {
        return recipes;
    }


    @Id
    @NotBlank
    @NonNull
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    @NonNull
    @Size(min = 8)
    private String password;
    @ElementCollection
    private Set<Long> recipes;

}
