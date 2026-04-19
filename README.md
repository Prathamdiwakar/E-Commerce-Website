# 🛒 E-Commerce REST API

A production-ready, full-featured e-commerce backend built with **Java Spring Boot**, deployed on **AWS Elastic Beanstalk** with **PostgreSQL on AWS RDS**.

🔗 **Live API:** (http://ecommerce-env.eba-kiw8vmcg.ap-south-1.elasticbeanstalk.com)
📦 **Tech Stack:** Java · Spring Boot · Spring Security · JWT · PostgreSQL · AWS

---

## ✨ Features

- **JWT Cookie-based Authentication** — Secure login/logout with HTTP-only cookies
- **Role-Based Access Control** — Three roles: `USER`, `SELLER`, `ADMIN` with endpoint-level authorization
- **Product & Category Management** — Full CRUD with image upload support
- **Cart & Order Flow** — Add to cart, place orders, manage order lifecycle
- **Address Management** — Users can manage multiple delivery addresses
- **Pagination & Sorting** — All list endpoints support page/size/sort params
- **Global Exception Handling** — Consistent error responses across all endpoints
- **Clean Layered Architecture** — Controller → Service → Repository separation

---

## 🏗️ Architecture

```
src/
├── config/          # Security config, JWT filter, CORS
├── controller/      # REST controllers (Auth, Product, Cart, Order, Address)
├── service/         # Business logic layer
├── repository/      # Spring Data JPA repositories
├── model/           # JPA entities
├── dto/             # Request/Response DTOs
├── exception/       # Custom exceptions + GlobalExceptionHandler
└── util/            # JWT utility, helpers
```

---

## 🔐 Authentication Flow

```
POST /api/auth/signup   → Register user
POST /api/auth/login    → Get JWT cookie
POST /api/auth/logout   → Clear cookie
```

JWT is stored in an **HTTP-only cookie** (not localStorage) for XSS protection.

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Access |
|--------|----------|--------|
| POST | `/api/auth/signup` | Public |
| POST | `/api/auth/login` | Public |
| POST | `/api/auth/logout` | Authenticated |

### Products
| Method | Endpoint | Access |
|--------|----------|--------|
| GET | `/api/products` | Public |
| GET | `/api/products/{id}` | Public |
| POST | `/api/products` | SELLER / ADMIN |
| PUT | `/api/products/{id}` | SELLER / ADMIN |
| DELETE | `/api/products/{id}` | ADMIN |

### Cart
| Method | Endpoint | Access |
|--------|----------|--------|
| GET | `/api/cart` | USER |
| POST | `/api/cart/add` | USER |
| DELETE | `/api/cart/remove/{itemId}` | USER |

### Orders
| Method | Endpoint | Access |
|--------|----------|--------|
| POST | `/api/orders` | USER |
| GET | `/api/orders/my` | USER |
| GET | `/api/orders` | ADMIN |
| PUT | `/api/orders/{id}/status` | ADMIN |

### Address
| Method | Endpoint | Access |
|--------|----------|--------|
| GET | `/api/address` | USER |
| POST | `/api/address` | USER |
| PUT | `/api/address/{id}` | USER |
| DELETE | `/api/address/{id}` | USER |

---

## 🚀 Getting Started (Local Setup)

### Prerequisites
- Java 17+
- Maven
- PostgreSQL (or use the cloud DB)

### Steps

```bash
# 1. Clone the repo
git clone https://github.com/Prathamdiwakar/E-Commerce-FullStack.git
cd E-Commerce-FullStack

# 2. Configure environment variables
# Create application-local.properties or set env vars:
# DB_URL, DB_USERNAME, DB_PASSWORD, JWT_SECRET

# 3. Run the application
./mvnw spring-boot:run
```

### Environment Variables
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ecommerce
SPRING_DATASOURCE_USERNAME=your_db_user
SPRING_DATASOURCE_PASSWORD=your_db_password
JWT_SECRET=your_secret_key
```

---

## ☁️ Deployment

- **Backend:** AWS Elastic Beanstalk (Java SE platform)
- **Database:** AWS RDS (PostgreSQL)
- **CI:** Manual deploy via `.jar` upload / EB CLI

---

## 🧪 Testing the API

Import the Postman collection or test endpoints directly:

```bash
# Register
curl -X POST https://your-live-url/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"name":"John","email":"john@example.com","password":"pass123","role":"USER"}'

# Login
curl -X POST https://your-live-url/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"pass123"}'
```

---

## 🛠️ Built With

| Technology | Purpose |
|------------|---------|
| Spring Boot 3 | Core framework |
| Spring Security 6 | Auth & authorization |
| JWT (jjwt) | Token generation |
| Spring Data JPA | ORM / database layer |
| PostgreSQL | Production database |
| AWS Elastic Beanstalk | App hosting |
| AWS RDS | Managed DB hosting |
| Lombok | Boilerplate reduction |
| Maven | Build tool |

---

## 👨‍💻 Author

**Pratham Diwakar**  
BCA Student · Backend Developer  
📧 prathamdiwakar44@gmail.com  
🔗 [GitHub](https://github.com/Prathamdiwakar) · [LinkedIn](https://linkedin.com/in/your-profile)

---

⭐ If you found this project useful, consider giving it a star!
