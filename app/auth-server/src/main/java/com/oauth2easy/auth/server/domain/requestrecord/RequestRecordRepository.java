package com.oauth2easy.auth.server.domain.requestrecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRecordRepository extends JpaRepository<RequestRecord, Long> {
}
