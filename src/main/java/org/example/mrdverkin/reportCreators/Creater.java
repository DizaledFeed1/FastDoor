package org.example.mrdverkin.reportCreators;

import org.example.mrdverkin.dto.ReportDTO;

public interface Creater {
    byte[] convertReport(ReportDTO report) throws Exception;
}
