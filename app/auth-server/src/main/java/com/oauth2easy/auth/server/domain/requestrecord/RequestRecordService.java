package com.oauth2easy.auth.server.domain.requestrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RequestRecordService {

    @Autowired
    private RequestRecordRepository requestRecordRepository;

    public void saveRecords(List<RequestRecord> servletRequests){
        requestRecordRepository.saveAll(servletRequests);
    }
}
