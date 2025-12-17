package com.globalexplorer.cityguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CityGuideApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CityGuideApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("🌍 Global Explorer - City Guide is Running!");
        System.out.println("🌐 Open: http://localhost:8080");
        System.out.println("==============================================\n");
    }
}
