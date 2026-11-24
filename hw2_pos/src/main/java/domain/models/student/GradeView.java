package domain.models.student;

import domain.models.shared.CriterionId;
import domain.models.shared.Score;
import java.time.LocalDate;

public record GradeView(
        CriterionId criterionId,
        Score score,
        LocalDate date,
        String comment
) {
}
