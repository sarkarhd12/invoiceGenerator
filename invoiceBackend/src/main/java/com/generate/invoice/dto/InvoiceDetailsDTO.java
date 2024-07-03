package com.generate.invoice.dto;

public class InvoiceDetailsDTO {
    private String invoiceNo;
    private String invoiceDetails;
    private String invoiceDate;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(String invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    @Override
    public String toString() {
        return "InvoiceDetailsDTO{" +
                "invoiceNo='" + invoiceNo + '\'' +
                ", invoiceDetails='" + invoiceDetails + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                '}';
    }
}
