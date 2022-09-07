package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.entity.User;
import recipes.repo.UserRepository;
import recipes.security.RecipeUserDetails;

@Service
public class RecipesUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public RecipesUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findById(email).orElseThrow(()
                -> new UsernameNotFoundException("User not found"));
        return new RecipeUserDetails(user);
    }
}
