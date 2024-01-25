# Payment Service

Payment Service is a Java application developed using Spring Boot (version 3.2.2) and Java 21. It serves as a backend service for handling fee payments, viewing payment details, and generating payment receipts. The application utilizes an H2 database for data storage.

## Technologies

- Java 21
- Spring Boot 3.2.2
- H2 Database
- JUnit
- Swagger for API documentation
- Spring Boot Actuator for monitoring and managing application

## Features

1. **Fee Payment:** The service allows users to make fee payments by providing the student's roll number.

2. **View Payment Details:**
    - By Payment Reference Number: Retrieve payment details using the unique payment reference number.
    - By Roll Number: View all payments associated with a specific roll number.

3. **Receipt Generation:**
    - Thymeleaf Templates: The service uses Thymeleaf templates to generate payment receipts.
    - PDF Generation: Receipts can be downloaded in PDF format for easy storage and printing.

4. **Swagger Documentation:**
    - API documentation is available through Swagger for easy exploration and integration.
    - Accessible at [http://localhost:8083/payment-service/swagger-ui/index.html#/](http://localhost:8083/payment-service/swagger-ui/index.html#/).
### Spring Boot Actuator

- Monitor and manage the application in production with Spring Boot Actuator.
- Endpoints include health, metrics, info, and more.
- Accessible at [http://localhost:8083/payment-service/actuator](http://localhost:8083/payment-service/actuator).

## Unit Test Cases

- JUnit test cases are implemented to ensure the reliability and correctness of the application.
  
## Setup

1. Clone the repository.
2. Configure your IDE or build tool for a Spring Boot application.
3. Run the application and access the Swagger documentation to explore the APIs.


## Endpoints

- **Fee Payment:**
    - POST `/payment?rollNo={studentRollNo}` - Pay fee for the student with the provided roll number.

- **View Payment Details:**
    - GET `/payment/{paymentRefNumber}` - Retrieve payment details by payment reference number.
    - GET `/payment?rollNum={studentRollNo}` - View all payments for a specific student roll number.

- **Generate Receipt:**
    - GET `/receipt?paymentRefNumber={paymentRefNumber}` - View/Download payment receipt in PDF.

# Getting Started

To get started with the project, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/MuhammadFaizan17/payment-service.git

2. Build Application:

   ```bash
   mvn clean install

3. Run Application:

   ```bash
   java -jar payment-0.0.1-SNAPSHOT.jar

4. Access Swagger:

   ```bash
   http://localhost:8083/payment-service/swagger-ui/index.html#/
   
