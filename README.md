# Otuya Global Digital Solution - E-Commerce Platform

A full-stack e-commerce web application built with **Angular 14** (frontend) and **Spring Boot 3.2.0** (backend) with **MySQL** database. Features user authentication, admin-restricted product management, order placement with contact details, image uploads, and mobile-responsive UI.

---

## 🏗️ Architecture

```
┌─────────────────┐      HTTP/REST       ┌──────────────────┐
│   Angular 14    │ ◄──────────────────► │  Spring Boot 3.2 │
│   (Port 8888)   │      JSON API        │   (Port 8080)    │
└─────────────────┘                      └──────────────────┘
                                               │
                                    ┌──────────┴──────────┐
                               ┌─────▼─────┐    ┌──────▼──────┐
                               │   MySQL   │    │  File Uploads│
                               │ecommerce_db│    │   /uploads   │
                               └───────────┘    └──────────────┘
```

---

## 📁 Project Structure

```
Ecommerce-app/
├── backend/                              # Spring Boot Backend
│   ├── pom.xml                           # Maven configuration
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ecommerce/backend/
│   │   │   │   ├── config/
│   │   │   │   │   ├── SecurityConfig.java       # JWT + Role-based access control
│   │   │   │   │   └── WebConfig.java            # File upload resource handler
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthController.java       # Login / Register
│   │   │   │   │   ├── SoftwarePackageController.java  # CRUD packages
│   │   │   │   │   ├── FileUploadController.java # Product image upload
│   │   │   │   │   └── OrderController.java      # Place / View orders
│   │   │   │   ├── dto/
│   │   │   │   │   ├── SoftwarePackageRequest.java
│   │   │   │   │   ├── SoftwarePackageResponse.java
│   │   │   │   │   ├── OrderRequest.java
│   │   │   │   │   └── OrderResponse.java
│   │   │   │   ├── entity/
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Role.java
│   │   │   │   │   ├── SoftwarePackage.java
│   │   │   │   │   └── Order.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   ├── RoleRepository.java
│   │   │   │   │   ├── SoftwarePackageRepository.java
│   │   │   │   │   └── OrderRepository.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── UserDetailsServiceImpl.java
│   │   │   │   │   ├── SoftwarePackageService.java
│   │   │   │   │   ├── OrderService.java
│   │   │   │   │   └── EmailService.java
│   │   │   │   ├── security/
│   │   │   │   │   ├── JwtUtil.java
│   │   │   │   │   └── JwtAuthFilter.java
│   │   │   │   └── EcommerceApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties        # DB, JWT, Mail config
│   │   └── test/
│   └── uploads/
│       └── packages/                         # Uploaded product images
│
├── Ecommerce Product App/                    # Angular 14 Frontend
│   ├── angular.json
│   ├── package.json
│   ├── src/
│   │   ├── app/
│   │   │   ├── app.component.ts/html/css
│   │   │   ├── app-routing.module.ts
│   │   │   ├── app.module.ts
│   │   │   ├── components/
│   │   │   │   └── software-package-card/
│   │   │   ├── models/
│   │   │   │   ├── software-package.ts
│   │   │   │   └── order.ts
│   │   │   ├── pages/
│   │   │   │   ├── login/
│   │   │   │   ├── register/
│   │   │   │   ├── software-packages/
│   │   │   │   ├── add-software-package/
│   │   │   │   ├── view-software-package/
│   │   │   │   ├── order/
│   │   │   │   └── my-orders/
│   │   │   └── services/
│   │   │       ├── auth.service.ts
│   │   │       ├── software-package.service.ts
│   │   │       └── order.service.ts
│   │   ├── index.html
│   │   └── styles.css
│   └── dist/                                 # Production build output
│
└── README.md                                 # This file
```

---

## 🚀 Quick Start (Local Development)

### Prerequisites

| Tool | Version | Download |
|------|---------|----------|
| Java | 17+ | https://adoptium.net/ |
| Maven | 3.9+ | https://maven.apache.org/ |
| Node.js | 18+ | https://nodejs.org/ |
| Angular CLI | 14 | `npm install -g @angular/cli@14` |
| MySQL | 8.0+ | https://dev.mysql.com/downloads/ |
| XAMPP (optional) | 3.3+ | https://www.apachefriends.org/ |

### Step 1: Configure MySQL Database

```sql
-- Create database (using XAMPP phpMyAdmin or MySQL CLI)
CREATE DATABASE ecommerce_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create test users (run via MySQL CLI or phpMyAdmin)
-- User 'admin' with password 'admin123' (ROLE_ADMIN)
-- User 'emilys' with password 'emilyspass' (ROLE_USER)
```

Database connection is configured in `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3308/ecommerce_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
server.port=8080
```

> **Note:** MySQL runs on port 3308 (XAMPP). If you use a different port, update `application.properties`.

### Step 2: Start the Backend

```powershell
# Open PowerShell in the backend directory
cd C:\xampp\htdocs\Ecommerce-app\backend

# Run with Maven
& "C:\Java\apache-maven-3.9.4\apache-maven-3.9.4\bin\mvn.cmd" spring-boot:run
```

**Expected output:** `Started EcommerceApplication in X seconds`

Backend is now running at: `http://localhost:8080`

### Step 3: Start the Frontend

```powershell
# Open PowerShell in the frontend directory
cd "C:\xampp\htdocs\Ecommerce-app\Ecommerce Product App"

# Install dependencies (first time only)
npm install

# Start development server on port 8888
npx ng serve --port 8888 --host 0.0.0.0
```

Frontend is now running at: `http://localhost:8888`

### Step 4: Access the Application

| Feature | URL |
|---------|-----|
| Home / Login | `http://localhost:8888/login` |
| Register | `http://localhost:8888/register` |
| Software Packages | `http://localhost:8888/packages` |
| Add Package (Admin) | `http://localhost:8888/packages/new` |
| View Package | `http://localhost:8888/packages/:id` |
| Place Order | `http://localhost:8888/packages/:id/order` |
| My Orders | `http://localhost:8888/my-orders` |

**Test Accounts:**

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| emilys | emilyspass | USER |

---

## 📱 Mobile Device Access (Same Wi-Fi)

Both servers bind to `0.0.0.0`, so they're accessible from any device on the same local network.

### Find Your Computer's IP Address

**Windows:**
```powershell
ipconfig
```
Look for `IPv4 Address` under your Wi-Fi adapter (e.g., `192.168.150.237`).

**Mac/Linux:**
```bash
ifconfig
```

### Access from Mobile Phone

On your mobile device (connected to the same Wi-Fi), open:

- **App:** `http://192.168.150.237:8888`
- **API:** `http://192.168.150.237:8080/api/packages`

The frontend dynamically uses `window.location.hostname:8080` for API calls, so it works from any device without code changes.

---

## 🌐 Public Hosting with ngrok (Option 1)

To make the application accessible from anywhere (not just local Wi-Fi), use **ngrok** to create secure public tunnels.

### Prerequisites

1. Download ngrok: https://ngrok.com/download
2. Create a free account: https://dashboard.ngrok.com/signup
3. Get your authtoken from the dashboard

### Configure ngrok

```powershell
# Authenticate ngrok (one-time setup)
ngrok config add-authtoken YOUR_AUTHTOKEN_HERE
```

### Step 1: Start Both Servers Locally

**Terminal 1 - Backend:**
```powershell
cd C:\xampp\htdocs\Ecommerce-app\backend
& "C:\Java\apache-maven-3.9.4\apache-maven-3.9.4\bin\mvn.cmd" spring-boot:run
```

**Terminal 2 - Frontend:**
```powershell
cd "C:\xampp\htdocs\Ecommerce-app\Ecommerce Product App"
npx ng serve --port 8888 --host 0.0.0.0
```

### Step 2: Create ngrok Tunnels

**Terminal 3 - Backend Tunnel:**
```powershell
ngrok http 8080
```
Output will show: `Forwarding https://xxxxx.ngrok-free.app -> http://localhost:8080`

**Terminal 4 - Frontend Tunnel:**
```powershell
ngrok http 8888
```
Output will show: `Forwarding https://yyyyy.ngrok-free.app -> http://localhost:8888`

### Step 3: Access from Any Device

Open the frontend ngrok URL in any browser or mobile device:

```
https://yyyyy.ngrok-free.app
```

The app will automatically call the backend at `https://xxxxx.ngrok-free.app/api/...` because the services use `window.location.hostname` dynamically.

> **⚠️ Important:** Free ngrok tunnels change URLs on every restart. You must restart both ngrok tunnels if you close and reopen them.

---

## 🔐 Security & Access Control

### Endpoint Protection

| Endpoint | Method | Access |
|----------|--------|--------|
| `/api/auth/login` | POST | Public |
| `/api/auth/register` | POST | Public |
| `/api/packages` | GET | Public |
| `/api/packages/{id}` | GET | Public |
| `/api/packages/categories` | GET | Public |
| `/api/uploads/{fileName}` | GET | Public |
| `/api/packages` | POST | ADMIN only |
| `/api/packages/{id}` | PUT | ADMIN only |
| `/api/packages/{id}` | PATCH | ADMIN only |
| `/api/packages/{id}` | DELETE | ADMIN only |
| `/api/uploads/product-image` | POST | ADMIN only |
| `/api/orders` | POST | Authenticated users |
| `/api/orders/me` | GET | Authenticated users |

### Test Credentials

| Username | Password | Role | Can Add Packages? | Can Delete Packages? | Can Upload Images? |
|----------|----------|------|-------------------|----------------------|-------------------|
| admin | admin123 | ADMIN | ✅ Yes | ✅ Yes | ✅ Yes |
| emilys | emilyspass | USER | ❌ No | ❌ No | ❌ No |

---

## 🛠️ Key Features

### Frontend (Angular 14)
- ✅ User login & registration with JWT authentication
- ✅ Mobile-responsive UI with hamburger menu
- ✅ Product catalog with search, category filter, sort, pagination
- ✅ Square product images with object-contain (never cropped)
- ✅ Pink/purple gradient theme with hover effects
- ✅ Order placement with item description, contact info, address
- ✅ Order history (My Orders page)
- ✅ Admin-only: Add/Edit/Delete packages, upload product images

### Backend (Spring Boot 3.2.0)
- ✅ Spring Security with JWT authentication
- ✅ Role-based access control (ADMIN / USER)
- ✅ RESTful API for products, orders, authentication
- ✅ File upload for product images (admin only)
- ✅ Email confirmation on order placement (non-blocking)
- ✅ Stock decrement on order placement
- ✅ MySQL database with Hibernate JPA
- ✅ CORS configured for cross-origin access

### Mobile Responsiveness
- ✅ Hamburger menu on mobile (< 1024px)
- ✅ Responsive product grid (1 col mobile → 2 tablet → 3-4 desktop)
- ✅ Dynamic API base URL (`window.location.hostname`)
- ✅ Touch-friendly buttons and form inputs
- ✅ Viewport meta tag for proper mobile scaling

---

## 📦 Build Commands

### Frontend Production Build
```powershell
cd "C:\xampp\htdocs\Ecommerce-app\Ecommerce Product App"
npx ng build --configuration production
```
Output: `dist/Ecommerce-Product-App/browser/`

### Backend Production JAR
```powershell
cd C:\xampp\htdocs\Ecommerce-app\backend
& "C:\Java\apache-maven-3.9.4\apache-maven-3.9.4\bin\mvn.cmd" clean package
```
Output: `backend/target/backend-1.0.0.jar`

---

## 🔧 Troubleshooting

| Issue | Solution |
|-------|----------|
| Backend won't start | Check MySQL is running on port 3308; verify `application.properties` credentials |
| Port 8080 already in use | Another backend instance is running; stop it first |
| Port 8888 already in use | Another frontend instance is running; stop it first |
| CORS error | Backend CORS allows `http://*` and `https://*`; restart backend |
| Images not showing | Verify `backend/uploads/packages/` directory exists with image files |
| Order fails with 403 | User must be logged in; order endpoints require authentication |
| Mobile can't connect | Ensure both devices are on the same Wi-Fi; use the computer's local IP |

---

## 📄 License

MIT License

---

## 👥 Contributors

- **Kilo** - Software Engineer (Full-stack implementation)

---

*Last updated: October 24, 2026*
