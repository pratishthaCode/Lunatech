package com.lunatech.airports.dao;

import com.lunatech.airports.db.AirportRepo;
import com.lunatech.airports.db.CountryRepo;
import com.lunatech.airports.model.Country;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryDaoService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CountryRepo countryRepo;

    @Autowired
    private AirportRepo airportRepo;

    public List<String> fetchCountry(@NonNull String prefix) {
        return countryRepo.findByLowerCaseNameStartingWith(prefix.toLowerCase())
                .stream().map(c -> c.getName()).
                        collect(Collectors.toList());
    }

    public Optional<Country> findCountry(@NonNull String countryParam) {
        Country country = countryRepo.
                          findByLowerCaseName(countryParam.toLowerCase());

        if (country == null) {
            country = countryRepo.
                      findByLowerCaseCode(countryParam.toLowerCase());
        }
        if (country == null) {
            List<String> countrySuggestions = fetchCountry(countryParam);
            if (countrySuggestions.size() == 1) {
                country = countryRepo.findByLowerCaseName(countrySuggestions.
                                                            get(0).
                                                            toLowerCase());
            }
        }
        return Optional.ofNullable(country);
    }

    public List<Country> topCountriesWithMaxAirports() {
        List<Long> countryIds = airportRepo.
                                fetchCountriesMaxOfAirports().
                                stream().
                                limit(10).
                                collect(Collectors.toList());
        return countryRepo.findByIdIn(countryIds);
    }

    public List<Country> topCountriesWithMinAirports() {
        List<Long> countryIds = airportRepo.
                                fetchCountriesMinOfAirports().
                                stream().
                                limit(10).
                                collect(Collectors.toList());
        return countryRepo.findByIdIn(countryIds);
    }

}
