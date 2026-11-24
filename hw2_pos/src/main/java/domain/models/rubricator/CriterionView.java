package domain.models.rubricator;

import domain.models.shared.CriterionId;
import domain.models.shared.Weight;

public record CriterionView(
        CriterionId id,
        String name,
        int maxScore,
        Weight weight
) {
}
