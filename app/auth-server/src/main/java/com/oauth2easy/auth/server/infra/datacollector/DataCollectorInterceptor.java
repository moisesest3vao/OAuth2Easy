package com.oauth2easy.auth.server.infra.datacollector;

import com.oauth2easy.auth.server.domain.requestrecord.RequestRecord;
import com.oauth2easy.auth.server.domain.requestrecord.RequestRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class DataCollectorInterceptor extends GenericFilterBean {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        new Thread(() -> {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            this.processRequest(servletRequest);
        }).start();

        chain.doFilter(request, response);
    }

    private void processRequest(HttpServletRequest servletRequest) {
        //add requestRecord in the cache to be saved in the next bulk saving.
        DataCollectorSchedule.recentRequestRecords.add(new RequestRecord(servletRequest));

         /*
            TODO: Collect data asynchronously and send it to the database
              The Machine Learning model will use this data after being processed
         */

    }
}
