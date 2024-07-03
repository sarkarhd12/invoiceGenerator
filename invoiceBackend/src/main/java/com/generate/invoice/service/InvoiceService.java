package com.generate.invoice.service;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.generate.invoice.dto.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

@Service
public class InvoiceService {

    public InvoiceResponseDTO generateInvoice(InvoiceRequestDTO invoiceRequest, LogoAndSignatureDto logoAndSignatureDto) {
        List<ItemDTO> items = invoiceRequest.getItems();
        List<ItemSetForPdf> itemSetForPdfs = new ArrayList<>();

        for (ItemDTO item : items) {
            ItemSetForPdf itemForPdf = new ItemSetForPdf();
            itemForPdf.setDescription(item.getDescription());
            itemForPdf.setUnitPrice(item.getUnitPrice());
            itemForPdf.setQuantity(item.getQuantity());
            itemForPdf.setDiscount(item.getDiscount());

            double netAmount = item.getUnitPrice() * item.getQuantity() - item.getDiscount();
            itemForPdf.setNetAmount(netAmount);

            if (invoiceRequest.getPlaceOfSupply().equals(invoiceRequest.getPlaceOfDelivery())) {
                itemForPdf.setTaxType("CGST & SGST");
                itemForPdf.setTaxRate(18);
                itemForPdf.setTaxAmount(new BigDecimal(netAmount * 0.09).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                itemForPdf.setTotalAmount(netAmount + 2 * itemForPdf.getTaxAmount());
            } else {
                itemForPdf.setTaxType("IGST");
                itemForPdf.setTaxRate(18);
                itemForPdf.setTaxAmount(new BigDecimal(netAmount * 0.18).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                itemForPdf.setTotalAmount(netAmount + itemForPdf.getTaxAmount());
            }

            itemSetForPdfs.add(itemForPdf);
        }

        String filePath = "src/main/resources/static/generated/invoice.pdf";
        try {
            generateInvoicePDF(invoiceRequest, filePath, itemSetForPdfs, logoAndSignatureDto);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        InvoiceResponseDTO response = new InvoiceResponseDTO();
        response.setInvoiceUrl(filePath);
        return response;
    }

    private void generateInvoicePDF(InvoiceRequestDTO invoiceRequest, String filePath, List<ItemSetForPdf> items, LogoAndSignatureDto logoAndSignatureDto) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        PdfPTable outerTable = new PdfPTable(2);
        outerTable.setWidthPercentage(100);
        outerTable.setSpacingBefore(10f);
        outerTable.setSpacingAfter(10f);

        PdfPTable leftTable = new PdfPTable(1);
        leftTable.setWidthPercentage(100);

        if (logoAndSignatureDto.getLogo() != null && !logoAndSignatureDto.getLogo().isEmpty()) {
            addLogo(leftTable, logoAndSignatureDto.getLogo());
        }

        addSpacing(leftTable, 2);

        PdfPCell sellerDetailsCell = new PdfPCell();
        sellerDetailsCell.setBorder(PdfPCell.NO_BORDER);
        sellerDetailsCell.addElement(new Paragraph("Sold By:"));
        sellerDetailsCell.addElement(new Paragraph(invoiceRequest.getSellerDetails().getName(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        sellerDetailsCell.addElement(new Paragraph(invoiceRequest.getSellerDetails().getAddress(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        sellerDetailsCell.addElement(new Paragraph(invoiceRequest.getSellerDetails().getCity() + ", " + invoiceRequest.getSellerDetails().getState() + " - " + invoiceRequest.getSellerDetails().getPincode(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        sellerDetailsCell.addElement(new Paragraph("PAN No: " + invoiceRequest.getSellerDetails().getPanNo(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        sellerDetailsCell.addElement(new Paragraph("GST Registration No: " + invoiceRequest.getSellerDetails().getGstRegistrationNo(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        leftTable.addCell(sellerDetailsCell);

        addSpacing(leftTable, 2);

        PdfPCell orderDetailsCell = new PdfPCell();
        orderDetailsCell.setBorder(PdfPCell.NO_BORDER);
        orderDetailsCell.addElement(new Paragraph("Order Details:",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        orderDetailsCell.addElement(new Paragraph("Order No: " + invoiceRequest.getOrderDetails().getOrderNo(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        orderDetailsCell.addElement(new Paragraph("Order Date: " + invoiceRequest.getOrderDetails().getOrderDate(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        leftTable.addCell(orderDetailsCell);

        PdfPCell leftTableCell = new PdfPCell(leftTable);
        leftTableCell.setBorder(PdfPCell.NO_BORDER);
        outerTable.addCell(leftTableCell);

        PdfPTable rightTable = new PdfPTable(1);
        rightTable.setWidthPercentage(100);

        PdfPCell invoiceDetailsCell = new PdfPCell();
        invoiceDetailsCell.setBorder(PdfPCell.NO_BORDER);
        invoiceDetailsCell.addElement(new Paragraph("Tax Invoice/Bill of Supply/Cash Memo", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        invoiceDetailsCell.addElement(new Paragraph("Original for Recipient", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        invoiceDetailsCell.addElement(new Paragraph("Invoice Details:", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        invoiceDetailsCell.addElement(new Paragraph("Invoice No: " + invoiceRequest.getInvoiceDetails().getInvoiceNo(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        invoiceDetailsCell.addElement(new Paragraph("Invoice Date: " + invoiceRequest.getInvoiceDetails().getInvoiceDate(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        invoiceDetailsCell.addElement(new Paragraph("Reverse Charge: " + (invoiceRequest.isReverseCharge() ? "Yes" : "No"), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        rightTable.addCell(invoiceDetailsCell);

        addSpacing(rightTable, 2);

        PdfPCell billingDetailsCell = new PdfPCell();
        billingDetailsCell.setBorder(PdfPCell.NO_BORDER);
        billingDetailsCell.addElement(new Paragraph("Billing Address:", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        billingDetailsCell.addElement(new Paragraph(invoiceRequest.getBillingDetails().getName(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        billingDetailsCell.addElement(new Paragraph(invoiceRequest.getBillingDetails().getAddress(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        billingDetailsCell.addElement(new Paragraph(invoiceRequest.getBillingDetails().getCity() + " " + invoiceRequest.getBillingDetails().getState() + " " + invoiceRequest.getBillingDetails().getPincode(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        rightTable.addCell(billingDetailsCell);

        addSpacing(rightTable, 2);

        PdfPCell shippingDetailsCell = new PdfPCell();
        shippingDetailsCell.setBorder(PdfPCell.NO_BORDER);
        shippingDetailsCell.addElement(new Paragraph("Shipping Address:", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        shippingDetailsCell.addElement(new Paragraph(invoiceRequest.getShippingDetails().getName(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        shippingDetailsCell.addElement(new Paragraph(invoiceRequest.getShippingDetails().getAddress(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        shippingDetailsCell.addElement(new Paragraph(invoiceRequest.getShippingDetails().getCity() + " " + invoiceRequest.getShippingDetails().getState() + " " + invoiceRequest.getShippingDetails().getPincode(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        rightTable.addCell(shippingDetailsCell);

        PdfPCell rightTableCell = new PdfPCell(rightTable);
        rightTableCell.setBorder(PdfPCell.NO_BORDER);
        outerTable.addCell(rightTableCell);

        document.add(outerTable);

        PdfPTable itemTable = new PdfPTable(7);
        itemTable.setWidthPercentage(100);
        addTableHeader(itemTable);
        addRows(itemTable, items);

        document.add(itemTable);

        double totalTaxAmount = items.stream().mapToDouble(ItemSetForPdf::getTaxAmount).sum();
        double totalAmount = items.stream().mapToDouble(ItemSetForPdf::getTotalAmount).sum();

    
        PdfPTable totalAmountTable = new PdfPTable(2);
        totalAmountTable.setWidthPercentage(100);
        totalAmountTable.setSpacingBefore(10f);
        totalAmountTable.addCell(new PdfPCell(new Phrase("Total Tax Amount:", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD))));
        totalAmountTable.addCell(new PdfPCell(new Phrase(String.valueOf(totalTaxAmount), new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL))));
        totalAmountTable.addCell(new PdfPCell(new Phrase("Total Amount:", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD))));
        totalAmountTable.addCell(new PdfPCell(new Phrase(String.valueOf(totalAmount), new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL))));

        document.add(totalAmountTable);


        // Seller Details Table
        PdfPTable sellerDetailsTable = new PdfPTable(2);
      //  sellerDetailsTable.setWidthPercentage(100);

        PdfPCell sellerNameCell = new PdfPCell(new Phrase("Seller Name: " + invoiceRequest.getSellerDetails().getName(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
      //  sellerNameCell.setBorder(PdfPCell.NO_BORDER);
        sellerDetailsTable.addCell(sellerNameCell);

        PdfPCell signatureCell = new PdfPCell();
     //   signatureCell.setBorder(PdfPCell.NO_BORDER);
        if (logoAndSignatureDto.getSignature() != null && !logoAndSignatureDto.getSignature().isEmpty()) {
            addSignature(signatureCell, logoAndSignatureDto.getSignature());
        }
        PdfPCell authorizedSignatoryCell = new PdfPCell(new Phrase("Authorized Signatory", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
        sellerDetailsTable.addCell(signatureCell);
        sellerDetailsTable.addCell(authorizedSignatoryCell);


        // Adding seller details table with spacing
        PdfPTable sellerDetailsWrapperTable = new PdfPTable(1);
        sellerDetailsWrapperTable.setWidthPercentage(100);
        PdfPCell sellerDetailsWrapperCell = new PdfPCell(sellerDetailsTable);
        sellerDetailsWrapperCell.setBorder(Rectangle.TOP);
        sellerDetailsWrapperTable.addCell(sellerDetailsWrapperCell);

        document.add(sellerDetailsWrapperTable);

        addAuthorizedSignatory(document);

        addFooter(document);
        document.close();
    }


    private void addLogo(PdfPTable table, String logoPath) throws IOException, BadElementException {
        Image logo = Image.getInstance(Base64.getDecoder().decode(logoPath));
        logo.scaleToFit(120, 50); // Set the maximum width and height of the logo
        logo.setAlignment(Element.ALIGN_LEFT);
        PdfPCell logoCell = new PdfPCell(logo);
        logoCell.setBorder(PdfPCell.NO_BORDER);
        logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(logoCell);
    }



    private void addSignature(PdfPCell cell, String base64String) throws IOException, DocumentException {
        byte[] signatureBytes = java.util.Base64.getDecoder().decode(base64String);
        Image signature = Image.getInstance(signatureBytes);
        signature.scaleToFit(130, 40);
        signature.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(signature);
    }

    private void addAuthorizedSignatory(Document document) throws DocumentException {
        Paragraph authorizedSignatory = new Paragraph("Authorized Signatory", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
        authorizedSignatory.setAlignment(Element.ALIGN_RIGHT);
        document.add(authorizedSignatory);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Description", "Unit Price", "Quantity", "Discount", "Net Amount", "Tax Amount", "Total Amount")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<ItemSetForPdf> items) {
        for (ItemSetForPdf item : items) {
            table.addCell(item.getDescription());
            table.addCell(String.valueOf(item.getUnitPrice()));
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.valueOf(item.getDiscount()));
            table.addCell(String.valueOf(item.getNetAmount()));
            table.addCell(String.valueOf(item.getTaxAmount()));
            table.addCell(String.valueOf(item.getTotalAmount()));
        }
    }

    private void addSpacing(PdfPTable table, int numberOfBlankCells) {
        for (int i = 0; i < numberOfBlankCells; i++) {
            PdfPCell blankCell = new PdfPCell(new Phrase(" "));
            blankCell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(blankCell);
        }
    }

    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph("With 20 years of experience in product sales, we have earned the trust of our customers. Our collaborations with over 1000 brands reinforce our commitment to quality and reliability.", new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
    }
}



