package com.rsai.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rsai.model.Need;
import java.util.List;

public interface NeedRepo extends JpaRepository<Need, Long> {
    List<Need> findByStatus(String status);
    List<Need> findByZone(String zone);
}