package domain.services;

import domain.models.rubricator.Rubricator;
import domain.models.shared.*;
import domain.models.student.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FinalScoreCalculatorTest {

    @Test
    void shouldCalculateCorrectFinalScore() {
        FinalScoreCalculator calculator = new FinalScoreCalculator();
        Rubricator rubricator = new Rubricator();

        CriterionId c1 = CriterionId.generate();
        CriterionId c2 = CriterionId.generate();
        rubricator.restoreCriterion(c1, "ДЗ", 10, new Weight(0.5));
        rubricator.restoreCriterion(c2, "Экзамен", 20, new Weight(0.5));

        Student student = new Student("Иван");
        student.addGrade(c1, new Score(8, 10), "");
        student.addGrade(c2, new Score(10, 20), "");

        FinalScore result = calculator.calculate(student, rubricator);

        assertEquals(0.65, result.value());
    }
}
