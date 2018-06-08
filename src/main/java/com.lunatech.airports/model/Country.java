package com.lunatech.airports.model;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

import static com.lunatech.airports.utils.ValueUtils.getLongValue;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "code", "name"})
@ToString(exclude = {"airports", "lowerCaseName", "lowerCaseCode"})
public class Country {
    @Id
    @Column(name = "countryId")
    @NonNull
    private long id;

    @NonNull
    private String code;

    @NonNull
    private String name;

    private String lowerCaseName;

    private String lowerCaseCode;

    private String continent;

    private String wikipediaLink;

    private String keywords;

    @Singular
    // @OneToMany(cascade = ALL, mappedBy = "country")
    @Transient
    private List<Airport> airports;

    @PrePersist
    @PreUpdate
    private void populateLowerCaseNameAndCode() {
        lowerCaseName = name == null ? null : name.toLowerCase();
        lowerCaseCode = code == null ? null : code.toLowerCase();
    }

    public static Country buildCountryFromMap(Map<String, String> values) {
        return builder()
                .id(getLongValue(values, "id"))
                .code(values.get("code"))
                .name(values.get("name"))
                .continent(values.get("continent"))
                .wikipediaLink(values.get("wikipedia_link"))
                .keywords(values.get("keywords"))
                .build();
    }
}
