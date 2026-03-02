# 🚀 CodeArena

<img width="1854" height="930" alt="CodeArena Dashboard" src="https://github.com/user-attachments/assets/3dabc7f0-ca58-4608-b7a1-b908b27e4645" />

**CodeArena** is a full-stack algorithmic coding platform designed to simulate a high-performance competitive programming environment. It provides a seamless flow for users to browse DSA topics, write code in an integrated editor, and receive real-time execution feedback.

---

### Tech Stack
* **Frontend:** React.js (Vite), Tailwind CSS, React Router DOM, Axios, Lucide React.
* **Backend:** Spring Boot 3, Java 17, Spring Security 6 (Stateless JWT).
* **Database & Storage:** MySQL (via Hibernate/JPA), AWS S3 (Spring Cloud AWS).
* **DevOps:** Docker.

---

## ✨ Key Features

* 🔐 **Secure Authentication:** Stateless JWT-based auth with BCrypt password hashing.
* 🚦 **Role-Based Access:** Granular control over API endpoints for submission and data viewing.
* ☁️ **Cloud-Native Storage:** Problem statements and test cases are securely served from AWS S3.
* 🔄 **Real-time Execution Polling:** Asynchronous submission handling with a polling mechanism to track status from `PENDING` to `ACCEPTED` or `COMPILE_ERROR`.
* 🛠️ **Centralized API Management:** Custom Axios interceptors to manage token injection and automatic 401/403 handling.

---

## 🛠️ Local Setup & Installation

### Prerequisites
- **Java:** 17 or higher
- **Node.js:** v18+
- **Database:** MySQL Server
- **Storage:** AWS S3 Bucket + IAM Credentials
- **Containerization:** Docker Desktop

### 1. Backend Setup (Spring Boot)
1.  Navigate to the backend directory:
    ```bash
    cd codearena-backend
    ```
2.  Configure your `src/main/resources/application.properties`:
    ```properties
    # Database Configuration
    spring.datasource.url=jdbc:mysql://localhost:3306/codearena_db
    spring.datasource.username=your_user
    spring.datasource.password=your_password

    # JWT Security
    application.security.jwt.secret-key=YourSecureSecretKey

    # AWS S3 Configuration
    spring.cloud.aws.credentials.access-key=your_access_key
    spring.cloud.aws.credentials.secret-key=your_secret_key
    spring.cloud.aws.region.static=ap-south-1
    spring.cloud.aws.s3.bucket=your-s3-bucket-name
    ```
3.  Run the application:
    ```bash
    ./mvnw spring-boot:run
    ```
    *The API will be live at `http://localhost:8080`*

### 2. Frontend Setup (React)
1.  Navigate to the frontend directory:
    ```bash
    cd codearena-frontend
    ```
2.  Install dependencies:
    ```bash
    npm install
    ```
3.  **(Optional)** If your backend port differs, update `baseURL` in `src/services/api.js`.
4.  Start the development server:
    ```bash
    npm run dev
    ```
    *The UI will be live at `http://localhost:5173`*
