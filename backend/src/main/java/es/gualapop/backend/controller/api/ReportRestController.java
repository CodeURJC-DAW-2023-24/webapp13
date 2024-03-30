package es.gualapop.backend.controller.api;

import com.fasterxml.jackson.annotation.JsonView;
import es.gualapop.backend.model.Report;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ReportRepository;
import es.gualapop.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{userID}")
    public ResponseEntity<?> newReports(HttpServletRequest request, @PathVariable("userID") Long userID) {
        //Obtener el nombre del usuario de la sesion
        String username = request.getUserPrincipal().getName();
        //Obtener el nombre del usuario al que se va a reportar
        String userReportedName = userRepository.findByUserID(userID)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getFullName();
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Formatear la fecha en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatter);

        String respuesta = "\"date: \"" + fechaFormateada + "\"user: \"" + username + "\"reported user: \"" + userReportedName;

        return ResponseEntity.ok().body(respuesta);
    }

    @JsonView(Report.Detailed.class)
    @PostMapping("/new")
    public ResponseEntity<Report> addNewReport(HttpServletRequest request,
                                               @RequestParam("userReported") String userReportedName,
                                               @RequestParam("description") String description,
                                               @RequestParam("category") String category) {
        //Create new report and set attributes
        Report report = new Report();
        String username = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User userReported = userRepository.findUserByUsername(userReportedName)
                .orElseThrow(() -> new RuntimeException("User to report not found"));
        LocalDate fechaActual = LocalDate.now();
        // Format the date to the desired one
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatter);
        report.setCreationDate(fechaFormateada);
        report.setUserReported(userReported.getUserID());
        report.setOwner(user.getUserID());
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

        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }
    @JsonView(Report.Detailed.class)
    @GetMapping("/")
    public ResponseEntity<List<Report>> getReports() {
        List<Report> reports = reportRepository.findAll();
        return ResponseEntity.ok().body(reports);
    }

    @JsonView(Report.Detailed.class)
    @GetMapping("/manage/{id}")
    public ResponseEntity<?> manageReport(@PathVariable("id") Long idReport) {
        Optional<Report> optionalReport = reportRepository.findById(idReport);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            return ResponseEntity.ok().body(report);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @JsonView(Report.Detailed.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable("id") Long idReport) {
        reportRepository.deleteById(idReport);
        return ResponseEntity.ok().body("Report deleted successfully");
    }

}
