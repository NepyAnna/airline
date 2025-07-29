package com.sheoanna.airline.airport;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Service
public class AirportIataCodeService {
    private final Set<String> iataCodes = new HashSet<>();

    @PostConstruct
    public void init() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/airports.dat"), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 4) {
                    String iata = parts[4].replace("\"", "");
                    if (iata != null && !iata.isEmpty() && !iata.equals("\\N")) {
                        iataCodes.add(iata);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load airports.dat", e);
        }
    }

    public boolean exists(String iataCode) {
        return iataCodes.contains(iataCode);
    }
}
