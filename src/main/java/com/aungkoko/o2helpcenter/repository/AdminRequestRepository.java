package com.aungkoko.o2helpcenter.repository;

import com.aungkoko.o2helpcenter.model.AdminRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRequestRepository extends JpaRepository<AdminRequest,Long> {
    AdminRequest findByEmail(String email);
}
