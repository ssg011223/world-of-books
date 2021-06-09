package com.codecool.wob.dao;

import com.codecool.wob.model.Listing;
import com.codecool.wob.util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ListingDao implements Dao<Listing>{
    @Override
    public void save(Iterable<Listing> listings) {
        Connection connection = JdbcConnection.getConnection();
        String sql = "INSERT INTO listing (id, title, description, inventory_item_location_id, listing_price, currency, quantity, listing_status, marketplace, upload_time, owner_email_address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
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
                "owner_email_address = excluded.owner_email_address";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

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

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
