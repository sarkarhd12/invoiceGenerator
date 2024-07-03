package com.generate.invoice.dto;

public class InvoiceResponseDTO {
    private String invoiceUrl;

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

    @Override
    public String toString() {
        return "InvoiceResponseDTO{" +
                "invoiceUrl='" + invoiceUrl + '\'' +
                '}';
    }
}
