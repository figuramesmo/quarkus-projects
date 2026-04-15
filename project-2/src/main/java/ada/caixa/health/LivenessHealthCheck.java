package ada.caixa.health;

import ada.caixa.repository.CourseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class LivenessHealthCheck implements HealthCheck {

    private final CourseRepository courseRepository;

    public LivenessHealthCheck(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public HealthCheckResponse call() {
        try {
            courseRepository.count();
            return HealthCheckResponse.up("Liveness check passed!");
        } catch (Exception e) {
            return HealthCheckResponse.down("Liveness check failed. Error: " + e.getMessage());
        }
    }
}
