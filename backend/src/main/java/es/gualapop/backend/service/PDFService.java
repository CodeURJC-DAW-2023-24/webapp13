package es.gualapop.backend.service;

import es.gualapop.backend.model.Product;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

@Service
public class PDFService {

    public byte[] generatePDF(Product product) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();
        document.add(new Paragraph("Detalles del Producto"));
        document.add(new Paragraph("ID del producto: " + product.getId()));
        document.add(new Paragraph("Título: " + product.getTitle()));
        document.add(new Paragraph("Descripción: " + product.getDescription()));
        document.add(new Paragraph("Precio: $" + product.getPrice()));
        document.add(new Paragraph("Dirección: " + product.getAddress()));

        document.close();

        return baos.toByteArray();
    }
}
