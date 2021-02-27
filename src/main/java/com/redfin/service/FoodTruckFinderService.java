package com.redfin.service;

import com.redfin.client.SocrataClient;
import com.redfin.model.SocrataFoodTruckData;
import com.redfin.util.DateTimeConverter;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FoodTruckFinderService {

    private static final String PRINT_MESSAGE = "%s %s";

    public void printOpenFoodTrucks() {
        SocrataClient client = new SocrataClient();
        List<SocrataFoodTruckData> data =  client.getCurrentData();
        List<SocrataFoodTruckData> filteredData =  getCurrentlyOpenTrucks(data);
        printFoodTrucks(filteredData);
    }

    private List<SocrataFoodTruckData> getCurrentlyOpenTrucks(List<SocrataFoodTruckData> data) {
        return data.stream()
                .filter(this::isOpen)
                .sorted(Comparator.comparing(SocrataFoodTruckData::getName))
                .collect(Collectors.toList());
    }

    private boolean isOpen(SocrataFoodTruckData foodTruckData) {
        ZonedDateTime now = DateTimeConverter.nowAtZone();
        return now.isAfter(foodTruckData.getStartTime()) && now.isBefore(foodTruckData.getEndTime());
    }

    private void printFoodTrucks(List<SocrataFoodTruckData> data) {
        if (data.isEmpty()) {
            System.out.println("Currently no food trucks are open");
        } else {
            Scanner readPageInputs = new Scanner(System.in);
            System.out.println("Food Trucks Open On " + DateTimeConverter.dayOfWeek());
            int page = 1;
            System.out.println("======================== Page 1 ========================");
            int counter = 0;
            for (SocrataFoodTruckData foodTruck : data) {
                if (counter == 10) {
                    System.out.println("Type \"next\" for next 10 food trucks");
                    String next = readPageInputs.nextLine();
                    while (!"next".equalsIgnoreCase(next)) {
                        System.out.println("Please type \"next\" for next 10 food trucks");
                        next = readPageInputs.nextLine();
                    }
                    System.out.println(String.format("======================== Page %s ========================", ++page));
                    counter = 0;
                }
                String message = String.format(PRINT_MESSAGE, foodTruck.getName(), foodTruck.getAddress());
                System.out.println(message);
                counter++;
            }
        }
    }
}
