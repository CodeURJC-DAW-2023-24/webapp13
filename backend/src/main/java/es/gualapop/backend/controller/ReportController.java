package es.gualapop.backend.controller;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.Report;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ReportRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class ReportController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;
    @GetMapping("/report/{userID}")
    public String newProducts(Model model, HttpServletRequest request, @PathVariable("userID") Long userID){

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
        System.out.println("Valor de userReportedName: " + userReportedName);
        Report report = new Report();
        String username = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        User userReported = userRepository.findUserByUsername(userReportedName).orElseThrow();
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

        //save con el repository
        reportRepository.save(report);

        return "redirect:/profile";
    }
}
