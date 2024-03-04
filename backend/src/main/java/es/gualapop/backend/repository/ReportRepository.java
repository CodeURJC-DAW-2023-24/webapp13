package es.gualapop.backend.repository;


import es.gualapop.backend.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findById(Long idReport);

    void deleteById(Long id);

}
