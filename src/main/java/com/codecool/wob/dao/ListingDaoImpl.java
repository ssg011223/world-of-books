package com.codecool.wob.dao;

import com.codecool.wob.model.Listing;
import com.codecool.wob.model.report.MonthlyReport;
import com.codecool.wob.util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListingDaoImpl implements ListingDao{
    @Override
    public void save(Iterable<Listing> listings) {
        Connection connection = JdbcConnection.getConnection();
        String sql = "INSERT INTO listing (id, title, description, inventory_item_location_id, listing_price, currency, quantity, listing_status, marketplace, upload_time, owner_email_address, saved_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                "ON CONFLICT (id) DO UPDATE " +
                "SET title = excluded.title," +
                "description = excluded.description," +
                "inventory_item_location_id = excluded.inventory_item_location_id," +
                "listing_price = excluded.listing_price," +
                "currency = excluded.currency," +
                "quantity = excluded.quantity," +
                "listing_status = excluded.listing_status," +
                "marketplace = excluded.marketplace," +
                "upload_time = excluded.upload_time," +
                "owner_email_address = excluded.owner_email_address," +
                "saved_at = excluded.saved_at";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            LocalDateTime now = LocalDateTime.now();

            for (Listing listing: listings) {
                ps.setObject(1, listing.getId());
                ps.setString(2, listing.getTitle());
                ps.setString(3, listing.getDescription());
                ps.setObject(4, listing.getInventoryItemLocationId());
                ps.setInt(5, listing.getPrice());
                ps.setString(6, listing.getCurrency());
                ps.setInt(7, listing.getQuantity());
                ps.setInt(8, listing.getStatus());
                ps.setInt(9, listing.getMarketplace());
                ps.setObject(10, listing.getUploadTime());
                ps.setString(11, listing.getOwnerEmailAddress());
                ps.setObject(12, now);

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Collection<Listing> findAll() {
        return null;
    }

    @Override
    public void deleteBefore(LocalDateTime localDateTime) {
        Connection connection = JdbcConnection.getConnection();
        String sql = "DELETE FROM listing WHERE saved_at < ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setObject(1, localDateTime);

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<MonthlyReport> getMonthlyReports() {
        Connection connection = JdbcConnection.getConnection();
        String sql = "SELECT month,\n" +
                "       sum(CASE WHEN marketplace_name = 'EBAY' THEN count END) AS total_ebay_listing_count,\n" +
                "       sum(CASE WHEN marketplace_name = 'EBAY' THEN total_listing_price END ) AS total_ebay_listing_price,\n" +
                "       sum(CASE WHEN marketplace_name = 'EBAY' THEN average_listing_price END) AS average_ebay_listing_price,\n" +
                "       sum(CASE WHEN marketplace_name = 'AMAZON' THEN count END) AS total_amazon_listing_count,\n" +
                "       sum(CASE WHEN marketplace_name = 'AMAZON' THEN total_listing_price END ) AS total_amazon_listing_price,\n" +
                "       sum(CASE WHEN marketplace_name = 'AMAZON' THEN average_listing_price END) AS average_amazon_listing_price\n" +
                "FROM\n" +
                "(SELECT date_trunc('month', upload_time) AS month,\n" +
                "       count(listing.id) as count,\n" +
                "       m.marketplace_name,\n" +
                "       avg(listing_price) as average_listing_price,\n" +
                "       sum(listing_price) as total_listing_price\n" +
                "FROM listing\n" +
                "JOIN marketplace m on m.id = listing.marketplace\n" +
                "WHERE upload_time IS NOT NULL\n" +
                "GROUP BY date_trunc('month', upload_time), m.marketplace_name\n" +
                "ORDER BY date_trunc('month', upload_time)) as lm\n" +
                "GROUP BY month";

        try {
            ResultSet rs = connection.createStatement().executeQuery(sql);
            List<MonthlyReport> reports = new ArrayList<>();
            LocalDate counter = null;

            while (rs.next()) {
                if (rs.getObject("month") == null) continue;
                LocalDate month = rs.getDate("month").toLocalDate();
                if (counter == null) counter = LocalDate.of(month.getYear(), month.getMonth(), month.getDayOfMonth());
                if (!counter.isEqual(month)) {
                    while (!counter.isEqual(month)) {
                        MonthlyReport emptyReport = new MonthlyReport();
                        emptyReport.setMonth(counter);
                        reports.add(emptyReport);
                        counter = counter.plusMonths(1);
                    }
                }
                reports.add(new MonthlyReport(month,
                        rs.getInt("total_ebay_listing_count"),
                        rs.getInt("total_ebay_listing_price"),
                        rs.getDouble("average_ebay_listing_price"),
                        rs.getInt("total_amazon_listing_count"),
                        rs.getInt("total_amazon_listing_price"),
                        rs.getDouble("average_amazon_listing_price")));
                counter = counter.plusMonths(1);
            }
            return reports;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }
}
