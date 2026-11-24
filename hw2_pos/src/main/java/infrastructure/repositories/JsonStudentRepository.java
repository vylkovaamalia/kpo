package infrastructure.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.models.student.Student;
import domain.models.student.StudentId;
import domain.repositories.StudentRepository;
import infrastructure.dtos.StudentDto;
import infrastructure.mappers.StudentMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonStudentRepository implements StudentRepository {
    private final File file;
    private final ObjectMapper mapper;
    private final StudentMapper studentMapper = new StudentMapper();
    private List<Student> cache;

    public JsonStudentRepository(String filePath) {
        this.file = new File(filePath);
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.cache = load();
    }

    private List<Student> load() {
        if (!file.exists()) return new ArrayList<>();
        try {
            List<StudentDto> dtos = mapper.readValue(file, new TypeReference<>() {});
            List<Student> students = new ArrayList<>();
            for (StudentDto dto : dtos) students.add(studentMapper.toDomain(dto));
            return students;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения студентов", e);
        }
    }

    private void saveToFile() {
        try {
            List<StudentDto> dtos = new ArrayList<>();
            for (Student s : cache) dtos.add(studentMapper.toDto(s));
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, dtos);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи студентов", e);
        }
    }

    @Override
    public void save(Student student) {
        cache.removeIf(s -> s.id().equals(student.id()));
        cache.add(student);
        saveToFile();
    }

    @Override
    public Optional<Student> findById(StudentId id) {
        for (Student s : cache) {
            if (s.id().equals(id)) return Optional.of(s);
        }
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(cache);
    }
}
