package application.services;

import domain.models.rubricator.Rubricator;
import domain.models.shared.FinalScore;
import domain.models.student.Student;
import domain.models.student.StudentId;
import domain.repositories.RubricatorRepository;
import domain.repositories.StudentRepository;
import domain.services.FinalScoreCalculator;

public class CalculateFinalScoreService {
    private final StudentRepository studentRepository;
    private final RubricatorRepository rubricatorRepository;
    private final FinalScoreCalculator calculator;

    public CalculateFinalScoreService(StudentRepository studentRepository,
                                      RubricatorRepository rubricatorRepository,
                                      FinalScoreCalculator calculator) {
        this.studentRepository = studentRepository;
        this.rubricatorRepository = rubricatorRepository;
        this.calculator = calculator;
    }

    public FinalScore execute(StudentId studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Студент не найден"));
        Rubricator rubricator = rubricatorRepository.get()
                .orElseThrow(() -> new IllegalStateException("Рубрикатор пуст"));

        return calculator.calculate(student, rubricator);
    }
}
