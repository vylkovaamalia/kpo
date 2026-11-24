package infrastructure.mappers;

import domain.models.shared.CriterionId;
import domain.models.shared.Score;
import domain.models.student.GradeView;
import domain.models.student.Student;
import domain.models.student.StudentId;
import infrastructure.dtos.GradeDto;
import infrastructure.dtos.StudentDto;

import java.time.LocalDate;
import java.util.ArrayList;

public class StudentMapper {
    public StudentDto toDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.id = student.id().toString();
        dto.name = student.name();
        dto.grades = new ArrayList<>();

        for (GradeView view : student.getGradesView()) {
            GradeDto g = new GradeDto();
            g.criterionId = view.criterionId().toString();
            g.scoreValue = view.score().value();
            g.scoreMax = view.score().max();
            g.date = view.date().toString();
            g.comment = view.comment();
            dto.grades.add(g);
        }
        return dto;
    }

    public Student toDomain(StudentDto dto) {
        Student student = Student.restore(StudentId.fromString(dto.id), dto.name);
        if (dto.grades != null) {
            for (GradeDto g : dto.grades) {
                student.restoreGrade(
                        CriterionId.fromString(g.criterionId),
                        new Score(g.scoreValue, g.scoreMax),
                        LocalDate.parse(g.date),
                        g.comment
                );
            }
        }
        return student;
    }
}
