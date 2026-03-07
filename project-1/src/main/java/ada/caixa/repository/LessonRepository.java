package ada.caixa.repository;

import ada.caixa.entity.Lesson;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LessonRepository implements PanacheRepository<Lesson> {
}
