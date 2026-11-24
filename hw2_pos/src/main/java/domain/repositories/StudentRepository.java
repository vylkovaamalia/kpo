package domain.repositories;

import domain.models.student.Student;
import domain.models.student.StudentId;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    void save(Student student);

    Optional<Student> findById(StudentId id);

    List<Student> findAll();
}
