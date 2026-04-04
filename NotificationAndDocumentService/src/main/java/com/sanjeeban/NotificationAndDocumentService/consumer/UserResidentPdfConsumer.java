package com.sanjeeban.NotificationAndDocumentService.consumer;


import com.sanjeeban.NotificationAndDocumentService.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserResidentPdfConsumer {

    @Autowired
    PdfService pdfService;

    @KafkaListener(topics="pdf")
    public void handlePdfGenerationForResidentUser(String msg){
        System.out.println("The kafka msg is : "+String.valueOf(msg));
        pdfService.generateResidentPdf(msg);
    }

}
