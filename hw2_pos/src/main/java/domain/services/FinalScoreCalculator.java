package domain.services;

import domain.models.rubricator.CriterionView; // Импортируем View
import domain.models.rubricator.Rubricator;
import domain.models.shared.CriterionId;
import domain.models.shared.FinalScore;
import domain.models.shared.Score;
import domain.models.student.Student;

import java.util.Optional;

public class FinalScoreCalculator {

    public FinalScore calculate(Student student, Rubricator rubricator) {
        double totalWeightedScore = 0.0;
        double totalWeight = 0.0;

        for (CriterionView criterion : rubricator.getCriteriaView()) {
            CriterionId criterionId = criterion.id();

            Optional<Score> scoreOpt = student.getScoreFor(criterionId);

            if (scoreOpt.isPresent()) {
                Score score = scoreOpt.get();

                double fraction = (double) score.value() / criterion.maxScore();

                totalWeightedScore += fraction * criterion.weight().value();
                totalWeight += criterion.weight().value();
            }
        }

        if (totalWeight == 0.0) {
            return new FinalScore(0.0);
        }

        double finalValue = totalWeightedScore / totalWeight;
        finalValue = Math.round(finalValue * 100.0) / 100.0;

        return new FinalScore(finalValue);
    }
}
