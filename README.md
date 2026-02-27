# CodeArena

CodeArena is a full-stack algorithmic coding platform designed to simulate a competitive programming environment. It allows users to browse Data Structures and Algorithms (DSA) topics, view problem details, write code in an integrated editor, and submit solutions with real-time feedback.

## 🏗️ System Architecture & Tech Stack

**Frontend (Client)**
* **Framework:** React.js with Vite
* **Styling:** Tailwind CSS
* **Routing:** React Router DOM
* **HTTP Client:** Axios (with custom interceptors for JWT management)
* **Icons:** Lucide React

**Backend (API)**
* **Framework:** Spring Boot 3 (Java)
* **Security:** Spring Security 6 with Stateless JWT Authentication
* **Database Access:** Spring Data JPA / Hibernate
* **Database:** MySQL
* **Cloud Storage:** AWS S3 (via Spring Cloud AWS) for secure file and test case retrieval

## ✨ Key Features

* **Secure Authentication:** Implemented stateless JWT (JSON Web Tokens) authentication with secure password hashing (BCrypt).
* **Role-Based Access:** Protected API endpoints ensuring only authenticated users can submit code and view sensitive data.
* **Dynamic Problem Set:** Fetches DSA topics and related problems dynamically from the backend.
* **AWS Integration:** Securely fetches problem descriptions and test cases from AWS S3 using the AWS SDK.
* **Real-time Execution Polling:** Asynchronous submission handling where the React frontend polls the Spring Boot backend to update the UI from `PENDING` to `ACCEPTED`, `WRONG_ANSWER`, or `COMPILE_ERROR`.
* **Centralized API Management:** Frontend utilizes an Axios interceptor to automatically attach authorization headers and handle 401/403 token expirations gracefully.

## 🛠️ Local Setup & Installation

### Prerequisites
* Java 17 or higher
* Node.js (v18+) and npm
* MySQL Server running locally
* AWS Account (with S3 bucket and IAM user credentials)
* Docker desktop running on background

### 1. Backend Setup (Spring Boot)
1. Clone the repository and navigate to the backend directory.
2. Open `src/main/resources/application.properties` and configure your environment variables:
   ```properties
   # Database Configuration
   spring.datasource.url=jdbc:mysql://localhost:3306/codearena_db
   spring.datasource.username=your_mysql_user
   spring.datasource.password=your_mysql_password

   # JWT Security
   application.security.jwt.secret-key=YourSecureBase64UrlEncodedSecretKeyHere

   # AWS S3 Configuration
   spring.cloud.aws.credentials.access-key=your_aws_access_key
   spring.cloud.aws.credentials.secret-key=your_aws_secret_key
   spring.cloud.aws.region.static=ap-south-1 # Or your configured region
   spring.cloud.aws.s3.bucket=your-s3-bucket-name
