package com.codecool.wob.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Marketplace {
    private int id;
    @JsonProperty(value = "marketplace_name")
    private String name;
}
