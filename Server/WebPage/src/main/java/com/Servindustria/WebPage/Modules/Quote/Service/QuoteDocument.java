package com.Servindustria.WebPage.Modules.Quote.Service;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Modules.Client.Model.CompanyClient;
import com.Servindustria.WebPage.Modules.Client.Model.NaturalClient;
import com.Servindustria.WebPage.Modules.Client.Repository.ClientRepository;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.Quote.Repository.QuoteRepository;
import com.Servindustria.WebPage.Modules.QuoteDetail.Model.QuoteDetail;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteDocument {

        private final ClientRepository clientRepository;
        private final QuoteRepository quoteDetailRepository;
        private final QuoteRepository quoteRepository;

    public void generateQuoteWord(Long quoteId, OutputStream outputStream) throws IOException {
        Quote quote = quoteRepository.findById(quoteId)
            .orElseThrow(() -> new ResourceNotFoundException("Cotización con ID " + quoteId + " no encontrada"));

        CompanyClient companyClient = clientRepository.findCompanyClientById(quote.getClient().getId()).orElse(null);
        NaturalClient naturalClient = clientRepository.findNaturalClientById(quote.getClient().getId()).orElse(null);
        if (naturalClient == null && companyClient == null) {
            throw new ResourceNotFoundException("Cliente asociado a la cotización con ID " + quoteId + " no encontrado");
        }

        List<QuoteDetail> details = quoteDetailRepository.findByQuoteI(quoteId);

        XWPFDocument doc = new XWPFDocument();

        // Logo
        XWPFParagraph logoParagraph = doc.createParagraph();
        logoParagraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun logoRun = logoParagraph.createRun();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("static/images/image.png")) {
            if (is != null) {
                logoRun.addPicture(is, Document.PICTURE_TYPE_PNG, "logo.png", Units.toEMU(380), Units.toEMU(70));
            }            
        } catch (Exception e) {
            System.err.println("Error al obtener la imagen del documento: " + e.getMessage());
        }

        // Empresa y RUC
        XWPFParagraph empresaP = doc.createParagraph();
        empresaP.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun empresaRun = empresaP.createRun();
        empresaRun.setText("SERVINDUSTRIA EXTINTORES Y FUMIGACION E.I.R.L");
        empresaRun.setBold(true);
        empresaRun.addBreak();
        empresaRun.setText("RUC: 20419021670");
        empresaRun.addBreak();
        empresaRun.setText("Fecha: " + quote.getDateEmi());

        // Cliente y asunto
        XWPFParagraph clienteP = doc.createParagraph();
        XWPFRun clienteRun = clienteP.createRun();
        clienteRun.addBreak();
        clienteRun.setText("Señores:");
        clienteRun.addBreak();
        clienteRun.setText(naturalClient != null
            ? naturalClient.getName() + " " + naturalClient.getLastName()
            : companyClient.getNameReasonSoc());
        clienteRun.addBreak();
        clienteRun.setText("At.: " + (naturalClient != null
            ? naturalClient.getName() + " " + naturalClient.getLastName()
            : companyClient.getNameReasonSoc()));
        clienteRun.addBreak();
        clienteRun.addBreak();
        clienteRun.setText("Ref.: Cotización de extintores y otros");
        clienteRun.addBreak();
        clienteRun.addBreak();
        clienteRun.setText("De mi consideración:");
        clienteRun.addBreak();
        clienteRun.setText("Por medio de la presente le hacemos llegar la siguiente cotización de acuerdo a vuestra solicitud:");
        clienteRun.addBreak();

        // Tabla de productos
        XWPFTable table = doc.createTable();
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Item");
        header.addNewTableCell().setText("Cant.");
        header.addNewTableCell().setText("Descripción");
        header.addNewTableCell().setText("P.Unit.");
        header.addNewTableCell().setText("Total");

        int item = 1;
        for (QuoteDetail detail : details) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(String.valueOf(item++));
            row.getCell(1).setText(String.valueOf(detail.getAmount()));
            row.getCell(2).setText(
                detail.getProduct() != null
                    ? detail.getProduct().getDescription()
                    : (detail.getService() != null ? detail.getService().getDescription() : "")
            );
            row.getCell(3).setText(detail.getUnitaryPrice().toString());
            row.getCell(4).setText(detail.getSubtotal().toString());
        }

        // Total
        XWPFParagraph totalP = doc.createParagraph();
        totalP.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun totalRun = totalP.createRun();
        totalRun.setBold(true);
        totalRun.setText("Total S/.......... " + quote.getTotal());
        totalRun.addBreak();
        totalRun.setText("INC. I.G.V.");

        // Características técnicas
        XWPFParagraph caracP = doc.createParagraph();
        XWPFRun caracRun = caracP.createRun();
        caracRun.setBold(true);
        caracRun.setText("CON LAS SIGUIENTES CARACTERISTICAS TECNICAS:");
        caracRun.addBreak();
        caracRun.setBold(false);
        caracRun.setText("- Cilindro de acero sin costura\n"
                + "- Acabado en pintura horneada color rojo brillante, resistente a la corrosión y al intemperie.\n"
                + "- Manómetro, indicador de presión\n"
                + "- Soporte para su instalación\n"
                + "- Válvula de latón forjado con palanca de soplete, de acero y dispositivo de seguridad\n"
                + "- Manguera de caucho sintético de alta presión de fácil operación\n"
                + "- Agente extintor polvo químico seco para fuegos de clase abc\n"
                + "- Cumple la NTP 350.026");

        // Servicio post venta
        XWPFParagraph servP = doc.createParagraph();
        XWPFRun servRun = servP.createRun();
        servRun.setBold(true);
        servRun.setText("INCLUYE EL SERVICIO VENTA:");
        servRun.addBreak();
        servRun.setBold(false);
        servRun.setText("- Etiquetas de capacidad, inspección, vencimiento, prueba hidrostática y soporte\n"
                + "- Tarjeta de inspección para el control y mantenimiento de extintores\n"
                + "- Garantía del año\n"
                + "- Certificado de operatividad de extintores para presentar a INDECI\n"
                + "- RPN: 2092-2006");

        // Condiciones de pago
        XWPFParagraph condP = doc.createParagraph();
        condP.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun condRun = condP.createRun();
        condRun.setBold(true);
        condRun.setText("CONDICIONES DE PAGO:");
        condRun.addBreak();
        condRun.setBold(false);
        condRun.setText("FORMA DE PAGO: contado c/e\n"
                + "TIEMPO DE ENTREGA: inmediata\n"
                + "VALIDEZ DE OFERTA: 30 días\n"
                + "IMPUESTO: incluye\n");
        condRun.addBreak();
        condRun.setText("Esperando vernos favorecidos por su amable atención, quedamos de Ud.");
        condRun.addBreak();
        condRun.setText("Muy atentamente,\nRichard Rojas Peña\nAdministrador\nwww.servindustria.com\nTelf: 533-0219 / 533-2505\nN: 328*5756 y Cel: 99828-5756");

        doc.write(outputStream);
        doc.close();
    }


    //QUOTE PDF
    public void generateQuotePdf(Long quoteId, OutputStream outputStream) throws IOException {
    Quote quote = quoteRepository.findById(quoteId)
        .orElseThrow(() -> new ResourceNotFoundException("Cotización con ID " + quoteId + " no encontrada"));

    CompanyClient companyClient = clientRepository.findCompanyClientById(quote.getClient().getId()).orElse(null);
    NaturalClient naturalClient = clientRepository.findNaturalClientById(quote.getClient().getId()).orElse(null);
    if (naturalClient == null && companyClient == null) {
        throw new ResourceNotFoundException("Cliente asociado a la cotización con ID " + quoteId + " no encontrado");
    }

    List<QuoteDetail> details = quoteDetailRepository.findByQuoteI(quoteId);

    PdfWriter writer = new PdfWriter(outputStream);
    PdfDocument pdf = new PdfDocument(writer);
    com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);

    // Logo
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("static/images/image.png")) {
        if (is != null) {
            byte[] imgBytes = is.readAllBytes();
            Image logo = new Image(ImageDataFactory.create(imgBytes));
            logo.setWidth(380);
            logo.setHeight(70);
            document.add(logo);
        }
    }
    
    // Empresa y RUC
    document.add(new Paragraph("SERVINDUSTRIA EXTINTORES Y FUMIGACION E.I.R.L").setBold());
    document.add(new Paragraph("RUC: 20419021670"));
    document.add(new Paragraph("Fecha: " + quote.getDateEmi()));

    // Cliente y asunto
    document.add(new Paragraph("\nSeñores:"));
    document.add(new Paragraph(naturalClient != null
        ? naturalClient.getName() + " " + naturalClient.getLastName()
        : companyClient.getNameReasonSoc()));
    document.add(new Paragraph("At.: " + (naturalClient != null
        ? naturalClient.getName() + " " + naturalClient.getLastName()
        : companyClient.getNameReasonSoc())));
    document.add(new Paragraph("\nRef.: Cotización de extintores y otros"));
    document.add(new Paragraph("\nDe mi consideración:"));
    document.add(new Paragraph("Por medio de la presente le hacemos llegar la siguiente cotización de acuerdo a vuestra solicitud:"));

    // Tabla de productos
    float[] columnWidths = {30, 40, 220, 60, 60};
    com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnWidths);
    table.addHeaderCell("Item");
    table.addHeaderCell("Cant.");
    table.addHeaderCell("Descripción");
    table.addHeaderCell("P.Unit.");
    table.addHeaderCell("Total");

    int item = 1;
    for (QuoteDetail detail : details) {
        table.addCell(String.valueOf(item++));
        table.addCell(String.valueOf(detail.getAmount()));
        table.addCell(detail.getProduct() != null
            ? detail.getProduct().getDescription()
            : (detail.getService() != null ? detail.getService().getDescription() : ""));
        table.addCell(detail.getUnitaryPrice().toString());
        table.addCell(detail.getSubtotal().toString());
    }
    document.add(table);

    // Total
    document.add(new Paragraph("Total S/.......... " + quote.getTotal()).setBold().setTextAlignment(TextAlignment.RIGHT));
    document.add(new Paragraph("INC. I.G.V.").setTextAlignment(TextAlignment.RIGHT));

    // Características técnicas
    document.add(new Paragraph("\nCON LAS SIGUIENTES CARACTERISTICAS TECNICAS:").setBold());
    document.add(new Paragraph(
        "- Cilindro de acero sin costura\n"
        + "- Acabado en pintura horneada color rojo brillante, resistente a la corrosión y al intemperie.\n"
        + "- Manómetro, indicador de presión\n"
        + "- Soporte para su instalación\n"
        + "- Válvula de latón forjado con palanca de soplete, de acero y dispositivo de seguridad\n"
        + "- Manguera de caucho sintético de alta presión de fácil operación\n"
        + "- Agente extintor polvo químico seco para fuegos de clase abc\n"
        + "- Cumple la NTP 350.026"
    ));

    // Servicio post venta
    document.add(new Paragraph("\nINCLUYE EL SERVICIO VENTA:").setBold());
    document.add(new Paragraph(
        "- Etiquetas de capacidad, inspección, vencimiento, prueba hidrostática y soporte\n"
        + "- Tarjeta de inspección para el control y mantenimiento de extintores\n"
        + "- Garantía del año\n"
        + "- Certificado de operatividad de extintores para presentar a INDECI\n"
        + "- RPN: 2092-2006"
    ));

    // Condiciones de pago
    document.add(new Paragraph("\nCONDICIONES DE PAGO:").setBold());
    document.add(new Paragraph(
        "FORMA DE PAGO: contado c/e\n"
        + "TIEMPO DE ENTREGA: inmediata\n"
        + "VALIDEZ DE OFERTA: 30 días\n"
        + "IMPUESTO: incluye\n"
    ));
    document.add(new Paragraph("Esperando vernos favorecidos por su amable atención, quedamos de Ud."));
    document.add(new Paragraph("Muy atentamente,\nRichard Rojas Peña\nAdministrador\nwww.servindustria.com\nTelf: 533-0219 / 533-2505\nN: 328*5756 y Cel: 99828-5756"));

    document.close();
}
}