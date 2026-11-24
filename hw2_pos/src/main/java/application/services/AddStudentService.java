package application.services;

import domain.models.student.Student;
import domain.repositories.StudentRepository;

public class AddStudentService {
    private final StudentRepository studentRepository;

    public AddStudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void execute(String name) {
        Student student = new Student(name); // Домен сам проверит имя
        studentRepository.save(student);
    }
}
