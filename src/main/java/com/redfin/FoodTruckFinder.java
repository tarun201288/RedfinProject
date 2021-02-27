package com.redfin;

import com.redfin.service.FoodTruckFinderService;

/**
 * This is the starting of application.
 * Run ./gradlew run to start the application
 */
public class FoodTruckFinder {
    public static void main(String[] args) {
        FoodTruckFinderService foodTruckFinderService = new FoodTruckFinderService();
        foodTruckFinderService.printOpenFoodTrucks();
    }
}
