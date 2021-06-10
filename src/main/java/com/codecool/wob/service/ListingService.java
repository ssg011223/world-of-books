package com.codecool.wob.service;

import com.codecool.wob.dao.Dao;
import com.codecool.wob.model.Listing;
import com.codecool.wob.util.ApiRequester;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class ListingService {
    private Dao<Listing> listingDao;

    public void saveData(Collection<Listing> listings) {
        listingDao.save(listings);
    }

    public Collection<Listing> getListingCollectionFromJsonArr(JSONArray arr) throws JsonProcessingException {
        Collection<Listing> res = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Listing newListing = objectMapper.readValue(obj.toString(), Listing.class);
            if (!obj.isNull("upload_time")){
                String uploadTime = obj.getString("upload_time");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y");
                LocalDate date = LocalDate.parse(uploadTime, formatter);
                newListing.setUploadTime(date);
            }
            if (validate(newListing)) res.add(newListing);
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
    public boolean validate(Listing listing) {
        return false;
    }

}
