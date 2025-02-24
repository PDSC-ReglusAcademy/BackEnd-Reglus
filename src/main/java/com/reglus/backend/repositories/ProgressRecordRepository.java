package com.reglus.backend.repositories;

import com.reglus.backend.model.entities.rooms.ProgressRecord;
import com.reglus.backend.model.entities.rooms.ProgressRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgressRecordRepository extends JpaRepository<ProgressRecord, Long> {
    // Busca registros de progresso por educatorId e studentId
    List<ProgressRecord> findByEducatorEducatorIdAndStudentStudentId(Long educatorId, Long studentId);
}