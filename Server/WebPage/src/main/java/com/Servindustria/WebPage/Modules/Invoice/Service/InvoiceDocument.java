package com.Servindustria.WebPage.Modules.Invoice.Service;

import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import com.google.common.base.Joiner;
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
    // Repositorios para acceder a la base de datos
    private final InvoiceRepository invoiceRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final ClientRepository clientRepository;

    // Método para generar un archivo Excel de la factura usando Apache POI
    public void generateInvoiceExcel(Long invoiceId, OutputStream outputStream) throws IOException {
        // Busca la factura por ID, lanza excepción si no existe
        Invoice invoice = invoiceRepository.findById(invoiceId)
            .orElseThrow(() -> new ResourceNotFoundException("Factura con ID " + invoiceId + " no encontrada"));

        // Busca el cliente asociado (puede ser empresa o persona natural)
        CompanyClient companyClient = clientRepository.findCompanyClientById(invoice.getClient().getId()).orElse(null);
        NaturalClient naturalClient = clientRepository.findNaturalClientById(invoice.getClient().getId()).orElse(null);
        if (naturalClient == null && companyClient == null) {
            throw new ResourceNotFoundException("Cliente asociado a la factura con ID " + invoiceId + " no encontrado");
        }

        // Obtiene los detalles de la factura
        List<InvoiceDetail> details = invoiceDetailRepository.findByInvoiceId(invoiceId);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Factura");

        // Estilos
        var titleStyle = workbook.createCellStyle();
        var titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFont(titleFont);

        var headerStyle = workbook.createCellStyle();
        var headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);

        var borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        borderStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        borderStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        borderStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);

        var normalStyle = workbook.createCellStyle();
        var normalFont = workbook.createFont();
        normalFont.setFontHeightInPoints((short) 10);
        normalStyle.setFont(normalFont);

        int rowIdx = 0;
        // --- Insertar imagen (logo) en la parte superior del Excel
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("static/images/logoFactura.png")) {
            if (is != null) {
            byte[] bytes = is.readAllBytes();
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            var helper = workbook.getCreationHelper();
            var drawing = sheet.createDrawingPatriarch();
            var anchor = helper.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(rowIdx);
            anchor.setCol2(3);
            anchor.setRow2(rowIdx + 6); 
            drawing.createPicture(anchor, pictureIdx);
            rowIdx += 6; 
            }
        }
        // --- Encabezado de empresa y factura ---
        Row companyRow = sheet.createRow(rowIdx++);
        var cell0 = companyRow.createCell(0);
        cell0.setCellValue("SERVINDUSTRIA EXTINTORES Y FUMIGACION E.I.R.L");
        cell0.setCellStyle(titleStyle);

        Row facturaRow = sheet.createRow(rowIdx++);
        var cell1 = facturaRow.createCell(0);
        cell1.setCellValue("FACTURA ELECTRÓNICA");
        cell1.setCellStyle(headerStyle);
        var cell2 = facturaRow.createCell(1);
        cell2.setCellValue(invoice.getCode());
        cell2.setCellStyle(headerStyle);

        // --- Datos de cliente y factura ---
        Row infoRow1 = sheet.createRow(rowIdx++);
        infoRow1.createCell(0).setCellValue("RAZÓN SOCIAL: " + (naturalClient != null ? naturalClient.getName() + " " + naturalClient.getLastName() : companyClient.getNameReasonSoc()));
        infoRow1.createCell(1).setCellValue("FECHA EMISIÓN: " + invoice.getDateEmi().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")));
        infoRow1.createCell(2).setCellValue("MONEDA: SOLES");

        Row infoRow2 = sheet.createRow(rowIdx++);
        infoRow2.createCell(0).setCellValue("RUC: " + (companyClient != null ? companyClient.getDocumentNumber() : ""));
        infoRow2.createCell(1).setCellValue("USUARIO: " + invoice.getClient().getAccount().getEmail());
        infoRow2.createCell(2).setCellValue("FORMA DE PAGO: " + invoice.getPaymentType());

        Row infoRow3 = sheet.createRow(rowIdx++);
        infoRow3.createCell(0).setCellValue("DIRECCIÓN: " + (companyClient != null ? companyClient.getAddress() : ""));
        infoRow3.createCell(1).setCellValue("TELÉFONO: " + (companyClient != null ? companyClient.getPhone1() : ""));

        rowIdx++; // Espacio vacío

        // --- Cabecera de la tabla de productos/servicios ---
        Row headerRow = sheet.createRow(rowIdx++);
        String[] headers = {"CANT.", "UND.", "DESCRIPCIÓN", "P. UNIT.", "DSCTO", "P. VENTA"};
        for (int i = 0; i < headers.length; i++) {
            var cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // --- Detalles de productos/servicios ---
        for (InvoiceDetail detail : details) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(detail.getAmount());
            row.createCell(1).setCellValue("UND");
            row.createCell(2).setCellValue(detail.getProduct() != null ? detail.getProduct().getDescription() : detail.getService().getDescription());
            row.createCell(3).setCellValue(detail.getUnitaryPrice().doubleValue());
            row.createCell(4).setCellValue("0.00");
            row.createCell(5).setCellValue(detail.getSubtotal().doubleValue());
            // Aplica bordes y estilo normal a cada celda
            for (int i = 0; i < 6; i++) {
                row.getCell(i).setCellStyle(borderStyle);
            }
        }

        // --- Observaciones ---
        List<String> observaciones = com.google.common.collect.Lists.newArrayList();
        for (InvoiceDetail detail : details) {
            if (detail.getAfterSales() != null && detail.getAfterSales().getServiceType() != null && !detail.getAfterSales().getServiceType().isEmpty()) {
                observaciones.add(detail.getAfterSales().getServiceType());
            }
        }
        if (!observaciones.isEmpty()) {
            Row obsRow = sheet.createRow(rowIdx++);
            obsRow.createCell(2).setCellValue("NOTA y/o OBSERVACIÓN: " + Joiner.on(" | ").join(observaciones));
        }

        rowIdx++; // Espacio vacío

        // --- Totales ---
        BigDecimal igv = invoice.getSubtotal().multiply(BigDecimal.valueOf(0.18));
        Row opRow = sheet.createRow(rowIdx++);
        opRow.createCell(4).setCellValue("OP. GRAVADA:");
        opRow.createCell(5).setCellValue(invoice.getSubtotal().subtract(igv).doubleValue());

        Row igvRow = sheet.createRow(rowIdx++);
        igvRow.createCell(4).setCellValue("IGV:");
        igvRow.createCell(5).setCellValue(igv.doubleValue());

        Row totalRow = sheet.createRow(rowIdx++);
        var totalCell = totalRow.createCell(4);
        totalCell.setCellValue("IMPORTE TOTAL:");
        totalCell.setCellStyle(headerStyle);
        var totalValueCell = totalRow.createCell(5);
        totalValueCell.setCellValue(invoice.getTotal().doubleValue());
        totalValueCell.setCellStyle(headerStyle);

        rowIdx++; // Espacio vacío

        // --- Pie de página ---
        Row footerRow1 = sheet.createRow(rowIdx++);
        footerRow1.createCell(0).setCellValue("SERVINDUSTRIA EXTINTORES Y FUMIGACION E.I.R.L");
        Row footerRow2 = sheet.createRow(rowIdx++);
        footerRow2.createCell(0).setCellValue("BANCOS: BCP - CTA CTE SOLES: 191-9182652015-62 / CCI: 00219100620515201562");
        Row footerRow3 = sheet.createRow(rowIdx++);
        footerRow3.createCell(0).setCellValue("DIRECCIÓN: AV. TOMAS VALLE NRO. 962 AV. VIRGEN DE LA PUERTA");
        Row footerRow4 = sheet.createRow(rowIdx++);
        footerRow4.createCell(0).setCellValue("Cel: 922149530 | ventas@servindustria.com");

        // Ajusta el ancho de las columnas automáticamente
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(outputStream);
        workbook.close();
    }

    // Método para generar un PDF de la factura usando iText
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

        // --- Logo y cabecera superior ---
        Table header = new Table(UnitValue.createPercentArray(new float[]{60, 40})).useAllAvailableWidth();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("static/images/logoFactura.png")) {
            if (is != null) {
                byte[] imgBytes = is.readAllBytes();
                Image logo = new Image(ImageDataFactory.create(imgBytes));
                logo.setWidth(300);
                logo.setHeight(65);
                header.addCell(new Cell().add(logo)
                    .setBorder(Border.NO_BORDER)
                    .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
            } else {
                header.addCell(new Cell().setBorder(Border.NO_BORDER));
            }
        }
        // Datos de contacto y factura electrónica
        Cell datosCell = new Cell()
            .add(new Paragraph("FACTURA ELECTRÓNICA").setBold().setFontSize(12).setTextAlignment(TextAlignment.CENTER))
            .add(new Paragraph(invoice.getCode()).setFontSize(11).setTextAlignment(TextAlignment.CENTER))
            .setBorder(Border.NO_BORDER)
            .setTextAlignment(TextAlignment.CENTER);
        header.addCell(datosCell);
        document.add(header);
        document.add(new Paragraph("\n"));


        // --- Datos de empresa y cliente/factura ---
        Table info = new Table(UnitValue.createPercentArray(new float[]{50, 50})).useAllAvailableWidth();
        // Bloque izquierdo
        String razonSocial = (naturalClient != null)
            ? naturalClient.getName() + " " + naturalClient.getLastName()
            : companyClient.getNameReasonSoc();
        info.addCell(new Cell()
            .add(new Paragraph("RAZÓN SOCIAL: " + razonSocial).setBold())
            .add(new Paragraph("RUC: " + (companyClient != null ? companyClient.getDocumentNumber() : naturalClient.getDocumentNumber())))
            .add(new Paragraph("DIRECCIÓN: " + (companyClient != null ? companyClient.getAddress() : "No Definido")))
            .add(new Paragraph("TELÉFONO: " + (companyClient != null ? companyClient.getPhone1() : naturalClient.getPhone1())))
            .setBorder(Border.NO_BORDER)
            .setFontSize(9)
        );
        // Bloque derecho
        info.addCell(new Cell()
            .add(new Paragraph("FECHA EMISIÓN: " + invoice.getDateEmi().format(java.time.format.DateTimeFormatter.ofPattern("dd '/' MM '/' yyyy - HH:mm:ss"))))
            .add(new Paragraph("MONEDA: SOLES"))
            .add(new Paragraph("USUARIO: " + invoice.getClient().getAccount().getEmail()))
            .add(new Paragraph("FORMA DE PAGO: " + invoice.getPaymentType()))
            .setBorder(Border.NO_BORDER)
            .setFontSize(9)
        );
        document.add(info);
        document.add(new Paragraph("\n"));

        // --- Tabla de productos/servicios ---
        Table table = new Table(UnitValue.createPercentArray(new float[]{8, 8, 46, 12, 12, 14})).useAllAvailableWidth();
        String[] headers = {"CANT.", "UND.", "DESCRIPCIÓN", "P. UNIT.", "DSCTO", "P. VENTA"};
        for (String h : headers) {
            table.addHeaderCell(new Cell().add(new Paragraph(h).setBold().setFontSize(9)).setTextAlignment(TextAlignment.CENTER));
        }
        for (InvoiceDetail detail : details) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(detail.getAmount())).setFontSize(9)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph("UND").setFontSize(9)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(
                (detail.getProduct() != null ? detail.getProduct().getDescription() : detail.getService().getDescription())
            ).setFontSize(9)));
            table.addCell(new Cell().add(new Paragraph(String.format("%.2f", detail.getUnitaryPrice())).setFontSize(9)).setTextAlignment(TextAlignment.RIGHT));
            table.addCell(new Cell().add(new Paragraph("0.00").setFontSize(9)).setTextAlignment(TextAlignment.RIGHT));
            table.addCell(new Cell().add(new Paragraph(String.format("%.2f", detail.getSubtotal())).setFontSize(9)).setTextAlignment(TextAlignment.RIGHT));
        }
        document.add(table);
        document.add(new Paragraph("\n"));

        // --- Observaciones ---
        List<String> observaciones = com.google.common.collect.Lists.newArrayList();
        for (InvoiceDetail detail : details) {
            if (detail.getAfterSales() != null && detail.getAfterSales().getServiceType() != null && !detail.getAfterSales().getServiceType().isEmpty()) {
                observaciones.add(detail.getAfterSales().getServiceType());
            }
        }
        if (!observaciones.isEmpty()) {
            String obsText = "NOTA y/o OBSERVACIÓN: " + Joiner.on(" | ").join(observaciones);
            document.add(new Paragraph(obsText).setFontSize(9).setBold());
        }

        // --- Totales ---
        BigDecimal igv = invoice.getSubtotal().multiply(BigDecimal.valueOf(0.18));
        Table totals = new Table(UnitValue.createPercentArray(new float[]{70, 15, 15})).useAllAvailableWidth();
        totals.addCell(new Cell(3, 1).setBorder(Border.NO_BORDER)); // Celda vacía para alinear totales a la derecha
        totals.addCell(new Cell().add(new Paragraph("OP. GRAVADA:").setFontSize(9)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        totals.addCell(new Cell().add(new Paragraph(String.format("%.2f", invoice.getSubtotal().subtract(igv))).setFontSize(9)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        totals.addCell(new Cell().add(new Paragraph("IGV:").setFontSize(9)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        totals.addCell(new Cell().add(new Paragraph(String.format("%.2f", igv)).setFontSize(9)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        totals.addCell(new Cell().add(new Paragraph("IMPORTE TOTAL:").setBold().setFontSize(10)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        totals.addCell(new Cell().add(new Paragraph(String.format("%.2f", invoice.getTotal())).setBold().setFontSize(10)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        document.add(totals);
        document.add(new Paragraph("\n"));


        // --- Pie de página: bancos, QR, etc. ---   AUN PARA MODIFICAR CON LOS METODOS DE PAGO
        document.add(new Paragraph("SERVINDUSTRIA EXTINTORES Y FUMIGACION E.I.R.L").setFontSize(9).setBold());
        document.add(new Paragraph("BANCOS: BCP - CTA CTE SOLES: 191-9182652015-62 / CCI: 00219100620515201562").setFontSize(8));
        document.add(new Paragraph("DIRECCIÓN: AV. TOMAS VALLE NRO. 962 AV. VIRGEN DE LA PUERTA").setFontSize(8));
        document.add(new Paragraph("Cel: 922149530 | ventas@servindustria.com").setFontSize(8));

        document.close();
    }
}
