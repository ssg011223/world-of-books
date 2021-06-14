package com.codecool.wob.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Listing {
    private UUID id;
    private String title;
    private String description;
    @JsonProperty(value = "location_id")
    private UUID inventoryItemLocationId;
    @JsonProperty(value = "listing_price")
    private double price;
    private String currency;
    private int quantity;
    @JsonProperty(value = "listing_status")
    private int status;
    private int marketplace;
    @JsonProperty(value = "upload_time", access = JsonProperty.Access.READ_ONLY)
    private LocalDate uploadTime;
    @JsonProperty(value = "owner_email_address")
    private String ownerEmailAddress;
    @JsonProperty(value = "saved_at")
    private LocalDate savedAt;
}
