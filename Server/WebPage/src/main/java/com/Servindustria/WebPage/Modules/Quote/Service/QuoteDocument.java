package com.Servindustria.WebPage.Modules.Quote.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Modules.Client.Model.CompanyClient;
import com.Servindustria.WebPage.Modules.Client.Model.NaturalClient;
import com.Servindustria.WebPage.Modules.Client.Repository.ClientRepository;
import com.Servindustria.WebPage.Modules.Quote.Model.Quote;
import com.Servindustria.WebPage.Modules.Quote.Repository.QuoteRepository;
import com.Servindustria.WebPage.Modules.QuoteDetail.Model.QuoteDetail;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableRowAlign;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteDocument {

    // Repositorios para acceder a datos de clientes y cotizaciones
    private final ClientRepository clientRepository;
    private final QuoteRepository quoteDetailRepository;
    private final QuoteRepository quoteRepository;

    /**
     * Genera un documento Word (.docx) con los datos de la cotización.
     * Usa Apache POI (org.apache.poi.xwpf.usermodel.*) para manipular archivos Word.
     */
    public void generateQuoteWord(Long quoteId, OutputStream outputStream) throws IOException {
        // Busca la cotización por ID
        Quote quote = quoteRepository.findById(quoteId)
            .orElseThrow(() -> new ResourceNotFoundException("Cotización con ID " + quoteId + " no encontrada"));

        // Busca el cliente asociado (puede ser empresa o persona natural)
        CompanyClient companyClient = clientRepository.findCompanyClientById(quote.getClient().getId()).orElse(null);
        NaturalClient naturalClient = clientRepository.findNaturalClientById(quote.getClient().getId()).orElse(null);
        if (naturalClient == null && companyClient == null) {
            throw new ResourceNotFoundException("Cliente asociado a la cotización con ID " + quoteId + " no encontrado");
        }

        // Obtiene los detalles de la cotización
        List<QuoteDetail> details = quoteDetailRepository.findByQuoteI(quoteId);

        // Crea un documento Word en blanco
        XWPFDocument doc = new XWPFDocument();

        // --- Agrega el logo usando Apache POI ---
        XWPFParagraph logoParagraph = doc.createParagraph();
        logoParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun logoRun = logoParagraph.createRun();
        logoRun.setFontFamily("Times New Roman");
        logoRun.setFontSize(12);
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("static/images/logoFactura.png")) {
            if (is != null) {
                logoRun.addPicture(is, Document.PICTURE_TYPE_PNG, "logo.png", Units.toEMU(380), Units.toEMU(90));
            }            
        } catch (Exception e) {
            System.err.println("Error al obtener la imagen del documento: " + e.getMessage());
        }

        // --- Agrega información de la empresa ---
        XWPFParagraph empresaP = doc.createParagraph();
        empresaP.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun empresaRun = empresaP.createRun();
        empresaRun.setFontFamily("Times New Roman");
        empresaRun.setFontSize(12);
        empresaRun.setBold(true);
        empresaRun.addBreak();
        empresaRun.setText("RUC / DNI: 20419021670");
        empresaRun.addBreak();
        XWPFParagraph fechaP = doc.createParagraph();
        fechaP.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun fechaRun = fechaP.createRun();
        fechaRun.setFontFamily("Times New Roman");
        fechaRun.setFontSize(12);
        fechaRun.setBold(true);
        fechaRun.setText("Los olivos " + quote.getDateEmi().format(java.time.format.DateTimeFormatter.ofPattern("dd 'de' MM 'del' yyyy - HH:mm")));

        // --- Agrega información del cliente y asunto ---
        XWPFParagraph clienteP = doc.createParagraph();
        XWPFRun clienteRun = clienteP.createRun();
        clienteRun.setFontFamily("Times New Roman");
        clienteRun.setFontSize(12);
        clienteRun.setBold(true);
        clienteRun.addBreak();
        clienteRun.setText("Señores:");
        clienteRun.addBreak();
        clienteRun.setText(naturalClient != null
            ? naturalClient.getName() + " " + naturalClient.getLastName()
            : companyClient.getNameReasonSoc());
        clienteRun.addBreak();
        clienteRun.setText("At.: " + "Priscilla Arboleda Ramirez");
        clienteRun.addBreak();

        XWPFParagraph refP = doc.createParagraph();
        refP.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun refRun = refP.createRun();
        refRun.setFontFamily("Times New Roman");
        refRun.setFontSize(12);
        refRun.setBold(true);
        refRun.addBreak();
        refRun.setText("Ref.: Cotización de extintores y otros");
        refRun.addBreak();
        
        XWPFParagraph considerParagraph = doc.createParagraph();
        considerParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun considerRun = considerParagraph.createRun();
        considerRun.setFontFamily("Times New Roman");
        considerRun.setFontSize(12);
        considerRun.addBreak();
        considerRun.setText("De mi consideración:");
        considerRun.addBreak();
        considerRun.addBreak();
        considerRun.setText("Por medio de la presente le hacemos llegar la siguiente cotización de acuerdo a vuestra solicitud:");
        considerRun.addBreak();

        // --- Crea una tabla de productos usando Apache POI ---
        XWPFTable table = doc.createTable();
        table.setTableAlignment(TableRowAlign.CENTER);
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Item");
        header.addNewTableCell().setText("Cant.");
        header.addNewTableCell().setText("Descripción");
        header.addNewTableCell().setText("P.Unit.");
        header.addNewTableCell().setText("Total");

        // Aplica fuente a la cabecera de la tabla
        for (int i = 0; i < header.getTableCells().size(); i++) {
            XWPFParagraph p = header.getCell(i).getParagraphs().get(0);
            XWPFRun r = p.getRuns().isEmpty() ? p.createRun() : p.getRuns().get(0);
            r.setFontFamily("Times New Roman");
            r.setFontSize(12);
            r.setBold(true);
        }

        int item = 1;
        for (QuoteDetail detail : details) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(String.valueOf(item++));
            row.getCell(1).setText(String.valueOf(detail.getAmount()));
            row.getCell(2).setText(
                detail.getProduct() != null
                    ? detail.getProduct().getType()
                    : (detail.getService() != null ? detail.getService().getType() : "")
            );
            row.getCell(3).setText("S/." + detail.getUnitaryPrice().toString());
            row.getCell(4).setText("S/." + detail.getSubtotal().toString());

            // Aplica fuente a cada celda de la fila
            for (int i = 0; i < row.getTableCells().size(); i++) {
                XWPFParagraph p = row.getCell(i).getParagraphs().get(0);
                XWPFRun r = p.getRuns().isEmpty() ? p.createRun() : p.getRuns().get(0);
                r.setFontFamily("Times New Roman");
                r.setFontSize(12);
            }
        }
        table.setWidth("10773");

        // --- Total de la cotización ---
        XWPFParagraph totalP = doc.createParagraph();
        totalP.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun totalRun = totalP.createRun();
        totalRun.setFontFamily("Times New Roman");
        totalRun.setFontSize(12);
        totalRun.setBold(true);
        totalRun.addBreak();
        totalRun.setText("Total S/.......... " + quote.getTotal());
        totalRun.addBreak();
        totalRun.addBreak();
        totalRun.setText("INC. I.G.V.");

        // --- Características técnicas y condiciones de pago ---
        List<String> characters = new ArrayList<>();
        for (QuoteDetail detail : details) {
            if (detail.getProduct() != null) {
                characters.add("- Producto: " + detail.getProduct().getDescription() + "- Cantidad: " + detail.getAmount());
            }
            if (detail.getService() != null) {
                characters.add("- Servicio: " + detail.getService().getDescription() + "- Cantidad: " + detail.getAmount());
            }
            if (detail.getAfterSales() != null) {
                characters.add("- Post Venta: " + detail.getAfterSales().getServiceType() + "- Cantidad: " + detail.getAmount());
            }
            characters.add(""); // Línea en blanco para separar
        }

        List<String> SellServices = ImmutableList.of(
            "-Etiquetas  de capacidad, instrucción, vencimiento, prueba hidrostática y soporte",
            "-Tarjetas de inspección para el control y mantenimiento de extintores ",
            "-Garantía :01 año ",
            "-Certificado de operatividad de extintores para presentar a INDECI",
            "-RPIN: 2092-2006"
        );


        List<String> conditions = ImmutableList.of(
            "FORMA DE PAGO: contado c/e",
            "TIEMPO DE ENTREGA: inmediata",
            "VALIDEZ DE OFERTA: 30 días",
            "IMPUESTO: incluye"
        );

        // --- Secciones finales del documento ---
        XWPFParagraph caracP = doc.createParagraph();
        XWPFRun caracTitleRun = caracP.createRun();
        caracTitleRun.setFontFamily("Times New Roman");
        caracTitleRun.setFontSize(12);
        caracTitleRun.setBold(true);
        caracTitleRun.setUnderline(UnderlinePatterns.SINGLE);
        caracTitleRun.setText("CON LAS SIGUIENTES CARACTERISTICAS TÉCNICAS:");
        caracTitleRun.addBreak();


        XWPFRun caracRun = caracP.createRun();
        caracRun.setFontFamily("Times New Roman");
        caracRun.setFontSize(12);
        for(String linea : characters) {
            caracRun.setText(linea);
            caracRun.addBreak();
        }
        
        XWPFParagraph sellService = doc.createParagraph();

        XWPFRun sellServiceTitleRun = sellService.createRun();
        sellServiceTitleRun.setFontFamily("Times New Roman");
        sellServiceTitleRun.setFontSize(12);
        sellServiceTitleRun.setBold(true);
        sellServiceTitleRun.setUnderline(UnderlinePatterns.SINGLE);
        sellServiceTitleRun.setText("INCLUYE EL SERVICIO VENTA:");
        sellServiceTitleRun.addBreak();

        XWPFRun sellServiceRun = sellService.createRun();
        sellServiceRun.setFontFamily("Times New Roman");
        sellServiceRun.setFontSize(12);
        for(String lineaSell : SellServices) {
            sellServiceRun.setText(lineaSell);
            sellServiceRun.addBreak();
        }

        XWPFParagraph condP = doc.createParagraph();
        XWPFRun condTitleRun = condP.createRun();
        condTitleRun.setFontFamily("Times New Roman");
        condTitleRun.setFontSize(12);
        condTitleRun.setBold(true);
        condTitleRun.setUnderline(UnderlinePatterns.SINGLE);
        condTitleRun.setText("CONDICIONES DE PAGO:");
        condTitleRun.addBreak();

        XWPFRun condRun = condP.createRun();
        condRun.setFontFamily("Times New Roman");
        condRun.setFontSize(12);
        for(String lineaCondi : conditions) {
            condRun.setText(lineaCondi);
            condRun.addBreak();
        } 
        condRun.addBreak();
        condRun.addBreak();

        XWPFParagraph attParagraph = doc.createParagraph();
        attParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun attRun = attParagraph.createRun();
        attRun.setFontFamily("Times New Roman");
        attRun.setFontSize(12);
        condRun.addBreak();
        attRun.setText("Esperando vernos favorecidos por su amable atención, quedamos de Ud.");
        attRun.addBreak();
        attRun.setText("Muy atentamente,\nPriscilla Arboleda Ramirez\nAdministrador\nwww.servindustria.com\nTelf: 533-0219 / 533-2505\nN: 328*5756 y Cel: 99828-5756");

        // Guarda el documento Word en el OutputStream recibido
        doc.write(outputStream);
        doc.close();
    }

    public void generateQuotePdf(Long quoteId, OutputStream outputStream) throws IOException {
        // Busca la cotización por ID
        Quote quote = quoteRepository.findById(quoteId)
            .orElseThrow(() -> new ResourceNotFoundException("Cotización con ID " + quoteId + " no encontrada"));

        // Busca el cliente asociado (puede ser empresa o persona natural)
        CompanyClient companyClient = clientRepository.findCompanyClientById(quote.getClient().getId()).orElse(null);
        NaturalClient naturalClient = clientRepository.findNaturalClientById(quote.getClient().getId()).orElse(null);
        if (naturalClient == null && companyClient == null) {
            throw new ResourceNotFoundException("Cliente asociado a la cotización con ID " + quoteId + " no encontrado");
        }

        // Obtiene los detalles de la cotización
        List<QuoteDetail> details = quoteDetailRepository.findByQuoteI(quoteId);

        // --- Inicializa iText para crear el PDF ---
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);

        // --- Agrega el logo usando iText ---
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("static/images/logoFactura.png")) {
            if (is != null) {
                byte[] imgBytes = is.readAllBytes();
                Image logo = new Image(ImageDataFactory.create(imgBytes));
                logo.setWidth(380);
                logo.setHeight(90);
                logo.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
                document.add(logo);
            }
        }

        // --- Información de la empresa ---
        document.add(new Paragraph("RUC / DNI: 20419021670").setBold().setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Los olivos " + quote.getDateEmi().format(java.time.format.DateTimeFormatter.ofPattern("dd 'de' MM 'del' yyyy - HH:mm"))).setTextAlignment(TextAlignment.RIGHT));

        // --- Información del cliente y asunto ---
        document.add(new Paragraph("\nSeñores:").setBold());
        document.add(new Paragraph(naturalClient != null
            ? naturalClient.getName() + " " + naturalClient.getLastName()
            : companyClient.getNameReasonSoc()).setBold());
        document.add(new Paragraph("At.: Priscilla Arboleda Ramirez").setBold());
        document.add(new Paragraph("\nRef.: Cotización de extintores y otros").setBold().setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("\nDe mi consideración:"));
        document.add(new Paragraph("Por medio de la presente le hacemos llegar la siguiente cotización de acuerdo a vuestra solicitud:"));

        // --- Tabla de productos usando iText ---
        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(5);
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Item").setBold()));
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Cant.").setBold()));
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Descripción").setBold()));
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("P.Unit.").setBold()));
        table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Total").setBold()));
        table.setWidth(com.itextpdf.layout.properties.UnitValue.createPointValue(539));

        int item = 1;
        for (QuoteDetail detail : details) {
            table.addCell(String.valueOf(item++));
            table.addCell(String.valueOf(detail.getAmount()));
            table.addCell(detail.getProduct() != null
                ? detail.getProduct().getType()
                : (detail.getService() != null ? detail.getService().getType() : ""));
            table.addCell("S/." + detail.getUnitaryPrice().toString());
            table.addCell("S/." + detail.getSubtotal().toString());
        }
        document.add(table);

        // --- Total de la cotización ---
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Total S/.......... " + quote.getTotal()).setBold().setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("INC. I.G.V.").setTextAlignment(TextAlignment.RIGHT));

        // --- Características técnicas y condiciones de pago ---
        List<String> characters = new ArrayList<>();
        for (QuoteDetail detail : details) {
            if (detail.getProduct() != null) {
                characters.add("- Producto: " + detail.getProduct().getDescription() + "- Cantidad: " + detail.getAmount());
            }
            if (detail.getService() != null) {
                characters.add("- Servicio: " + detail.getService().getDescription() + "- Cantidad: " + detail.getAmount());
            }
            if (detail.getAfterSales() != null) {
                characters.add("- Post Venta: " + detail.getAfterSales().getServiceType() + "- Cantidad: " + detail.getAmount());
            }
            characters.add(""); // Línea en blanco para separar
        }
        String charactersText = Joiner.on("\n").join(characters);

        List<String> SellServices = ImmutableList.of(
            "-Etiquetas  de capacidad, instrucción, vencimiento, prueba hidrostática y soporte",
            "-Tarjetas de inspección para el control y mantenimiento de extintores ",
            "-Garantía :01 año ",
            "-Certificado de operatividad de extintores para presentar a INDECI",
            "-RPIN: 2092-2006"
        );
        String sellServicesText = Joiner.on("\n").join(SellServices);

        List<String> conditions = ImmutableList.of(
            "FORMA DE PAGO: contado c/e",
            "TIEMPO DE ENTREGA: inmediata",
            "VALIDEZ DE OFERTA: 30 días",
            "IMPUESTO: incluye"
        );
        String conditionsText = Joiner.on("\n").join(conditions);

        document.add(new Paragraph("\nCON LAS SIGUIENTES CARACTERISTICAS TÉCNICAS:").setBold().setUnderline());
        document.add(new Paragraph(charactersText));
        document.add(new Paragraph("\nINCLUYE EL SERVICIO VENTA:").setBold().setUnderline());
        document.add(new Paragraph(sellServicesText));
        document.add(new Paragraph("\nCONDICIONES DE PAGO:").setBold().setUnderline());
        document.add(new Paragraph(conditionsText));
        document.add(new Paragraph("\nEsperando vernos favorecidos por su amable atención, quedamos de Ud."));
        document.add(new Paragraph("Muy atentamente,\nPriscilla Arboleda Ramirez\nAdministrador\nwww.servindustria.com\nTelf: 533-0219 / 533-2505\nN: 328*5756 y Cel: 99828-5756").setTextAlignment(TextAlignment.CENTER));

        // --- Cierra el documento PDF ---
        document.close();
    }
}