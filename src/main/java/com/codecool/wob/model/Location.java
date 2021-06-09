package com.codecool.wob.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private UUID id;
    private String managerName;
    private String phone;
    private String primaryAddress;
    private String secondaryAddress;
    private String country;
    private String town;
    private String postalCode;
}
