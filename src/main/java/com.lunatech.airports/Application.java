package com.lunatech.airports;

import com.lunatech.airports.repo.AirportRepo;
import com.lunatech.airports.repo.CountryRepo;
import com.lunatech.airports.repo.RunwayRepo;
import com.lunatech.airports.model.Airport;
import com.lunatech.airports.model.Country;
import com.lunatech.airports.model.Runway;
import com.lunatech.airports.utils.UtilityToReadCSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@SpringBootApplication
public class Application {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    CountryRepo countryRepository;

    @Autowired
    AirportRepo airportRepository;

    @Autowired
    RunwayRepo runwayRepository;

    @Bean
    @Profile("!test")
    public CommandLineRunner bootstrap(ApplicationContext ctx) {
        return args -> {
            if (!countryRepository.findAll().iterator().hasNext()) {
                buildCountries();
                buildAirports();
                buildRunways();
            }
        };
    }
    void buildCountries() throws IOException {
        UtilityToReadCSV.readCsv("countries.csv").parallelStream().forEach(row -> {
            Country country = Country.
                    buildCountryFromMap(row);
            log.debug("Saving {}", country);
            countryRepository.save(country);
        });
    }

    void buildAirports() throws IOException {
        UtilityToReadCSV.readCsv("airports.csv").parallelStream().forEach(row -> {
            Airport airport = Airport.
                    buildAirportFromMap(row, (country) -> countryRepository.findByLowerCaseCode(country.toLowerCase()).getId());
            log.debug("Saving {}", airport);
            airportRepository.save(airport);
        });
    }

    void buildRunways() throws IOException {
        UtilityToReadCSV.readCsv("runways.csv").parallelStream().forEach(row -> {
            Runway runway = Runway.
                    buildRunwayFromMap(row, (airportId) -> airportRepository.findOne(airportId).getCountryId());
            log.debug("Saving {}", runway);
            runwayRepository.save(runway);
        });
    }

}
