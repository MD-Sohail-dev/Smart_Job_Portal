# 🧠 Smart Job Portal

The **Smart Job Portal** is a web-based application developed using **Spring Boot**.  
It helps job seekers and employers connect easily.  
The portal allows **users** to register, log in, apply for jobs, and manage their profiles,  
while **admins** can post jobs and manage users.  
It also includes **JWT authentication** and **Google OAuth2 login** for secure access.

---

## 🚀 Main Features

### 👤 Public (Without Login)
1. **Signup** – A new user can register by providing their username, email, and password.  
   - Endpoint: `/public/signup`  
   - Description: Creates a new user account and stores it in the database.

2. **Login** – Existing users can log in using their username and password.  
   - Endpoint: `/public/login`  
   - Description: Authenticates the user and returns a **JWT token** for secure access.

3. **Google Login** – Users can log in directly with their **Google account**.  
   - Endpoint: `/auth/google/callback`  
   - Description: Uses Google OAuth2 for authentication and automatically creates a new account if not found.

---

### 🧑‍💼 User (After Login)
1. **See All Jobs** – Displays all the available job posts.  
   - Endpoint: `/user/see-job`

2. **Apply for a Job by Title** – Allows the user to apply for a specific job by its title.  
   - Endpoint: `/user/apply-job-byTitle?title=jobTitle`  
   - Description:  
     - The system checks if the user has already applied for the job.  
     - If not, it adds the job to the user’s applied list and sends a confirmation email.  

3. **View Profile** – Shows the profile details of the currently logged-in user.  
   - Endpoint: `/user/see-profile`

4. **Update Profile** – Allows the user to update their profile information like username, email, or password.  
   - Endpoint: `/user/update-user`

5. **Delete Account** – Deletes the currently logged-in user’s account from the database.  
   - Endpoint: `/user/delete-user`

6. **Email Notification** – After applying for a job, the user receives a confirmation email.  
   - The email includes the job details and a thank-you message.

---

### 👨‍💻 Admin
1. **Post a New Job** – Admin can post a new job for users to apply.  
   - Endpoint: `/admin/post-job`  
   - Description: Adds a new job to the database.

2. **View All Users** – Admin can view all registered users.  
   - Endpoint: `/admin/see-all-user`

---

## ⚙️ Technologies Used

| Category | Technology |
|-----------|-------------|
| **Backend Framework** | Spring Boot |
| **Database** | MongoDB |
| **Security** | Spring Security, JWT (JSON Web Token) |
| **OAuth Login** | Google OAuth2 |
| **Email Service** | Spring Boot Mail |
| **Documentation** | Swagger (OpenAPI) |
| **Build Tool** | Maven |
| **Language** | Java 8 |
| **Logging** | Lombok (Slf4j) |

---

## 🔐 Security and Authentication

- **JWT Authentication:**  
  When a user logs in successfully, a JWT token is generated and returned.  
  This token must be included in the **Authorization header** for accessing protected endpoints.

  Example:  


- **Google OAuth2:**  
Users can log in using their Google accounts.  
The system automatically creates a new user if their email doesn’t already exist.

---

## 📧 Email Notification

- After a successful job application, the user receives a confirmation email.  
- The email includes the job title, company name, and a thank-you message.  
- It is sent using **Spring Boot Mail**.

---

## 📘 API Documentation

The project uses **Swagger (OpenAPI)** for API documentation.  
You can view all available endpoints in an interactive format at:  
👉 **`http://localhost:8081/swagger-ui.html`**

---

## 💡 Future Enhancements

- Add an **Admin Dashboard** to manage jobs and users visually.  
- Allow **Resume Uploading** and **Profile Pictures**.  
- Introduce **Job Recommendations** using AI.  
- Add **Pagination and Filtering** in job listings.  
- Create a **Frontend Interface** using React or Angular.

---

## 👨‍💻 Developed By

**MD Sohail Ansari**  
🎓 B.Tech in Electronics and Communication Engineering  
📍 Durgapur, West Bengal  
💼 Aspiring Java Developer | Backend Developer  



