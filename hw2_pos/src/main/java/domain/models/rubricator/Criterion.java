package domain.models.rubricator;

import domain.models.shared.CriterionId;
import domain.models.shared.Weight;

public class Criterion {
    private final CriterionId id;
    private final String name;
    private final int maxScore;
    private final Weight weight;

    Criterion(CriterionId id, String name, int maxScore, Weight weight) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название критерия не может быть пустым");
        }
        if (maxScore <= 0) {
            throw new IllegalArgumentException("Максимальный балл должен быть > 0");
        }
        this.id = id;
        this.name = name;
        this.maxScore = maxScore;
        this.weight = weight;
    }

    public CriterionId id() { return id; }
    public String name() { return name; }
    public int maxScore() { return maxScore; }
    public Weight weight() { return weight; }
}
