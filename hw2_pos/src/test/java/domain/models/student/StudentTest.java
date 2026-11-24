package domain.models.student;

import domain.models.shared.CriterionId;
import domain.models.shared.Score;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void shouldNotAllowDuplicateGrades() {
        Student student = new Student("Иван");
        CriterionId criterionId = CriterionId.generate();
        Score score1 = new Score(8, 10);

        student.addGrade(criterionId, score1, "Хорошо");

        assertThrows(IllegalStateException.class,
                () -> student.addGrade(criterionId, new Score(9, 10), "Отлично"));
    }
}
