package com.codecool.wob.dao;

import com.codecool.wob.model.Listing;
import com.codecool.wob.model.report.MonthlyReport;
import com.codecool.wob.model.report.Report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ListingDao extends Dao<Listing, UUID>{
    void deleteBefore(LocalDateTime localDateTime);
    List<MonthlyReport> getMonthlyReports();
    Report getTotalReportWithoutMonthlyReports();
}
