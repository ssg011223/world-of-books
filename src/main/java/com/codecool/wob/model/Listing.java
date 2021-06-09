package com.codecool.wob.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Listing {
    private UUID id;
    private String title;
    private String description;
    @JsonProperty(value = "inventory_item_location_id")
    private UUID inventoryItemLocationId;
    @JsonProperty(value = "listing_price")
    private int price;
    private String currency;
    private int quantity;
    @JsonProperty(value = "listing_status")
    private int status;
    private int marketplace;
    @JsonProperty(value = "upload_time")
    private LocalDate uploadTime;
    @JsonProperty(value = "owner_email_address")
    private String ownerEmailAddress;
}
