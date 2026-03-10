# 📚 Library Management System

A full-stack **REST API** built with **Spring Boot** for managing a library system with role-based access control.

---

## 🛠️ Tech Stack

| Technology | Usage |
|---|---|
| Java 17 | Core language |
| Spring Boot 3.2.3 | Framework |
| Spring Security | Authentication & Authorization |
| JWT (jjwt 0.12.3) | Token-based auth |
| Spring Data JPA | ORM |
| MySQL | Database |
| Lombok | Boilerplate reduction |
| Swagger / OpenAPI | API Documentation |
| Maven | Build tool |

---

## 🗂️ Project Structure
```
src/main/java/com/library/
├── config/          # Security, Swagger, DataInitializer
├── controller/      # REST Controllers
├── dto/
│   ├── request/     # Request DTOs
│   └── response/    # Response DTOs
├── entity/          # JPA Entities
├── enums/           # Enums (Role, BookStatus, IssueStatus)
├── exception/       # Global Exception Handling
├── repository/      # JPA Repositories
├── security/        # JWT Filter, UserDetailsService
└── service/
    └── impl/        # Service Implementations
```

---

## ⚙️ Setup & Installation

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/library-management.git
cd library-management
```

### 2. Configure MySQL
Create a database:
```sql
CREATE DATABASE library_db;
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 3. Run the Application
```bash
run:-
File Name= 'LibraryManagementApplication'
```

---

## 🔐 Default Admin Credentials

On first startup, admin is **auto-created**:

| Field | Value |
|---|---|
| Username | `admin` |
| Password | `admin@123` |
| Role | `ADMIN` |

---

## 📖 API Documentation

Once running, open Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

---

## 🔑 Authentication Flow
```
1. POST /api/auth/login       → Get JWT token
2. Copy token from response
3. Click Authorize in Swagger → Paste: Bearer <token>
4. Now access protected APIs
```

---

## 📋 API Endpoints

### 🌐 Public
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/login` | Login (Admin or User) |
| POST | `/api/auth/register` | Self-register as USER |

### 👑 Admin Only
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/admin/authors` | Create author |
| GET | `/api/admin/authors` | Get all authors |
| PUT | `/api/admin/authors/{id}` | Update author |
| DELETE | `/api/admin/authors/{id}` | Delete author |
| POST | `/api/admin/users` | Create user |
| GET | `/api/admin/users` | Get all users |
| PUT | `/api/admin/users/{id}` | Update user |
| DELETE | `/api/admin/users/{id}` | Delete user |

### 📚 Books
| Method | Endpoint | Role | Description |
|---|---|---|---|
| POST | `/api/books` | ADMIN/Author | Create book |
| GET | `/api/books` | Any Auth | Get all books |
| GET | `/api/books/{id}` | Any Auth | Get book by ID |
| GET | `/api/books/search?name=` | Any Auth | Search by name |
| GET | `/api/books/status/{status}` | Any Auth | Filter by status |
| PUT | `/api/books/{id}` | Author only | Update own book |
| DELETE | `/api/books/{id}` | Author only | Delete own book |
| POST | `/api/books/{id}/review` | USER | Add review |

### 📋 Issue & Return
| Method | Endpoint | Role | Description |
|---|---|---|---|
| POST | `/api/issues/issue/{bookId}` | USER | Issue a book |
| PUT | `/api/issues/return/{issueId}` | USER | Return a book |
| GET | `/api/issues/my` | USER | My issued books |
| GET | `/api/issues/all` | ADMIN | All issued books |
| GET | `/api/issues/overdue` | ADMIN | Overdue books |

### 👤 User Profile
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/user/profile` | My profile |
| GET | `/api/user/fine` | My fine amount |

---

## 💰 Fine Policy

- Books must be returned within **14 days**
- Fine: **₹5 per day** after due date
- Fine is auto-calculated on return

---

## 👨‍💻 Author

Built with ❤️ using Spring Boot
