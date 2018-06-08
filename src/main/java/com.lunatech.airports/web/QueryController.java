package com.lunatech.airports.web;

import com.lunatech.airports.dao.AirportDaoService;
import com.lunatech.airports.dao.CountryDaoService;
import com.lunatech.airports.dao.RunwayDaoService;
import com.lunatech.airports.model.Airport;
import com.lunatech.airports.model.Country;
import com.lunatech.airports.model.Runway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 *  Controller for handling Query on Country Code and Country Name
 */
@Controller
public class QueryController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CountryDaoService countryService;

    @Autowired
    private AirportDaoService airportService;

    @Autowired
    private RunwayDaoService runwayService;

    @RequestMapping("/query")
    public String query() {
        log.info("Query");
        return "query";
    }

    /**
     * Provide airport and runways for Country Name or Code
     * @param countryParam Country Name or Country Code
     * @return map of country and airports including runways information
     */
    @RequestMapping("/query/{countryParam}")
    public String queryCountry(@PathVariable String countryParam, Model model) {
        Optional<Country> country = countryService.findCountry(countryParam);
        if (country.isPresent()) {
            List<Airport> airports = airportService.findAirports(country.get());

            Map<Long, Airport> airportMap = new HashMap<>();
            airports.forEach(a -> {
                airportMap.put(a.getId(), a);
                a.setRunways(new ArrayList<>());
            });

            List<Runway> runways = runwayService.fetchRunwaysForAirports(airports);
            runways.forEach(r -> airportMap.get(r.getAirportId()).getRunways().add(r));
            model.addAttribute("country", country.get());
            model.addAttribute("airports", airports);

        } else {
            log.info("Invalid Country");
            model.addAttribute("error", "Invalid Country " + countryParam);
        }
        return "query";
    }

}
