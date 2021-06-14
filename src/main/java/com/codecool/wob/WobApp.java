package com.codecool.wob;

import com.codecool.wob.dao.ListingDaoImpl;
import com.codecool.wob.dao.LocationDaoImp;
import com.codecool.wob.dao.MarketplaceDaoImp;
import com.codecool.wob.dao.StatusDaoImp;
import com.codecool.wob.service.ListingService;
import com.codecool.wob.service.LocationService;
import com.codecool.wob.service.MarketplaceService;
import com.codecool.wob.service.StatusService;
import com.codecool.wob.util.JdbcConnection;
import com.codecool.wob.util.PropertyLoader;

import java.io.IOException;
import java.sql.SQLException;

public class WobApp {
    public static void main(String[] args) throws IOException, SQLException {
        // Init connection properties
        JdbcConnection.init(PropertyLoader.getDBUrl(), PropertyLoader.getDBUsername(), PropertyLoader.getDBPassword());

        ListingService listingService = new ListingService(new ListingDaoImpl(), new MarketplaceDaoImp(), new StatusDaoImp());
        MarketplaceService marketplaceService = new MarketplaceService(new MarketplaceDaoImp());
        StatusService statusService = new StatusService(new StatusDaoImp());
        LocationService locationService = new LocationService(new LocationDaoImp());

        marketplaceService.run();
        statusService.run();
        locationService.run();
        listingService.run();
        listingService.saveReportAsJsonFile(listingService.getFullReport());
    }
}
