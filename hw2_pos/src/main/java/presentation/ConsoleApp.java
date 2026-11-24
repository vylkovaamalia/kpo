package presentation;

import application.services.*;
import domain.models.shared.CriterionId;
import domain.models.student.GradeView;
import domain.models.student.Student;
import domain.models.student.StudentId;

import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private final Scanner scanner = new Scanner(System.in);

    private final AddStudentService addStudentService;
    private final AddCriterionService addCriterionService;
    private final AddGradeService addGradeService;
    private final ChangeGradeService changeGradeService;
    private final RemoveGradeService removeGradeService;
    private final CalculateFinalScoreService calculateFinalScoreService;
    private final ExportFinalReportService exportFinalReportService;
    private final QueryService queryService;

    public ConsoleApp(AddStudentService addStudentService,
                      AddCriterionService addCriterionService,
                      AddGradeService addGradeService,
                      ChangeGradeService changeGradeService,
                      RemoveGradeService removeGradeService,
                      CalculateFinalScoreService calculateFinalScoreService,
                      ExportFinalReportService exportFinalReportService,
                      QueryService queryService) {
        this.addStudentService = addStudentService;
        this.addCriterionService = addCriterionService;
        this.addGradeService = addGradeService;
        this.changeGradeService = changeGradeService;
        this.removeGradeService = removeGradeService;
        this.calculateFinalScoreService = calculateFinalScoreService;
        this.exportFinalReportService = exportFinalReportService;
        this.queryService = queryService;
    }

    public void run() {
        while (true) {
            System.out.println("\n=== МЕНЮ МОДУЛЯ ПОС ===");
            System.out.println("1. Добавить студента");
            System.out.println("2. Добавить критерий");
            System.out.println("3. Выставить оценку");
            System.out.println("4. Изменить оценку");
            System.out.println("5. Отменить (удалить) оценку");
            System.out.println("6. Посчитать итог студента");
            System.out.println("7. Экспорт отчета (JSON)");
            System.out.println("8. Показать всех студентов и их оценки");
            System.out.println("9. Показать рубрикатор");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addStudent();
                    case "2" -> addCriterion();
                    case "3" -> addGrade();
                    case "4" -> changeGrade();
                    case "5" -> removeGrade();
                    case "6" -> calculateFinalScore();
                    case "7" -> exportReport();
                    case "8" -> showAllStudents();
                    case "9" -> showRubricator();
                    case "0" -> {
                        System.out.println("Выход...");
                        return;
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("ОШИБКА: " + e.getMessage());
            }
        }
    }

    private void addStudent() {
        System.out.print("Введите имя студента: ");
        String name = scanner.nextLine();
        addStudentService.execute(name);
        System.out.println("Студент успешно добавлен!");
    }

    private void addCriterion() {
        System.out.print("Название критерия: ");
        String name = scanner.nextLine();
        System.out.print("Максимальный балл: ");
        int max = Integer.parseInt(scanner.nextLine());
        System.out.print("Вес критерия (от 0.0 до 1.0): ");
        double weight = Double.parseDouble(scanner.nextLine());

        addCriterionService.execute(name, max, weight);
        System.out.println("Критерий успешно добавлен!");
    }

    private void addGrade() {
        System.out.print("ID студента: ");
        StudentId studentId = StudentId.fromString(scanner.nextLine());
        System.out.print("ID критерия: ");
        CriterionId criterionId = CriterionId.fromString(scanner.nextLine());
        System.out.print("Балл: ");
        int score = Integer.parseInt(scanner.nextLine());
        System.out.print("Комментарий (опционально): ");
        String comment = scanner.nextLine();

        addGradeService.execute(studentId, criterionId, score, comment);
        System.out.println("Оценка выставлена!");
    }

    private void changeGrade() {
        System.out.print("ID студента: ");
        StudentId studentId = StudentId.fromString(scanner.nextLine());
        System.out.print("ID критерия: ");
        CriterionId criterionId = CriterionId.fromString(scanner.nextLine());
        System.out.print("Новый балл: ");
        int score = Integer.parseInt(scanner.nextLine());

        changeGradeService.execute(studentId, criterionId, score);
        System.out.println("Оценка изменена!");
    }

    private void removeGrade() {
        System.out.print("ID студента: ");
        StudentId studentId = StudentId.fromString(scanner.nextLine());
        System.out.print("ID критерия: ");
        CriterionId criterionId = CriterionId.fromString(scanner.nextLine());

        removeGradeService.execute(studentId, criterionId);
        System.out.println("Оценка отменена (удалена)!");
    }

    private void calculateFinalScore() {
        System.out.print("ID студента: ");
        StudentId studentId = StudentId.fromString(scanner.nextLine());
        var result = calculateFinalScoreService.execute(studentId);
        System.out.printf("Итоговый балл (от 0 до 1): %.2f%n", result.value());
    }

    private void exportReport() {
        exportFinalReportService.execute();
    }

    private void showAllStudents() {
        List<Student> students = queryService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("Студентов пока нет.");
            return;
        }
        for (Student s : students) {
            System.out.println("\nСтудент: " + s.name() + " (ID: " + s.id() + ")");
            List<GradeView> grades = queryService.getStudentGrades(s.id());
            if (grades.isEmpty()) {
                System.out.println("  [нет оценок]");
            } else {
                for (GradeView g : grades) {
                    System.out.printf("  - Критерий %s: %d/%d (%s)%n",
                            g.criterionId(), g.score().value(), g.score().max(), g.comment());
                }
            }
        }
    }

    private void showRubricator() {
        var rubricator = queryService.getRubricator();
        var criteria = rubricator.getCriteriaView();

        if (criteria.isEmpty()) {
            System.out.println("Рубрикатор пуст.");
            return;
        }
        System.out.println("\n=== РУБРИКАТОР ===");
        for (var c : criteria) {
            System.out.printf("ID: %s | %s | Макс: %d | Вес: %.2f%n",
                    c.id(), c.name(), c.maxScore(), c.weight().value());
        }
    }
}
