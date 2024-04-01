package es.gualapop.backend.controller.api;

import com.fasterxml.jackson.annotation.JsonView;
import es.gualapop.backend.model.Report;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ReportRepository;
import es.gualapop.backend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "Get new reports for a user")
    @GetMapping("/{userID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New reports retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> newReports(HttpServletRequest request, @PathVariable("userID") Long userID) {
        //Obtener el nombre del usuario al que se va a reportar
        Optional<User> userReportedName = userRepository.findByUserID(userID);
        if(userReportedName.isPresent()) {
            // Obtener la fecha actual
            LocalDate fechaActual = LocalDate.now();
            // Formatear la fecha en el formato deseado
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = fechaActual.format(formatter);

            String respuesta = "\"date: \"" + fechaFormateada + "\"reported user: \"" + userReportedName.get().getUsername();

            return ResponseEntity.ok().body(respuesta);
        } else {
            return ResponseEntity.notFound().build();
        }

    }


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
        return ResponseEntity.ok().body(reports);
    }

    @JsonView(Report.Detailed.class)
    @Operation(summary = "Manage report by ID")
    @GetMapping("/manage/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    public ResponseEntity<?> manageReport(@PathVariable("id") Long idReport) {
        Optional<Report> optionalReport = reportRepository.findById(idReport);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            return ResponseEntity.ok().body(report);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
