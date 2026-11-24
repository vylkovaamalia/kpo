package application.services;

import domain.models.rubricator.Rubricator;
import domain.models.shared.Weight;
import domain.repositories.RubricatorRepository;

public class AddCriterionService {
    private final RubricatorRepository rubricatorRepository;

    public AddCriterionService(RubricatorRepository rubricatorRepository) {
        this.rubricatorRepository = rubricatorRepository;
    }

    public void execute(String name, int maxScore, double weightValue) {
        Rubricator rubricator = rubricatorRepository.get().orElseGet(Rubricator::new);
        Weight weight = new Weight(weightValue); // VO проверит вес
        rubricator.addCriterion(name, maxScore, weight);
        rubricatorRepository.save(rubricator);
    }
}
