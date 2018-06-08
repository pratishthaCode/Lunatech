package com.lunatech.airports.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Map;
import java.util.function.Function;

import static com.lunatech.airports.utils.ValueUtils.getLongValue;
import static com.lunatech.airports.utils.ValueUtils.getStringValue;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"airportId", "id"})
@ToString(exclude = "airport")
public class Runway {

    @Id
    @Column(name = "runwayId")
    private long id;

    @NonNull
    private Long airportId;

    // @NonNull
    // @ManyToOne(optional = false)
    // @JoinColumn(name = "airportId", nullable = false)
    @Transient
    private Airport airport;

    // To improve performance
    @NonNull
    private Long countryId;

    private String airportIdent;
    private String lengthFt;
    private String widthFt;
    private String surface;
    private String lighted;
    private String closed;

    private String le_ident;
    private String le_latitude_deg;
    private String le_longitude_deg;
    private String le_elevation_ft;
    private String le_heading_degT;
    private String le_displaced_threshold_ft;

    private String he_ident;
    private String he_latitude_deg;
    private String he_longitude_deg;
    private String he_elevation_ft;
    private String he_heading_degT;
    private String he_displaced_threshold_ft;

    public static Runway buildRunwayFromMap(Map<String, String> values, Function<Long, Long> fetchAirportCountry) {
        return builder()
                .id(getLongValue(values, "id"))
                .airportId(getLongValue(values, "airport_ref"))
                .countryId(fetchAirportCountry.apply(getLongValue(values, "airport_ref")))
                .airportIdent(getStringValue(values, "airport_ident"))
                .lengthFt(getStringValue(values, "length_ft"))
                .widthFt(getStringValue(values, "width_ft"))
                .surface(getStringValue(values, "surface"))
                .lighted(getStringValue(values, "lighted"))
                .closed(getStringValue(values, "closed"))
                .le_ident(getStringValue(values, "le_ident"))
                .le_latitude_deg(getStringValue(values, "le_latitude_deg"))
                .le_longitude_deg(getStringValue(values, "le_longitude_deg"))
                .le_elevation_ft(getStringValue(values, "le_elevation_ft"))
                .le_heading_degT(getStringValue(values, "le_heading_degT"))
                .le_displaced_threshold_ft(getStringValue(values, "le_displaced_threshold_ft"))
                .he_ident(getStringValue(values, "he_ident"))
                .he_latitude_deg(getStringValue(values, "he_latitude_deg"))
                .he_longitude_deg(getStringValue(values, "he_longitude_deg"))
                .he_elevation_ft(getStringValue(values, "he_elevation_ft"))
                .he_heading_degT(getStringValue(values, "he_heading_degT"))
                .he_displaced_threshold_ft(getStringValue(values, "he_displaced_threshold_ft"))
                .build();
    }

}
