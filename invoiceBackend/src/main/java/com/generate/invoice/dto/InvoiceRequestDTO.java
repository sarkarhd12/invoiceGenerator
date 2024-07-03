package com.generate.invoice.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class InvoiceRequestDTO {
    private SellerDetailsDto sellerDetails;
    private String placeOfSupply;
    private BillingDetailsDTO billingDetails;
    private ShippingDetailsDTO shippingDetails;
    private String placeOfDelivery;
    private OrderDetailsDto orderDetails;
    private InvoiceDetailsDTO invoiceDetails;
    private boolean reverseCharge;
    private List<ItemDTO> items;

    public SellerDetailsDto getSellerDetails() {
        return sellerDetails;
    }

    public void setSellerDetails(SellerDetailsDto sellerDetails) {
        this.sellerDetails = sellerDetails;
    }

    public String getPlaceOfSupply() {
        return placeOfSupply;
    }

    public void setPlaceOfSupply(String placeOfSupply) {
        this.placeOfSupply = placeOfSupply;
    }

    public BillingDetailsDTO getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(BillingDetailsDTO billingDetails) {
        this.billingDetails = billingDetails;
    }

    public ShippingDetailsDTO getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(ShippingDetailsDTO shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public String getPlaceOfDelivery() {
        return placeOfDelivery;
    }

    public void setPlaceOfDelivery(String placeOfDelivery) {
        this.placeOfDelivery = placeOfDelivery;
    }

    public OrderDetailsDto getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetailsDto orderDetails) {
        this.orderDetails = orderDetails;
    }

    public InvoiceDetailsDTO getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(InvoiceDetailsDTO invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public boolean isReverseCharge() {
        return reverseCharge;
    }

    public void setReverseCharge(boolean reverseCharge) {
        this.reverseCharge = reverseCharge;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }




    @Override
    public String toString() {
        return "InvoiceRequestDTO{" +
                "sellerDetails=" + sellerDetails +
                ", placeOfSupply='" + placeOfSupply + '\'' +
                ", billingDetails=" + billingDetails +
                ", shippingDetails=" + shippingDetails +
                ", placeOfDelivery='" + placeOfDelivery + '\'' +
                ", orderDetails=" + orderDetails +
                ", invoiceDetails=" + invoiceDetails +
                ", reverseCharge=" + reverseCharge +
                ", items=" + items +

                '}';
    }
}
