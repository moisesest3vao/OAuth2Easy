package com.oauth2easy.auth.server.infra.interceptor;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class DataCollectorInterceptor extends GenericFilterBean {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        new Thread(() -> {
            /*
            TODO: Collect data asynchronously and send it to the database
              The Machine Learning model will use this data after being processed
            */
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        chain.doFilter(request, response);
    }
}
