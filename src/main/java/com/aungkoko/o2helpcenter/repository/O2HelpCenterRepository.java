package com.aungkoko.o2helpcenter.repository;

import com.aungkoko.o2helpcenter.model.OxygenHelpCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface O2HelpCenterRepository extends JpaRepository<OxygenHelpCenter,Long> {
}
