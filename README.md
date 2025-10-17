# 🚀 Smart Job Portal

A Spring Boot and MongoDB-based Job Portal application that enables **Admins** to post jobs and **Users** to view, apply, update, or delete their accounts. Built with secure role-based access using **Spring Security** and **Basic Auth**.

---

## 📌 Features

### 👨‍💼 Admin
- Register/login as admin (role: `ADMIN`)
- Post a job → `POST /admin/post-job`
- View all registered users → `GET /admin/see-all-user`

### 👤 User
- Register a new account → `POST /public/create-user`
- View all available jobs → `GET /user/see-job`
- Apply to jobs by title → `GET /user/apply-job-byTitle/{title}`
- Update account details → `PUT /user/update-user`
- Delete account → `DELETE /user/delete-user`

---

## 🔐 Authentication & Security

- Uses **Spring Security** with **HTTP Basic Authentication**
- Stateless session management (`SessionCreationPolicy.STATELESS`)
- Role-based access:
  - `/admin/**` → `ROLE_ADMIN`
  - `/user/**` → `ROLE_USER`
  - `/public/**` → Open for registration

---

## 🛠 Tech Stack

- Java 8  
- Spring Boot 2.7.18  
- MongoDB  
- Spring Data MongoDB  
- Spring Security  
- Lombok  
- Postman (for testing APIs)

---

## 📂 Project Structure
src
└── main
├── java
│ └── com.mdsohail.smartjobportal
│ ├── configuration/ // Spring Security config
│ ├── controller/ // Admin, User, Public APIs
│ ├── entity/ // Job and User model
│ ├── repository/ // MongoDB Repositories
│ ├── service/ // Business logic
│ └── SmartJobPortalApplication.java
└── resources
└── application.properties


---

## 📈 Future Enhancements

- JWT-based authentication (replace basic auth)
- Pagination and filtering for job listings
- Job expiration or status tracking
- Admin dashboard UI with Spring MVC / React
- Email notification when job is applied

---

## 👨‍💻 Author

**MD Sohail Ansari**

---



