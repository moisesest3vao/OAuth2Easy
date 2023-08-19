package com.oauth2easy.auth.server.domain.requestrecord;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Entity
public class RequestRecord {
    @Id
    @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public RequestRecord(HttpServletRequest servletRequest) {
        this.date = new Date();
    }
}
