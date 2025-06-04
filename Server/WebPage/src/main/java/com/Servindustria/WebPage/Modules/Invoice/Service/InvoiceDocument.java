package com.Servindustria.WebPage.Modules.Invoice.Service;

import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Exception.ResourceNotFoundException;
import com.Servindustria.WebPage.Modules.Client.Model.CompanyClient;
import com.Servindustria.WebPage.Modules.Client.Model.NaturalClient;
import com.Servindustria.WebPage.Modules.Client.Repository.ClientRepository;
import com.Servindustria.WebPage.Modules.Invoice.Model.Invoice;
import com.Servindustria.WebPage.Modules.Invoice.Repository.InvoiceRepository;
import com.Servindustria.WebPage.Modules.InvoiceDetail.Model.InvoiceDetail;
import com.Servindustria.WebPage.Modules.InvoiceDetail.Repository.InvoiceDetailRepository;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceDocument {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final ClientRepository clientRepository;

    public void generateInvoiceExcel(Long invoiceId, OutputStream outputStream) throws IOException {
    Invoice invoice = invoiceRepository.findById(invoiceId)
            .orElseThrow(() -> new ResourceNotFoundException("Factura con ID " + invoiceId + " no encontrada"));
        
        CompanyClient companyClient = clientRepository.findCompanyClientById(invoice.getClient().getId()).orElse(null);
        NaturalClient naturalClient = clientRepository.findNaturalClientById(invoice.getClient().getId()).orElse(null);
        if(naturalClient == null && companyClient == null) {
            throw new ResourceNotFoundException("Cliente asociado a la factura con ID " + invoiceId + " no encontrado");
        }   

        List<InvoiceDetail> details = invoiceDetailRepository.findByInvoiceId(invoiceId); 

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Factura");

        // Estilos básicos
        var headerStyle = workbook.createCellStyle();
        var font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)12);
        headerStyle.setFont(font);

        var titleStyle = workbook.createCellStyle();
        var titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short)14);
        titleStyle.setFont(titleFont);

        var borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        borderStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        borderStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        borderStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);

        int rowIdx = 0;

        // Encabezado de empresa
        Row companyRow = sheet.createRow(rowIdx++);
        var cell0 = companyRow.createCell(0);
        cell0.setCellValue("SERVINDUSTRIA EXTINTORES Y FUMIGACION E.I.R.L");
        cell0.setCellStyle(titleStyle);
        // Datos de la factura
        Row facturaRow = sheet.createRow(rowIdx++);
        var cell1 = facturaRow.createCell(0);
        cell1.setCellValue("FACTURA ELECTRONICA");
        cell1.setCellStyle(headerStyle);
        var cell2 = facturaRow.createCell(1);
        cell2.setCellValue(invoice.getCode());
        cell2.setCellStyle(headerStyle);

        // Datos del cliente y factura
        Row clientRow = sheet.createRow(rowIdx++);
        clientRow.createCell(0).setCellValue("RAZÓN SOCIAL: " + (naturalClient != null ? naturalClient.getName() + " " + naturalClient.getLastName() : companyClient.getNameReasonSoc()));
        clientRow.createCell(1).setCellValue("FECHA EMISIÓN: " + invoice.getDateEmi());
        clientRow.createCell(2).setCellValue("MONEDA: SOLES");

        Row userRow = sheet.createRow(rowIdx++);
        userRow.createCell(0).setCellValue("USUARIO: " + invoice.getClient().getAccount().getEmail());
        userRow.createCell(1).setCellValue("FORMA DE PAGO: " + invoice.getPaymentType());

        // Espacio
        rowIdx++;

        // Cabecera de tabla de productos/servicios
        Row headerRow = sheet.createRow(rowIdx++);
        String[] headers = {"CANT.", "UND.", "DESCRIPCIÓN", "P. UNIT.", "DSCTO", "P. VENTA"};
        for (int i = 0; i < headers.length; i++) {
            var cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Detalles
        for (InvoiceDetail detail : details) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(detail.getAmount());
            row.createCell(1).setCellValue("UND");
            row.createCell(2).setCellValue(detail.getProduct() != null ? detail.getProduct().getDescription() : detail.getService().getDescription()); 
            row.createCell(3).setCellValue(detail.getUnitaryPrice().doubleValue());
            row.createCell(4).setCellValue("0.00");
            row.createCell(5).setCellValue(detail.getSubtotal().doubleValue());
            // Aplica bordes a cada celda de la fila de detalle
            for (int i = 0; i < 6; i++) {
                row.getCell(i).setCellStyle(borderStyle);
            }
        }

        // Espacio
        rowIdx++;

        // Totales
        Row opRow = sheet.createRow(rowIdx++);
        opRow.createCell(4).setCellValue("OP. GRAVADA:");
        opRow.createCell(5).setCellValue(invoice.getSubtotal().doubleValue());

        Row igvRow = sheet.createRow(rowIdx++);
        igvRow.createCell(4).setCellValue("IGV:");
        igvRow.createCell(5).setCellValue(invoice.getTotal().subtract(invoice.getSubtotal()).doubleValue());

        Row totalRow = sheet.createRow(rowIdx++);
        var totalCell = totalRow.createCell(4);
        totalCell.setCellValue("IMPORTE TOTAL:");
        totalCell.setCellStyle(headerStyle);
        var totalValueCell = totalRow.createCell(5);
        totalValueCell.setCellValue(invoice.getTotal().doubleValue());
        totalValueCell.setCellStyle(headerStyle);

        // Ajusta el ancho de las columnas automáticamente
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        // Guardar archivo
        workbook.write(outputStream);
        workbook.close();
    }

    
    public void generateInvoicePdf(Long invoiceId, OutputStream outputStream) throws IOException {
    Invoice invoice = invoiceRepository.findById(invoiceId)
        .orElseThrow(() -> new ResourceNotFoundException("Factura con ID " + invoiceId + " no encontrada"));

    CompanyClient companyClient = clientRepository.findCompanyClientById(invoice.getClient().getId()).orElse(null);
    NaturalClient naturalClient = clientRepository.findNaturalClientById(invoice.getClient().getId()).orElse(null);
    if (naturalClient == null && companyClient == null) {
        throw new ResourceNotFoundException("Cliente asociado a la factura con ID " + invoiceId + " no encontrado");
    }

    List<InvoiceDetail> details = invoiceDetailRepository.findByInvoiceId(invoiceId);

    PdfWriter writer = new PdfWriter(outputStream);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    // Logo (opcional)
    try (InputStream is = getClass().getResourceAsStream("Server/WebPage/src/main/resources/static/images/image.png")) {
        if (is != null) {
            byte[] imgBytes = is.readAllBytes();
            Image logo = new Image(ImageDataFactory.create(imgBytes));
            logo.setWidth(180);
            logo.setHeight(60);
            document.add(logo);
        }
    }

    // Empresa y título
    document.add(new Paragraph("SERVINDUSTRIA EXTINTORES Y FUMIGACION E.I.R.L").setBold().setFontSize(14));
    document.add(new Paragraph("FACTURA ELECTRONICA: " + invoice.getCode()).setBold().setFontSize(12));
    document.add(new Paragraph("RAZÓN SOCIAL: " + (naturalClient != null ? naturalClient.getName() + " " + naturalClient.getLastName() : companyClient.getNameReasonSoc())));
    document.add(new Paragraph("FECHA EMISIÓN: " + invoice.getDateEmi() + "    MONEDA: SOLES"));
    document.add(new Paragraph("USUARIO: " + invoice.getClient().getAccount().getEmail() + "    FORMA DE PAGO: " + invoice.getPaymentType()));
    document.add(new Paragraph("\n"));

    // Tabla de productos/servicios
    Table table = new Table(UnitValue.createPercentArray(new float[]{10, 10, 40, 15, 10, 15})).useAllAvailableWidth();
    String[] headers = {"CANT.", "UND.", "DESCRIPCIÓN", "P. UNIT.", "DSCTO", "P. VENTA"};
    for (String h : headers) {
        table.addHeaderCell(new Cell().add(new Paragraph(h).setBold()));
    }

    for (InvoiceDetail detail : details) {
        table.addCell(String.valueOf(detail.getAmount()));
        table.addCell("UND");
        table.addCell(detail.getProduct() != null ? detail.getProduct().getDescription() : detail.getService().getDescription());
        table.addCell(String.valueOf(detail.getUnitaryPrice()));
        table.addCell("0.00");
        table.addCell(String.valueOf(detail.getSubtotal()));
    }
    document.add(table);

    // Totales
    document.add(new Paragraph("\n"));
    Table totals = new Table(UnitValue.createPercentArray(new float[]{60, 20, 20})).useAllAvailableWidth();
    totals.addCell(new Cell(1,2).add(new Paragraph("OP. GRAVADA:")).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
    totals.addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getSubtotal()))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
    totals.addCell(new Cell(1,2).add(new Paragraph("IGV:")).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
    totals.addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getTotal().subtract(invoice.getSubtotal())))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
    totals.addCell(new Cell(1,2).add(new Paragraph("IMPORTE TOTAL:").setBold()).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
    totals.addCell(new Cell().add(new Paragraph(String.valueOf(invoice.getTotal())).setBold()).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
    document.add(totals);

    document.close();
    }
}
