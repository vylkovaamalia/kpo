package application.services;

import application.dto.FinalReportDto;
import application.port.ReportExporter;
import domain.models.rubricator.Rubricator;
import domain.models.shared.FinalScore;
import domain.models.student.Student;
import domain.repositories.RubricatorRepository;
import domain.repositories.StudentRepository;
import domain.services.FinalScoreCalculator;

import java.util.ArrayList;
import java.util.List;

public class ExportFinalReportService {
    private final StudentRepository studentRepository;
    private final RubricatorRepository rubricatorRepository;
    private final FinalScoreCalculator calculator;
    private final ReportExporter exporter;

    public ExportFinalReportService(StudentRepository studentRepository,
                                    RubricatorRepository rubricatorRepository,
                                    FinalScoreCalculator calculator,
                                    ReportExporter exporter) {
        this.studentRepository = studentRepository;
        this.rubricatorRepository = rubricatorRepository;
        this.calculator = calculator;
        this.exporter = exporter;
    }

    public void execute() {
        Rubricator rubricator = rubricatorRepository.get()
                .orElseThrow(() -> new IllegalStateException("Нет критериев"));

        List<Student> students = studentRepository.findAll();
        List<FinalReportDto> reportData = new ArrayList<>();

        for (Student student : students) {
            FinalScore score = calculator.calculate(student, rubricator);
            double displayScore = Math.round(score.value() * 100.0) / 100.0;
            reportData.add(new FinalReportDto(student.name(), displayScore));
        }

        exporter.export(reportData);
    }
}
