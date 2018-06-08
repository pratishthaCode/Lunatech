package com.lunatech.airports.web;

import com.lunatech.airports.dao.CountryDaoService;
import com.lunatech.airports.dao.RunwayDaoService;
import com.lunatech.airports.model.Country;
import com.lunatech.airports.model.Runway;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class ReportController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ToString
    public static class CountryRunwayStats {
        public final Country country;
        public Set<String> runways = new HashSet<>();
        public Set<String> runwayIdentifications = new HashSet<>();

        CountryRunwayStats(Country country) {
            this.country = country;
        }
    }

    @Autowired
    private CountryDaoService countryService;

    @Autowired
    private RunwayDaoService runwayService;

    @RequestMapping("/report")
    public String report(Model model) {
        /*
            Retrieve top ten countries with highest number of airports
         */
        List<Country> countriesWithHighestNumberAirports = countryService.topTenCountriesWithMostNumberOfAirports();

        /*
            Retrieve top ten countries with lowest number of airports
         */
        List<Country> countriesWithLowestNumberAirports = countryService.topTenCountriesWithLeastNumberOfAirports();

        Map<String, List<CountryRunwayStats>> runwayStats = new HashMap<>();
        runwayStats.put("Highest Number Airports", getStatsFast(countriesWithHighestNumberAirports));
        runwayStats.put("Lowest Number Airports", getStatsFast(countriesWithLowestNumberAirports));

        model.addAttribute("results", runwayStats);
        return "report";
    }
    /*
            Retrieval of all runways and corresponding 10 top runway identification on the basis of Le_Ident
     */
    public List<CountryRunwayStats> getStatsFast(List<Country> countries) {
        Map<Long, List<Runway>> runways = runwaysForCountries(countries);
        return countries.stream().map(c -> {
            CountryRunwayStats stats = new CountryRunwayStats(c);
            List<Runway> countryRunways = runways.get(c.getId());
            stats.runways = runwaySurfaces(countryRunways);
            stats.runwayIdentifications = topTenIdentifications(countryRunways);
            return stats;
        }).collect(Collectors.toList());
    }

    /*
            Retrieval of runways corresponding to countries
     */
    public Map<Long, List<Runway>> runwaysForCountries(List<Country> countries) {
        Map<Long, List<Runway>> results = new HashMap<>();
        countries.forEach(c -> results.put(c.getId(), new ArrayList<>()));

        List<Runway> runways = runwayService.fetchRunwaysForCountries(countries);
        runways.forEach(r -> results.get(r.getCountryId()).add(r));

        return results;
    }

    /*
        Retrieval of runway surfaces for runways
    */
    private Set<String> runwaySurfaces(List<Runway> runways) {
        return runways.stream().map(r -> r.getSurface()).filter(r -> !r.isEmpty()).collect(Collectors.toSet());
    }
    /*
        Top 10 most common runway identification retrieval
    */
    private Set<String> topTenIdentifications(List<Runway> runways) {
        /*
            Group all runways wih identical on the basis of Le_ident
         */
        Map<String, Long> identificationCounts = runways.stream()
                                                                .map(r -> r.getLe_ident())
                                                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<Map.Entry<String, Long>> countedIden = new ArrayList<>(identificationCounts.entrySet());
        /*
        sorting on the basis of counts of identifications
         */
        countedIden.sort((x, y) -> (int) (x.getValue() - y.getValue()));
        return countedIden.stream().limit(10).map(Map.Entry::getKey).collect(Collectors.toSet());
    }


}
