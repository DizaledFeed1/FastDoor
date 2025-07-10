package org.example.mrdverkin.reportCreators;

import org.example.mrdverkin.dto.ReportDTO;
import org.springframework.context.annotation.Bean;

import java.io.File;

public interface Creater {
    byte[] convertReport(ReportDTO report) throws Exception;
}
