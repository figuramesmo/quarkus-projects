package ada.caixa.health;

import ada.caixa.repository.CourseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheck implements HealthCheck {

    private final CourseRepository courseRepository;

    public ReadinessHealthCheck(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    @Override
    public HealthCheckResponse call() {
        try {
            courseRepository.count();
            return HealthCheckResponse
                    .named("Readiness check passed!")
                    .up()
                    .withData("courseCount", courseRepository.count())
                    .build();
        } catch (Exception e) {
            return HealthCheckResponse.down("Readiness check failed. Error: " + e.getMessage());
        }
    }

}
