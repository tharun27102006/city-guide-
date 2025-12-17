package com.globalexplorer.cityguide.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;
    
    @Column(nullable = false)
    private String customerName;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private Integer travelers;
    
    @Column(nullable = false)
    private LocalDate travelDate;
    
    @Column(nullable = false)
    private Double totalPrice;
    
    @Column(nullable = false)
    private String paymentMethod;
    
    @Column(nullable = false)
    private String status; // PENDING, CONFIRMED, CANCELLED
    
    @Column(nullable = false)
    private LocalDateTime bookingDate;
    
    private String specialRequests;
    
    // Constructors
    public Booking() {}
    
    public Booking(Long id, Destination destination, String customerName, String email, String phone, 
                   Integer travelers, LocalDate travelDate, Double totalPrice, String paymentMethod, 
                   String status, LocalDateTime bookingDate, String specialRequests) {
        this.id = id;
        this.destination = destination;
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
        this.travelers = travelers;
        this.travelDate = travelDate;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.bookingDate = bookingDate;
        this.specialRequests = specialRequests;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Destination getDestination() { return destination; }
    public void setDestination(Destination destination) { this.destination = destination; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Integer getTravelers() { return travelers; }
    public void setTravelers(Integer travelers) { this.travelers = travelers; }
    
    public LocalDate getTravelDate() { return travelDate; }
    public void setTravelDate(LocalDate travelDate) { this.travelDate = travelDate; }
    
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }
    
    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }
}
