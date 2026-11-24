import application.port.ReportExporter;
import application.services.*;
import domain.repositories.RubricatorRepository;
import domain.repositories.StudentRepository;
import domain.services.FinalScoreCalculator;
import infrastructure.export.JsonReportExporter;
import infrastructure.repositories.JsonRubricatorRepository;
import infrastructure.repositories.JsonStudentRepository;
import presentation.ConsoleApp;

public class Main {
    public static void main(String[] args) {
        StudentRepository studentRepository = new JsonStudentRepository("students.json");
        RubricatorRepository rubricatorRepository = new JsonRubricatorRepository("rubricator.json");
        ReportExporter reportExporter = new JsonReportExporter("report.json");

        FinalScoreCalculator calculator = new FinalScoreCalculator();

        AddStudentService addStudentService = new AddStudentService(studentRepository);
        AddCriterionService addCriterionService = new AddCriterionService(rubricatorRepository);
        AddGradeService addGradeService = new AddGradeService(studentRepository, rubricatorRepository);
        ChangeGradeService changeGradeService = new ChangeGradeService(studentRepository, rubricatorRepository);
        RemoveGradeService removeGradeService = new RemoveGradeService(studentRepository);
        CalculateFinalScoreService calculateFinalScoreService = new CalculateFinalScoreService(studentRepository, rubricatorRepository, calculator);
        ExportFinalReportService exportFinalReportService = new ExportFinalReportService(studentRepository, rubricatorRepository, calculator, reportExporter);
        QueryService queryService = new QueryService(studentRepository, rubricatorRepository);

        ConsoleApp app = new ConsoleApp(
                addStudentService,
                addCriterionService,
                addGradeService,
                changeGradeService,
                removeGradeService,
                calculateFinalScoreService,
                exportFinalReportService,
                queryService
        );

        app.run();
    }
}
