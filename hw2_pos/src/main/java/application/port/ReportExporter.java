package application.port;

import application.dto.FinalReportDto;
import java.util.List;

public interface ReportExporter {
    void export(List<FinalReportDto> report);
}