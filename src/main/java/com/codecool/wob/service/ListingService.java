package com.codecool.wob.service;

import com.codecool.wob.dao.Dao;
import com.codecool.wob.model.Listing;
import com.codecool.wob.model.Location;
import com.codecool.wob.util.ApiRequester;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class ListingService {
    private Dao listingDao;

    public void saveData(Collection<Listing> listings) {
        listingDao.save(listings);
    }

    public Collection<Listing> getListingCollectionFromJsonArr(JSONArray arr) throws JsonProcessingException {
        Collection<Listing> res = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            if (!validate(obj)) continue;
            Listing newListing = objectMapper.readValue(obj.toString(), Listing.class);
            if (!obj.isNull("upload_time")){
                String uploadTime = obj.getString("upload_time");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y");
                LocalDate date = LocalDate.parse(uploadTime, formatter);
                newListing.setUploadTime(date);
            }
            res.add(newListing);
        }

        return res;
    }

    public JSONArray fetchListingJsonData() throws IOException {
        return ApiRequester.getJsonArrData(new URL("https://my.api.mockaroo.com/listing?key=63304c70"));
    }

    public void run() throws IOException {
        JSONArray arr = this.fetchListingJsonData();
        Collection<Listing> listings = this.getListingCollectionFromJsonArr(arr);
        this.saveData(listings);
    }

    // TODO: Implement validation
    public boolean validate(JSONObject obj) {
        boolean res = true;
        if (!isValidUUID(obj.getString("id"))) res = false;
        if (!isValidTitle(obj.getString("title"))) res = false;
        if (!isValidDescription(obj.getString("description"))) res = false;
        if (!isValidLocationId(obj.getString("location_id"))) res = false;
        if (!isValidListingPrice(obj.getDouble("listing_price"))) res = false;
        if (!isValidCurrency(obj.getString("currency"))) res = false;
        if (!isValidQuantity(obj.getInt("quantity"))) res = false;
        if (obj.isNull("listing_status") || obj.getInt("listing_status") < 1 || obj.getInt("listing_status") > 4 ) res = false;
        if (obj.isNull("marketplace")) res = false;
        if (!isValidOwnerEmailAddress(obj.getString("owner_email_address"))) res = false;
        return res;
    }

    private boolean isValidUUID(String str) {
        return str != null && str.matches("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");
    }

    private boolean isValidTitle(String str) {
        return str != null;
    }

    private boolean isValidDescription(String str) {
        return str != null;
    }

    private boolean isValidLocationId(String str) {
        return isValidUUID(str);
    }

    private boolean isValidListingPrice(Double d) {
        return d.toString().matches("^[0-9]+(\\.[0-9][0-9])$");
    }

    private boolean isValidCurrency(String str) {
        return str != null && str.length() == 3;
    }

    private boolean isValidQuantity(int i) {
        return i > 0;
    }

    private boolean isValidOwnerEmailAddress(String str) {
        return str != null && str.matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$");
    }

    private boolean isInteger(String str) {
        return str.matches("^[0-9]+$");
    }

}
