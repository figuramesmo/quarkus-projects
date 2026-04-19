package ada.caixa.repository;

import ada.caixa.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public Optional<User> findBuSignInCredentialsOptional(String email, String password) {
        return find("email = ?1 and password = ?2", email, password).firstResultOptional();
    }
}
