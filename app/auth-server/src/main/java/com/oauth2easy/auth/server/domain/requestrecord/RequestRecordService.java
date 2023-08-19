package com.oauth2easy.auth.server.domain.requestrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestRecordService {

    @Autowired
    private RequestRecordRepository requestRecordRepository;

    public void saveRecords(List<HttpServletRequest> servletRequests){
        requestRecordRepository.saveAll(servletRequests.stream().map(RequestRecord::new).collect(Collectors.toList()));
    }
}
