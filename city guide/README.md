# Global Explorer - City Guide & Booking System

A professional web application for discovering and booking travel destinations worldwide.

## Features

- **Browse Destinations**: Explore 16+ popular tourist destinations across Europe, Asia, Americas, and India
- **Advanced Search**: Search destinations by name, country, or description
- **Filter by Region**: Filter destinations by continent/region
- **Secure Booking**: Complete booking system with multiple payment options
- **Database Persistence**: SQLite database for storing destinations and bookings
- **REST API**: RESTful APIs for all operations
- **Responsive Design**: Mobile-friendly UI

## Technology Stack

### Backend
- **Spring Boot 3.2.0** - Java framework
- **Spring Data JPA** - Database operations
- **SQLite** - Embedded database
- **Maven** - Build tool
- **Lombok** - Reduce boilerplate code

### Frontend
- **HTML5/CSS3** - Structure and styling
- **JavaScript (Vanilla)** - Client-side logic
- **Font Awesome** - Icons

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+** (or use Maven Wrapper)
- Modern web browser

## Project Structure

```
city-guide/
├── src/
│   ├── main/
│   │   ├── java/com/globalexplorer/cityguide/
│   │   │   ├── CityGuideApplication.java          # Main application
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java           # Database initialization
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java            # Home page controller
│   │   │   │   ├── DestinationController.java     # Destinations API
│   │   │   │   └── BookingController.java         # Bookings API
│   │   │   ├── model/
│   │   │   │   ├── Destination.java               # Destination entity
│   │   │   │   └── Booking.java                   # Booking entity
│   │   │   ├── repository/
│   │   │   │   ├── DestinationRepository.java     # Destination data access
│   │   │   │   └── BookingRepository.java         # Booking data access
│   │   │   └── service/
│   │   │       ├── DestinationService.java        # Destination business logic
│   │   │       └── BookingService.java            # Booking business logic
│   │   └── resources/
│   │       ├── application.properties             # Application configuration
│   │       └── static/
│   │           ├── index.html                     # Main page
│   │           ├── css/
│   │           │   └── style.css                  # Styles
│   │           └── js/
│   │               └── app.js                     # Client-side logic
├── pom.xml                                        # Maven configuration
└── README.md                                      # This file
```

## Getting Started

### 1. Clone or Navigate to Project

```bash
cd "c:\Users\tharu\OneDrive\desktop\city guide"
```

### 2. Build the Project

```bash
mvn clean install
```

Or using Maven Wrapper (if available):
```bash
./mvnw clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

Or using Maven Wrapper:
```bash
./mvnw spring-boot:run
```

### 4. Access the Application

Open your browser and navigate to:
```
http://localhost:8080
```

## API Endpoints

### Destinations

- `GET /api/destinations` - Get all destinations
- `GET /api/destinations/{id}` - Get destination by ID
- `GET /api/destinations/country/{country}` - Get destinations by country
- `GET /api/destinations/search?query={query}` - Search destinations
- `POST /api/destinations` - Create new destination (admin)
- `PUT /api/destinations/{id}` - Update destination (admin)
- `DELETE /api/destinations/{id}` - Delete destination (admin)

### Bookings

- `GET /api/bookings` - Get all bookings
- `GET /api/bookings/{id}` - Get booking by ID
- `GET /api/bookings/email/{email}` - Get bookings by email
- `POST /api/bookings` - Create new booking
- `PUT /api/bookings/{id}` - Update booking
- `DELETE /api/bookings/{id}/cancel` - Cancel booking

## Database

The application uses SQLite database (`cityguide.db`) which is automatically created in the project root on first run.

Initial data includes 16 destinations:
- **Europe**: Eiffel Tower, Colosseum, Sagrada Familia
- **Americas**: Statue of Liberty
- **Asia**: Mount Fuji, Grand Palace
- **India**: Taj Mahal, Golden Temple, Red Fort, Jaipur City Palace, Mysore Palace, Gateway of India, Hawa Mahal, Kerala Backwaters, Qutub Minar, Ajanta & Ellora Caves

## Configuration

Application configuration can be modified in `src/main/resources/application.properties`:

```properties
# Server port
server.port=8080

# Database
spring.datasource.url=jdbc:sqlite:cityguide.db

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Features Demonstration

### 1. Browse Destinations
- Visit homepage to see all destinations
- Each card shows destination name, country, rating, price, and description

### 2. Filter Destinations
- Use filter buttons to view destinations by region (Europe, Asia, Americas, India)

### 3. Search Functionality
- Use the search bar to find destinations by name, country, or keywords

### 4. Book a Trip
- Click "Book Now" on any destination
- Fill in your details (name, email, phone, travelers, travel date)
- Review booking summary
- Proceed to payment

### 5. Payment Processing
- Choose payment method (Card/UPI/Wallet)
- Enter payment details
- Confirm payment
- Receive booking confirmation

## Development

### Adding New Destinations

Use the API or add directly to `DataInitializer.java`:

```java
destinationRepository.save(createDestination(
    "Destination Name", 
    "Country", 
    "Description",
    price, 
    rating, 
    "Category", 
    "Highlights"
));
```

### Hot Reload

The project includes Spring Boot DevTools for automatic restart on code changes.

## Testing

### Manual Testing
1. Start the application
2. Test each feature:
   - Browse destinations
   - Search and filter
   - Create bookings
   - View booking confirmations

### API Testing with cURL

```bash
# Get all destinations
curl http://localhost:8080/api/destinations

# Search destinations
curl "http://localhost:8080/api/destinations/search?query=India"

# Create booking
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "destination": {"id": 1},
    "customerName": "John Doe",
    "email": "john@example.com",
    "phone": "1234567890",
    "travelers": 2,
    "travelDate": "2024-06-15",
    "totalPrice": 2598.00,
    "paymentMethod": "CARD"
  }'
```

## Troubleshooting

### Port Already in Use
If port 8080 is already in use, change the port in `application.properties`:
```properties
server.port=8081
```

### Database Issues
Delete `cityguide.db` file and restart the application to recreate the database.

### Build Errors
Ensure Java 17+ is installed:
```bash
java -version
```

## Future Enhancements

- User authentication and authorization
- Admin dashboard for managing destinations
- Email notifications for bookings
- Payment gateway integration
- Reviews and ratings system
- Image upload functionality
- Multi-language support

## License

This project is created for educational purposes.

## Support

For issues or questions, please create an issue in the project repository.

---

**Enjoy exploring the world with Global Explorer!** 🌍✈️
