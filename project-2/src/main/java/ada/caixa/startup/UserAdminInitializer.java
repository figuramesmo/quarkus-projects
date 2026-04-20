package ada.caixa.startup;

import ada.caixa.entity.User;
import ada.caixa.repository.UserRepository;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UserAdminInitializer {

    private static final Logger log = LoggerFactory.getLogger(UserAdminInitializer.class);
    private final UserRepository userRepository;

    public UserAdminInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Startup
    @Transactional
    public void init(){
        User adminUser = new User(
                "admin",
                "admin",
                "admin",
                "ADMIN"
        );

        userRepository.persist(adminUser);
        Log.info("[CREATE] Admin user created with username: 'admin' and password: 'admin'");
    }
}
