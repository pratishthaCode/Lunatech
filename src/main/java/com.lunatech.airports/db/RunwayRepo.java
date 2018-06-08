package com.lunatech.airports.db;

import com.lunatech.airports.model.Runway;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RunwayRepo extends CrudRepository<Runway, Long> {

    List<Runway> findByAirportId(Long airportId);

    List<Runway> findByAirportIdIn(List<Long> airportIds);

    List<Runway> findByCountryId(Long countryId);

    List<Runway> findByCountryIdIn(List<Long> countryIds);
}
