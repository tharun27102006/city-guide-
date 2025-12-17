// API Base URL
const API_BASE_URL = 'http://localhost:8080/api';

// State
let destinations = [];
let currentDestination = null;
let currentBooking = {};

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadDestinations();
    setupEventListeners();
    setMinDate();
});

// Load destinations from API
async function loadDestinations(filter = 'all') {
    const grid = document.getElementById('destinations-grid');
    grid.innerHTML = '<div class="loading"><i class="fas fa-spinner fa-spin"></i><p>Loading destinations...</p></div>';
    
    try {
        let url = `${API_BASE_URL}/destinations`;
        if (filter !== 'all') {
            url += `/country/${filter}`;
        }
        
        const response = await fetch(url);
        if (!response.ok) throw new Error('Failed to load destinations');
        
        destinations = await response.json();
        displayDestinations(destinations);
    } catch (error) {
        console.error('Error loading destinations:', error);
        grid.innerHTML = `
            <div class="loading">
                <i class="fas fa-exclamation-triangle"></i>
                <p>Error loading destinations. Please make sure the server is running.</p>
            </div>
        `;
    }
}

// Display destinations
function displayDestinations(destinationsToShow) {
    const grid = document.getElementById('destinations-grid');
    
    if (destinationsToShow.length === 0) {
        grid.innerHTML = '<div class="loading"><p>No destinations found.</p></div>';
        return;
    }
    
    grid.innerHTML = destinationsToShow.map(dest => `
        <div class="destination-card" onclick="openBookingModal(${dest.id})">
            <div class="card-image-container">
                <div class="card-badges">
                    <span class="category-badge">${dest.category || 'Featured'}</span>
                    <span class="price-badge">$${dest.price}</span>
                </div>
                <div class="destination-image" style="background: linear-gradient(135deg, ${getGradientColors(dest.name)}), url('${dest.imageUrl || ''}');">
                </div>
            </div>
            <div class="destination-content">
                <h3 class="destination-title">${dest.name}</h3>
                <p class="destination-location">${dest.country}</p>
                <div class="rating-stars">
                    ${generateStars(dest.rating)}
                    <span class="rating-number">${dest.rating}</span>
                </div>
                <p class="destination-description">${dest.description}</p>
                <button class="btn-book" onclick="event.stopPropagation(); openBookingModal(${dest.id})">
                    Book Now
                </button>
            </div>
        </div>
    `).join('');
}

// Generate star rating HTML
function generateStars(rating) {
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating % 1 >= 0.5;
    const emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);
    
    let stars = '';
    for (let i = 0; i < fullStars; i++) {
        stars += '<i class="fas fa-star"></i>';
    }
    if (hasHalfStar) {
        stars += '<i class="fas fa-star-half-alt"></i>';
    }
    for (let i = 0; i < emptyStars; i++) {
        stars += '<i class="far fa-star"></i>';
    }
    return stars;
}

// Get gradient colors based on destination name
function getGradientColors(name) {
    const gradients = {
        'Eiffel Tower': '#667eea 0%, #764ba2 100%',
        'Colosseum': '#f093fb 0%, #f5576c 100%',
        'Sagrada Familia': '#4facfe 0%, #00f2fe 100%',
        'Statue of Liberty': '#43e97b 0%, #38f9d7 100%',
        'Mount Fuji': '#fa709a 0%, #fee140 100%',
        'Grand Palace': '#30cfd0 0%, #330867 100%',
        'Taj Mahal': '#a8edea 0%, #fed6e3 100%',
        'Golden Temple': '#ffd89b 0%, #19547b 100%',
        'Red Fort': '#ff6e7f 0%, #bfe9ff 100%',
        'Jaipur City Palace': '#ffecd2 0%, #fcb69f 100%',
        'Mysore Palace': '#ff9a9e 0%, #fecfef 100%',
        'Gateway of India': '#a1c4fd 0%, #c2e9fb 100%',
        'Hawa Mahal': '#fbc2eb 0%, #a6c1ee 100%',
        'Kerala Backwaters': '#84fab0 0%, #8fd3f4 100%',
        'Qutub Minar': '#cfd9df 0%, #e2ebf0 100%',
        'Ajanta & Ellora Caves': '#a6c0fe 0%, #f68084 100%'
    };
    return gradients[name] || '#667eea 0%, #764ba2 100%';
}

// Setup event listeners
function setupEventListeners() {
    // Search functionality
    document.getElementById('search-btn').addEventListener('click', searchDestinations);
    document.getElementById('search-input').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') searchDestinations();
    });
    
    // Filter buttons
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            const filter = btn.getAttribute('data-filter');
            loadDestinations(filter);
        });
    });
    
    // Modal close buttons
    document.querySelectorAll('.close').forEach(closeBtn => {
        closeBtn.addEventListener('click', () => {
            closeBtn.closest('.modal').style.display = 'none';
        });
    });
    
    // Click outside modal to close
    window.addEventListener('click', (e) => {
        if (e.target.classList.contains('modal')) {
            e.target.style.display = 'none';
        }
    });
    
    // Booking form - Proceed to payment
    document.getElementById('proceed-payment').addEventListener('click', proceedToPayment);
    
    // Payment methods
    document.querySelectorAll('.payment-method').forEach(method => {
        method.addEventListener('click', () => {
            document.querySelectorAll('.payment-method').forEach(m => m.classList.remove('active'));
            method.classList.add('active');
            
            const paymentType = method.getAttribute('data-method');
            document.getElementById('card-payment').style.display = paymentType === 'card' ? 'block' : 'none';
            document.getElementById('upi-payment').style.display = paymentType === 'upi' ? 'block' : 'none';
            document.getElementById('wallet-payment').style.display = paymentType === 'wallet' ? 'block' : 'none';
        });
    });
    
    // Payment form submission
    document.getElementById('payment-form').addEventListener('submit', confirmPayment);
    
    // Confirmation close
    document.getElementById('close-confirmation').addEventListener('click', () => {
        document.getElementById('confirmation-modal').style.display = 'none';
        location.reload();
    });
    
    // Update booking summary when travelers change
    document.getElementById('travelers').addEventListener('input', updateBookingSummary);
}

// Search destinations
async function searchDestinations() {
    const query = document.getElementById('search-input').value.trim();
    if (!query) {
        loadDestinations();
        return;
    }
    
    const grid = document.getElementById('destinations-grid');
    grid.innerHTML = '<div class="loading"><i class="fas fa-spinner fa-spin"></i><p>Searching...</p></div>';
    
    try {
        const response = await fetch(`${API_BASE_URL}/destinations/search?query=${encodeURIComponent(query)}`);
        if (!response.ok) throw new Error('Search failed');
        
        const results = await response.json();
        displayDestinations(results);
    } catch (error) {
        console.error('Error searching:', error);
        grid.innerHTML = '<div class="loading"><p>Error performing search.</p></div>';
    }
}

// Open booking modal
async function openBookingModal(destinationId) {
    try {
        const response = await fetch(`${API_BASE_URL}/destinations/${destinationId}`);
        if (!response.ok) throw new Error('Failed to load destination details');
        
        currentDestination = await response.json();
        
        document.getElementById('booking-details').innerHTML = `
            <div style="background: var(--light-bg); padding: 1rem; border-radius: 8px; margin-bottom: 1.5rem;">
                <h3>${currentDestination.name}</h3>
                <p style="color: #666;"><i class="fas fa-map-marker-alt"></i> ${currentDestination.country}</p>
                <p style="margin-top: 0.5rem;"><strong>Price:</strong> $${currentDestination.price} per person</p>
            </div>
        `;
        
        // Reset form
        document.getElementById('booking-form').reset();
        document.getElementById('travelers').value = 1;
        updateBookingSummary();
        
        document.getElementById('booking-modal').style.display = 'block';
    } catch (error) {
        console.error('Error opening booking modal:', error);
        alert('Error loading destination details.');
    }
}

// Update booking summary
function updateBookingSummary() {
    const travelers = parseInt(document.getElementById('travelers').value) || 1;
    const pricePerPerson = currentDestination.price;
    const total = pricePerPerson * travelers;
    
    document.getElementById('booking-summary-details').innerHTML = `
        <p><strong>Destination:</strong> ${currentDestination.name}</p>
        <p><strong>Price per person:</strong> $${pricePerPerson}</p>
        <p><strong>Number of travelers:</strong> ${travelers}</p>
        <hr style="margin: 1rem 0;">
        <p style="font-size: 1.2rem;"><strong>Total Amount:</strong> <span style="color: var(--secondary-color);">$${total}</span></p>
    `;
    
    currentBooking.totalPrice = total;
}

// Proceed to payment
function proceedToPayment() {
    const form = document.getElementById('booking-form');
    
    // Validate form
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }
    
    // Collect booking data
    currentBooking = {
        destination: currentDestination,
        customerName: document.getElementById('name').value,
        email: document.getElementById('email').value,
        phone: document.getElementById('phone').value,
        travelers: parseInt(document.getElementById('travelers').value),
        travelDate: document.getElementById('travel-date').value,
        specialRequests: document.getElementById('special-requests').value,
        totalPrice: currentBooking.totalPrice
    };
    
    // Close booking modal and open payment modal
    document.getElementById('booking-modal').style.display = 'none';
    document.getElementById('payment-modal').style.display = 'block';
}

// Confirm payment
async function confirmPayment(e) {
    e.preventDefault();
    
    const paymentMethod = document.querySelector('.payment-method.active').getAttribute('data-method');
    
    // Prepare booking data for API
    const bookingData = {
        destination: { id: currentDestination.id },
        customerName: currentBooking.customerName,
        email: currentBooking.email,
        phone: currentBooking.phone,
        travelers: currentBooking.travelers,
        travelDate: currentBooking.travelDate,
        totalPrice: currentBooking.totalPrice,
        paymentMethod: paymentMethod.toUpperCase(),
        specialRequests: currentBooking.specialRequests
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/bookings`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(bookingData)
        });
        
        if (!response.ok) throw new Error('Booking failed');
        
        const booking = await response.json();
        
        // Show confirmation
        document.getElementById('payment-modal').style.display = 'none';
        showConfirmation(booking);
    } catch (error) {
        console.error('Error creating booking:', error);
        alert('Error processing your booking. Please try again.');
    }
}

// Show confirmation
function showConfirmation(booking) {
    document.getElementById('confirmation-details').innerHTML = `
        <div style="background: var(--light-bg); padding: 1.5rem; border-radius: 8px; margin: 1.5rem 0; text-align: left;">
            <p><strong>Booking ID:</strong> #${booking.id}</p>
            <p><strong>Destination:</strong> ${booking.destination.name}</p>
            <p><strong>Name:</strong> ${booking.customerName}</p>
            <p><strong>Email:</strong> ${booking.email}</p>
            <p><strong>Travelers:</strong> ${booking.travelers}</p>
            <p><strong>Travel Date:</strong> ${booking.travelDate}</p>
            <p><strong>Total Amount:</strong> $${booking.totalPrice}</p>
            <p><strong>Status:</strong> <span style="color: #27ae60;">${booking.status}</span></p>
        </div>
        <p style="margin-top: 1rem;">A confirmation email has been sent to ${booking.email}</p>
    `;
    
    document.getElementById('confirmation-modal').style.display = 'block';
}

// Set minimum date for travel date input
function setMinDate() {
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);
    const minDate = tomorrow.toISOString().split('T')[0];
    document.getElementById('travel-date').setAttribute('min', minDate);
}
