package com.generate.invoice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generate.invoice.dto.InvoiceRequestDTO;
import com.generate.invoice.dto.InvoiceResponseDTO;
import com.generate.invoice.dto.LogoAndSignatureDto;
import com.generate.invoice.service.InvoiceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;


@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }



    @PostMapping(value = "/generate", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<InvoiceResponseDTO> generateInvoice(
            @RequestPart("invoiceRequest") String invoiceRequestJson,
            @RequestPart("logo") MultipartFile logo,
            @RequestPart("signature") MultipartFile signature) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        InvoiceRequestDTO invoiceRequest = objectMapper.readValue(invoiceRequestJson, InvoiceRequestDTO.class);
        System.out.println(logo.toString());
        System.out.println(signature);
        LogoAndSignatureDto logoAndSignatureDto = new LogoAndSignatureDto();
        logoAndSignatureDto.setLogo(Base64.getEncoder().encodeToString(logo.getBytes()));
        logoAndSignatureDto.setSignature(Base64.getEncoder().encodeToString(signature.getBytes()));

        InvoiceResponseDTO response = invoiceService.generateInvoice(invoiceRequest, logoAndSignatureDto);

        return ResponseEntity.ok(response);
    }
}


