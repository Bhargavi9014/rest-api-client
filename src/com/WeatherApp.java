package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter city name: ");
        
        String city = sc.nextLine();

        String apiKey = "a9741bd9139129af8a5b0f1d4771d678";

        String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        try {
        	
            URL url = new URL(weatherApiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            String inputLine;
            
            StringBuilder responseContent = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            
            in.close();

            String response = responseContent.toString();
            
            //System.out.println(response);

            String weather = extractValue(response, "description\":\"", "\"");
            String temp = extractValue(response, "\"temp\":", ",");
            String humidity = extractValue(response, "\"humidity\":", ",");

            System.out.println("\n--- Weather Report ---");
            System.out.println("City: " + city);
            System.out.println("Temperature: " + temp + "°C");
            System.out.println("Weather: " + weather);
            System.out.println("Humidity: " + humidity + "%");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }


    private static String extractValue(String json, String keyStart, String keyEnd) {
    	
        int start = json.indexOf(keyStart);
        
        if (start == -1) {
            return "N/A";
        } else {
            start = start + keyStart.length();
            
            int end = json.indexOf(keyEnd, start);
            
            if (end == -1) {
                return "N/A";
            } else {
                String value = json.substring(start, end);
                return value;
            }
        }
    }

}
