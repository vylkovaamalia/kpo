package application.services;

import domain.models.rubricator.Rubricator;
import domain.models.student.GradeView;
import domain.models.student.Student;
import domain.models.student.StudentId;
import domain.repositories.RubricatorRepository;
import domain.repositories.StudentRepository;

import java.util.List;

public class QueryService {
    private final StudentRepository studentRepository;
    private final RubricatorRepository rubricatorRepository;

    public QueryService(StudentRepository studentRepository, RubricatorRepository rubricatorRepository) {
        this.studentRepository = studentRepository;
        this.rubricatorRepository = rubricatorRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Rubricator getRubricator() {
        return rubricatorRepository.get().orElseGet(Rubricator::new);
    }

    public Student getStudent(StudentId id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Студент не найден"));
    }

    public List<GradeView> getStudentGrades(StudentId id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Студент не найден"))
                .getGradesView();
    }
}
