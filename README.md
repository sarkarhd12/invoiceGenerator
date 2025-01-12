# invoiceGenerator
Invoice Generation System Documentation
Table of Contents

    Introduction

    Backend
        Technologies Used
        Project Structure
        Dependencies
        Endpoints
        Running the Application

    Frontend
        Technologies Used
        Form Structure
        Styling
        Scripts
    Integration
    Conclusion


Introduction

This documentation provides an overview of the invoice generation system, including both backend and frontend components. The system allows users to input invoice details through a form and generate an invoice in PDF format.


Backend
Technologies Used

    Java
    Spring Boot
    iText for PDF generation

Project Structure

lua

src/main/java/com/invoicegenerator/
|-- controller/
|   |-- InvoiceController.java
|-- model/
|   |-- InvoiceRequest.java
|   |-- LogoAndSignatureDto.java
|-- service/
|   |-- InvoiceService.java
|-- utils/
|   |-- PdfGenerator.java
|-- InvoiceGeneratorApplication.java

Dependencies
    spring-boot-starter-web
    itextpdf


Endpoint  :   POST /invoice/generate

    Description: Generates a PDF invoice based on the provided data.
    Request: Multipart/form-data including JSON data and images for logo and signature.
    Response: PDF file.

Running the Application

    Clone the repository.
    Navigate to the project directory.
    Run mvn clean install.
    Run mvn spring-boot:run.
    The application will start on http://localhost:8080.

Frontend

Technologies Used
    HTML
    CSS
    JavaScript


The form is divided into multiple sections:

    Seller Details
    Order Details
    Invoice Details
    Billing Address
    Shipping Address
    Items
    Place of Supply
    Logo and Signature



Integration

The frontend form submits data to the backend endpoint /invoice/generate. Ensure the form is properly set up to send a multipart/form-data request, including both JSON data and image files.