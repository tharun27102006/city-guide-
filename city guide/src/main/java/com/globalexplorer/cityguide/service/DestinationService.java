package com.globalexplorer.cityguide.service;

import com.globalexplorer.cityguide.model.Destination;
import com.globalexplorer.cityguide.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {
    
    @Autowired
    private DestinationRepository destinationRepository;
    
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }
    
    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }
    
    public List<Destination> getDestinationsByCountry(String country) {
        return destinationRepository.findByCountry(country);
    }
    
    public List<Destination> searchDestinations(String query) {
        return destinationRepository.searchDestinations(query);
    }
    
    public Destination saveDestination(Destination destination) {
        return destinationRepository.save(destination);
    }
    
    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }
}
