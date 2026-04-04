package com.sanjeeban.NotificationAndDocumentService.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.netflix.discovery.converters.Auto;
import com.sanjeeban.NotificationAndDocumentService.dto.ResidentDatasourceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.File;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class PdfService {


    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    private RestTemplate restTemplate;


    public void generateResidentPdf(String msg) {

        try{
            String url = "http://localhost:7001/api/1.0/admin/residentDatasource/"+msg;
            ResidentDatasourceResponse residentDatasourceResponse =
                    restTemplate.getForObject(url,ResidentDatasourceResponse.class);
            generatePdf(residentDatasourceResponse);
            System.out.println(residentDatasourceResponse.toString());
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    public void generatePdf(ResidentDatasourceResponse resident) {

        try {

            String folderPath = uploadDir;
            File folder = new File(folderPath);

            if (!folder.exists()) {
                folder.mkdir();
            }

            String filePath = folderPath + "resident_" + resident.getUserInformationDtoResponse().getUniqueid() + ".pdf";

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("User Information"));
            document.add(new Paragraph("Resident ID: " + resident.getUserInformationDtoResponse().getUniqueid()));
            document.add(new Paragraph("Name: " + resident.getUserInformationDtoResponse().getName()));
            document.add(new Paragraph("Email: " + resident.getUserInformationDtoResponse().getEmail()));
            document.add(new Paragraph("Mobile: " + resident.getUserInformationDtoResponse().getMobile()));

            document.close();

            System.out.println("PDF generated at: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
