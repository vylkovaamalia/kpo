package infrastructure.export;

import application.dto.FinalReportDto;
import application.port.ReportExporter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReportExporter implements ReportExporter {
    private final File file;
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonReportExporter(String filePath) {
        this.file = new File(filePath);
    }

    @Override
    public void export(List<FinalReportDto> report) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, report);
            System.out.println("Отчет успешно сохранен в " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения отчета", e);
        }
    }
}
