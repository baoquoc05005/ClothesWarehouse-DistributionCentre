# Clothes Warehouse with Distribution Centre Integration

This project consists of two Spring Boot applications:
1. **Clothes Warehouse** - Main application with user management, item management, and admin features
2. **Distribution Centre Manager** - REST API for managing distribution centres and their inventory

## Project Structure

### Clothes Warehouse
- Spring Boot 3.1.0 application with Spring Security, Thymeleaf, and Bootstrap
- User roles: admin, warehouse employee, regular user
- Features: item management, user registration, role-based access control
- Integration with Distribution Centre Manager for requesting items

### Distribution Centre Manager
- Spring Boot 3.1.0 REST API with Basic Authentication
- Models: Distribution Centre, Item
- Features: manage distribution centres, items, and handle item requests
- Two Spring profiles: H2 (dev) and PostgreSQL (qa)

## How to Run

### Distribution Centre Manager (Run First)
```bash
cd DistributionCentreManager

# For dev profile (H2 database)
./mvnw spring-boot:run

# For qa profile (PostgreSQL with Docker)
docker-compose up -d
./mvnw spring-boot:run -Dspring.profiles.active=qa
```

### Clothes Warehouse
```bash
cd Assignment1
./mvnw spring-boot:run
```

## Access the Applications

### Clothes Warehouse
- URL: http://localhost:8080
- Admin credentials: username: admin, password: admin
- Employee credentials: username: employee, password: employee

### Distribution Centre Manager API
- URL: http://localhost:8081/api
- API credentials: username: api-user, password: api-password
- Endpoints:
  - GET /api/distribution-centres - List all distribution centres
  - GET /api/distribution-centres/closest - Find closest distribution centre with item
  - POST /api/items/request - Request an item from a distribution centre

## Testing the Integration

1. Log in to Clothes Warehouse as admin
2. Navigate to the Distribution Centres page
3. Use the request form to request an item:
   - Brand: BALENCIAGA, STONE_ISLAND, or DIOR
   - Item Name: T-Shirt or Running Shoes
   - Quantity: Any number less than available stock
4. Verify that:
   - The item is added to the warehouse inventory
   - The quantity is deducted from the distribution centre

## Spring Profiles in Distribution Centre Manager

### Dev Profile (Default)
- Uses H2 in-memory database
- Configuration in `application-dev.properties`
- H2 console available at /h2-console

### QA Profile
- Uses PostgreSQL database (requires Docker)
- Configuration in `application-qa.properties`
- Docker configuration in `docker-compose.yml`

## Technologies Used

- Spring Boot 3.1.0
- Spring Security
- Spring Data JPA
- Thymeleaf
- Bootstrap 5
- H2 Database
- PostgreSQL
- Docker
- Jackson for JSON processing
