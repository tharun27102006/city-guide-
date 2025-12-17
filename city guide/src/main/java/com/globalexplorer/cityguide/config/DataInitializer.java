package com.globalexplorer.cityguide.config;

import com.globalexplorer.cityguide.model.Destination;
import com.globalexplorer.cityguide.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private DestinationRepository destinationRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (destinationRepository.count() > 0) {
            System.out.println("✅ Database already populated with " + destinationRepository.count() + " destinations");
            return;
        }
        
        System.out.println("🔄 Initializing database with destinations...");
        
        // European Destinations
        destinationRepository.save(createDestination(
            "Eiffel Tower", "France", 
            "The iconic Eiffel Tower stands as a symbol of Paris and France. This iron lattice tower offers breathtaking views of the city from its observation decks.",
            1299.0, 4.9, "Europe", "Iconic landmark, City views, Fine dining"
        ));
        
        destinationRepository.save(createDestination(
            "Colosseum", "Italy",
            "Ancient Roman amphitheater and one of the greatest works of architecture and engineering in history.",
            1499.0, 4.8, "Europe", "Ancient history, Roman architecture, Gladiator arena"
        ));
        
        destinationRepository.save(createDestination(
            "Sagrada Familia", "Spain",
            "Gaudí's masterpiece basilica, a stunning example of modernist architecture still under construction.",
            1199.0, 4.7, "Europe", "Modernist architecture, Gaudí design, UNESCO site"
        ));
        
        // American Destinations
        destinationRepository.save(createDestination(
            "Statue of Liberty", "USA",
            "Symbol of freedom and democracy, standing tall in New York Harbor.",
            1899.0, 4.8, "Americas", "Historic monument, Harbor views, Immigration history"
        ));
        
        // Asian Destinations
        destinationRepository.save(createDestination(
            "Mount Fuji", "Japan",
            "Japan's highest and most iconic mountain, an active volcano revered in Japanese culture.",
            1799.0, 4.9, "Asia", "Sacred mountain, Volcanic peak, Cherry blossoms"
        ));
        
        destinationRepository.save(createDestination(
            "Grand Palace", "Thailand",
            "A complex of buildings at the heart of Bangkok, the former residence of the King of Siam.",
            999.0, 4.6, "Asia", "Royal palace, Thai architecture, Buddhist temples"
        ));
        
        // Indian Destinations
        destinationRepository.save(createDestination(
            "Taj Mahal", "India",
            "Iconic white marble mausoleum, a UNESCO World Heritage site and symbol of eternal love.",
            899.0, 5.0, "India", "Marble monument, Mughal architecture, UNESCO site"
        ));
        
        destinationRepository.save(createDestination(
            "Golden Temple", "India",
            "The holiest Gurdwara of Sikhism, known for its stunning golden architecture and spiritual significance.",
            799.0, 4.9, "India", "Sikh shrine, Golden architecture, Spiritual center"
        ));
        
        destinationRepository.save(createDestination(
            "Red Fort", "India",
            "Historic fortified palace in Delhi, a symbol of India's rich Mughal heritage.",
            699.0, 4.7, "India", "Mughal fort, Red sandstone, Independence site"
        ));
        
        destinationRepository.save(createDestination(
            "Jaipur City Palace", "India",
            "A stunning blend of Rajasthani and Mughal architecture in the heart of Pink City.",
            849.0, 4.8, "India", "Royal palace, Pink city, Rajput heritage"
        ));
        
        destinationRepository.save(createDestination(
            "Mysore Palace", "India",
            "Grand palace known for its Indo-Saracenic architecture and illuminated evening displays.",
            749.0, 4.8, "India", "Royal residence, Indo-Saracenic style, Light displays"
        ));
        
        destinationRepository.save(createDestination(
            "Gateway of India", "India",
            "Iconic arch monument in Mumbai, built during the British Raj, overlooking the Arabian Sea.",
            599.0, 4.6, "India", "Iconic arch, Mumbai landmark, Arabian Sea views"
        ));
        
        destinationRepository.save(createDestination(
            "Hawa Mahal", "India",
            "Palace of Winds in Jaipur, famous for its unique honeycomb facade with 953 windows.",
            649.0, 4.7, "India", "Palace of Winds, Pink sandstone, 953 windows"
        ));
        
        destinationRepository.save(createDestination(
            "Kerala Backwaters", "India",
            "Network of serene lagoons, lakes and canals offering unique houseboat experiences.",
            1099.0, 4.9, "India", "Houseboat cruise, Natural beauty, Tropical paradise"
        ));
        
        destinationRepository.save(createDestination(
            "Qutub Minar", "India",
            "73-meter tall victory tower in Delhi, a UNESCO World Heritage site and architectural marvel.",
            549.0, 4.6, "India", "Minaret tower, UNESCO site, Indo-Islamic architecture"
        ));
        
        destinationRepository.save(createDestination(
            "Ajanta & Ellora Caves", "India",
            "Ancient rock-cut caves featuring stunning Buddhist, Hindu and Jain monuments.",
            949.0, 4.8, "India", "Rock-cut caves, Ancient art, UNESCO sites"
        ));
        
        System.out.println("✅ Database initialized with " + destinationRepository.count() + " destinations");
    }
    
    private Destination createDestination(String name, String country, String description, 
                                         Double price, Double rating, String category, String highlights) {
        Destination dest = new Destination();
        dest.setName(name);
        dest.setCountry(country);
        dest.setDescription(description);
        dest.setPrice(price);
        dest.setRating(rating);
        dest.setCategory(category);
        dest.setHighlights(highlights);
        dest.setImageUrl("/static/images/" + name.toLowerCase().replace(" ", "-") + ".jpg");
        return dest;
    }
}
