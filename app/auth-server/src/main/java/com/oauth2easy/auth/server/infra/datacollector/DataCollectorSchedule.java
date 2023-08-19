package com.oauth2easy.auth.server.infra.datacollector;

import com.oauth2easy.auth.server.domain.requestrecord.RequestRecord;
import com.oauth2easy.auth.server.domain.requestrecord.RequestRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataCollectorSchedule {
    public static List<RequestRecord> recentRequestRecords = new ArrayList<>();
    @Autowired
    private RequestRecordService requestRecordService;

    @Scheduled(fixedDelay = 10000)
    public void saveRecentRequestRecords() {
        List<RequestRecord> recentRecords = DataCollectorSchedule.recentRequestRecords;
        DataCollectorSchedule.recentRequestRecords = new ArrayList<>();
        requestRecordService.saveRecords(recentRecords);
    }
}

