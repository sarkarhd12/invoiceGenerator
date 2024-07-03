package com.generate.invoice.dto;

import org.springframework.web.multipart.MultipartFile;

public class LogoAndSignatureDto {
    private String logo;
    private String signature;

    // Getters and Setters
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    @Override
    public String toString() {
        return "LogoAndSignatureDto{" +
                "logo=" + logo +
                ", signature=" + signature +
                '}';
    }
}
