package com.lunatech.airports.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import static com.lunatech.airports.utils.ValueUtils.getLongValue;
import static com.lunatech.airports.utils.ValueUtils.getStringValue;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"countryId", "id", "name"})
@ToString(exclude = {"country", "runways"})
public class Airport {

    @Id
    @Column(name = "airportId")
    private long id;

    @NonNull
    private String ident;

    @NonNull
    private String name;

    private String type;
    private String latitudeDeg;
    private String longitudeDeg;
    private String elevationFt;
    private String continent;
    private String isoCountry;
    private String isoRegion;
    private String municipality;
    private String scheduledService;
    private String gpsCode;
    private String iataCode;
    private String localCode;
    private String homeLink;
    private String wikipediaLink;
    private String keywords;

    @NonNull
    private Long countryId;

    // @NonNull
    // @ManyToOne(optional = false)
    // @JoinColumn(name = "countryId", nullable = false)
    @Transient
    private Country country;

    @Singular
    // @OneToMany(cascade = ALL, mappedBy = "airport")
    @Transient
    private List<Runway> runways;


    public static Airport buildAirportFromMap(Map<String, String> values, Function<String, Long> fetchCountry) {
        return builder()
                .id(getLongValue(values, "id"))
                .ident(getStringValue(values, "ident"))
                .type(getStringValue(values, "type"))
                .name(getStringValue(values, "name"))
                .latitudeDeg(getStringValue(values, "latitude_deg"))
                .longitudeDeg(getStringValue(values, "longitude_deg"))
                .elevationFt(getStringValue(values, "elevation_ft"))
                .continent(getStringValue(values, "continent"))
                .isoCountry(getStringValue(values, "iso_country"))
                .countryId(fetchCountry.apply(getStringValue(values, "iso_country")))
                .isoRegion(getStringValue(values, "iso_region"))
                .municipality(getStringValue(values, "municipality"))
                .scheduledService(getStringValue(values, "scheduled_service"))
                .gpsCode(getStringValue(values, "gps_code"))
                .iataCode(getStringValue(values, "iata_code"))
                .localCode(getStringValue(values, "local_code"))
                .homeLink(getStringValue(values, "home_link"))
                .wikipediaLink(getStringValue(values, "wikipedia_link"))
                .keywords(getStringValue(values, "keywords"))
                .build();
    }

}
