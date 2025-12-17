package com.globalexplorer.cityguide.controller;

import com.globalexplorer.cityguide.model.Destination;
import com.globalexplorer.cityguide.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin(origins = "*")
public class DestinationController {
    
    @Autowired
    private DestinationService destinationService;
    
    @GetMapping
    public ResponseEntity<List<Destination>> getAllDestinations() {
        return ResponseEntity.ok(destinationService.getAllDestinations());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        return destinationService.getDestinationById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/country/{country}")
    public ResponseEntity<List<Destination>> getDestinationsByCountry(@PathVariable String country) {
        return ResponseEntity.ok(destinationService.getDestinationsByCountry(country));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Destination>> searchDestinations(@RequestParam String query) {
        return ResponseEntity.ok(destinationService.searchDestinations(query));
    }
    
    @PostMapping
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) {
        return ResponseEntity.ok(destinationService.saveDestination(destination));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody Destination destination) {
        destination.setId(id);
        return ResponseEntity.ok(destinationService.saveDestination(destination));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.ok().build();
    }
}
