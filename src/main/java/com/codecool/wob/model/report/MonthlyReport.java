package com.codecool.wob.model.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReport {
    private LocalDate month;
    private int totalEbayListingCount;
    private int totalEbayListingPrice;
    private double averageEbayListingPrice;
    private int totalAmazonListingCount;
    private int totalAmazonListingPrice;
    private double averageAmazonListingPrice;
}
