package es.gualapop.backend.repository;


import es.gualapop.backend.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;


public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findById(Long idReport);
    List<Report> findByOwner(Long id);
    List<Report> findByUserReported(Long id);

    void deleteById(Long id);

}
