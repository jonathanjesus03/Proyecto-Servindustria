package com.Servindustria.WebPage.Modules.Quote.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Modules.Client.Model.CompanyClient;
import com.Servindustria.WebPage.Modules.Client.Model.NaturalClient;
import com.Servindustria.WebPage.Modules.Client.Repository.ClientRepository;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.Quote.Repository.QuoteRepository;
import com.Servindustria.WebPage.Modules.QuoteDetail.Model.QuoteDetail;
import com.Servindustria.WebPage.Modules.QuoteDetail.Repository.QuoteDetailRepository;

import lombok.RequiredArgsConstructor;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;


@Service
@RequiredArgsConstructor
public class QuoteEmail {
       // Inyección de dependencias de los repositorios (Spring Data JPA)
    private final QuoteRepository quoteRepository;
    private final QuoteDetailRepository quoteDetailRepository;
    private final ClientRepository clientRepository;
    private static final Logger logger = LoggerFactory.getLogger(QuoteEmail.class); 

    @Autowired
    private EmailProperties emailProperties;

    // Método principal para enviar el correo de cotización
    public void SendEmailQuote(Long quoteId) {
        // Busca la cotización por ID, lanza excepción si no existe
        Quote quote = quoteRepository.findById(quoteId)
            .orElseThrow(() -> new ResourceNotFoundException("Cotización con ID " + quoteId + " no encontrada"));

        // Busca el cliente asociado, puede ser empresa o persona natural
        CompanyClient companyClient = clientRepository.findCompanyClientById(quote.getClient().getId()).orElse(null);
        NaturalClient naturalClient = clientRepository.findNaturalClientById(quote.getClient().getId()).orElse(null);

        // Obtiene datos del cliente
        String nombreCliente = MoreObjects.firstNonNull(
            naturalClient != null ? naturalClient.getName() + " " + naturalClient.getLastName() : null,
            companyClient != null ? companyClient.getNameReasonSoc() : null
        );
        
        if (Strings.isNullOrEmpty(nombreCliente)) {
            throw new ResourceNotFoundException("Cliente asociado a la cotización con ID " + quoteId + " no encontrado");
        }       
        
        String emailDestino = quote.getClient().getEmail();
        String ubicacion = companyClient != null ? companyClient.getAddress() : "no definido";

        // Obtiene los detalles de la cotización
        List<QuoteDetail> detalles = quoteDetailRepository.findByQuoteId(quoteId);

        // Usando Guava (com.google.common.base.Joiner) para unir filas de la tabla en un solo String
        List<String> filas = new ArrayList<>();
        filas.add("<tr style='background-color:#f1f1f1'><th>Producto/Servicio</th><th>Cantidad</th><th>P. Unitario</th><th>Subtotal</th></tr>");
        for (QuoteDetail det : detalles) {
            String producto = det.getProduct() != null ? det.getProduct().getDescription() : (det.getService() != null ? det.getService().getDescription() : "Servicio");
            filas.add("<tr>"
                + "<td>" + producto + "</td>"
                + "<td>" + det.getAmount() + "</td>"
                + "<td>S/ " + String.format("%.2f", det.getUnitaryPrice()) + "</td>"
                + "<td>S/ " + String.format("%.2f", det.getSubtotal()) + "</td>"
                + "</tr>");
        }
        // Une todas las filas en un solo String HTML
        String detallesTable = Joiner.on("").join(filas);

        // Formatea la fecha usando java.time.format.DateTimeFormatter
        String fecha = quote.getDateEmi().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        // Construye el mensaje HTML del correo
        String htmlMsg = """
            <div style="font-family: 'Segoe UI', Arial, sans-serif; background-color: #f4f6f8; padding: 30px; color: #333">
            <div style="max-width: 700px; margin: auto; background-color: #fff; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 12px rgba(0,0,0,0.1)">
                <div style="background-color: #FF6074; padding: 20px; text-align: center">
                <img src='cid:logo.png' style="height: 40px; display: inline-block; vertical-align: middle; margin-right: 10px" />
                <h2 style="display: inline-block; color: #fff; margin: 0; font-size: 22px">Cotización Recibida</h2>
                </div>
                <div style="padding: 30px">
                <p>Hola <strong>%s</strong>,</p>
                <p>Gracias por contactarte con <strong>Servindustria</strong>. Aquí tienes los detalles de tu solicitud:</p>
                
                <table style="width: 100%%; border-collapse: collapse; margin: 20px 0; font-size: 14px">
                    <tr><td style="padding: 6px 0"><strong>Nombre:</strong></td><td>%s</td></tr>
                    <tr><td style="padding: 6px 0"><strong>Correo:</strong></td><td><a href='mailto:%s'>%s</a></td></tr>
                    <tr><td style="padding: 6px 0"><strong>Ubicación:</strong></td><td>%s</td></tr>
                    <tr><td style="padding: 6px 0"><strong>Fecha:</strong></td><td>%s</td></tr>
                </table>

                <h3 style="margin-top: 30px; padding-bottom: 10px; border-bottom: 2px solid #FF6074; font-size: 18px">Detalle de la Cotización</h3>
                <table style="width: 100%%; border-collapse: collapse; margin-top: 10px; font-size: 14px">
                    %s
                    <tr style="background-color: #f9f9f9">
                    <td style="padding: 8px; text-align: right; font-weight: bold">Total General:</td>
                    <td style="padding: 8px; font-weight: bold; color: #FF6074">S/ %.2f</td>
                    </tr>
                </table>

                <p style="margin-top: 30px">Uno de nuestros asesores se comunicará contigo a la brevedad.</p>
                <p style="font-size: 12px; color: #999; margin-top: 30px">Este mensaje fue generado automáticamente el %s</p>
                </div>

                <div style="background-color: #f0f0f0; text-align: center; padding: 15px; font-size: 13px; color: #666">
                Para más información, visita <a href="https://servindustria.com" style="color: #FF6074; text-decoration: none;">servindustria.com</a>
                </div>
            </div>
            </div>
        """.formatted(nombreCliente, nombreCliente, emailDestino, emailDestino, ubicacion, fecha, detallesTable, quote.getTotal(), fecha);

        // Envía el correo de forma asíncrona
        sendEmail(emailDestino, nombreCliente, quote.getId().toString(), htmlMsg, quote.getCod());
        logger.info("Correo de cotización enviado a {}", emailDestino);
    }
            
    @Async
    public void sendEmail(String emailDestino, String nombreCliente, String quoteId, String htmlMsg, String quoteCod){
           try {
            // Usa Apache Commons Email (org.apache.commons.mail.HtmlEmail) para enviar correos HTML
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.gmail.com"); // Servidor SMTP de Gmail
            email.setSmtpPort(587); // Puerto TLS
            email.setAuthentication(emailProperties.getUsername(), emailProperties.getPassword());
            email.setStartTLSEnabled(true); // Habilita TLS

            email.setFrom(emailProperties.getUsername()); // Remitente
            email.addTo(emailDestino); // Destinatario
            email.setSubject("Cotización #" + quoteId + " - Servindustria"); // Asunto
            email.setHtmlMsg(htmlMsg); // Mensaje HTML
            email.setTextMsg("Hola " + nombreCliente + ", gracias por tu solicitud."); // Mensaje de texto plano
        
            File wordAttachment = new File("C:/Users/Samsung/Desktop/Proyecto-Servindustria/Documents/quote-" + quoteCod + ".docx");
            if (wordAttachment.exists()) {
                email.attach(wordAttachment.toURI().toURL(), "Cotizacion.docx", "Cotización generada automáticamente");
            }
            File pdfAttachment = new File("C:/Users/Samsung/Desktop/Proyecto-Servindustria/Documents/quote-" + quoteCod + ".pdf");
            if (pdfAttachment.exists()) {
                email.attach(pdfAttachment.toURI().toURL(), "Cotizacion.pdf", "Cotización generada automáticamente");
            }

            // Adjunta el logo como recurso embebido en el correo
            File logo = new File("Server/WebPage/src/main/resources/static/images/logo.png");
            String cid = email.embed(logo, "logo.png");

            new Thread(() -> {
            try {
                logger.info("Intentando enviar correo de cotización a {}", emailDestino);

                email.send();
                logger.info("Correo enviado correctamente a " + emailDestino);
            } catch (EmailException e) {
                e.printStackTrace();
                logger.error("Ocurrio un problema con el mensaje -> {}", emailDestino);
            }
        }).start();
        } catch (EmailException | java.net.MalformedURLException e) {
            e.printStackTrace();
            logger.error("Error al enviar correo a {}: {}", emailDestino, e.getMessage(), e);

        }
    }
    
}


