package application.services;

import domain.models.rubricator.CriterionView;
import domain.models.rubricator.Rubricator;
import domain.models.shared.CriterionId;
import domain.models.shared.Score;
import domain.models.student.Student;
import domain.models.student.StudentId;
import domain.repositories.RubricatorRepository;
import domain.repositories.StudentRepository;

public class ChangeGradeService {
    private final StudentRepository studentRepository;
    private final RubricatorRepository rubricatorRepository;

    public ChangeGradeService(StudentRepository studentRepository, RubricatorRepository rubricatorRepository) {
        this.studentRepository = studentRepository;
        this.rubricatorRepository = rubricatorRepository;
    }

    public void execute(StudentId studentId, CriterionId criterionId, int newScoreValue) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Студент не найден"));
        Rubricator rubricator = rubricatorRepository.get()
                .orElseThrow(() -> new IllegalStateException("Рубрикатор пуст"));

        CriterionView criterion = rubricator.findCriterion(criterionId)
                .orElseThrow(() -> new IllegalArgumentException("Критерий не найден"));

        Score newScore = new Score(newScoreValue, criterion.maxScore());

        student.changeGrade(criterionId, newScore);

        studentRepository.save(student);
    }
}
