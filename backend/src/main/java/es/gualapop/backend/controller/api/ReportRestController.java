package es.gualapop.backend.controller.api;

import com.fasterxml.jackson.annotation.JsonView;
import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.Report;
import es.gualapop.backend.model.Review;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ReportRepository;
import es.gualapop.backend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;


    @JsonView(Report.Detailed.class)
    @Operation(summary = "Add new report")
    @PostMapping("/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Report created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Report> addNewReport(HttpServletRequest request,
                                               @RequestParam("userReported") String userReportedName,
                                               @RequestParam("description") String description,
                                               @RequestParam("category") String category) {
        //Create new report and set attributes
        Report report = new Report();
        Optional<User> userReported = userRepository.findUserByUsername(userReportedName);
        if(userReported.isPresent()) {
            LocalDate fechaActual = LocalDate.now();
            // Format the date to the desired one
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = fechaActual.format(formatter);
            report.setCreationDate(fechaFormateada);
            report.setUserReported(userReported.get().getUserID());
            report.setOwner((long) 2);
            report.setDescription(description);
            switch (category) {
                case "Nombre inapropiado":
                    report.setTitle("Nombre inapropiado");
                    break;
                case "Producto fraudulento":
                    report.setTitle("Producto fraudulento");
                    break;
                case "Cuenta fraudulenta":
                    report.setTitle("Cuenta fraudulenta");
                    break;
                default:
                    break;
            }
            reportRepository.save(report);

            return ResponseEntity.ok().body(report);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @JsonView(Report.Detailed.class)
    @Operation(summary = "Get all reports")
    @GetMapping("/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reports retrieved successfully")
    })
    public ResponseEntity<List<Report>> getReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.isEmpty()? ResponseEntity.notFound().build() : ResponseEntity.ok().body(reports);
    }

    @JsonView(Report.Detailed.class)
    @Operation(summary = "Manage report by ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    public ResponseEntity<Report> manageReport(@PathVariable("id") Long idReport) {
        Optional<Report> optionalReport = reportRepository.findById(idReport);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            return ResponseEntity.ok().body(report);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete Report by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful Report delete",
                    content = {@Content(
                            mediaType = "application/json"
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Report not found",
                    content = @Content
            )
    })
    @JsonView(Report.Detailed.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Long id) {

        Optional<Report> optionalReport = reportRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String firstRole = getFirstRoleFromAuthentication(authentication);
        if (optionalReport.isPresent()) {
            if ("ROLE_ADMIN".equals(firstRole)) {
                reportRepository.deleteById(id);
                return ResponseEntity.ok().body("Report deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    private String getFirstRoleFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            if (!authentication.getAuthorities().isEmpty()) {
                GrantedAuthority firstAuthority = authentication.getAuthorities().iterator().next();
                return firstAuthority.getAuthority();
            }
        }
        return null;
    }

}
