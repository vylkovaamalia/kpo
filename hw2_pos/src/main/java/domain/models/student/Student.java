package domain.models.student;

import domain.models.shared.CriterionId;
import domain.models.shared.Score;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Student {
    private final StudentId id;
    private final String name;
    private final List<Grade> grades;

    public Student(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        this.id = StudentId.generate();
        this.name = name;
        this.grades = new ArrayList<>();
    }

    private Student(StudentId id, String name) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.grades = new ArrayList<>();
    }

    public void addGrade(CriterionId criterionId, Score score, String comment) {
        if (findGrade(criterionId).isPresent()) {
            throw new IllegalStateException("Оценка по этому критерию уже есть");
        }
        grades.add(new Grade(criterionId, score, LocalDate.now(), comment));
    }

    public void changeGrade(CriterionId criterionId, Score newScore) {
        Grade grade = findGrade(criterionId)
                .orElseThrow(() -> new IllegalArgumentException("Оценка не найдена"));
        grade.changeScore(newScore);
    }

    public void removeGrade(CriterionId criterionId) {
        grades.removeIf(g -> g.criterionId().equals(criterionId));
    }

    private Optional<Grade> findGrade(CriterionId criterionId) {
        for (Grade g : grades) {
            if (g.criterionId().equals(criterionId)) {
                return Optional.of(g);
            }
        }
        return Optional.empty();
    }

    public Optional<Score> getScoreFor(CriterionId criterionId) {
        for (Grade g : grades) {
            if (g.criterionId().equals(criterionId)) {
                return Optional.of(g.score());
            }
        }
        return Optional.empty();
    }

    public List<GradeView> getGradesView() {
        List<GradeView> views = new ArrayList<>();
        for (Grade g : grades) {
            views.add(new GradeView(
                    g.criterionId(),
                    g.score(),
                    g.date(),
                    g.comment()
            ));
        }
        return views;
    }

    public StudentId id() { return id; }
    public String name() { return name; }

    public static Student restore(StudentId id, String name) {
        return new Student(id, name);
    }

    public void restoreGrade(CriterionId criterionId, Score score, LocalDate date, String comment) {
        if (findGrade(criterionId).isPresent()) {
            throw new IllegalStateException("Ошибка в данных: дубликат оценки по критерию " + criterionId);
        }
        grades.add(new Grade(criterionId, score, date, comment));
    }
}