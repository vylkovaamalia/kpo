package domain.repositories;

import domain.models.rubricator.Rubricator;
import java.util.Optional;

public interface RubricatorRepository {
    void save(Rubricator rubricator);

    Optional<Rubricator> get();
}
