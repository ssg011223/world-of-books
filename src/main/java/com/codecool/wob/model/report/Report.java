package com.codecool.wob.model.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private int totalListingCount;
    private int totalEbayListingCount;
    private int totalEbayListingPrice;
    private double averageEbayListingPrice;
    private int totalAmazonListingCount;
    private int totalAmazonListingPrice;
    private double averageAmazonListingPrice;
    private List<MonthlyReport> monthlyReports;
}
