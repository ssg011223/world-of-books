package com.codecool.wob.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private UUID id;
    @JsonProperty(value = "manager_name")
    private String managerName;
    private String phone;
    @JsonProperty(value = "address_primary")
    private String primaryAddress;
    @JsonProperty(value = "address_secondary")
    private String secondaryAddress;
    private String country;
    private String town;
    @JsonProperty(value = "postal_code")
    private String postalCode;
}
