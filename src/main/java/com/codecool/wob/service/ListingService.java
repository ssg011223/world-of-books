package com.codecool.wob.service;

import com.codecool.wob.dao.Dao;
import com.codecool.wob.model.Listing;
import com.codecool.wob.model.Marketplace;
import com.codecool.wob.util.ApiRequester;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class ListingService {
    private Dao listingDao;
    private Dao marketplaceDao;
    private final String LOG_FILE_NAME = "importLog.csv";

    public void saveData(Collection<Listing> listings) {
        listingDao.save(listings);
    }

    public Collection<Listing> getListingCollectionFromJsonArr(JSONArray arr) throws IOException {
        Collection<Listing> res = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<Marketplace> marketplaces = marketplaceDao.findAll();
        FileWriter fw = new FileWriter(LOG_FILE_NAME);
        PrintWriter pw = new PrintWriter(fw, true);

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            String invalidFieldName = validate(obj);
            if (!invalidFieldName.equals("")) {
                pw.print(obj.getString("id") + ",");
                pw.print(marketplaces.stream().filter(m -> m.getId() == obj.getInt("marketplace")).map(Marketplace::getName).findFirst().orElse("Unknown") + ",");
                pw.println(invalidFieldName);
                continue;
            }
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
    public String validate(JSONObject obj) {
        if (!isValidUUID(obj.getString("id"))) return "id";
        if (!isValidTitle(obj.getString("title"))) return "title";
        if (!isValidDescription(obj.getString("description"))) return "description";
        if (!isValidLocationId(obj.getString("location_id"))) return "location_id";
        if (!isValidListingPrice(obj.getDouble("listing_price"))) return "listing_price";
        if (!isValidCurrency(obj.getString("currency"))) return "currency";
        if (!isValidQuantity(obj.getInt("quantity"))) return "quantity";
        if (obj.isNull("listing_status") || obj.getInt("listing_status") < 1 || obj.getInt("listing_status") > 4 ) return "listing_status";
        if (obj.isNull("marketplace")) return "marketplace";
        if (!isValidOwnerEmailAddress(obj.getString("owner_email_address"))) return "owner_email_address";
        return "";
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
