package application.services;

import domain.models.shared.CriterionId;
import domain.models.student.Student;
import domain.models.student.StudentId;
import domain.repositories.StudentRepository;

public class RemoveGradeService {
    private final StudentRepository studentRepository;

    public RemoveGradeService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void execute(StudentId studentId, CriterionId criterionId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Студент не найден"));

        student.removeGrade(criterionId);

        studentRepository.save(student);
    }
}
