package es.gualapop.backend.controller;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.Report;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ReportRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class ReportController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReportRepository reportRepository;
    @GetMapping("/report/{userID}")
    public String newReports(Model model, HttpServletRequest request, @PathVariable("userID") Long userID){

        //Obtener el nombre del usuario de la sesion
        String username = request.getUserPrincipal().getName();

        //Obtener el nombre del usuario al que se va a reportar
        String userReportedName = userRepository.findByUserID(userID).get().getName();

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Formatear la fecha en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatter);

        // Agregar atributos al modelo
        model.addAttribute("fechaActual", fechaFormateada);
        model.addAttribute("user", username);
        model.addAttribute("userReported", userReportedName);

        return "reportForm";
    }

    @PostMapping("/addNewReport/{userReported}")
    public String addNewProduct(HttpServletRequest request, Model model,
                                @PathVariable("userReported") String userReportedName,
                                @RequestParam("Description") String description,
                                @RequestParam("Category") String category
                                ) throws IOException {
        //Create new product and set attributes
        Report report = new Report();
        String username = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        User userReported = userRepository.findUserByUsername(userReportedName).orElseThrow();
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

        return "redirect:/profile";
    }

    @GetMapping("/getReports")
    public String getReports(Model model) {

        List<Report> reports = reportRepository.findAll();
        model.addAttribute("reports", reports);

        return "adminPanel";
    }


    @GetMapping("/gestionReport/{report}")
    public String gestionReport(Model model, @PathVariable("report") Long idReport) {

        Optional<Report> optionalReport = reportRepository.findById(idReport);

        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            model.addAttribute("report", report);
        } else {
            return "error";
        }
        return "reportPanel";
    }


    @GetMapping("/deleteUser/{id}")
    public String deleteUser(Model model,@PathVariable("id") Long idReport) {


        Report report = reportRepository.findById(idReport).orElseThrow();
        User user = userRepository.findByUserID(report.getUserReported()).orElseThrow();
        
        List<Product> userProducts = productRepository.findByOwner(user.getUserID());
        for (Product eachProduct : userProducts) {
            productRepository.deleteById(eachProduct.getId());
        }

        List<Report> userReported = reportRepository.findByUserReported(user.getUserID());
        for (Report eachReport : userReported) {
            reportRepository.deleteById(eachReport.getId());
        }
        List<Report> userReports = reportRepository.findByOwner(user.getUserID());
        for (Report eachReport : userReports) {
            reportRepository.deleteById(eachReport.getId());
        }

        userRepository.deleteById(user.getUserID());

        return "redirect:/getReports";
    }

    @GetMapping("/conserveUser/{id}")
    public String preserveUser(Model model,@PathVariable("id") Long idReport) {

        Optional<Report> report = reportRepository.findById(idReport);
        reportRepository.deleteById(report.get().getId());


        return "redirect:/getReports";
    }




}
